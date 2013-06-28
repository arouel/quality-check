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
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.CycleHandlingStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.BlueprintCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.DefaultArrayCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.DefaultEnumCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.ArrayTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.InterfaceOfTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.SetterMethodMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeMatchingStrategy;

import com.google.common.collect.ImmutableList;

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
		list.add(new StrategyPair(new TypeMatchingStrategy(Collection.class), new SingleValueCreationStrategy<Collection<Object>>(
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
		list.add(createStrategyPair(String.class, STRING_DEFAULT));
		list.add(createStrategyPair(Long.class, LONG_DEFAULT));
		list.add(createStrategyPair(long.class, LONG_DEFAULT));
		list.add(createStrategyPair(Integer.class, INTEGER_DEFAULT));
		list.add(createStrategyPair(int.class, INTEGER_DEFAULT));
		list.add(createStrategyPair(Boolean.class, BOOLEAN_DEFAULT));
		list.add(createStrategyPair(boolean.class, BOOLEAN_DEFAULT));
		list.add(createStrategyPair(Character.class, CHARACTER_DEFAULT));
		list.add(createStrategyPair(char.class, CHARACTER_DEFAULT));
		list.add(createStrategyPair(Short.class, SHORT_DEFAULT));
		list.add(createStrategyPair(short.class, SHORT_DEFAULT));
		list.add(createStrategyPair(Byte.class, BYTE_DEFAULT));
		list.add(createStrategyPair(byte.class, BYTE_DEFAULT));
		list.add(createStrategyPair(Float.class, FLOAT_DEFAULT));
		list.add(createStrategyPair(float.class, FLOAT_DEFAULT));
		list.add(createStrategyPair(Double.class, DOUBLE_DEFAULT));
		list.add(createStrategyPair(double.class, DOUBLE_DEFAULT));

		addDefaultEnumStrategy(list);
		addDefaultArrayStrategy(list);
		addDefaultCollections(list);
		list.add(new StrategyPair(new SetterMethodMatchingStrategy(), new BlueprintCreationStrategy()));

		return list;
	}

	private static <T> StrategyPair createStrategyPair(final Class<T> clazz, final T defaultValue) {
		return new StrategyPair(new TypeMatchingStrategy(clazz), new SingleValueCreationStrategy<T>(defaultValue));
	}

	public DefaultBlueprintConfiguration() {
		super(createDefaultAttributeMapping(), ImmutableList.<CycleHandlingStrategy<?>> of(), false);
	}
}
