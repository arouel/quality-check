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
package net.sf.qualitytest.blueprint.strategy.creation;

import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintSession;

import org.junit.Assert;
import org.junit.Test;

public class IterateValueCreationStrategyTest {

	@Test
	public void testIterate_Integer() {
		final IterateValueCreationStrategy<Integer> s = new IterateValueCreationStrategy<Integer>(Integer.valueOf(0), Integer.valueOf(100),
				Integer.valueOf(1000));

		Assert.assertEquals(0, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(100, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(1000, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(0, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
	}
}
