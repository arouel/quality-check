package net.sf.qualitytest.blueprint.strategy.creation;

import java.util.Random;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.ValueCreationStrategy;

public class RandomEnumCreationStrategy implements ValueCreationStrategy<Enum<?>> {

	private final Random random = new Random();

	/**
	 * Blueprint an enum value using the default configuration.
	 * 
	 * This method will return the first enum constant in the enumeration.
	 * 
	 * @param <T>
	 * @param expectedClazz
	 *            the class of an enumeration.
	 * @return a valid enum value.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enum<?> createValue(final Class<?> expectedClazz) {
		Check.notNull(expectedClazz, "expectedClazz");
		final Class<? extends Enum<?>> enumClazz = (Class<? extends Enum<?>>) expectedClazz;
		final Enum<?>[] enumConstants = enumClazz.getEnumConstants();
		final int index = random.nextInt(enumConstants.length);
		return enumConstants.length > 0 ? enumConstants[index] : null;
	}

}
