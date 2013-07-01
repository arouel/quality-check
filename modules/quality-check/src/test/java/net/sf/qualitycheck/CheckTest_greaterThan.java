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

import net.sf.qualitycheck.exception.IllegalNotGreaterThanException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_greaterThan {

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_byte_failure() {
		final byte a = 3;
		final byte b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_byte_failure_message() {
		final byte a = 2;
		final byte b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_byte_ok() {
		final byte a = 1;
		final byte b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b));
	}

	@Test
	public void testGreaterThan_byte_ok_message() {
		final byte a = 1;
		final byte b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."));
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_char_failure() {
		final char a = 3;
		final char b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_char_failure_message() {
		final char a = 2;
		final char b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_char_ok() {
		final char a = 1;
		final char b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b));
	}

	@Test
	public void testGreaterThan_char_ok_message() {
		final char a = 1;
		final char b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."));
	}

	@Test
	public void testGreaterThan_Comparable() {
		final String check = "quality";
		final String expected = "check";

		Assert.assertEquals("quality", Check.greaterThan(expected, check));
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_double_failure() {
		final double a = 3;
		final double b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_double_failure_message() {
		final double a = 2;
		final double b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_double_ok() {
		final double a = 1;
		final double b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b), 0.0);
	}

	@Test
	public void testGreaterThan_double_ok_message() {
		final double a = 1;
		final double b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."), 0.0);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_float_failure() {
		final float a = 3;
		final float b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_float_failure_message() {
		final float a = 2;
		final float b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_float_ok() {
		final float a = 1;
		final float b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b), 0.0f);
	}

	@Test
	public void testGreaterThan_float_ok_message() {
		final float a = 1;
		final float b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."), 0.0f);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_int_failure() {
		final int a = 3;
		final int b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_int_failure_message() {
		final int a = 2;
		final int b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_int_ok() {
		final int a = 1;
		final int b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b));
	}

	@Test
	public void testGreaterThan_int_ok_message() {
		final int a = 1;
		final int b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."));
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_long_failure() {
		final long a = 3;
		final long b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_long_failure_message() {
		final long a = 2;
		final long b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_long_ok() {
		final long a = 1;
		final long b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b));
	}

	@Test
	public void testGreaterThan_long_ok_message() {
		final long a = 1;
		final long b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."));
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_short_failure() {
		final short a = 3;
		final short b = 2;
		Check.greaterThan(a, b);
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThan_short_failure_message() {
		final short a = 2;
		final short b = 2;
		Check.greaterThan(a, b, "Must be greater than '1'.");
	}

	@Test
	public void testGreaterThan_short_ok() {
		final short a = 1;
		final short b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b));
	}

	@Test
	public void testGreaterThan_short_ok_message() {
		final short a = 1;
		final short b = 2;
		Assert.assertEquals(2, Check.greaterThan(a, b, "Must be greater than '1'."));
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThanFailure_Comparable() {
		final String check = "1quality";
		final String expected = "2check";

		Check.greaterThan(expected, check);
	}

	@Test
	public void testGreaterThanMessage_Comparable() {
		final String check = "quality";
		final String expected = "check";

		Assert.assertEquals("quality", Check.greaterThan(expected, check, "Must be greater than 'check'."));
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThanMessageFailure_Comparable() {
		final String check = "1quality";
		final String expected = "2check";

		Check.greaterThan(expected, check, "Must be greater than 'check'.");
	}

	@Test(expected = IllegalNotGreaterThanException.class)
	public void testGreaterThanMessageFailure_Equals() {
		final int a = 1;
		final int b = 1;

		Check.greaterThan(Integer.valueOf(a), Integer.valueOf(b), "Must be greater than 'check'.");
	}

}
