package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalPositiveArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notPositive {

	@Test
	public void notPositive_double_checkReferenceIsSame() {
		final double prime = -7.0;
		Assert.assertEquals(prime, Check.notPositive(-7.0), 0.0);
		Assert.assertEquals(prime, Check.notPositive(-7.0, "prime"), 0.0);
	}

	@Test
	public void notPositive_double_specialCases_valid() {
		// Double.MIN_VALUE is the smallest possible positive double value
		Check.notPositive(Double.NaN, "NaN");
		Check.notPositive(Double.NEGATIVE_INFINITY, "Negative Infinity");
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_double_withReference_isInvalid() {
		Check.notPositive(1.0);
	}

	@Test
	public void notPositive_double_withReference_isValid() {
		Check.notPositive(0.0);
		Check.notPositive(Double.NEGATIVE_INFINITY);
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_double_withReference_withName_isInvalid() {
		Check.notPositive(Double.POSITIVE_INFINITY, "Positive Infinity");
	}

	@Test
	public void notPositive_double_withReference_withName_isValid() {
		Check.notPositive(-1.0, "one");
	}

	@Test
	public void notPositive_float_checkReferenceIsSame() {
		final float prime = -7.0f;
		Assert.assertEquals(prime, Check.notPositive(-7.0f), 0.0f);
		Assert.assertEquals(prime, Check.notPositive(-7.0f, "prime"), 0.0f);
	}

	@Test
	public void notPositive_float_specialCases_valid() {
		// Float.MIN_VALUE is the smallest possible positive double value
		Check.notPositive(Float.NaN, "NaN");
		Check.notPositive(Float.NEGATIVE_INFINITY, "Negative Infinity");
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_float_withReference_isInvalid() {
		Check.notPositive(1.0f);
	}

	@Test
	public void notPositive_float_withReference_isValid() {
		Check.notPositive(0.0f);
		Check.notPositive(Float.NEGATIVE_INFINITY);
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_float_withReference_withName_isInvalid() {
		Check.notPositive(Float.POSITIVE_INFINITY, "Positive Infinity");
	}

	@Test
	public void notPositive_float_withReference_withName_isValid() {
		Check.notPositive(-1.0f, "one");
	}

	@Test
	public void notPositive_int_checkReferenceIsSame() {
		final int prime = -7;
		Assert.assertSame(prime, Check.notPositive(-7));
		Assert.assertSame(prime, Check.notPositive(-7, "prime"));
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_int_withReference_isInvalid() {
		Check.notPositive(1);
	}

	@Test
	public void notPositive_int_withReference_isValid() {
		Check.notPositive(0);
		Check.notPositive(Integer.MIN_VALUE);
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_int_withReference_withName_isInvalid() {
		Check.notPositive(Integer.MAX_VALUE, "max");
	}

	@Test
	public void notPositive_int_withReference_withName_isValid() {
		Check.notPositive(-1, "one");
	}

	@Test
	public void notPositive_long_checkReferenceIsSame() {
		final long prime = -7L;
		Assert.assertSame(prime, Check.notPositive(-7L));
		Assert.assertSame(prime, Check.notPositive(-7L, "prime"));
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_long_withReference_isInvalid() {
		Check.notPositive(1L);
	}

	@Test
	public void notPositive_long_withReference_isValid() {
		Check.notPositive(0L);
		Check.notPositive(Long.MIN_VALUE);
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_long_withReference_withName_isInvalid() {
		Check.notPositive(Long.MAX_VALUE, "max");
	}

	@Test
	public void notPositive_long_withReference_withName_isValid() {
		Check.notPositive(-1L, "one");
	}

	@Test
	public void notPositive_short_checkReferenceIsSame() {
		final short prime = (short) -7;
		Assert.assertSame(prime, Check.notPositive((short) -7));
		Assert.assertSame(prime, Check.notPositive((short) -7, "prime"));
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_short_withReference_isInvalid() {
		Check.notPositive((short) 1);
	}

	@Test
	public void notPositive_short_withReference_isValid() {
		Check.notPositive((short) 0);
		Check.notPositive(Short.MIN_VALUE);
	}

	@Test(expected = IllegalPositiveArgumentException.class)
	public void notPositive_short_withReference_withName_isInvalid() {
		Check.notPositive(Short.MAX_VALUE, "max");
	}

	@Test
	public void notPositive_short_withReference_withName_isValid() {
		Check.notPositive((short) -1, "one");
	}
}
