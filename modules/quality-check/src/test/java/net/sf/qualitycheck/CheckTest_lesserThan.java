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

import net.sf.qualitycheck.exception.IllegalNotLesserThanException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_lesserThan {

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_byte_failure() {
		final byte a = 2;
		final byte b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_byte_failure_message() {
		final byte a = 2;
		final byte b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_byte_ok() {
		final byte a = 3;
		final byte b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b));
	}

	@Test
	public void testlesserThan_byte_ok_message() {
		final byte a = 3;
		final byte b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '3'."));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_char_failure() {
		final char a = 2;
		final char b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_char_failure_message() {
		final char a = 2;
		final char b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_char_ok() {
		final char a = 3;
		final char b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b));
	}

	@Test
	public void testlesserThan_char_ok_message() {
		final char a = 3;
		final char b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '3'."));
	}

	@Test
	public void testlesserThan_Comparable() {
		final String check = "check";
		final String expected = "quality";

		Assert.assertEquals("check", Check.lesserThan(expected, check));
	}

	@Test
	public void testLesserThan_Comparable() {
		final String check = "check";
		final String expected = "quality";

		Assert.assertEquals("check", Check.lesserThan(expected, check));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_double_failure() {
		final double a = 2;
		final double b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_double_failure_message() {
		final double a = 2;
		final double b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_double_ok() {
		final double a = 3;
		final double b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b), 0.0);
	}

	@Test
	public void testlesserThan_double_ok_message() {
		final double a = 3;
		final double b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '3'."), 0.0);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_float_failure() {
		final float a = 2;
		final float b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_float_failure_message() {
		final float a = 2;
		final float b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_float_ok() {
		final float a = 3;
		final float b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b), 0.0f);
	}

	@Test
	public void testlesserThan_float_ok_message() {
		final float a = 3;
		final float b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '3'."), 0.0f);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_int_failure() {
		final int a = 2;
		final int b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_int_failure_message() {
		final int a = 2;
		final int b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_int_ok() {
		final int a = 3;
		final int b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b));
	}

	@Test
	public void testlesserThan_int_ok_message() {
		final int a = 3;
		final int b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '3'."));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_long_failure() {
		final long a = 2;
		final long b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_long_failure_message() {
		final long a = 2;
		final long b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_long_ok() {
		final long a = 3;
		final long b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b));
	}

	@Test
	public void testlesserThan_long_ok_message() {
		final long a = 3;
		final long b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '3'."));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_short_failure() {
		final short a = 2;
		final short b = 3;
		Check.lesserThan(a, b);
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testlesserThan_short_failure_message() {
		final short a = 2;
		final short b = 3;
		Check.lesserThan(a, b, "Must be lesser than '1'.");
	}

	@Test
	public void testlesserThan_short_ok() {
		final short a = 3;
		final short b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b));
	}

	@Test
	public void testlesserThan_short_ok_message() {
		final short a = 3;
		final short b = 1;
		Assert.assertEquals(1, Check.lesserThan(a, b, "Must be lesser than '1'."));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testLesserThanFailure_Comparable() {
		final Long check = Long.valueOf(322l);
		final Long expected = Long.valueOf(42l);
		Check.lesserThan(expected, check);
	}

	@Test
	public void testLesserThanMessage_Comparable() {
		final String check = "check";
		final String expected = "quality";

		Assert.assertEquals("check", Check.lesserThan(expected, check, "Must be lesser than 'quality'."));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testLesserThanMessageFailure_Comparable() {
		final Long check = Long.valueOf(100l);
		final Long expected = Long.valueOf(42l);

		Check.lesserThan(expected, check, "Must be lesser than '42'.");
	}
}
