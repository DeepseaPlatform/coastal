/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
package za.ac.sun.cs.coastal.instrument.agent;

import java.lang.instrument.Instrumentation;

/**
 * Java agent to transform certain classes with instrumentation. 
 */
public class InstrumentationAgent {

	public static void premain(String agentArgs, Instrumentation instrumentation) {
		System.out.println("AGENT!");
	}

}
