package za.ac.sun.cs.coastal;

import static org.junit.Assert.*;

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
	
	/*
	 * Second-level: pass an input stream to Version 
	 */

	@Test
	public void testInputStream234() {
		Version version = new Version();
		String versionString = "version = 2.3.4";
		InputStream stream = new ByteArrayInputStream(versionString.getBytes());
		assertEquals("2.3.4", version.read(stream));
	}
	
	@Test
	public void testInputStreamMacro() {
		Version version = new Version();
		String versionString = "version = %VERSION%";
		InputStream stream = new ByteArrayInputStream(versionString.getBytes());
		assertEquals(Version.DEFAULT_VERSION, version.read(stream));
	}
	
	@Test
	public void testInputStreamNull() {
		Version version = new Version();
		String versionString = "";
		InputStream stream = new ByteArrayInputStream(versionString.getBytes());
		assertEquals(Version.DEFAULT_VERSION, version.read(stream));
	}
	
	/*
	 * Third-level: pass a resource name to Version 
	 */

	@Test
	public void testResourceName345() {
		Version version = new Version();
		assertEquals("3.4.5", version.read("app0.properties"));
	}
	
	@Test
	public void testResourceNameMacro() {
		Version version = new Version();
		assertEquals(Version.DEFAULT_VERSION, version.read("app1.properties"));
	}
	
	@Test
	public void testResourceNameNull() {
		Version version = new Version();
		assertEquals(Version.DEFAULT_VERSION, version.read("app2.properties"));
	}
	
	@Test
	public void testResourceNameBadFile() {
		Version version = new Version();
		assertEquals(Version.DEFAULT_VERSION, version.read("appX.properties"));
	}
	
}
