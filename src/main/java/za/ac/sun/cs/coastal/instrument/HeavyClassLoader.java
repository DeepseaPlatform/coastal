package za.ac.sun.cs.coastal.instrument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.diver.SymbolicState;
import za.ac.sun.cs.coastal.symbolic.State;

public class HeavyClassLoader extends ClassLoader {

	private static final String VM_NAME = "za.ac.sun.cs.coastal.symbolic.VM";

	private static final String SYMBOLIC_STATE_NAME = "za.ac.sun.cs.coastal.symbolic.SymbolicState";

	private static final String STATE_NAME = "za.ac.sun.cs.coastal.symbolic.State";

	private static final String COASTAL_EXCEPTION_PREFIX = "za.ac.sun.cs.coastal.symbolic.exceptions.";
	
	private final COASTAL coastal;

	private final Logger log;

	private final InstrumentationClassManager manager;

	private final ThreadLocal<SymbolicState> symbolicState = new ThreadLocal<>();

	public HeavyClassLoader(COASTAL coastal, InstrumentationClassManager manager, SymbolicState symbolicState) {
		this.coastal = coastal;
		this.log = coastal.getLog();
		this.manager = manager;
		this.symbolicState.set(symbolicState);
	}

	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		long t = System.currentTimeMillis();
		manager.startLoad();
		Class<?> clas = loadClass0(name, resolve);
		manager.endLoad(t);
		return clas;
	}

	public synchronized Class<?> loadClass0(String name, boolean resolve) throws ClassNotFoundException {
		log.trace("> ((symbolicState #{}))", Integer.toHexString(symbolicState.get().hashCode()));
		Class<?> clas = findLoadedClass(name);
		if (clas != null) {
			log.trace("> loading class {}, found in cache", name);
			return clas;
		}
		if (name.equals(SYMBOLIC_STATE_NAME)) {
			log.trace("> loading class {} from parent (1)", name);
			return symbolicState.get().getClass();
		} else if (name.equals(STATE_NAME)) {
			log.trace("> loading class {} from parent (2)", name);
			return State.class;
		} else if (name.startsWith(COASTAL_EXCEPTION_PREFIX)) {
			log.trace("> loading class {} from parent (3)", name);
			return super.loadClass(name, resolve);
		}
		if (name.startsWith("ins.") && coastal.isTarget(name.substring(4))) {
			String trueName = name.substring(4);
			log.trace("> loading class {}, identified as target", trueName);
			byte[] raw = manager.loadHeavyInstrumented(name, trueName);
			if (raw != null) {
				log.trace("> defining class {} instrumented as {}", trueName, name);
				clas = defineClass(name, raw, 0, raw.length);
			}
		} else if (coastal.isTarget(name)) {
			log.trace("> loading class {}, identified as target", name);
			byte[] raw = manager.loadHeavyInstrumented(this, name);
			if (raw != null) {
				log.trace("> defining class {} instrumented", name);
				clas = defineClass(name, raw, 0, raw.length);
			}
		}
		if (clas == null) {
			byte[] raw = manager.loadUninstrumented(name);
			if (raw != null) {
				log.trace("> loading class {}, uninstrumented (1)", name);
				clas = defineClass(name, raw, 0, raw.length);
			}
		}
		if (clas == null) {
			clas = findSystemClass(name);
			if (clas != null) {
				log.trace("> loading class {}, uninstrumented (2)", name);
			}
		}
		if (resolve && clas != null) {
			log.trace("> resolving class {}", name);
			resolveClass(clas);
		}
		if (clas == null) {
			log.trace("> class {} not found", name);
			throw new ClassNotFoundException(name);
		}
		if ((clas != null) && name.equals(VM_NAME)) {
			try {
				log.trace("> try to set symbolic state #{}", Integer.toHexString(symbolicState.get().hashCode()));
				Method st = clas.getDeclaredMethod("setState", State.class);
				st.invoke(null, symbolicState.get());
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return clas;
	}

}
