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
package net.sf.qualitytest.blueprint.strategy.matching;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class ArrayTypeMatchingStrategyTest {

	@Test
	public void testExactMatch() {
		Assert.assertTrue(new ArrayTypeMatchingStrategy().matchesByType(String[].class));
	}

	@Test
	public void testNoMatch() {
		Assert.assertFalse(new ArrayTypeMatchingStrategy().matchesByType(long.class));
	}

	@Test
	public void testObjectMatch() {
		Assert.assertTrue(new ArrayTypeMatchingStrategy().matchesByType(Object[].class));
		Assert.assertTrue(new ArrayTypeMatchingStrategy().matchesByType(byte[].class));
		Assert.assertTrue(new ArrayTypeMatchingStrategy().matchesByType(Map[].class));
	}

	@Test
	public void testPrimitiveMatch() {
		Assert.assertTrue(new ArrayTypeMatchingStrategy().matchesByType(int[].class));
	}
}
