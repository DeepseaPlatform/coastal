package za.ac.sun.cs.coastal;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class VersionTest {

	@Test
	public void testProps123() {
		Version version = new Version();
		Properties props = new Properties();
		props.setProperty("version", "1.2.3");
		assertEquals("1.2.3", version.read(props));
	}

	@Test
	public void testPropsMacro() {
		Version version = new Version();
		Properties props = new Properties();
		props.setProperty("version", "%VERSION%");
		assertEquals(Version.DEFAULT_VERSION, version.read(props));
	}
	
	@Test
	public void testPropsNull() {
		Version version = new Version();
		Properties props = new Properties();
		assertEquals(Version.DEFAULT_VERSION, version.read(props));
	}
	
}
