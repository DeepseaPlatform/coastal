package za.ac.sun.cs.coastal.instrument;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.logging.log4j.Logger;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.messages.Broker;
import za.ac.sun.cs.coastal.messages.Tuple;
import za.ac.sun.cs.coastal.surfer.TraceState;

public class InstrumentationClassManager {

	private final COASTAL coastal;

	private final Logger log;

	private final Broker broker;

	private final boolean showInstrumentation;

	private final String writeClassfile;

	private final List<String> classPaths = new ArrayList<>();

	private final Map<String, String> jars = new HashMap<>();

	private final AtomicLong requestCount = new AtomicLong(0);

	private final AtomicLong cacheHitCount = new AtomicLong(0);

	private final AtomicLong instrumentedCount = new AtomicLong(0);

	private final AtomicLong loadTime = new AtomicLong(0);

	private final AtomicLong instrumentedTime = new AtomicLong(0);

	private final AtomicLong uninstrumentedTime = new AtomicLong(0);

	private final AtomicLong preInstrumentedSize = new AtomicLong(0);

	private final AtomicLong postInstrumentedSize = new AtomicLong(0);

	private final Map<String, byte[]> clearCache = new HashMap<>();
	
	private final Map<String, byte[]> heavyCache = new HashMap<>();
	
	private final Map<String, byte[]> lightCache = new HashMap<>();

	public InstrumentationClassManager(COASTAL coastal, String classPath) {
		this.coastal = coastal;
		log = coastal.getLog();
		broker = coastal.getBroker();
		broker.subscribe("coastal-stop", this::report);
		showInstrumentation = coastal.getConfig().getBoolean("coastal.settings.show-instrumentation", false);
		writeClassfile = coastal.getConfig().getString("coastal.settings.write-classfile", null);
		String[] paths = classPath.split(File.pathSeparator);
		for (String path : paths) {
			classPaths.add(path);
		}
		classPaths.add(".");
		for (int i = 0; true; i++) {
			String key = "coastal.target.jar(" + i + ")";
			String jar = coastal.getConfig().getString(key);
			if (jar == null) {
				break;
			}
			String dir = coastal.getConfig().getString(key + "[@directory]");
			jars.put(jar, dir);
		}
	}

	public ClassLoader createHeavyClassLoader(SymbolicState symbolicState) {
		return new HeavyClassLoader(coastal, this, symbolicState);
	}

	public ClassLoader createLightClassLoader(TraceState traceState) {
		return new LightClassLoader(coastal, this, traceState);
	}

	public void startLoad() {
		requestCount.incrementAndGet();
	}

	public void endLoad(long time) {
		loadTime.addAndGet(System.currentTimeMillis() - time);
	}

	public byte[] loadUninstrumented(String name) {
		long t = System.currentTimeMillis();
		byte[] unInstrumented = clearCache.get(name);
		if (unInstrumented == null) {
			unInstrumented = loadUninstrumented0(name);
		} else {
			cacheHitCount.incrementAndGet();
		}
		uninstrumentedTime.addAndGet(System.currentTimeMillis() - t);
		return unInstrumented;
	}

	private synchronized byte[] loadUninstrumented0(String name) {
		byte[] unInstrumented = clearCache.get(name);
		if (unInstrumented == null) {
			unInstrumented = loadFile(name.replace('.', '/').concat(".class"), false, false);
			clearCache.put(name, unInstrumented);
		}
		return unInstrumented;
	}

	public byte[] loadHeavyInstrumented(String name) {
		long t = System.currentTimeMillis();
		byte[] instrumented = heavyCache.get(name);
		if (instrumented == null) {
			instrumented = loadHeavyInstrumented0(name);
			heavyCache.put(name, instrumented);
		} else {
			cacheHitCount.incrementAndGet();
		}
		instrumentedTime.addAndGet(System.currentTimeMillis() - t);
		return instrumented;
	}

	private synchronized byte[] loadHeavyInstrumented0(String name) {
		byte[] instrumented = heavyCache.get(name);
		if (instrumented == null) {
			byte[] in = loadFile(name.replace('.', '/').concat(".class"), true, true);
			if (in == null) {
				return null;
			}
			ClassReader cr = new ClassReader(in);
			ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
			HeavyAdapter ia = new HeavyAdapter(coastal, name, cw);
			cr.accept(ia, 0);
			instrumented = cw.toByteArray();
			instrumentedCount.incrementAndGet();
			preInstrumentedSize.addAndGet(in.length);
			postInstrumentedSize.addAndGet(instrumented.length);
			log.trace("*** instrumented {}: {} -> {} bytes", name, in.length, instrumented.length);
			if (writeClassfile != null) {
				writeFile(writeClassfile, instrumented);
			}
			if (showInstrumentation) {
				ia.showInstrumentation();
			}
		}
		return instrumented;
	}

