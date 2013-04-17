package net.sf.qualitytest.blueprint.strategy.creation;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.ValueCreationStrategy;

public class DefaultEnumCreationStrategy implements ValueCreationStrategy<Enum<?>> {

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
		return enumConstants.length > 0 ? enumConstants[0] : null;
	}

}
