package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNotEqualException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

/******************************************************************************* 
 * Copyright 2012 André Rouél
 * Copyright 2012 Dominik Seichter
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
public class CheckTest_equals {

	@Test(expected=IllegalNullArgumentException.class)
	public void testExpectedNull() {
		Check.equals(null, Long.valueOf(4));
	}
	
	@Test(expected=IllegalNullArgumentException.class)
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
	public void testEqualityMessage() {
		final Long expected = new Long(100001);
		final Long check = new Long(100001);
		
		Assert.assertEquals(new Long(100001), Check.equals(expected, check, "Should be equal to 10001."));
	}
	
	@Test(expected=IllegalNotEqualException.class)
	public void testNoEquality() {
		final Long expected = new Long(100001);
		final Long check = new Long(100002);
		
		Check.equals(expected, check);
	}
	
	@Test(expected=IllegalNotEqualException.class)
	public void testNoEqualityMessage() {
		final Long expected = new Long(100001);
		final Long check = new Long(100002);
		
		Check.equals(expected, check, "Should be equal to 100001");
	}
}
