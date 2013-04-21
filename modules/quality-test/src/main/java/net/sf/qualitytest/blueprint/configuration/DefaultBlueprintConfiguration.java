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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.strategy.creation.BlueprintCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.DefaultArrayCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.DefaultEnumCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.ArrayTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.InterfaceOfTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.SetterMethodMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeMatchingStrategy;

/**
 * Default {@code BlueprintConfiguration} which assigns all primitive types and their corresponding object types which a
 * default value, which is usually {@code 0}.
 * 
 * Additionally, support for the interfaces {@code java.util.List}, {@code java.util.Set} and {@code java.util.list} is
 * added. If these interface are encountered, empty lists, lists and sets are created.
 * 
 * @author Dominik Seichter
 * 
 */
public final class DefaultBlueprintConfiguration extends ImmutableBlueprintConfiguration {

	private static final String STRING_DEFAULT = "";
	private static final Long LONG_DEFAULT = Long.valueOf(0l);
	private static final Integer INTEGER_DEFAULT = Integer.valueOf(0);
	private static final Boolean BOOLEAN_DEFAULT = Boolean.FALSE;
	private static final Character CHARACTER_DEFAULT = Character.valueOf((char) 0);
	private static final Short SHORT_DEFAULT = Short.valueOf((short) 0);
	private static final Byte BYTE_DEFAULT = Byte.valueOf((byte) 0);
	private static final Float FLOAT_DEFAULT = Float.valueOf(0.0f);
	private static final Double DOUBLE_DEFAULT = Double.valueOf(0.0);

	/**
	 * Add the default array handling to a list.
	 * 
	 * @param list
	 */
	public static void addDefaultArrayStrategy(final List<StrategyPair> list) {
		list.add(new StrategyPair(new ArrayTypeMatchingStrategy(), new DefaultArrayCreationStrategy(
				DefaultArrayCreationStrategy.DEFAULT_LENGTH)));
	}

	/**
	 * Add the default implementations for collection interfaces to a list.
	 * 
	 * @param list
	 */
	public static void addDefaultCollections(final List<StrategyPair> list) {
		Check.notNull(list, "list");

		list.add(new StrategyPair(new TypeMatchingStrategy(Map.class), new SingleValueCreationStrategy<Map<Object, Object>>(
				new HashMap<Object, Object>())));
		list.add(new StrategyPair(new TypeMatchingStrategy(Set.class), new SingleValueCreationStrategy<Set<Object>>(new HashSet<Object>())));
		list.add(new StrategyPair(new TypeMatchingStrategy(List.class), new SingleValueCreationStrategy<List<Object>>(
				new ArrayList<Object>())));
	}

	/**
	 * Add the default enum handling to a list.
	 * 
	 * @param list
	 */
	public static void addDefaultEnumStrategy(final List<StrategyPair> list) {
		list.add(new StrategyPair(new InterfaceOfTypeMatchingStrategy(Enum.class), new DefaultEnumCreationStrategy()));
	}

	private static List<StrategyPair> createDefaultAttributeMapping() {
		final List<StrategyPair> list = new ArrayList<StrategyPair>();
		list.add(new StrategyPair(new TypeMatchingStrategy(String.class), new SingleValueCreationStrategy<String>(STRING_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Long.class), new SingleValueCreationStrategy<Long>(LONG_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(long.class), new SingleValueCreationStrategy<Long>(LONG_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Integer.class), new SingleValueCreationStrategy<Integer>(INTEGER_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(int.class), new SingleValueCreationStrategy<Integer>(INTEGER_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Boolean.class), new SingleValueCreationStrategy<Boolean>(BOOLEAN_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(boolean.class), new SingleValueCreationStrategy<Boolean>(BOOLEAN_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Character.class), new SingleValueCreationStrategy<Character>(CHARACTER_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(char.class), new SingleValueCreationStrategy<Character>(CHARACTER_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Short.class), new SingleValueCreationStrategy<Short>(SHORT_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(short.class), new SingleValueCreationStrategy<Short>(SHORT_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Byte.class), new SingleValueCreationStrategy<Byte>(BYTE_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(byte.class), new SingleValueCreationStrategy<Byte>(BYTE_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Float.class), new SingleValueCreationStrategy<Float>(FLOAT_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(float.class), new SingleValueCreationStrategy<Float>(FLOAT_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(Double.class), new SingleValueCreationStrategy<Double>(DOUBLE_DEFAULT)));
		list.add(new StrategyPair(new TypeMatchingStrategy(double.class), new SingleValueCreationStrategy<Double>(DOUBLE_DEFAULT)));

		addDefaultEnumStrategy(list);
		addDefaultArrayStrategy(list);
		addDefaultCollections(list);
		list.add(new StrategyPair(new SetterMethodMatchingStrategy(), new BlueprintCreationStrategy()));

		return list;
	}

	public DefaultBlueprintConfiguration() {
		super(createDefaultAttributeMapping(), false);
	}
}
