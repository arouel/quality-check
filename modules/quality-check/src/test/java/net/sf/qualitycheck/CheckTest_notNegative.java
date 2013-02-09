package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notNegative {

	@Test
	public void notNegative_checkReferenceIsSame() {
		final int prime = 7;
		Assert.assertSame(prime, Check.notNegative(7));
		Assert.assertSame(prime, Check.notNegative(7, "prime"));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_withReference_isInvalid() {
		Check.notNegative(-1);
	}

	@Test
	public void notNegative_withReference_isValid() {
		Check.notNegative(0);
		Check.notNegative(Integer.MAX_VALUE);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_withReference_withName_isInvalid() {
		Check.notNegative(Integer.MIN_VALUE, "min");
	}

	@Test
	public void notNegative_withReference_withName_isValid() {
		Check.notNegative(1, "one");
	}

}
