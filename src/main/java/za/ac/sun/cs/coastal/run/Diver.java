package za.ac.sun.cs.coastal.run;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.Disposition;
import za.ac.sun.cs.coastal.reporting.Banner;

public class Diver implements Callable<Void> {

	private final Disposition disposition;

	private final Configuration configuration;

	private final Logger log;

	//	private final BlockingQueue<Model> models;
	//
	//	private final BlockingQueue<SegmentedPC> pcs;
	//
	//	private final InstrumentationClassManager classManager;
	//
	//	private final List<InstructionListener> instructionListeners;
	//
	//	private final List<MarkerListener> markerListeners;
	//
	//	private final List<PathListener> pathListeners;

	public Diver(Disposition disposition) {
		this.disposition = disposition;
		this.configuration = disposition.getConfiguration();
		this.log = configuration.getLog();
		//		this.models = models;
		//		this.pcs = pcs;
		//		this.classManager = classManager;
		//		this.instructionListeners = instructionListeners;
		//		this.markerListeners = markerListeners;
		//		this.pathListeners = pathListeners;
	}

	@Override
	public Void call() throws Exception {
		log.info("Diver thread starting");
		try {
			while (true) {
				log.info(Banner.getBannerLine("starting dive " + disposition.getNextDiveCount(), '-'));
				long t0 = System.currentTimeMillis();
				SymbolicState symbolicState = new SymbolicState(disposition);
				ClassLoader classLoader = disposition.createClassLoader(symbolicState);
				SymbolicVM.setState(symbolicState);
				performRun(classLoader);
				disposition.recordDiveTime(System.currentTimeMillis() - t0);
				disposition.notifyPathListeners(symbolicState);
				SegmentedPC spc = symbolicState.getSegmentedPathCondition();
				// log.info(Banner.getBannerLine("stopping dive, spc == " + spc, '-'));
				disposition.addPc(spc);
				// log.info(Banner.getBannerLine("after dive pcs.size() == " + pcs.size(), '-'));
				if (!symbolicState.mayContinue()) {
					disposition.stopWork();
				}
			}
		} catch (InterruptedException e) {
			log.info("diver canceled");
			throw e;
		}
	}

	private static final PrintStream NUL = new PrintStream(new OutputStream() {
		@Override
		public void write(int b) throws IOException {
			// do nothing
		}
	});

	private void performRun(ClassLoader classLoader) {
		PrintStream out = System.out, err = System.err;
		try {
			Class<?> clas = classLoader.loadClass(configuration.getMain());
			Method meth = clas.getMethod("main", String[].class);
			// Redirect System.out/System.err
			if (!configuration.getEchoOutput()) {
				System.setOut(NUL);
				System.setErr(NUL);
			}
			meth.invoke(null, new Object[] { new String[0] });
			System.setOut(out);
			System.setErr(err);
		} catch (ClassNotFoundException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (NoSuchMethodException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (SecurityException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (IllegalAccessException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (IllegalArgumentException x) {
			System.setOut(out);
			System.setErr(err);
			x.printStackTrace();
		} catch (InvocationTargetException x) {
			System.setOut(out);
			System.setErr(err);
			Throwable t = x.getCause();
			if ((t == null) || !(t instanceof LimitConjunctException)) {
				// x.printStackTrace();
				try {
					SymbolicVM.startCatch(-1);
				} catch (LimitConjunctException e) {
					// ignore, since run is over in any case
				}
			}
		}
	}

}
