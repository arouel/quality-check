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

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.ValueCreationStrategy;
import net.sf.qualitytest.blueprint.ValueMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.CaseInsensitiveValueMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeValueMatchingStrategy;

import com.google.common.collect.ImmutableMap;

/**
 * Configure how blueprinting is done. A BlueprintConfiguration defines how the values for certain attributes are
 * generated.
 * 
 * This class is immutable all modifier methods have to return a new instance of this class.
 * 
 * @author Dominik Seichter
 */
class ImmutableBlueprintConfiguration implements BlueprintConfiguration {

	/**
	 * Mapping of class members
	 */
	private final Map<ValueMatchingStrategy, ValueCreationStrategy<?>> mapping;
	private final boolean withPublicAttributes;

	/**
	 * Create an empty {@code BlueprintConfiguration}
	 */
	public ImmutableBlueprintConfiguration() {
		mapping = ImmutableMap.of();
		withPublicAttributes = false;
	}

	protected ImmutableBlueprintConfiguration(final Map<ValueMatchingStrategy, ValueCreationStrategy<?>> attributeMapping,
			final boolean withPublicAttributes) {
		Check.notNull(attributeMapping, "attributeMapping");

		mapping = ImmutableMap.copyOf(attributeMapping);
		this.withPublicAttributes = withPublicAttributes;
	}

	@Override
	@Throws(IllegalNullArgumentException.class)
	public ValueCreationStrategy<?> findCreationStrategyForMethod(final Method method) {
		Check.notNull(method, "method");

		for (final Map.Entry<ValueMatchingStrategy, ValueCreationStrategy<?>> entry : mapping.entrySet()) {
			if (entry.getKey().matches(method.getName())) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	@Throws(IllegalNullArgumentException.class)
	public ValueCreationStrategy<?> findCreationStrategyForType(final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		for (final Map.Entry<ValueMatchingStrategy, ValueCreationStrategy<?>> entry : mapping.entrySet()) {
			if (entry.getKey().matches(clazz)) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	public Map<ValueMatchingStrategy, ValueCreationStrategy<?>> getAttributeMappings() {
		return mapping;
	}

	@Override
	public boolean isWithPublicAttributes() {
		return withPublicAttributes;
	}

	@Override
	@Throws(IllegalNullArgumentException.class)
	public <T> T object(final Class<T> clazz) {
		return Blueprint.object(clazz, this, new BlueprintSession());
	}

	@Override
	@Throws(IllegalNullArgumentException.class)
	public <T> BlueprintConfiguration with(final Class<T> type, final T value) {
		return with(new TypeValueMatchingStrategy(type), new SingleValueCreationStrategy<T>(value));
	}

	@Override
	@Throws(IllegalNullArgumentException.class)
	public <T> BlueprintConfiguration with(final String name, final T value) {
		return with(new CaseInsensitiveValueMatchingStrategy(name), new SingleValueCreationStrategy<T>(value));
	}

	@Override
	@Throws(IllegalNullArgumentException.class)
	public BlueprintConfiguration with(final ValueMatchingStrategy matcher, final ValueCreationStrategy<?> creator) {
		Check.notNull(matcher, "matcher");
		Check.notNull(creator, "creator");

		final Map<ValueMatchingStrategy, ValueCreationStrategy<?>> mapping = new HashMap<ValueMatchingStrategy, ValueCreationStrategy<?>>();
		mapping.putAll(this.mapping);
		mapping.put(matcher, creator);
		return new ImmutableBlueprintConfiguration(mapping, withPublicAttributes);
	}

	@Override
	public BlueprintConfiguration withPublicAttributes(final boolean withPublicAttributes) {
		return new ImmutableBlueprintConfiguration(mapping, withPublicAttributes);
	}

}
