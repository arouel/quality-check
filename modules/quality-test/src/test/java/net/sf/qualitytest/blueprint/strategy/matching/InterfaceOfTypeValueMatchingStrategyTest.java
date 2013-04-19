package net.sf.qualitytest.blueprint.strategy.matching;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class InterfaceOfTypeValueMatchingStrategyTest {

	@Test
	public void testEquals() {
		Assert.assertEquals(new InterfaceOfTypeMatchingStrategy(Number.class), new InterfaceOfTypeMatchingStrategy(Number.class));
	}

	@Test
	public void testEqualsHashCode() {
		Assert.assertEquals(new InterfaceOfTypeMatchingStrategy(Number.class).hashCode(), new InterfaceOfTypeMatchingStrategy(
				Number.class).hashCode());
	}

	@Test
	public void testExactMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(String.class).matches(String.class));
	}

	@Test
	public void testInheritedMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Number.class).matches(Long.class));
	}

	@Test
	public void testNoMatch() {
		Assert.assertFalse(new InterfaceOfTypeMatchingStrategy(String.class).matches(Long.class));
	}

	@Test
	public void testNotEquals() {
		Assert.assertNotEquals(new InterfaceOfTypeMatchingStrategy(String.class), new InterfaceOfTypeMatchingStrategy(
				Number.class));
	}

	@Test
	public void testNotEqualsHashCode() {
		Assert.assertNotEquals(new InterfaceOfTypeMatchingStrategy(String.class).hashCode(), new InterfaceOfTypeMatchingStrategy(
				Number.class).hashCode());
	}

	@Test
	public void testObjectdMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matches(Long.class));
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matches(String.class));
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matches(Map.class));
	}
}
