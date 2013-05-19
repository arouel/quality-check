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

import java.lang.reflect.Array;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CreationStrategy;

public class DefaultArrayCreationStrategy implements CreationStrategy<Object> {

	public static final int DEFAULT_LENGTH = 7;

	/**
	 * Initalize an array using blueprinted objects.
	 * 
	 * @param <T>
	 *            Type parameter of the array.
	 * @param array
	 *            class of the array
	 * @param arraySize
	 *            size of the array
	 * @param value
	 *            object where the array is stored
	 * @param config
	 *            A {@code BlueprintConfiguration}
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static <T> void initializeArray(final Class<T> array, final int arraySize, final Object value,
			final BlueprintConfiguration config, final BlueprintSession session) {
		for (int i = 0; i < arraySize; i++) {
			final Object blueprint = Blueprint.construct(array.getComponentType(), config, session);
			Array.set(value, i, blueprint);
		}
	}

	private final int length;

	public DefaultArrayCreationStrategy(final int length) {
		this.length = length;
	}

	@Override
	public Object createValue(final Class<?> expectedClass, final BlueprintConfiguration config, final BlueprintSession session) {
		Check.notNull(expectedClass, "expectedClass");

		final Object value = Array.newInstance(expectedClass.getComponentType(), length);

		if (!expectedClass.getComponentType().isPrimitive()) {
			initializeArray(expectedClass, length, value, config, session);
		}

		return value;
	}

}
