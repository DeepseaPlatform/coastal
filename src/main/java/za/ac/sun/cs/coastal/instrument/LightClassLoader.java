package za.ac.sun.cs.coastal.instrument;

import java.lang.reflect.Field;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;
import za.ac.sun.cs.coastal.surfer.TraceState;
import za.ac.sun.cs.coastal.symbolic.State;

public class LightClassLoader extends ClassLoader {

	private static final String VM_NAME = "za.ac.sun.cs.coastal.symbolic.VM";

	private static final String TRACE_STATE_NAME = "za.ac.sun.cs.coastal.symbolic.TraceState";
	
	private static final String STATE_NAME = "za.ac.sun.cs.coastal.symbolic.State";
	
	private static final String STATE_FIELD_NAME = "state";

	private final COASTAL coastal;
	
	private final Logger log;

	private final InstrumentationClassManager manager;

	private final TraceState traceState;
	
	public LightClassLoader(COASTAL coastal, InstrumentationClassManager manager, TraceState traceState) {
		this.coastal = coastal;
		this.log = coastal.getLog();
		this.manager = manager;
		this.traceState = traceState;
	}

	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		long t = System.currentTimeMillis();
		manager.startLoad();
		Class<?> clas = loadClass0(name, resolve);
		manager.endLoad(t);
		return clas;
	}

	public Class<?> loadClass0(String name, boolean resolve) throws ClassNotFoundException {
		Class<?> clas = findLoadedClass(name);
		if (clas != null) {
			log.trace("*** loading class {}, found in cache", name);
			return clas;
		}
		if (name.equals(TRACE_STATE_NAME)) {
			log.trace("*** loading class {} from parent", name);
			return traceState.getClass();
		} else if (name.equals(STATE_NAME)) {
			log.trace("*** loading class {} from parent", name);
			return State.class;
		}
		if (coastal.isTarget(name)) {
			log.trace("*** loading class {}, identified as target", name);
			byte[] raw = manager.loadLightInstrumented(name);
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
		if ((clas != null) && name.equals(VM_NAME)) {
			try {
				Field ss = clas.getDeclaredField(STATE_FIELD_NAME);
				ss.set(null, traceState);
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return clas;
	}

}
