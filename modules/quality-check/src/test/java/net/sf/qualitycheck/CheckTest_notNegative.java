package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notNegative {

	@Test
	public void notNegative_double_checkReferenceIsSame() {
		final double prime = 7.0;
		Assert.assertEquals(prime, Check.notNegative(7.0), 0.0);
		Assert.assertEquals(prime, Check.notNegative(7.0, "prime"), 0.0);
	}

	@Test
	public void notNegative_double_specialCases_valid() {
		// Double.MIN_VALUE is the smallest possible positive double value
		Check.notNegative(Double.MIN_VALUE, "min");
		Check.notNegative(Double.NaN, "NaN");
		Check.notNegative(Double.POSITIVE_INFINITY, "Positive Infinity");
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_double_withReference_isInvalid() {
		Check.notNegative(-1.0);
	}

	@Test
	public void notNegative_double_withReference_isValid() {
		Check.notNegative(0.0);
		Check.notNegative(Double.MAX_VALUE);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_double_withReference_withName_isInvalid() {
		Check.notNegative(Double.NEGATIVE_INFINITY, "Negative Infinity");
	}

	@Test
	public void notNegative_double_withReference_withName_isValid() {
		Check.notNegative(1.0, "one");
	}

	@Test
	public void notNegative_float_checkReferenceIsSame() {
		final float prime = 7.0f;
		Assert.assertEquals(prime, Check.notNegative(7.0f), 0.0f);
		Assert.assertEquals(prime, Check.notNegative(7.0f, "prime"), 0.0f);
	}

	@Test
	public void notNegative_float_specialCases_valid() {
		// Float.MIN_VALUE is the smallest possible positive double value
		Check.notNegative(Float.MIN_VALUE, "min");
		Check.notNegative(Float.NaN, "NaN");
		Check.notNegative(Float.POSITIVE_INFINITY, "Positive Infinity");
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_float_withReference_isInvalid() {
		Check.notNegative(-1.0f);
	}

	@Test
	public void notNegative_float_withReference_isValid() {
		Check.notNegative(0.0f);
		Check.notNegative(Float.MAX_VALUE);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_float_withReference_withName_isInvalid() {
		Check.notNegative(Float.NEGATIVE_INFINITY, "Negative Infinity");
	}

	@Test
	public void notNegative_float_withReference_withName_isValid() {
		Check.notNegative(1.0f, "one");
	}

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

	@Test
	public void notNegative_short_checkReferenceIsSame() {
		final short prime = (short) 7;
		Assert.assertSame(prime, Check.notNegative((short) 7));
		Assert.assertSame(prime, Check.notNegative((short) 7, "prime"));
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_short_withReference_isInvalid() {
		Check.notNegative((short) -1);
	}

	@Test
	public void notNegative_short_withReference_isValid() {
		Check.notNegative((short) 0);
		Check.notNegative(Short.MAX_VALUE);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void notNegative_short_withReference_withName_isInvalid() {
		Check.notNegative(Short.MIN_VALUE, "min");
	}

	@Test
	public void notNegative_short_withReference_withName_isValid() {
		Check.notNegative((short) 1, "one");
	}
}
