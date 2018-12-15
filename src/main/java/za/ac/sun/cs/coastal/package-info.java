/**
 * Main package for the COASTAL concolic execution framework. Includes some
 * helper classes, mainly for initialization and very high-level abstractions.
 * 
 * <p>
 * COASTAL can be used directly from the command line.  (Insert example.)
 * </p>
 * 
 * <p>
 * COASTAL can also be used programmatically.  Here is code to construct and execute an instance of COASTAL:
 * </p>
 * 
 * <pre>{@code
 * import org.apache.commons.configuration2.ImmutableConfiguration;
 * import org.apache.logging.log4j.LogManager;
 * import org.apache.logging.log4j.Logger;
 * import za.ac.sun.cs.coastal.COASTAL;
 * import za.ac.sun.cs.coastal.Reporter;
 * 
 * {
 *    final Logger log = LogManager.getLogger("MY-COASTAL");
 *    ImmutableConfiguration config = ConfigHelper.loadConfiguration(log, "basic.xml",
 *       "<coastal><target><trigger>mypackage.MyProgram.calculate(X: int)</trigger></target></coastal>");
 *    if (config == null) {
 *       System.out.println("Configuration error");
 *       System.exit(1);
 *    }
 *    final COASTAL coastal = new COASTAL(log, config);
 *    if (coastal == null) {
 *       System.out.println("COASTAL constructor error");
 *       System.exit(1);
 *    }
 *    coastal.start(false);
 *    final Reporter reporter = coastal.getReporter();
 *    System.out.println("Number of paths: " + reporter.getLong("PathTree.inserted-count"));
 * }
 * }</pre>
 */
package za.ac.sun.cs.coastal;
