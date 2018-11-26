package za.ac.sun.cs.coastal.instrument;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;

public class InstrumentationClassLoader extends ClassLoader {

	private final COASTAL coastal;
	
	private final Logger log;

	private final InstrumentationClassManager manager;

	public InstrumentationClassLoader(COASTAL coastal, InstrumentationClassManager manager) {
		this.coastal = coastal;
		this.log = coastal.getLog();
		this.manager = manager;
	}

	public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
		long t = System.currentTimeMillis();
		manager.startLoad();
		Class<?> clas = findLoadedClass(name);
		if (clas != null) {
			log.trace("*** loading class {}, found in cache", name);
			manager.endLoad(t, true);
			return clas;
		}
		if (coastal.isTarget(name)) {
			log.trace("*** loading class {}, identified as target", name);
			byte[] raw = manager.instrument(name);
			if (raw != null) {
				log.trace("*** class {} instrumented", name);
				clas = defineClass(name, raw, 0, raw.length);
			}
		}
		if (clas == null) {
			log.trace("*** loading class {}, uninstrumented", name);
			clas = findSystemClass(name);
		}
		if (resolve && clas != null) {
			log.trace("*** resolving class {}", name);
			resolveClass(clas);
		}
		if (clas == null) {
			log.trace("*** class {} not found", name);
			manager.endLoad(t);
			throw new ClassNotFoundException(name);
		}
		manager.endLoad(t);
		return clas;
	}

}
