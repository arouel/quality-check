package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNaNArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notNaN {

	@Test(expected = IllegalNaNArgumentException.class)
	public void notNaNDouble_Fail() {
		Check.notNaN(Double.NaN);
	}

	@Test
	public void notNaNDouble_Ok() {
		Assert.assertEquals(1.0d, Check.notNaN(1.0d), 0.0d);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void notNaNDoubleArgument_Fail() {
		Check.notNaN(Double.NaN, "float");
	}

	@Test
	public void notNaNDoubleArgument_Ok() {
		Assert.assertEquals(1.0d, Check.notNaN(1.0d, "double"), 0.0d);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void notNaNFloat_Fail() {
		Check.notNaN(Float.NaN);
	}

	@Test
	public void notNaNFloat_Ok() {
		Assert.assertEquals(1.0f, Check.notNaN(1.0f), 0.0f);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void notNaNFloatArgument_Fail() {
		Check.notNaN(Float.NaN, "float");
	}

	@Test
	public void notNaNFloatArgument_Ok() {
		Assert.assertEquals(1.0f, Check.notNaN(1.0f, "float"), 0.0f);
	}

}
