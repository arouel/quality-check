package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNumericArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_isNumeric {

	@Test(expected = IllegalNumericArgumentException.class)
	public void isNumeric_decimalNumber_fail() {
		Check.isNumeric("1.23");
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void isNumeric_fail() {
		Check.isNumeric("Hallo Welt!");
	}

	@Test
	public void isNumeric_longNumericString_okay() {
		Assert.assertEquals("1230000000000000000000000000", Check.isNumeric("1230000000000000000000000000"));
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void isNumeric_negativeNumber_fail() {
		Check.isNumeric("-123");
	}

	@Test
	public void isNumeric_okay() {
		Assert.assertEquals("123", Check.isNumeric("123"));
	}

	@Test
	public void isNumeric_startsWithZero_okay() {
		Assert.assertEquals("0123", Check.isNumeric("0123"));
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void isNumeric_withArgument_decimalNumber_fail() {
		Check.isNumeric("1.23", "numeric");
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void isNumeric_withArgument_fail() {
		Check.isNumeric("Hallo Welt", "numeric");
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void isNumeric_withArgument_negativeNumber_fail() {
		Check.isNumeric("-123", "numeric");
	}

	@Test
	public void isNumeric_withArgument_okay() {
		Assert.assertEquals("123", Check.isNumeric("123", "numeric"));
	}

	@Test
	public void isNumeric_withArgument_startsWithZero_okay() {
		Assert.assertEquals("0123", Check.isNumeric("0123", "numeric"));
	}

}
