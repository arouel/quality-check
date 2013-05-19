package net.sf.qualitytest.blueprint.strategy.creation;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class RandomEnumCreationStrategyTest {

	enum EnumWithoutValues {
	}

	enum EnumWithValues {
		A, B, C
	}

	@Test
	public void createValue_argIsAnEmptyEnum() {
		Assert.assertNull(new RandomEnumCreationStrategy().createValue(EnumWithoutValues.class));
	}

	@Test
	public void createValue_argIsAnEnum() {
		Assert.assertNotNull(new RandomEnumCreationStrategy().createValue(EnumWithValues.class));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void createValue_argIsNotAnEnum() {
		new RandomEnumCreationStrategy().createValue(Character.class);
	}

}
