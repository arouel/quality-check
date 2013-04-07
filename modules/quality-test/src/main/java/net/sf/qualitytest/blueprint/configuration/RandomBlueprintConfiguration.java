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
package net.sf.qualitytest.blueprint.configuration;

import java.util.HashMap;
import java.util.Map;

import net.sf.qualitytest.blueprint.ValueCreationStrategy;
import net.sf.qualitytest.blueprint.ValueMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.BlueprintStringCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomBooleanValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomByteValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomCharValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomDoubleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomFloatValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomIntValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomLongValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomShortValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeValueMatchingStrategy;

/**
 * {@code BlueprintConfiguration} which assigns all primitive types and their corresponding object types with a random
 * value.
 * 
 * Additionally, support for the interfaces {@code java.util.List}, {@code java.util.Set} and {@code java.util.Map} is
 * added. If these interface are encountered, empty lists, maps and sets are created.
 * 
 * @author Dominik Seichter
 * 
 */
public final class RandomBlueprintConfiguration extends ImmutableBlueprintConfiguration {

	private static final ValueCreationStrategy<Long> LONG_DEFAULT = new RandomLongValueCreationStrategy();
	private static final ValueCreationStrategy<Integer> INTEGER_DEFAULT = new RandomIntValueCreationStrategy();
	private static final ValueCreationStrategy<Boolean> BOOLEAN_DEFAULT = new RandomBooleanValueCreationStrategy();
	private static final ValueCreationStrategy<Character> CHARACTER_DEFAULT = new RandomCharValueCreationStrategy();
	private static final ValueCreationStrategy<Short> SHORT_DEFAULT = new RandomShortValueCreationStrategy();
	private static final ValueCreationStrategy<Byte> BYTE_DEFAULT = new RandomByteValueCreationStrategy();
	private static final ValueCreationStrategy<Float> FLOAT_DEFAULT = new RandomFloatValueCreationStrategy();
	private static final ValueCreationStrategy<Double> DOUBLE_DEFAULT = new RandomDoubleValueCreationStrategy();

	private static Map<ValueMatchingStrategy, ValueCreationStrategy<?>> createDefaultAttributeMapping() {
		final Map<ValueMatchingStrategy, ValueCreationStrategy<?>> map = new HashMap<ValueMatchingStrategy, ValueCreationStrategy<?>>();
		map.put(new TypeValueMatchingStrategy(String.class), new BlueprintStringCreationStrategy());
		map.put(new TypeValueMatchingStrategy(Long.class), LONG_DEFAULT);
		map.put(new TypeValueMatchingStrategy(long.class), LONG_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Integer.class), INTEGER_DEFAULT);
		map.put(new TypeValueMatchingStrategy(int.class), INTEGER_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Boolean.class), BOOLEAN_DEFAULT);
		map.put(new TypeValueMatchingStrategy(boolean.class), BOOLEAN_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Character.class), CHARACTER_DEFAULT);
		map.put(new TypeValueMatchingStrategy(char.class), CHARACTER_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Short.class), SHORT_DEFAULT);
		map.put(new TypeValueMatchingStrategy(short.class), SHORT_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Byte.class), BYTE_DEFAULT);
		map.put(new TypeValueMatchingStrategy(byte.class), BYTE_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Float.class), FLOAT_DEFAULT);
		map.put(new TypeValueMatchingStrategy(float.class), FLOAT_DEFAULT);
		map.put(new TypeValueMatchingStrategy(Double.class), DOUBLE_DEFAULT);
		map.put(new TypeValueMatchingStrategy(double.class), DOUBLE_DEFAULT);

		DefaultBlueprintConfiguration.addDefaultCollections(map);

		return map;
	}

	public RandomBlueprintConfiguration() {
		super(createDefaultAttributeMapping());
	}

}
