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

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_array {

	@Test
	public void testArray() {
		final int[] exampleArray = { 1, 2, 3 };
		final int[] array = Blueprint.def().with(int[].class, exampleArray).construct(int[].class);
		Assert.assertArrayEquals(exampleArray, array);
	}

	@Test
	public void testArrayDefault() {
		final int[] array = Blueprint.def().construct(int[].class);
		Assert.assertEquals(7, array.length);
	}
}
