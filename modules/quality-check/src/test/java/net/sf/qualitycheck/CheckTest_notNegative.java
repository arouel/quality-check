package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notNegative {

	@Test
	public void notNegative_int_checkReferenceIsSame() {
		final int prime = 7;
		Assert.assertSame(prime, Check.notNegative(7));
		Assert.assertSame(prime, Check.notNegative(7, "prime"));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_int_withReference_isInvalid() {
		Check.notNegative(-1);
	}

	@Test
	public void notNegative_int_withReference_isValid() {
		Check.notNegative(0);
		Check.notNegative(Integer.MAX_VALUE);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_int_withReference_withName_isInvalid() {
		Check.notNegative(Integer.MIN_VALUE, "min");
	}

	@Test
	public void notNegative_int_withReference_withName_isValid() {
		Check.notNegative(1, "one");
	}

	@Test
	public void notNegative_long_checkReferenceIsSame() {
		final long prime = 7L;
		Assert.assertSame(prime, Check.notNegative(7L));
		Assert.assertSame(prime, Check.notNegative(7L, "prime"));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_long_withReference_isInvalid() {
		Check.notNegative(-1L);
	}

	@Test
	public void notNegative_long_withReference_isValid() {
		Check.notNegative(0L);
		Check.notNegative(Long.MAX_VALUE);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_long_withReference_withName_isInvalid() {
		Check.notNegative(Long.MIN_VALUE, "min");
	}

	@Test
	public void notNegative_long_withReference_withName_isValid() {
		Check.notNegative(1L, "one");
	}

}
