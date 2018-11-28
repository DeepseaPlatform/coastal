package za.ac.sun.cs.coastal.instrument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class InstrumentationClassManager {

	private final COASTAL coastal;
	
	private final Logger log;
	
	private final Broker broker;

	private final List<String> classPaths = new ArrayList<>();

	private final AtomicLong requestCount = new AtomicLong(0);
	
	private final AtomicLong cacheHitCount = new AtomicLong(0);

	private final AtomicLong instrumentedCount = new AtomicLong(0);

	private final AtomicLong loadTime = new AtomicLong(0);
	
	private final AtomicLong instrumentedTime = new AtomicLong(0);

	private final AtomicLong uninstrumentedTime = new AtomicLong(0);
	
	private final AtomicLong preInstrumentedSize = new AtomicLong(0);

	private final AtomicLong postInstrumentedSize = new AtomicLong(0);

	private final Map<String, byte[]> cache = new HashMap<>();

	//private final Map<Long, SymbolicState> stateMap = new HashMap<>();

	public InstrumentationClassManager(COASTAL coastal, String classPath) {
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		String[] paths = classPath.split(File.pathSeparator);
		for (String path : paths) {
			classPaths.add(path);
		}
		classPaths.add(".");
	}

	public ClassLoader createClassLoader(SymbolicState symbolicState) {
		// long threadId = Thread.currentThread().getId();
		return new InstrumentationClassLoader(coastal, this, symbolicState);
	}

	public void startLoad() {
		requestCount.incrementAndGet();
	}
	
	public void endLoad(long time) {
		loadTime.addAndGet(System.currentTimeMillis() - time);
	}
	
	public byte[] loadUninstrumented(String name) {
		long t = System.currentTimeMillis();
		byte[] unInstrumented = cache.get(name);
		if (unInstrumented == null) {
			unInstrumented = loadUninstrumented0(name);
		} else {
			cacheHitCount.incrementAndGet();
		}
		uninstrumentedTime.addAndGet(System.currentTimeMillis() - t);
		return unInstrumented;
	}

	private synchronized byte[] loadUninstrumented0(String name) {
		byte[] unInstrumented = cache.get(name);
		if (unInstrumented == null) {
			unInstrumented = loadFile(name.replace('.', '/').concat(".class"));
			cache.put(name, unInstrumented);
		}
		return unInstrumented;
	}
	
	public byte[] loadIinstrumented(String name) {
		long t = System.currentTimeMillis();
		byte[] instrumented = cache.get(name);
		if (instrumented == null) {
			instrumented = loadIinstrumented0(name);
		} else {
			cacheHitCount.incrementAndGet();
		}
		instrumentedTime.addAndGet(System.currentTimeMillis() - t);
		return instrumented;
	}

	
	private synchronized byte[] loadIinstrumented0(String name) {
		byte[] instrumented = cache.get(name);
		if (instrumented == null) {
			byte[] in = loadFile(name.replace('.', '/').concat(".class"));
			if (in == null) {
				return null;
			}
			ClassReader cr = new ClassReader(in);
			ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
			InstrumentationAdapter ia = new InstrumentationAdapter(coastal, name, cw);
			cr.accept(ia, 0);
			instrumented = cw.toByteArray();
			preInstrumentedSize.addAndGet(in.length);
			postInstrumentedSize.addAndGet(instrumented.length);
			log.trace("*** instrumented {}: {} -> {} bytes", name, in.length, instrumented.length);
		}
		return instrumented;
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

	public void report(Object object) {
		broker.publish("report", new Tuple("Instrumentation.requests-count", requestCount.get()));
		broker.publish("report", new Tuple("Instrumentation.cache-hit-count", cacheHitCount.get()));
		broker.publish("report", new Tuple("Instrumentation.instrumented-count", instrumentedCount.get()));
		broker.publish("report", new Tuple("Instrumentation.pre-instrumented-size", preInstrumentedSize.get()));
		broker.publish("report", new Tuple("Instrumentation.post-instrumented-size", postInstrumentedSize.get()));
		broker.publish("report", new Tuple("Instrumentation.load-time", loadTime.get()));
		broker.publish("report", new Tuple("Instrumentation.instrumented-time", instrumentedTime.get()));
		broker.publish("report", new Tuple("Instrumentation.uninstrumented-time", uninstrumentedTime.get()));
	}

	private int instructionCounter = 0;
	private int methodCounter = 0;
	private Map<Integer, Integer> firstInstruction = new TreeMap<>();
	private Map<Integer, Integer> lastInstruction = new TreeMap<>();
	private Map<Integer, BitSet> branchInstructions = new TreeMap<>();
	
	public Integer getFirstInstruction(int methodNumber) {
		return firstInstruction.get(methodNumber);
	}

	public Integer getLastInstruction(int methodNumber) {
		return lastInstruction.get(methodNumber);
	}

	public BitSet getJumpPoints(int methodNumber) {
		return branchInstructions.get(methodNumber);
	}

	public int getInstructionCounter() {
		return instructionCounter;
	}

	public int getNextInstructionCounter() {
		return ++instructionCounter;
	}
	
	public int getMethodCounter() {
		return methodCounter;
	}
	
	public int getNextMethodCounter() {
		return ++methodCounter;
	}
	
	public void registerFirstInstruction() {
		firstInstruction.put(methodCounter, instructionCounter + 1);
	}
	
	public void registerLastInstruction() {
		lastInstruction.put(methodCounter, instructionCounter);
	}
	
}
