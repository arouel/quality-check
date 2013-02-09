package net.sf.qualitycheck.immutableobject.domain;

import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class AccessorPrefixTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(AccessorPrefix.class, areImmutable());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_isEmpty() {
		new AccessorPrefix("");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_isEmptyAfterTrim() {
		new AccessorPrefix(" ");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_isNull() {
		new AccessorPrefix(null);
	}

}
