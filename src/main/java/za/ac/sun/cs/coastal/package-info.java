/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019, Computer Science, Stellenbosch University.  All rights reserved.
 *
 * Licensed under GNU Lesser General Public License, version 3.
 * See LICENSE.md file in the project root for full license information.
 */
/**
 * Main package for the COASTAL concolic execution framework. Includes some
 * helper classes, mainly for initialization and very high-level abstractions.
 * 
 * {@after}
 * 
 * <p>
 * COASTAL can be used directly from the command line.  (Insert example.)
 * </p>
 * 
 * <p>
 * COASTAL can also be used programmatically.  Here is code to construct and execute an instance of COASTAL:
 * </p>
 * 
 * {@prejava
 *import org.apache.commons.configuration2.ImmutableConfiguration;
 *import org.apache.logging.log4j.LogManager;
 *import org.apache.logging.log4j.Logger;
 *import za.ac.sun.cs.coastal.COASTAL;
 *import za.ac.sun.cs.coastal.Reporter;
 *
 *{
 *   final Logger log = LogManager.getLogger("MY-COASTAL");
 *   ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, "basic.properties",
 *      "coastal.target.trigger = mypackage.MyProgram.calculate(X: int)");
 *   if (config == null) {
 *      System.out.println("Configuration error");
 *      System.exit(1);
 *   }
 *   final COASTAL coastal = new COASTAL(log, config);
 *   if (coastal == null) {
 *      System.out.println("COASTAL constructor error");
 *      System.exit(1);
 *   }
 *   coastal.start(false);
 *   final Reporter reporter = coastal.getReporter();
 *   System.out.println("Number of paths: " + reporter.getLong("PathTree.inserted-count"));
 *}
 * }
 */
package za.ac.sun.cs.coastal;
