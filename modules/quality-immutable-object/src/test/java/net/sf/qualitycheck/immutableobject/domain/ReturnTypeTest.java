package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ReturnTypeTest {

	@Test
	public void equals_identical() {
		final ReturnType a = ReturnType.of(String.class);
		final ReturnType b = ReturnType.of(String.class);
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final ReturnType a = ReturnType.of(String.class);
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final ReturnType a = ReturnType.of(String.class);
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final ReturnType a = ReturnType.of(String.class);
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void toString_notEmpty() {
		assertEquals(String.class.getName(), ReturnType.of(String.class).toString());
	}

}
