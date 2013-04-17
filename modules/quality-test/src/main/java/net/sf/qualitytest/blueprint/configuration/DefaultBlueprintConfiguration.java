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
import net.sf.qualitytest.blueprint.CreationStrategy;
import net.sf.qualitytest.blueprint.MatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.DefaultArrayCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.DefaultEnumCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.ArrayTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.InterfaceOfTypeValueMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeValueMatchingStrategy;

/**
 * Default {@code BlueprintConfiguration} which assigns all primitive types and their corresponding object types which a
 * default value, which is usually {@code 0}.
 * 
 * Additionally, support for the interfaces {@code java.util.List}, {@code java.util.Set} and {@code java.util.Map} is
 * added. If these interface are encountered, empty lists, maps and sets are created.
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
	 * Add the default array handling to a map.
	 * 
	 * @param map
	 */
	public static void addDefaultArrayStrategy(final Map<MatchingStrategy, CreationStrategy<?>> map) {
		map.put(new ArrayTypeMatchingStrategy(), new DefaultArrayCreationStrategy(DefaultArrayCreationStrategy.DEFAULT_LENGTH));
	}

	/**
	 * Add the default implementations for collection interfaces to a map.
	 * 
	 * @param map
	 */
	public static void addDefaultCollections(final Map<MatchingStrategy, CreationStrategy<?>> map) {
		Check.notNull(map, "map");

		map.put(new TypeValueMatchingStrategy(Map.class), new SingleValueCreationStrategy<Map<Object, Object>>(
				new HashMap<Object, Object>()));
		map.put(new TypeValueMatchingStrategy(Set.class), new SingleValueCreationStrategy<Set<Object>>(new HashSet<Object>()));
		map.put(new TypeValueMatchingStrategy(List.class), new SingleValueCreationStrategy<List<Object>>(new ArrayList<Object>()));
	}

	/**
	 * Add the default enum handling to a map.
	 * 
	 * @param map
	 */
	public static void addDefaultEnumStrategy(final Map<MatchingStrategy, CreationStrategy<?>> map) {
		map.put(new InterfaceOfTypeValueMatchingStrategy(Enum.class), new DefaultEnumCreationStrategy());
	}

	private static Map<MatchingStrategy, CreationStrategy<?>> createDefaultAttributeMapping() {
		final Map<MatchingStrategy, CreationStrategy<?>> map = new HashMap<MatchingStrategy, CreationStrategy<?>>();
		map.put(new TypeValueMatchingStrategy(String.class), new SingleValueCreationStrategy<String>(STRING_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Long.class), new SingleValueCreationStrategy<Long>(LONG_DEFAULT));
		map.put(new TypeValueMatchingStrategy(long.class), new SingleValueCreationStrategy<Long>(LONG_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Integer.class), new SingleValueCreationStrategy<Integer>(INTEGER_DEFAULT));
		map.put(new TypeValueMatchingStrategy(int.class), new SingleValueCreationStrategy<Integer>(INTEGER_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Boolean.class), new SingleValueCreationStrategy<Boolean>(BOOLEAN_DEFAULT));
		map.put(new TypeValueMatchingStrategy(boolean.class), new SingleValueCreationStrategy<Boolean>(BOOLEAN_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Character.class), new SingleValueCreationStrategy<Character>(CHARACTER_DEFAULT));
		map.put(new TypeValueMatchingStrategy(char.class), new SingleValueCreationStrategy<Character>(CHARACTER_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Short.class), new SingleValueCreationStrategy<Short>(SHORT_DEFAULT));
		map.put(new TypeValueMatchingStrategy(short.class), new SingleValueCreationStrategy<Short>(SHORT_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Byte.class), new SingleValueCreationStrategy<Byte>(BYTE_DEFAULT));
		map.put(new TypeValueMatchingStrategy(byte.class), new SingleValueCreationStrategy<Byte>(BYTE_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Float.class), new SingleValueCreationStrategy<Float>(FLOAT_DEFAULT));
		map.put(new TypeValueMatchingStrategy(float.class), new SingleValueCreationStrategy<Float>(FLOAT_DEFAULT));
		map.put(new TypeValueMatchingStrategy(Double.class), new SingleValueCreationStrategy<Double>(DOUBLE_DEFAULT));
		map.put(new TypeValueMatchingStrategy(double.class), new SingleValueCreationStrategy<Double>(DOUBLE_DEFAULT));

		addDefaultEnumStrategy(map);
		addDefaultArrayStrategy(map);
		addDefaultCollections(map);

		return map;
	}

	public DefaultBlueprintConfiguration() {
		super(createDefaultAttributeMapping(), false);
	}
}
