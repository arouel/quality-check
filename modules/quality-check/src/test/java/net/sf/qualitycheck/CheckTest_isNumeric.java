/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
