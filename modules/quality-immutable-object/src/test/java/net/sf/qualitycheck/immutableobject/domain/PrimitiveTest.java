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
