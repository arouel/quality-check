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

import net.sf.qualitycheck.exception.IllegalNotGreaterOrEqualThanException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_greaterOrEqualThan {

	@Test
	public void testGreaterOrEqualThan_Comparable() {
		final String check = "quality";
		final String expected = "check";

		Assert.assertEquals("quality", Check.greaterOrEqualThan(expected, check));
	}

	@Test(expected = IllegalNotGreaterOrEqualThanException.class)
	public void testGreaterOrEqualThanFailure_Comparable() {
		final String check = "1quality";
		final String expected = "2check";

		Check.greaterOrEqualThan(expected, check);
	}

	@Test
	public void testGreaterOrEqualThanMessage_Comparable() {
		final String check = "quality";
		final String expected = "check";

		Assert.assertEquals("quality", Check.greaterOrEqualThan(expected, check, "Must be greater than 'check'."));
	}

	@Test(expected = IllegalNotGreaterOrEqualThanException.class)
	public void testGreaterOrEqualThanMessageFailure_Comparable() {
		final String check = "1quality";
		final String expected = "2check";

		Check.greaterOrEqualThan(expected, check, "Must be greater than 'check'.");
	}

	@Test
	public void testGreaterOrEqualThanMessageFailure_Equals() {
		final int a = 1;
		final int b = 1;

		Assert.assertEquals(Integer.valueOf(1), Check.greaterOrEqualThan(a, b, "Must be greater or equal than 'check'."));
	}
}
