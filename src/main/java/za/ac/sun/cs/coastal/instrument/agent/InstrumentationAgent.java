/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.instrument.agent;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

import org.apache.logging.log4j.Logger;

import za.ac.sun.cs.coastal.COASTAL;

/**
 * Java agent to transform certain classes with instrumentation.
 */
public final class InstrumentationAgent implements ClassFileTransformer {

	protected static final InstrumentationAgent INSTANCE;

	static {
		INSTANCE = new InstrumentationAgent();
	}

	protected COASTAL coastal;

	/**
	 * The logger for this analysis run. This is not created but set by the outside
	 * world.
	 */
	protected Logger log;

	private InstrumentationAgent() {
	}

//	public static InstrumentationAgent getInstance() {
//		return instance;
//	}

	public static void premain(String agentArgs, Instrumentation instrumentation) {
		instrumentation.addTransformer(INSTANCE);
	}

	public static void activate(COASTAL coastal) {
		INSTANCE.coastal = coastal;
		INSTANCE.log = coastal.getLog();
		INSTANCE.log.info("agent activated");
	}

	/**
	 *
	 *
	 * @param loader
	 *                            defining loader of the class to be transformed,
	 *                            may be {@code null} if the bootstrap loader
	 * @param className
	 *                            name of the class in the internal form of fully
	 *                            qualified class and interface names
	 * @param classBeingRedefined
	 *                            this is triggered by a redefine or retransform,
	 *                            the class being redefined or retransformed; if
	 *                            this is a class load, {@code null}
	 * @param protectionDomain
	 *                            protection domain of the class being defined or
	 *                            redefined
	 * @param classfileBuffer
	 *                            input byte buffer in class file format - must not
	 *                            be modified
	 * @return well-formed class file buffer (the result of the transform), or
	 *         {@code null} if no transform is performed
	 * @throws IllegalClassFormatException
	 *                                     if the input does not represent a
	 *                                     well-formed class file
	 * @see java.lang.instrument.ClassFileTransformer#transform(java.lang.ClassLoader,
	 *      java.lang.String, java.lang.Class, java.security.ProtectionDomain,
	 *      byte[])
	 */
	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if (loader.getClass().getSimpleName().equals("HeavyClassLoader")) {
			log.info("(((className=={})))", className);
		}
		return null;
	}
}
