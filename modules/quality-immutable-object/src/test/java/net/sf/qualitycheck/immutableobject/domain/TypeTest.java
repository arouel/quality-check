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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class TypeTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Type.class, areImmutable());
	}

	@Test
	public void collectionVariant_checkAll() {
		assertTrue(Type.of(Collection.class).isCollectionVariant());
		assertTrue(Type.of(Iterable.class).isCollectionVariant());
		assertTrue(Type.of(List.class).isCollectionVariant());
		assertTrue(Type.of(Map.class).isCollectionVariant());
		assertTrue(Type.of(Set.class).isCollectionVariant());
		assertTrue(Type.of(SortedSet.class).isCollectionVariant());
		assertTrue(Type.of(SortedSet.class).isCollectionVariant());
		assertEquals(7, CollectionVariant.values().length);
	}

	@Test
	public void construct_innerInterface() {
		final Type type = new Type("com.github.before.Immutable$InnerInterface");
		assertEquals("com.github.before.Immutable.InnerInterface", type.toString());
	}

	@Test
	public void construct_typeGeneric() {
		final Type type = new Type("T");
		assertEquals(Package.UNDEFINED, type.getPackage());
		assertEquals("T", type.getName());
		assertEquals(GenericDeclaration.UNDEFINED, type.getGenericDeclaration());
	}

	@Test
	public void construct_typeWithoutPackage() {
		new Type("Immutable");
	}

	@Test
	public void construct_typeWithoutPackageAndGeneric() {
		final Type type = new Type("List<String>");
		assertEquals("List", type.getName());
		assertEquals("String", type.getGenericDeclaration().getDeclaration());
		assertEquals("List<String>", type.toString());
	}

	@Test
	public void construct_typeWithPackage() {
		new Type("com.github.before.Immutable");
	}

	@Test
	public void createTypeWithGenericDeclaration() {
		new Type("java.util", "List", "");
		new Type("java.util", "List", "String");
		new Type("java.util", "List", "? extends Set");
	}

	@Test
	public void equals_identical() {
		final Type a = new Type(String.class.getName());
		final Type b = new Type(String.class.getName());
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Type a = new Type(String.class.getName());
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Type a = new Type(String.class.getName());
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Type a = new Type(String.class.getName());
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void evaluateJavaLangValueType() {
		assertNotNull(Type.evaluateJavaLangType("Boolean"));
		assertTrue(Type.evaluateJavaLangType("Boolean").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Byte").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Character").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Double").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Float").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Integer").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Long").isBoxedType());
		assertFalse(Type.evaluateJavaLangType("Number").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("Short").isBoxedType());
		assertFalse(Type.evaluateJavaLangType("String").isBoxedType());

		assertNotNull(Type.evaluateJavaLangType("java.lang.Boolean"));
		assertTrue(Type.evaluateJavaLangType("java.lang.Boolean").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Byte").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Character").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Double").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Float").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Integer").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Long").isBoxedType());
		assertFalse(Type.evaluateJavaLangType("java.lang.Number").isBoxedType());
		assertTrue(Type.evaluateJavaLangType("java.lang.Short").isBoxedType());
		assertFalse(Type.evaluateJavaLangType("java.lang.String").isBoxedType());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void evaluateJavaLangValueType_typeName_isEmpty() {
		assertNull(Type.evaluateJavaLangType(""));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluateJavaLangValueType_typeName_isNull() {
		Type.evaluateJavaLangType(null);
	}

	@Test
	public void isBoolean() {
		assertTrue(new Type("boolean").isBoolean());
		assertFalse(new Type("Boolean").isBoolean());
		assertFalse(new Type("int").isBoolean());
		assertFalse(new Type("Long").isBoolean());
	}

	@Test
	public void isBoxedBoolean() {
		assertTrue(new Type("java.lang.Boolean").isBoxedBoolean());
		assertTrue(Type.of(Boolean.class).isBoxedBoolean());
		assertFalse(new Type("Boolean").isBoxedBoolean());
		assertFalse(new Type("int").isBoxedBoolean());
		assertFalse(new Type("Long").isBoxedBoolean());
	}

	@Test
	public void isBoxedByte() {
		assertTrue(new Type("java.lang.Byte").isBoxedByte());
		assertTrue(Type.of(Byte.class).isBoxedByte());
		assertFalse(new Type("Byte").isBoxedByte());
		assertFalse(new Type("int").isBoxedByte());
		assertFalse(new Type("Long").isBoxedByte());
	}

	@Test
	public void isBoxedChar() {
		assertTrue(Type.of(Character.class).isBoxedCharacter());
		assertFalse(new Type("Char").isBoxedCharacter());
		assertFalse(new Type("Character").isBoxedCharacter());
		assertFalse(new Type("int").isBoxedCharacter());
	}

	@Test
	public void isBoxedDouble() {
		assertTrue(new Type("java.lang.Double").isBoxedDouble());
		assertTrue(Type.of(Double.class).isBoxedDouble());
		assertFalse(new Type("Double").isBoxedDouble());
		assertFalse(new Type("int").isBoxedDouble());
		assertFalse(new Type("Long").isBoxedDouble());
	}

	@Test
	public void isBoxedFloat() {
		assertTrue(new Type("java.lang.Float").isBoxedFloat());
		assertTrue(Type.of(Float.class).isBoxedFloat());
		assertFalse(new Type("Float").isBoxedFloat());
		assertFalse(new Type("double").isBoxedFloat());
		assertFalse(new Type("Double").isBoxedFloat());
	}

	@Test
	public void isBoxedInt() {
		assertTrue(new Type("java.lang.Integer").isBoxedInteger());
		assertTrue(Type.of(Integer.class).isBoxedInteger());
		assertFalse(new Type("Int").isBoxedInteger());
		assertFalse(new Type("Integer").isBoxedInteger());
		assertFalse(new Type("long").isBoxedInteger());
	}

	@Test
	public void isBoxedLong() {
		assertTrue(new Type("java.lang.Long").isBoxedLong());
		assertTrue(Type.of(Long.class).isBoxedLong());
		assertFalse(new Type("Long").isBoxedLong());
		assertFalse(new Type("int").isBoxedLong());
		assertFalse(new Type("double").isBoxedLong());
	}

	@Test
	public void isBoxedShort() {
		assertTrue(new Type("java.lang.Short").isBoxedShort());
		assertTrue(Type.of(Short.class).isBoxedShort());
		assertFalse(new Type("Short").isBoxedShort());
		assertFalse(new Type("int").isBoxedShort());
		assertFalse(new Type("Long").isBoxedShort());
	}

	@Test
	public void isBoxedType() {
		assertTrue(new Type("java.lang.Boolean").isBoxedType());
		assertTrue(Type.of(Boolean.class).isBoxedType());
		assertTrue(new Type("java.lang.Byte").isBoxedType());
		assertTrue(Type.of(Byte.class).isBoxedType());
		assertTrue(new Type("java.lang.Character").isBoxedType());
		assertTrue(Type.of(Character.class).isBoxedType());
		assertTrue(new Type("java.lang.Double").isBoxedType());
		assertTrue(Type.of(Double.class).isBoxedType());
		assertTrue(new Type("java.lang.Float").isBoxedType());
		assertTrue(Type.of(Float.class).isBoxedType());
		assertTrue(new Type("java.lang.Integer").isBoxedType());
		assertTrue(Type.of(Integer.class).isBoxedType());
		assertTrue(new Type("java.lang.Long").isBoxedType());
		assertTrue(Type.of(Long.class).isBoxedType());
		assertTrue(new Type("java.lang.Short").isBoxedType());
		assertTrue(Type.of(Short.class).isBoxedType());

		assertFalse(new Type("Boolean").isBoxedType());
		assertFalse(new Type("Byte").isBoxedType());
		assertFalse(new Type("Character").isBoxedType());
		assertFalse(new Type("Double").isBoxedType());
		assertFalse(new Type("Float").isBoxedType());
		assertFalse(new Type("Integer").isBoxedType());
		assertFalse(new Type("Long").isBoxedType());
		assertFalse(new Type("Short").isBoxedType());
	}

	@Test
	public void isByte() {
		assertTrue(new Type("byte").isByte());
		assertFalse(new Type("Byte").isByte());
		assertFalse(new Type("int").isByte());
		assertFalse(new Type("Long").isByte());
	}

	@Test
	public void isChar() {
		assertTrue(new Type("char").isChar());
		assertFalse(new Type("Char").isChar());
		assertFalse(new Type("Character").isChar());
		assertFalse(new Type("int").isChar());
	}

	@Test
	public void isDouble() {
		assertTrue(new Type("double").isDouble());
		assertFalse(new Type("Double").isDouble());
		assertFalse(new Type("int").isDouble());
		assertFalse(new Type("Long").isDouble());
	}

	@Test
	public void isFloat() {
		assertTrue(new Type("float").isFloat());
		assertFalse(new Type("Float").isFloat());
		assertFalse(new Type("double").isFloat());
		assertFalse(new Type("Double").isFloat());
	}

	@Test
	public void isInt() {
		assertTrue(new Type("int").isInt());
		assertFalse(new Type("Int").isInt());
		assertFalse(new Type("Integer").isInt());
		assertFalse(new Type("long").isInt());
	}

	@Test
	public void isJavaLangType() {
		assertTrue(new Type("java.lang.Boolean").isJavaLangType());
		assertTrue(Type.of(Boolean.class).isJavaLangType());
		assertTrue(new Type("java.lang.Byte").isJavaLangType());
		assertTrue(Type.of(Byte.class).isJavaLangType());
		assertTrue(new Type("java.lang.Character").isJavaLangType());
		assertTrue(Type.of(Character.class).isJavaLangType());
		assertTrue(new Type("java.lang.Double").isJavaLangType());
		assertTrue(Type.of(Double.class).isJavaLangType());
		assertTrue(new Type("java.lang.Float").isJavaLangType());
		assertTrue(Type.of(Float.class).isJavaLangType());
		assertTrue(new Type("java.lang.Integer").isJavaLangType());
		assertTrue(Type.of(Integer.class).isJavaLangType());
		assertTrue(new Type("java.lang.Long").isJavaLangType());
		assertTrue(Type.of(Long.class).isJavaLangType());
		assertTrue(new Type("java.lang.Short").isJavaLangType());
		assertTrue(Type.of(Short.class).isJavaLangType());
		assertTrue(new Type("java.lang.Number").isJavaLangType());
		assertTrue(Type.of(Number.class).isJavaLangType());
		assertTrue(new Type("java.lang.String").isJavaLangType());
		assertTrue(Type.of(String.class).isJavaLangType());

		assertFalse(new Type("Boolean").isJavaLangType());
		assertFalse(new Type("Byte").isJavaLangType());
		assertFalse(new Type("Character").isJavaLangType());
		assertFalse(new Type("Double").isJavaLangType());
		assertFalse(new Type("Float").isJavaLangType());
		assertFalse(new Type("Integer").isJavaLangType());
		assertFalse(new Type("Long").isJavaLangType());
		assertFalse(new Type("Short").isJavaLangType());
	}

	@Test
	public void isLong() {
		assertTrue(new Type("long").isLong());
		assertFalse(new Type("Long").isLong());
		assertFalse(new Type("int").isLong());
		assertFalse(new Type("double").isLong());
	}

	@Test
	public void isNumber() {
		assertTrue(Type.of(Number.class).isNumber());
		assertFalse(Type.of(Long.class).isNumber());
	}

	@Test
	public void isShort() {
		assertTrue(new Type("short").isShort());
		assertFalse(new Type("Short").isShort());
		assertFalse(new Type("int").isShort());
		assertFalse(new Type("Long").isShort());
	}

	@Test
	public void isString() {
		assertTrue(new Type("java.lang.String").isString());
		assertTrue(Type.of(String.class).isString());
		assertFalse(new Type("String").isString());
		assertFalse(new Type("org.apache.xpath.operations.String").isString());
	}

	@Test
	public void toString_primitive() {
		final Type type = new Type("int");
		assertEquals("int", type.toString());
	}

}
