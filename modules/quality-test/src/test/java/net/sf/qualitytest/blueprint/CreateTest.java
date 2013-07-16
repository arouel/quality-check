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

import net.sf.qualitytest.CoverageForPrivateConstructor;
import net.sf.qualitytest.StaticCheck;

import org.junit.Assert;
import org.junit.Test;

public class CreateTest {

	private enum Colors {
		RED, GREEN, BLUE
	}

	@Test
	public void testAddCoverageToMyPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(Create.class);
	}

	@Test
	public void testCreateNil() {
		Assert.assertNull(Create.nil().createValue(String.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testCreateRandomBoolean() {
		final Boolean result = Create.randomBoolean().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomByte() {
		final Byte result = Create.randomByte().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomCharacter() {
		final Character result = Create.randomChar().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomDouble() {
		final Double result = Create.randomDouble().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	};

	@Test
	public void testCreateRandomEnum() {
		final Colors result = (Colors) Create.randomEnum().createValue(Colors.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomFloat() {
		final Float result = Create.randomFloat().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomInt() {
		final Integer result = Create.randomInteger().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomLong() {
		final Long result = Create.randomLong().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateRandomShort() {
		final Short result = Create.randomShort().createValue(Object.class, Blueprint.def(), new BlueprintSession());
		Assert.assertNotNull(result);
	}

	@Test
	public void testCreateSingleValue() {
		Assert.assertEquals("Hallo", Create.value("Hallo").createValue(String.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testMakeSureClassIsFinalAndNotAccessible() {
		StaticCheck.classIsFinal(Create.class);
		StaticCheck.noPublicDefaultConstructor(Create.class);
	}

}
