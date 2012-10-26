package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNumberArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_isNumber {

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_decimalNumber_fail() {
		Check.isNumber("1.23");
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_fail() {
		Check.isNumber("Hallo Welt!");
	}

	@Test
	public void isNumber_negativeNumber_okay() {
		Assert.assertEquals(-123, Check.isNumber("-123"));
	}

	@Test
	public void isNumber_okay() {
		Assert.assertEquals(123, Check.isNumber("123"));
	}

	@Test
	public void isNumber_startsWithZero_okay() {
		Assert.assertEquals(123, Check.isNumber("0123"));
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_withArgument_decimalNumber_fail() {
		Check.isNumber("1.23", "numeric");
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_withArgument_fail() {
		Check.isNumber("Hallo Welt", "numeric");
	}

	@Test
	public void isNumber_withArgument_negativeNumber_okay() {
		Assert.assertEquals(-123, Check.isNumber("-123", "numeric"));
	}

	@Test
	public void isNumber_withArgument_okay() {
		Assert.assertEquals(123, Check.isNumber("123", "numeric"));
	}

	@Test
	public void isNumber_withArgument_startsWithZero_okay() {
		Assert.assertEquals(123, Check.isNumber("0123", "numeric"));
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumeric_longNumericString_fail() {
		Check.isNumber("1230000000000000000000000000");
	}

}
