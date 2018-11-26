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

import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;

public class InstrumentationClassManager {

	private final COASTAL coastal;
	
	private final Logger log;
	
	private final Broker broker;

	private final List<String> classPaths = new ArrayList<>();

	private int requestCount = 0, cachedCount = 0, instrumentedCount = 0;

	private long loadTime = 0, instrumentedTime = 0;

	private int preInstrumentedSize = 0, postInstrumentedSize = 0;

	private final Map<String, byte[]> cache = new HashMap<>();

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

	public ClassLoader createClassLoader() {
		return new InstrumentationClassLoader(coastal, this);
	}

	public void startLoad() {
		requestCount++;
	}
	
	public void endLoad(long time) {
		endLoad(time, false);
	}

	public void endLoad(long time, boolean cached) {
		loadTime += System.currentTimeMillis() - time;
		if (cached) {
			cachedCount++;
		}
	}

	public byte[] instrument(String name) {
		long t = System.currentTimeMillis();
		byte[] instrumented = cache.get(name);
		if (instrumented == null) {
			instrumented = instrument0(name);
			if (instrumented != null) {
				cache.put(name, instrumented);
				instrumentedCount++;
			}
		}
		instrumentedTime += System.currentTimeMillis() - t;
		return instrumented;
	}

	private byte[] instrument0(String name) {
		byte[] in = loadFile(name.replace('.', '/').concat(".class"));
		if (in == null) {
			return null;
		}
		ClassReader cr = new ClassReader(in);
		ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
		InstrumentationAdapter ia = new InstrumentationAdapter(coastal, name, cw);
		cr.accept(ia, 0);
		byte[] out = cw.toByteArray();
		preInstrumentedSize += in.length;
		postInstrumentedSize += out.length;
		log.trace("*** instrumented {}: {} -> {} bytes", name, in.length, out.length);
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

	public void report(Object object) {
		broker.publish("report", new Tuple("Instrumentation.requests-count", requestCount));
		broker.publish("report", new Tuple("Instrumentation.cached-count", cachedCount));
		broker.publish("report", new Tuple("Instrumentation.instrumented-count", instrumentedCount));
		broker.publish("report", new Tuple("Instrumentation.pre-instrumented-size", preInstrumentedSize));
		broker.publish("report", new Tuple("Instrumentation.post-instrumented-size", postInstrumentedSize));
		broker.publish("report", new Tuple("Instrumentation.load-time", loadTime));
		broker.publish("report", new Tuple("Instrumentation.instrumented-time", instrumentedTime));
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
