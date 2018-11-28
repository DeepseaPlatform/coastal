package za.ac.sun.cs.coastal.instrument;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class InstrumentationClassLoader extends ClassLoader {

	private final COASTAL coastal;
	
	private final Logger log;

	private final InstrumentationClassManager manager;

	private final SymbolicState symbolicState;
	
	public InstrumentationClassLoader(COASTAL coastal, InstrumentationClassManager manager, SymbolicState symbolicState) {
		this.coastal = coastal;
		this.log = coastal.getLog();
		this.manager = manager;
		this.symbolicState = symbolicState;
	}

	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		log.trace("##====## LOADCLASS({}, {})", name, resolve);
		long t = System.currentTimeMillis();
		manager.startLoad();
		Class<?> clas = loadClass0(name, resolve);
		manager.endLoad(t);
		String aa = (clas == null) ? "??" : clas.getName();
		ClassLoader bb = (clas == null) ? null : clas.getClassLoader();
		log.trace("#----# LOADING CLASS {} WITH LOADER {}", aa, bb);
		return clas;
	}

	public Class<?> loadClass0(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> clas = findLoadedClass(name);
		if (clas != null) {
			log.trace("*** loading class {}, found in cache", name);
			return clas;
		}
		if (name.equals("za.ac.sun.cs.coastal.symbolic.SymbolicState")) {
			log.trace("*** loading class {} from parent", name);
			return symbolicState.getClass();
		}
		if (coastal.isTarget(name)) {
			log.trace("*** loading class {}, identified as target", name);
			byte[] raw = manager.loadIinstrumented(name);
			if (raw != null) {
				log.trace("*** class {} instrumented", name);
				clas = defineClass(name, raw, 0, raw.length);
			}
		}
		if (clas == null) {
			byte[] raw = manager.loadUninstrumented(name);
			if (raw != null) {
				log.trace("*** loading class {}, uninstrumented (1)", name);
				clas = defineClass(name, raw, 0, raw.length);
			}
		}
		if (clas == null) {
			clas = findSystemClass(name);
			if (clas != null) {
				log.trace("*** loading class {}, uninstrumented (2)", name);
			}
		}
		if (resolve && clas != null) {
			log.trace("*** resolving class {}", name);
			resolveClass(clas);
		}
		if (clas == null) {
			log.trace("*** class {} not found", name);
			throw new ClassNotFoundException(name);
		}
		if ((clas != null) && name.equals("za.ac.sun.cs.coastal.symbolic.SymbolicVM")) {
			try {
				Field ss = clas.getDeclaredField("symbolicState");
				ss.set(null, symbolicState);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return clas;
	}

}
