package za.ac.sun.cs.coastal;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class VersionTest {

	@Test
	public void testVersion() {
		assertEquals("0.1.0", CompileInfo.VERSION);
	}

}
