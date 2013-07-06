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

import net.sf.qualitytest.blueprint.MatchingStrategy;

import org.junit.Assert;
import org.junit.Test;

public class InterfaceOfTypeValueMatchingStrategyTest {

	@Test
	public void testExactMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(String.class).matchesByType(String.class));
	}

	@Test
	public void testInheritedMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Number.class).matchesByType(Long.class));
	}

	@Test
	public void testMatchesByField_mustReturnFalse() throws SecurityException, NoSuchFieldException {
		final MatchingStrategy s = new InterfaceOfTypeMatchingStrategy(Object.class);
		Assert.assertFalse(s.matchesByField(null));
		Assert.assertFalse(s.matchesByField(String.class.getDeclaredField("hash")));
	}

	@Test
	public void testNoMatch() {
		Assert.assertFalse(new InterfaceOfTypeMatchingStrategy(String.class).matchesByType(Long.class));
	}

	@Test
	public void testObjectdMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matchesByType(Long.class));
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matchesByType(String.class));
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matchesByType(Map.class));
	}

}