	public byte[] loadLightInstrumented(String name) {
		long t = System.currentTimeMillis();
		byte[] instrumented = lightCache.get(name);
		if (instrumented == null) {
			instrumented = loadLightInstrumented0(name);
			lightCache.put(name, instrumented);
		} else {
			cacheHitCount.incrementAndGet();
		}
		instrumentedTime.addAndGet(System.currentTimeMillis() - t);
		return instrumented;
	}

	private synchronized byte[] loadLightInstrumented0(String name) {
		byte[] instrumented = lightCache.get(name);
		if (instrumented == null) {
			byte[] in = loadFile(name.replace('.', '/').concat(".class"), true, true);
			if (in == null) {
				return null;
			}
			ClassReader cr = new ClassReader(in);
			ClassWriter cw = new ClassWriter(cr, ClassWriter.COMPUTE_FRAMES);
			LightAdapter ia = new LightAdapter(coastal, name, cw);
			cr.accept(ia, 0);
			instrumented = cw.toByteArray();
			instrumentedCount.incrementAndGet();
			preInstrumentedSize.addAndGet(in.length);
			postInstrumentedSize.addAndGet(instrumented.length);
			log.trace("*** instrumented {}: {} -> {} bytes", name, in.length, instrumented.length);
			if (writeClassfile != null) {
				writeFile(writeClassfile, instrumented);
			}
			if (showInstrumentation) {
				ia.showInstrumentation();
			}
		}
		return instrumented;
	}

	public void writeFile(String filename, byte[] contents) {
		try (FileOutputStream fos = new FileOutputStream(filename)) {
			fos.write(contents);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private byte[] loadFile(String filename, boolean tryResource, boolean tryJar) {
		InputStream in = searchFor(filename, tryResource);
		if (in != null) {
			try {
				return IOUtils.toByteArray(in);
			} catch (IOException x) {
				// ignore
			}
		} else if (tryJar) {
			for (Map.Entry<String, String> entry : jars.entrySet()) {
				in = searchFor(entry.getKey(), tryResource);
				if (in == null) {
					continue;
				}
				try (ZipInputStream zin = new ZipInputStream(new BufferedInputStream(in))) {
					String fullFilename = entry.getValue();
					if (fullFilename != null) {
						if (fullFilename.endsWith("/")) {
							fullFilename += filename;
						} else {
							fullFilename += "/" + filename;
						}
					} else {
						fullFilename = filename;
					}
					ZipEntry ze = zin.getNextEntry();
					while (ze != null) {
						if (ze.getName().equals(fullFilename)) {
							ByteArrayOutputStream out = new ByteArrayOutputStream();
							byte[] buffer = new byte[8192];
							int len = 0;
							while ((len = zin.read(buffer, 0, 8192)) != -1) {
								out.write(buffer, 0, len);
							}
							zin.closeEntry();
							out.close();
							log.trace("]]] file {} found: {}::{}", filename, entry.getKey(), fullFilename);
							return out.toByteArray();
						}
						ze = zin.getNextEntry();
					}
				} catch (IOException x) {
					// ignore
				}
			}
		}
		return null;
	}

	private InputStream searchFor(String filename, boolean tryResource) {
		for (String classPath : classPaths) {
			File file = new File(classPath, filename);
			if (file.exists() && !file.isDirectory()) {
				try {
					log.trace("]]] file {} found: {}", filename, file.getAbsolutePath());
					FileInputStream in = new FileInputStream(file);
					if (in != null) {
						return new BufferedInputStream(in);
					}
				} catch (FileNotFoundException x) {
					// ignore
				}
			}
		}
		if (tryResource) {
			final ClassLoader loader = Thread.currentThread().getContextClassLoader();
			InputStream in = loader.getResourceAsStream(filename);
			if (in != null) {
				log.trace("]]] resource {} found", filename);
			}
			return in;
		} else {
			return null;
		}
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
	private int newVariableCounter = 0;
	private Map<Integer, Integer> firstInstruction = new TreeMap<>();
	private Map<Integer, Integer> lastInstruction = new TreeMap<>();
	private Map<Integer, BitSet> linenumbers = new TreeMap<>();
	private Map<Integer, BitSet> branchInstructions = new TreeMap<>();

	public Integer getFirstInstruction(int methodNumber) {
		return firstInstruction.get(methodNumber);
	}

	public Integer getLastInstruction(int methodNumber) {
		return lastInstruction.get(methodNumber);
	}

	public BitSet getLineNumbers(int methodNumber) {
		return linenumbers.get(methodNumber);
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
	
	public int getNewVariableCounter() {
		return newVariableCounter;
	}
	
	public int getNextNewVariableCounter() {
		return ++newVariableCounter;
	}
	
	public void registerFirstInstruction() {
		firstInstruction.put(methodCounter, instructionCounter + 1);
	}

	public void registerLastInstruction() {
		lastInstruction.put(methodCounter, instructionCounter);
	}

	public void registerLinenumbers(BitSet linenumbers) {
		this.linenumbers.put(methodCounter, linenumbers);
	}

}
