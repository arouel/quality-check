package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class PackageTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		new Package("");
	}

	@Test
	public void construct_name_isNotEmpty() {
		new Package("com.github.before");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		new Package(null);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_nameOnlyWithDot_isEmpty() {
		new Package(".");
	}

	@Test
	public void isUndefined() {
		assertTrue(Package.UNDEFINED.isUndefined());
		assertFalse(Package.JAVA_LANG.isUndefined());
		assertFalse(new Package("net.sf.qualitycheck").isUndefined());
	}

}
