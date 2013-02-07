package net.sf.qualitycheck.immutableobject.domain;

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

}
