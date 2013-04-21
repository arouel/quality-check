package net.sf.qualitytest.blueprint.strategy.matching;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

public class InterfaceOfTypeValueMatchingStrategyTest {

	@Test
	public void testExactMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(String.class).matchesByType(String.class));
	}

	@Test
	public void testInheritedMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Number.class).matchesByType(Long.class));
	}

	@Test
	public void testNoMatch() {
		Assert.assertFalse(new InterfaceOfTypeMatchingStrategy(String.class).matchesByType(Long.class));
	}

	@Test
	public void testObjectdMatch() {
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matchesByType(Long.class));
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matchesByType(String.class));
		Assert.assertTrue(new InterfaceOfTypeMatchingStrategy(Object.class).matchesByType(Map.class));
	}
}
