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

import net.sf.qualitytest.blueprint.BlueprintTest;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintStringCreationStrategyTest {

	@Test
	public void testIsNull() {
		Assert.assertNull(new NullValueCreationStrategy<String>().createValue(String.class));
	}

	@Test
	public void testStringIsUuid() {
		Assert.assertTrue(BlueprintTest.UUID_PATTERN.matcher(new BlueprintStringCreationStrategy().createValue(String.class)).matches());
	}

	@Test
	public void testStringNotEmpty() {
		Assert.assertNotNull(new BlueprintStringCreationStrategy().createValue(String.class));
		Assert.assertNotEquals(0, new BlueprintStringCreationStrategy().createValue(String.class));
	}

}
