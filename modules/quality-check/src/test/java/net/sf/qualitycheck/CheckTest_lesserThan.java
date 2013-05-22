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

	@Test
	public void testLesserThan_Comparable() {
		final String check = "check";
		final String expected = "quality";

		Assert.assertEquals("check", Check.lesserThan(expected, check));
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

		Check.lesserThan(expected, check, "Must be greater than '42'.");
	}
}
