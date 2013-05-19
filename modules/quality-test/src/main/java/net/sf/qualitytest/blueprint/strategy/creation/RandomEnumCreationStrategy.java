/*******************************************************************************
 * Copyright 2013 Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.qualitytest.blueprint.strategy.creation;

import java.util.Random;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

/**
 * Creation strategy which creates a random enum constant during construction of a blueprint.
 * 
 * @author Dominik Seichter
 */
public class RandomEnumCreationStrategy extends ValueCreationStrategy<Enum<?>> {

	private final Random random = new Random();

	/**
	 * Blueprint an enum value using the default configuration.
	 * <p>
	 * This method returns the a randomly selected value of a given enumeration.
	 * 
	 * @param expectedClazz
	 *            the class of an enumeration
	 * @return a valid enum value
	 */
	@Override
	@Throws({ IllegalNullArgumentException.class, IllegalStateOfArgumentException.class })
	public Enum<?> createValue(final Class<?> expectedClazz) {
		Check.notNull(expectedClazz, "expectedClazz");
		Check.stateIsTrue(expectedClazz.isEnum(), "Argument 'expectedClazz' must be an enum.");
		@SuppressWarnings("unchecked")
		final Class<? extends Enum<?>> enumClazz = (Class<? extends Enum<?>>) expectedClazz;
		final Enum<?>[] enumConstants = enumClazz.getEnumConstants();
		final int index = enumConstants.length > 0 ? random.nextInt(enumConstants.length) : -1;
		return enumConstants.length > 0 ? enumConstants[index] : null;
	}

}
