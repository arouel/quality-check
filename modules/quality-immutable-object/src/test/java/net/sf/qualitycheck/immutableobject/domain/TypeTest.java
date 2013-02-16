package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

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
	public void isLong() {
		assertTrue(new Type("long").isLong());
		assertFalse(new Type("Long").isLong());
		assertFalse(new Type("int").isLong());
		assertFalse(new Type("double").isLong());
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
