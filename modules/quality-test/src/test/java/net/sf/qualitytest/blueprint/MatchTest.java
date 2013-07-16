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
package net.sf.qualitytest.blueprint;

import net.sf.qualitytest.StaticCheck;

import org.junit.Assert;
import org.junit.Test;

public class MatchTest {

	@Test
	public void testMakeSureClassIsFinalAndNotAccessible() {
		StaticCheck.classIsFinal(Match.class);
		StaticCheck.noPublicDefaultConstructor(Match.class);
	}

	@Test
	public void testMatchByName() throws SecurityException, NoSuchMethodException {
		Assert.assertTrue(Match.name("toString").matchesByMethod(String.class.getDeclaredMethod("toString")));
		Assert.assertFalse(Match.name("Hallo").matchesByMethod(String.class.getDeclaredMethod("toString")));
	}

	@Test
	public void testMatchBySuperType() {
		Assert.assertTrue(Match.instanceOf(Number.class).matchesByType(Long.class));
		Assert.assertFalse(Match.instanceOf(Number.class).matchesByType(String.class));
	}

	@Test
	public void testMatchByType() {
		Assert.assertTrue(Match.type(String.class).matchesByType(String.class));
		Assert.assertFalse(Match.type(Long.class).matchesByType(String.class));
	}

}
