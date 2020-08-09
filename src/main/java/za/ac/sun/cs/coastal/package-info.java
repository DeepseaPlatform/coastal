/*
 * This file is part of the COASTAL tool, https://deepseaplatform.github.io/coastal/
 *
 * Copyright (c) 2019-2020, Computer Science, Stellenbosch University.
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *  http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
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
