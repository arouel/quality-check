package net.sf.qualitytest.blueprint.strategy.matching;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class InterfaceOfTypeValueMatchingStrategyTest {

	@Test
	public void testExactMatch() {
		Assert.assertTrue(new InterfaceOfTypeValueMatchingStrategy(String.class).matches(String.class));
	}

	@Test
	public void testInheritedMatch() {
		Assert.assertTrue(new InterfaceOfTypeValueMatchingStrategy(Number.class).matches(Long.class));
	}

	@Test
	public void testNoMatch() {
		Assert.assertFalse(new InterfaceOfTypeValueMatchingStrategy(String.class).matches(Long.class));
	}

	@Test
	public void testObjectdMatch() {
		Assert.assertTrue(new InterfaceOfTypeValueMatchingStrategy(Object.class).matches(Long.class));
		Assert.assertTrue(new InterfaceOfTypeValueMatchingStrategy(Object.class).matches(String.class));
		Assert.assertTrue(new InterfaceOfTypeValueMatchingStrategy(Object.class).matches(Map.class));
	}
}
