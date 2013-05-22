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

	@Test
	public void testGreaterThan_Comparable() {
		final String check = "quality";
		final String expected = "check";

		Assert.assertEquals("quality", Check.greaterThan(expected, check));
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

		Check.greaterThan(a, b, "Must be greater than 'check'.");
	}
}
