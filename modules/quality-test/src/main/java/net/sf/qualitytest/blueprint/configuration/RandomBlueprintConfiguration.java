/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
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

import net.sf.qualitytest.blueprint.CycleHandlingStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.BlueprintCreationStrategy;
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
import net.sf.qualitytest.blueprint.strategy.matching.InterfaceOfTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.SetterMethodMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeMatchingStrategy;

import com.google.common.collect.ImmutableList;

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
		list.add(new StrategyPair(new InterfaceOfTypeMatchingStrategy(Enum.class), new RandomEnumCreationStrategy()));
	}

	private static List<StrategyPair> createDefaultAttributeMapping() {
		final List<StrategyPair> list = new ArrayList<StrategyPair>();
		list.add(new StrategyPair(new TypeMatchingStrategy(String.class), new BlueprintStringCreationStrategy()));
		list.add(new StrategyPair(new TypeMatchingStrategy(Long.class), LONG_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(long.class), LONG_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Integer.class), INTEGER_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(int.class), INTEGER_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Boolean.class), BOOLEAN_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(boolean.class), BOOLEAN_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Character.class), CHARACTER_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(char.class), CHARACTER_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Short.class), SHORT_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(short.class), SHORT_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Byte.class), BYTE_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(byte.class), BYTE_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Float.class), FLOAT_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(float.class), FLOAT_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(Double.class), DOUBLE_DEFAULT));
		list.add(new StrategyPair(new TypeMatchingStrategy(double.class), DOUBLE_DEFAULT));

		addRandomEnumStrategy(list);
		DefaultBlueprintConfiguration.addDefaultArrayStrategy(list);
		DefaultBlueprintConfiguration.addDefaultCollections(list);
		list.add(new StrategyPair(new SetterMethodMatchingStrategy(), new BlueprintCreationStrategy()));

		return list;
	}

	public RandomBlueprintConfiguration() {
		super(createDefaultAttributeMapping(), ImmutableList.<CycleHandlingStrategy<?>> of(), false);
	}
}
