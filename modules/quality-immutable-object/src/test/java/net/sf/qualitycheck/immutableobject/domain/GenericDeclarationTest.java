/*******************************************************************************
 * Copyright 2013 André Rouél
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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class GenericDeclarationTest {

	@Test
	public void equals_identical() {
		final GenericDeclaration a = GenericDeclaration.of("? extends List<String>");
		final GenericDeclaration b = GenericDeclaration.of("? extends List<String>");
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final GenericDeclaration a = GenericDeclaration.of("? extends List<String>");
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final GenericDeclaration a = GenericDeclaration.of("? extends List<String>");
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final GenericDeclaration a = GenericDeclaration.of("? extends List<String>");
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void parseFrom_okay() {
		assertEquals(GenericDeclaration.of("String"), GenericDeclaration.parseFrom("List<String>"));
		assertEquals(GenericDeclaration.of("T extends List<String>"), GenericDeclaration.parseFrom("List<T extends List<String>>"));
		// assertEquals(Sets.newHashSet("List", "String"), GenericDeclaration.parseFrom("<T extends Annotation> T"));
	}

	@Test
	public void parseFrom_wrongDefinition() {
		assertSame(GenericDeclaration.UNDEFINED, GenericDeclaration.parseFrom("<T extends Annotation> T"));
	}

	@Test
	public void toString_notEmpty() {
		assertEquals("? extends List<String>", GenericDeclaration.of("? extends List<String>").toString());
	}

}
