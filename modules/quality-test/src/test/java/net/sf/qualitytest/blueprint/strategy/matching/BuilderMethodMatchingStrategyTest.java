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

import net.sf.qualitytest.blueprint.MatchingStrategy;

import org.junit.Assert;
import org.junit.Test;

public class BuilderMethodMatchingStrategyTest {

	private static class MyBuilder {
		@SuppressWarnings("unused")
		public void value(final int a) {
			System.out.println(a);
		}
	}

	private static class MyBuilderThisIsNot {
		@SuppressWarnings("unused")
		public void value(final int a) {
			System.out.println(a);
		}
	}

	@Test
	public void testMatchBuilder() throws SecurityException, NoSuchMethodException {
		final MatchingStrategy s = new BuilderMethodMatchingStrategy("Builder");
		Assert.assertTrue(s.matchesByMethod(MyBuilder.class.getMethod("value", int.class)));
	}

	@Test
	public void testMatchBuilder_false() throws SecurityException, NoSuchMethodException {
		final MatchingStrategy s = new BuilderMethodMatchingStrategy("Builder");
		Assert.assertFalse(s.matchesByMethod(MyBuilderThisIsNot.class.getMethod("value", int.class)));
	}

	@Test
	public void testMatchesByField_mustReturnFalse() throws SecurityException, NoSuchFieldException {
		final MatchingStrategy s = new BuilderMethodMatchingStrategy("Builder");
		Assert.assertFalse(s.matchesByField(null));
		Assert.assertFalse(s.matchesByField(String.class.getDeclaredField("hash")));
	}

}
