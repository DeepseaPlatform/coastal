package za.ac.sun.cs.coastal.instrument;

import java.io.PrintWriter;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.Configuration;
import za.ac.sun.cs.coastal.symbolic.SymbolicState;

public class InstrumentationClassLoader extends ClassLoader {

	private final Configuration configuration;

	private final Logger log;

	private final InstrumentationClassManager manager;
	
	private final SymbolicState symbolicState;
	
	InstrumentationClassLoader(Configuration configuration, InstrumentationClassManager manager, SymbolicState symbolicState) {
		this.configuration = configuration;
		this.log = configuration.getLog();
		this.manager = manager;
		this.symbolicState = symbolicState;
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
		if (configuration.isTarget(name)) {
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
