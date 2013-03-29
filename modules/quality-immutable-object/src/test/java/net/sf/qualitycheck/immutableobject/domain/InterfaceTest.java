package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

public class InterfaceTest {

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_withoutInterface() {
		Interface.of(String.class);
	}

	@Test
	public void equals_different() {
		final Interface a = Interface.of(Serializable.class);
		final Interface b = Interface.of(Comparable.class);
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Interface a = Interface.of(Serializable.class);
		final Interface b = Interface.of(Serializable.class);
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Interface a = Interface.of(Serializable.class);
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Interface a = Interface.of(Serializable.class);
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Interface a = Interface.of(Serializable.class);
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void toString_notEmpty() {
		assertEquals(Serializable.class.getName(), Interface.of(Serializable.class).toString());
	}

}
