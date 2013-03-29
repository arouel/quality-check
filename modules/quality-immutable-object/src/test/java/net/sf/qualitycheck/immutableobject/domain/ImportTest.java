package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import org.junit.Test;

public class ImportTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Import.class, areImmutable());
	}

	@Test
	public void equals_identical() {
		final Import a = Import.of(String.class);
		final Import b = Import.of(String.class.getName());
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Import a = Import.of(String.class);
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Import a = Import.of(String.class);
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Import a = Import.of(String.class);
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void toString_notEmpty() {
		assertFalse(Import.of(String.class).toString().isEmpty());
	}

}
