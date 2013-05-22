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
package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import org.junit.Test;

public class PrimitiveTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Primitive.class, areImmutable());
	}

	@Test
	public void isPrimitive() {
		assertTrue(Primitive.isPrimitive("boolean"));
		assertTrue(Primitive.isPrimitive("byte"));
		assertTrue(Primitive.isPrimitive("char"));
		assertTrue(Primitive.isPrimitive("double"));
		assertTrue(Primitive.isPrimitive("float"));
		assertTrue(Primitive.isPrimitive("int"));
		assertTrue(Primitive.isPrimitive("long"));
		assertTrue(Primitive.isPrimitive("short"));
		assertEquals(8, Primitive.values().length);

		assertFalse(Primitive.isPrimitive(""));
		assertFalse(Primitive.isPrimitive("void"));
		assertFalse(Primitive.isPrimitive("abcdef"));
	}

}
