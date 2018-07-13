package za.ac.sun.cs.coastal.instrument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.ClassWriter;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.reporting.Reporter;
import za.ac.sun.cs.coastal.reporting.Reporters;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;

public class InstrumentationClassLoader extends ClassLoader implements Reporter {

	private static final Logger lgr = Configuration.getLogger();

	private static final boolean dumpInstrumenter = Configuration.getDumpInstrumenter();

	private final List<String> classPaths = new ArrayList<>();

	private int requestCount = 0, cachedCount = 0, instrumentedCount = 0;
	
	private int preInstrumentedSize = 0, postInstrumentedSize = 0;

	private long ltime = 0, itime = 0;
	
	public InstrumentationClassLoader(String classPath) {
		Reporters.register(this);
		String paths[] = classPath.split(File.pathSeparator);
		for (String path : paths) {
			classPaths.add(path);
		}
		classPaths.add(".");
	}

	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		long t0 = System.currentTimeMillis();
		requestCount++;
		Class<?> clas = findLoadedClass(name);
		if (clas != null) {
			if (dumpInstrumenter) {
				lgr.trace("*** loading class {}, found in cache", name);
			}
			cachedCount++;
			ltime += System.currentTimeMillis() - t0;
			return clas;
		}
		if (Configuration.isTarget(name)) {
			if (dumpInstrumenter) {
				lgr.trace("*** loading class {}, identified as target", name);
			}
			long t1 = System.currentTimeMillis();
			byte[] raw = instrument(name);
			itime += System.currentTimeMillis() - t1;
			if (raw != null) {
				if (dumpInstrumenter) {
					lgr.trace("*** class {} instrumented", name);
				}
				instrumentedCount++;
				clas = defineClass(name, raw, 0, raw.length);
			}
		}
		if (clas == null) {
			if (dumpInstrumenter) {
				lgr.trace("*** loading class {}, uninstrumented", name);
			}
			clas = findSystemClass(name);
		}
		if (resolve && clas != null) {
			if (dumpInstrumenter) {
				lgr.trace("*** resolving class {}", name);
			}
			resolveClass(clas);
		}
		if (clas == null) {
			if (dumpInstrumenter) {
				lgr.trace("*** class {} not found", name);
			}
			ltime += System.currentTimeMillis() - t0;
			throw new ClassNotFoundException(name);
		}
		ltime += System.currentTimeMillis() - t0;
		return clas;
	}

	private byte[] instrument(String name) {
		byte[] in = loadFile(name.replace('.', '/').concat(".class"));
		if (in == null) {
			return null;
		}
		ClassReader cr = new ClassReader(in);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
		InstrumentationAdapter ia = new InstrumentationAdapter(name, cw); 
		cr.accept(ia, 0);
		byte[] out = cw.toByteArray();
		preInstrumentedSize += in.length;
		postInstrumentedSize += out.length;
		if (dumpInstrumenter) {
			lgr.trace("*** instrumented {}: {} -> {} bytes", name, in.length, out.length);
		}
		return out;
	}

	private byte[] loadFile(String filename) {
		File file = searchFor(filename);
		if (file != null) {
			byte[] data = new byte[(int) file.length()];
			try (FileInputStream in = new FileInputStream(file)) {
				in.read(data);
				return data;
			} catch (IOException x) {
				// ignore
			}
		}
		return null;
	}

	private File searchFor(String filename) {
		for (String classPath : classPaths) {
			File file = new File(classPath, filename);
			if (file.exists() && !file.isDirectory()) {
				return file;
			}
		}
		return null;
	}

	// ======================================================================
	//
	// REPORTING
	//
	// ======================================================================

	@Override
	public String getName() {
		return "Instrumentation";
	}

	@Override
	public void report(PrintWriter out) {
		out.println("  Class load requests: " + requestCount);
		out.println("  Cache hits: " + cachedCount);
		out.println("  Classes instrumented: " + instrumentedCount);
		out.println("  Pre-instrumented size: " + preInstrumentedSize);
		out.println("  Post-instrumented size: " + postInstrumentedSize);
		out.println("  Load time: " + ltime);
		out.println("  Instrumentation time: " + itime);
	}

}
