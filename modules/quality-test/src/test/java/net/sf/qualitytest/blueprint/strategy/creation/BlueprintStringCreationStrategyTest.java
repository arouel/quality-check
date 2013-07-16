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

import java.util.regex.Pattern;

import net.sf.qualitytest.blueprint.BlueprintTest;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintStringCreationStrategyTest {

	@Test
	public void testIsNull() {
		Assert.assertNull(new NullValueCreationStrategy<String>().createValue(String.class));
	}

	@Test
	public void testMaxLength_isFour() {
		final Pattern SHORT_UUID_PATTERN = Pattern.compile("[a-z0-9]{4}");
		Assert.assertNotNull(new BlueprintStringCreationStrategy(4).createValue(String.class));
		Assert.assertEquals(4, new BlueprintStringCreationStrategy(4).createValue(String.class).length());
		Assert.assertTrue(SHORT_UUID_PATTERN.matcher(new BlueprintStringCreationStrategy(4).createValue(String.class)).matches());
	}

	@Test
	public void testMaxLength_isZero() {
		Assert.assertNotNull(new BlueprintStringCreationStrategy(0).createValue(String.class));
		Assert.assertEquals("", new BlueprintStringCreationStrategy(0).createValue(String.class));
	}

	@Test
	public void testStringIsUuid() {
		Assert.assertTrue(BlueprintTest.UUID_PATTERN.matcher(new BlueprintStringCreationStrategy().createValue(String.class)).matches());
	}

	@Test
	public void testStringNotEmpty() {
		Assert.assertNotNull(new BlueprintStringCreationStrategy().createValue(String.class));
		Assert.assertNotEquals("", new BlueprintStringCreationStrategy().createValue(String.class));
	}

}
