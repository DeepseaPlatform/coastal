package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

import za.ac.sun.cs.coastal.reporting.ReporterManager;

public class ConfigurationBuilderTest {

	@Test
	public void testMainNotSet() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		assertFalse(cb.isMainSet());
	}

	@Test
	public void testMainSet() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		ConfigurationBuilder cb0 = cb.setMain("main");
		assertEquals(cb, cb0);
		assertTrue(cb.isMainSet());
	}
	
	@Test
	public void testStringProperties() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		assertEquals(cb, cb.setArgs("args"));
		assertEquals(cb, cb.setMain("main"));
		Configuration c = cb.construct();
		assertEquals("args", c.getArgs());
		assertEquals("main", c.getMain());
	}
	
	@Test
	public void testBooleanProperties() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		assertEquals(cb, cb.setDumpAll(true));
		assertEquals(cb, cb.setDumpPaths(true));
		assertEquals(cb, cb.setEchoOutput(true));
		assertEquals(cb, cb.setObeyStops(true));
		Configuration c = cb.construct();
		assertTrue(c.getDumpAll());
		assertTrue(c.getDumpPaths());
		assertTrue(c.getEchoOutput());
		assertTrue(c.getObeyStops());
	}
	
	@Test
	public void testLongProperties() {
		final Logger log = LogManager.getLogger("COASTAL-TEST");
		final String version = "coastal-test";
		final ReporterManager reporterManager = new ReporterManager();
		ConfigurationBuilder cb = new ConfigurationBuilder(log, version, reporterManager);
		assertEquals(cb, cb.setLimitConjuncts(123));
		assertEquals(cb, cb.setLimitPaths(234));
		assertEquals(cb, cb.setLimitRuns(345));
		assertEquals(cb, cb.setLimitTime(456));
		Configuration c = cb.construct();
		assertEquals(123, c.getLimitConjuncts());
		assertEquals(234, c.getLimitPaths());
		assertEquals(345, c.getLimitRuns());
		assertEquals(456, c.getLimitTime());
	}
	
}
