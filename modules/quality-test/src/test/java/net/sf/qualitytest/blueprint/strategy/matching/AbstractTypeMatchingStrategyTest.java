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

import org.junit.Assert;
import org.junit.Test;

public class AbstractTypeMatchingStrategyTest {

	public abstract class MyAbstract {
		public abstract void doSomething();
	}

	@Test
	public void testMatchAbstract() {
		Assert.assertEquals(true, new AbstractTypeMatchingStrategy().matchesByType(MyAbstract.class));
	}

	@Test
	public void testMatchNoString() throws SecurityException, NoSuchMethodException {
		Assert.assertEquals(false, new AbstractTypeMatchingStrategy().matchesByMethod(String.class.getDeclaredMethod("toString")));
	}

	@Test
	public void testMatchNotAbstract() {
		Assert.assertEquals(false, new AbstractTypeMatchingStrategy().matchesByType(AbstractTypeMatchingStrategyTest.class));
	}
}
