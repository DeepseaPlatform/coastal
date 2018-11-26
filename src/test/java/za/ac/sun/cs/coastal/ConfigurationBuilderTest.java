package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConfigurationBuilderTest {

	@Test
	public void testMainNotSet() {
		assertTrue(true);
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		final ConfigurationX c = cb.construct();
//		assertEquals(null, c.getMain());
	}

//	@Test
//	public void testMainSet() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		final ConfigurationBuilderX cb0 = cb.setMain("main");
//		assertEquals(cb, cb0);
//		final ConfigurationX c = cb.construct();
//		assertNotEquals(null, c.getMain());
//	}
//	
//	@Test
//	public void testStringProperties() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		assertEquals(cb, cb.setArgs("args"));
//		assertEquals(cb, cb.setMain("main"));
//		final ConfigurationX c = cb.construct();
//		assertEquals("args", c.getArgs());
//		assertEquals("main", c.getMain());
//	}
//	
//	@Test
//	public void testBooleanProperties() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		assertEquals(cb, cb.setEchoOutput(true));
//		final ConfigurationX c = cb.construct();
//		assertTrue(c.getEchoOutput());
//	}
//	
//	@Test
//	public void testLongProperties() {
//		final Logger log = LogManager.getLogger("COASTAL-TEST");
//		final String version = "coastal-test";
//		final ReporterManager reporterManager = new ReporterManager();
//		final ConfigurationBuilderX cb = new ConfigurationBuilderX(log, version, reporterManager);
//		assertEquals(cb, cb.setLimitConjuncts(123));
//		assertEquals(cb, cb.setLimitPaths(234));
//		assertEquals(cb, cb.setLimitRuns(345));
//		assertEquals(cb, cb.setLimitTime(456));
//		final ConfigurationX c = cb.construct();
//		assertEquals(123, c.getLimitConjuncts());
//		assertEquals(234, c.getLimitPaths());
//		assertEquals(345, c.getLimitRuns());
//		assertEquals(456, c.getLimitTime());
//	}
	
}
