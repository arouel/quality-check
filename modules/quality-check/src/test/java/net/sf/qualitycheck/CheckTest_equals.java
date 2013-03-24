/*******************************************************************************
 * Copyright 2013 André Rouél 
 * Copyright 2013 Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 ******************************************************************************/

package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNotEqualException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_equals {

	@Test(expected = IllegalNullArgumentException.class)
	public void testCheckNull() {
		Check.equals(Long.valueOf(4), null);
	}

	@Test
	public void testEquality() {
		final Long expected = new Long(100001);
		final Long check = new Long(100001);

		Assert.assertEquals(new Long(100001), Check.equals(expected, check));
	}

	@Test
	public void testEquality_boolean() {
		final boolean expected = true;
		final boolean check = true;

		Assert.assertEquals(true, Check.equals(expected, check));
	}

	@Test
	public void testEquality_byte() {
		final byte expected = (byte) 123;
		final byte check = (byte) 123;

		Assert.assertEquals((byte) 123, Check.equals(expected, check));
	}

	@Test
	public void testEquality_char() {
		final char expected = 'F';
		final char check = 'F';

		Assert.assertEquals('F', Check.equals(expected, check));
	}

	@Test
	public void testEquality_int() {
		final int expected = 100001;
		final int check = 100001;

		Assert.assertEquals(100001, Check.equals(expected, check));
	}

	@Test
	public void testEquality_long() {
		final long expected = 100001;
		final long check = 100001;

		Assert.assertEquals(100001, Check.equals(expected, check));
	}

	@Test
	public void testEquality_short() {
		final short expected = (short) 1001;
		final short check = (short) 1001;

		Assert.assertEquals((short) 1001, Check.equals(expected, check));
	}

	@Test
	public void testEqualityMessage() {
		final Long expected = new Long(100001);
		final Long check = new Long(100001);

		Assert.assertEquals(new Long(100001), Check.equals(expected, check, "Should be equal to 10001."));
	}

	@Test
	public void testEqualityMessage_boolean() {
		final boolean expected = false;
		final boolean check = false;

		Assert.assertEquals(false, Check.equals(expected, check, "Should be equal to false."));
	}

	@Test
	public void testEqualityMessage_byte() {
		final byte expected = (byte) 123;
		final byte check = (byte) 123;

		Assert.assertEquals((byte) 123, Check.equals(expected, check, "Should be equal to 10001."));
	}

	@Test
	public void testEqualityMessage_char() {
		final char expected = 'F';
		final char check = 'F';

		Assert.assertEquals('F', Check.equals(expected, check, "Should be equal to 'F'."));
	}

	@Test
	public void testEqualityMessage_int() {
		final int expected = 100001;
		final int check = 100001;

		Assert.assertEquals(100001, Check.equals(expected, check, "Should be equal to 10001."));
	}

	@Test
	public void testEqualityMessage_long() {
		final long expected = 100001;
		final long check = 100001;

		Assert.assertEquals(100001, Check.equals(expected, check, "Should be equal to 10001."));
	}

	@Test
	public void testEqualityMessage_short() {
		final short expected = (short) 1001;
		final short check = (short) 1001;

		Assert.assertEquals((short) 1001, Check.equals(expected, check, "Should be equal to 10001."));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testExpectedNull() {
		Check.equals(null, Long.valueOf(4));
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality() {
		final Long expected = new Long(100001);
		final Long check = new Long(100002);

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality_boolean() {
		final boolean expected = true;
		final boolean check = false;

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality_byte() {
		final byte expected = (byte) 123;
		final byte check = (byte) 12;

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality_char() {
		final char expected = 'F';
		final char check = 'E';

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality_int() {
		final int expected = 100001;
		final int check = -100001;

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality_long() {
		final long expected = 100001;
		final long check = -100001;

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEquality_short() {
		final short expected = (short) 1001;
		final short check = (short) 1002;

		Check.equals(expected, check);
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage() {
		final Long expected = new Long(100001);
		final Long check = new Long(100002);

		Check.equals(expected, check, "Should be equal to 100001");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage_boolean() {
		final boolean expected = false;
		final boolean check = true;

		Check.equals(expected, check, "Should be equal to false");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage_byte() {
		final byte expected = (byte) 123;
		final byte check = (byte) 12;

		Check.equals(expected, check, "Should be equal to 123");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage_char() {
		final char expected = 'F';
		final char check = 'E';

		Check.equals(expected, check, "Should be equal to 'F'");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage_int() {
		final int expected = 100001;
		final int check = -10;

		Check.equals(expected, check, "Should be equal to 100001");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage_long() {
		final long expected = 100001;
		final long check = 1000032;

		Check.equals(expected, check, "Should be equal to 100001");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testNoEqualityMessage_short() {
		final short expected = (short) 1001;
		final short check = (short) 10012;

		Check.equals(expected, check, "Should be equal to 100001");
	}

}
