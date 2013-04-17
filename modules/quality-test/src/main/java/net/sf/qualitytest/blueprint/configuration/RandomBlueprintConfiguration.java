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

import java.util.ArrayList;
import java.util.List;

import net.sf.qualitytest.blueprint.strategy.creation.BlueprintStringCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomBooleanValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomByteValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomCharValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomDoubleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomEnumCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomFloatValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomIntValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomLongValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomShortValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.ValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.InterfaceOfTypeValueMatchingStrategy;
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

	/**
	 * Add the default enum handling to a map.
	 * 
	 * @param map
	 */
	public static void addRandomEnumStrategy(final List<StrategyPair> list) {
		list.add(new StrategyPair(new InterfaceOfTypeValueMatchingStrategy(Enum.class), new RandomEnumCreationStrategy()));
	}

	private static List<StrategyPair> createDefaultAttributeMapping() {
		final List<StrategyPair> list = new ArrayList<StrategyPair>();
		list.add(new StrategyPair(new TypeValueMatchingStrategy(String.class), new BlueprintStringCreationStrategy()));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Long.class), LONG_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(long.class), LONG_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Integer.class), INTEGER_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(int.class), INTEGER_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Boolean.class), BOOLEAN_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(boolean.class), BOOLEAN_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Character.class), CHARACTER_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(char.class), CHARACTER_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Short.class), SHORT_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(short.class), SHORT_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Byte.class), BYTE_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(byte.class), BYTE_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Float.class), FLOAT_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(float.class), FLOAT_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(Double.class), DOUBLE_DEFAULT));
		list.add(new StrategyPair(new TypeValueMatchingStrategy(double.class), DOUBLE_DEFAULT));

		addRandomEnumStrategy(list);
		DefaultBlueprintConfiguration.addDefaultArrayStrategy(list);
		DefaultBlueprintConfiguration.addDefaultCollections(list);

		return list;
	}

	public RandomBlueprintConfiguration() {
		super(createDefaultAttributeMapping(), false);
	}

}
