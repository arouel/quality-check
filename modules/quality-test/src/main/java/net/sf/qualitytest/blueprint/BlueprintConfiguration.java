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
package net.sf.qualitytest.blueprint;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.CaseInsensitiveValueMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeValueMatchingStrategy;

/**
 * Configure how blueprinting is done. A BlueprintConfiguration defines how the values for certain attributes are
 * generated.
 * 
 * This class is immutable all modifier methods have to return a new instance of this class.
 * 
 * @author Dominik Seichter
 */
public class BlueprintConfiguration implements Cloneable {

	private final Map<ValueMatchingStrategy, ValueCreationStrategy<?>> attributeMapping = new HashMap<ValueMatchingStrategy, ValueCreationStrategy<?>>();

	public BlueprintConfiguration() {
		// Empty default constructor
	}

	protected BlueprintConfiguration(final Map<ValueMatchingStrategy, ValueCreationStrategy<?>> attributeMapping) {
		Check.notNull(attributeMapping, "attributeMapping");
		this.attributeMapping.putAll(attributeMapping);
	}

	@Override
	public Object clone() {
		final BlueprintConfiguration c = new BlueprintConfiguration();
		c.attributeMapping.putAll(attributeMapping);
		return c;
	}

	/**
	 * Find a creation strategy that matches on the given method.
	 * 
	 * @param method
	 *            A setter method
	 * 
	 * @return a {@code ValueCreationStrategy} or {@code null}
	 */
	public ValueCreationStrategy<?> findCreationStrategyForMethod(final Method method) {
		Check.notNull(method, "method");

		for (final Map.Entry<ValueMatchingStrategy, ValueCreationStrategy<?>> entry : attributeMapping.entrySet()) {
			if (entry.getKey().matches(method.getName())) {
				return entry.getValue();
			}
		}

		return null;
	}

	public ValueCreationStrategy<?> findCreationStrategyForType(final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		for (final Map.Entry<ValueMatchingStrategy, ValueCreationStrategy<?>> entry : attributeMapping.entrySet()) {
			if (entry.getKey().matches(clazz)) {
				return entry.getValue();
			}
		}

		return null;
	}

	public Map<ValueMatchingStrategy, ValueCreationStrategy<?>> getAttributeMappings() {
		return Collections.unmodifiableMap(attributeMapping);
	}

	/**
	 * Blueprint a Java-Object using this configuration.
	 * 
	 * @see Blueprint
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	public <T> T object(final Class<T> clazz) {
		return Blueprint.object(clazz, this);
	}

	/**
	 * Replace every attribute with the typee {@code type} with a given value.
	 * 
	 * @param type
	 *            a Java type.
	 * @param value
	 *            value which should be assigned to the attribute
	 * 
	 * @return the changed blueprint configuration.
	 */
	public <T> BlueprintConfiguration with(final Class<T> type, final T value) {
		return this.with(new TypeValueMatchingStrategy(type), new SingleValueCreationStrategy<T>(value));
	}

	/**
	 * Replace every attribute with the name {@code name} with a given value.
	 * 
	 * @param name
	 *            case insensitive name of an attribute.
	 * @param value
	 *            value which should be assigned to the attribute
	 * 
	 * @return the changed blueprint configuration.
	 */
	public <T> BlueprintConfiguration with(final String name, final T value) {
		return this.with(new CaseInsensitiveValueMatchingStrategy(name), new SingleValueCreationStrategy<T>(value));
	}

	/**
	 * Replace every attribute which matches a given strategy with a given value.
	 * 
	 * @param matcher
	 *            Matching strategy to define if the replaced should be applied.
	 * @param creator
	 *            Creation strategy which actually creates a new value.
	 * 
	 * @return the changed blueprint configuration.
	 */
	public BlueprintConfiguration with(final ValueMatchingStrategy matcher, final ValueCreationStrategy<?> creator) {
		final BlueprintConfiguration config = (BlueprintConfiguration) clone();
		config.attributeMapping.put(Check.notNull(matcher, "matcher"), Check.notNull(creator, "creator"));
		return config;
	}
}
