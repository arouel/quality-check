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

import net.sf.qualitycheck.exception.IllegalEqualException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notEquals {

	@Test(expected = IllegalNullArgumentException.class)
	public void testCheckNull() {
		Check.notEquals(Long.valueOf(4), null);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality() {
		final Long expected = new Long(100002);
		final Long check = new Long(100002);

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_boolean() {
		final boolean expected = true;
		final boolean check = true;

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_byte() {
		final byte expected = (byte) 123;
		final byte check = (byte) 123;

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_char() {
		final char expected = 'F';
		final char check = 'F';

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_Comparable() {
		final Comparable<String> expected = "100001";
		final Comparable<String> check = "100001";

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_int() {
		final int expected = -100001;
		final int check = -100001;

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_long() {
		final long expected = 100001;
		final long check = 100001;

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEquality_short() {
		final short expected = (short) 1001;
		final short check = (short) 1001;

		Check.notEquals(expected, check);
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage() {
		final Long expected = new Long(100001);
		final Long check = new Long(100001);

		Check.notEquals(expected, check, "Should not be equal to 100001");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_boolean() {
		final boolean expected = false;
		final boolean check = false;

		Check.notEquals(expected, check, "Should not be equal to false");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_byte() {
		final byte expected = (byte) 123;
		final byte check = (byte) 123;

		Check.notEquals(expected, check, "Should not be equal to 123");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_char() {
		final char expected = 'E';
		final char check = 'E';

		Check.notEquals(expected, check, "Should not be equal to 'F'");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_Comparable() {
		final Comparable<String> expected = "100001";
		final Comparable<String> check = "100001";

		Check.notEquals(expected, check, "Should not be equal to \"100001\"");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_int() {
		final int expected = -10;
		final int check = -10;

		Check.notEquals(expected, check, "Should not be equal to 100001");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_long() {
		final long expected = 100001;
		final long check = 100001;

		Check.notEquals(expected, check, "Should not be equal to 100001");
	}

	@Test(expected = IllegalEqualException.class)
	public void testEqualityMessage_short() {
		final short expected = (short) 1001;
		final short check = (short) 1001;

		Check.notEquals(expected, check, "Should not be equal to 100001");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testExpectedNull() {
		Check.notEquals(null, Long.valueOf(4));
	}

	@Test
	public void testUnEquality() {
		final Long expected = new Long(100001);
		final Long check = new Long(100002);

		Assert.assertEquals(new Long(100002), Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_boolean() {
		final boolean expected = false;
		final boolean check = true;

		Assert.assertEquals(true, Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_byte() {
		final byte expected = (byte) 122;
		final byte check = (byte) 123;

		Assert.assertEquals((byte) 123, Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_char() {
		final char expected = 'E';
		final char check = 'F';

		Assert.assertEquals('F', Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_Comparable() {
		final Comparable<String> expected = "100002";
		final Comparable<String> check = "100001";

		Assert.assertEquals("100001", Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_int() {
		final int expected = 100000;
		final int check = 100001;

		Assert.assertEquals(100001, Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_long() {
		final long expected = 100000;
		final long check = 100001;

		Assert.assertEquals(100001, Check.notEquals(expected, check));
	}

	@Test
	public void testUnEquality_short() {
		final short expected = (short) 1002;
		final short check = (short) 1001;

		Assert.assertEquals((short) 1001, Check.notEquals(expected, check));
	}

	@Test
	public void testUnEqualityMessage() {
		final Long expected = new Long(100000);
		final Long check = new Long(100001);

		Assert.assertEquals(new Long(100001), Check.notEquals(expected, check, "Should not be equal to 100000."));
	}

	@Test
	public void testUnEqualityMessage_boolean() {
		final boolean expected = true;
		final boolean check = false;

		Assert.assertEquals(false, Check.notEquals(expected, check, "Should not be equal to true."));
	}

	@Test
	public void testUnEqualityMessage_byte() {
		final byte expected = (byte) 122;
		final byte check = (byte) 123;

		Assert.assertEquals((byte) 123, Check.notEquals(expected, check, "Should be not equal to 122."));
	}

	@Test
	public void testUnEqualityMessage_char() {
		final char expected = 'E';
		final char check = 'F';

		Assert.assertEquals('F', Check.notEquals(expected, check, "Should not be equal to 'E'."));
	}

	@Test
	public void testUnEqualityMessage_Comparable() {
		final Comparable<String> expected = "100000";
		final Comparable<String> check = "100001";

		Assert.assertEquals("100001", Check.notEquals(expected, check, "Should not be equal to \"100000\"."));
	}

	@Test
	public void testUnEqualityMessage_int() {
		final int expected = 100000;
		final int check = 100001;

		Assert.assertEquals(100001, Check.notEquals(expected, check, "Should not be equal to 10000."));
	}

	@Test
	public void testUnEqualityMessage_long() {
		final long expected = 100000;
		final long check = 100001;

		Assert.assertEquals(100001, Check.notEquals(expected, check, "Should not be equal to 100000."));
	}

	@Test
	public void testUnEqualityMessage_short() {
		final short expected = (short) 1000;
		final short check = (short) 1001;

		Assert.assertEquals((short) 1001, Check.notEquals(expected, check, "Should not be equal to 1000."));
	}

}
