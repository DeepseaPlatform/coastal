package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.junit.Test;

public class VersionTest {

	/*
	 * First-level: pass properties to Version 
	 */

	@Test
	public void testProps123() {
		Properties props = new Properties();
		props.setProperty("version", "1.2.3");
		assertEquals("1.2.3", Version.read(props, false));
	}

	@Test
	public void testPropsMacro() {
		Properties props = new Properties();
		props.setProperty("version", "%VERSION%");
		assertEquals(Version.DEFAULT_VERSION, Version.read(props, false));
	}
	
	@Test
	public void testPropsNull() {
		Properties props = new Properties();
		assertEquals(Version.DEFAULT_VERSION, Version.read(props, false));
	}
	
	/*
	 * Second-level: pass an input stream to Version 
	 */

	@Test
	public void testInputStream234() {
		String versionString = "version = 2.3.4";
		InputStream stream = new ByteArrayInputStream(versionString.getBytes());
		assertEquals("2.3.4", Version.read(stream));
	}
	
	@Test
	public void testInputStreamMacro() {
		String versionString = "version = %VERSION%";
		InputStream stream = new ByteArrayInputStream(versionString.getBytes());
		assertEquals(Version.DEFAULT_VERSION, Version.read(stream, false));
	}
	
	@Test
	public void testInputStreamNull() {
		String versionString = "";
		InputStream stream = new ByteArrayInputStream(versionString.getBytes());
		assertEquals(Version.DEFAULT_VERSION, Version.read(stream, false));
	}
	
	/*
	 * Third-level: pass a resource name to Version 
	 */

	@Test
	public void testResourceName345() {
		assertEquals("3.4.5", Version.read("app0.properties", false));
	}
	
	@Test
	public void testResourceNameMacro() {
		assertEquals(Version.DEFAULT_VERSION, Version.read("app1.properties", false));
	}
	
	@Test
	public void testResourceNameNull() {
		assertEquals(Version.DEFAULT_VERSION, Version.read("app2.properties", false));
	}
	
	@Test
	public void testResourceNameBadFile() {
		assertEquals(Version.DEFAULT_VERSION, Version.read("appX.properties", false));
	}
	
}
