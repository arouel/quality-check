/*******************************************************************************
 * Copyright 2013 André Rouél
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

import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

/**
 * Defines a blueprint configuration
 * 
 * @author André Rouél
 */
public interface BlueprintConfiguration {

	/**
	 * Find a creation strategy that matches on the given method.
	 * 
	 * @param method
	 *            A setter method
	 * 
	 * @return a {@code ValueCreationStrategy} or {@code null}
	 */
	CreationStrategy<?> findCreationStrategyForMethod(final Method method);

	CreationStrategy<?> findCreationStrategyForType(final Class<?> clazz);

	/**
	 * Retrieve if public attributes are filled during blueprinting.
	 * 
	 * @return {@code true} if public attributes are filled during blueprinting
	 */
	boolean isWithPublicAttributes();

	/**
	 * Construct a Java-Object using a class as a blueprint.
	 * 
	 * @see Blueprint
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	<T> T construct(final Class<T> clazz);

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
	<T> BlueprintConfiguration with(final Class<T> type, final T value);

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
	BlueprintConfiguration with(final MatchingStrategy matcher, final CreationStrategy<?> creator);

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
	<T> BlueprintConfiguration with(final String name, final T value);

	/**
	 * Configure whether public attributes should be filled with values during blueprinting.
	 * 
	 * @param withPublicAttributes
	 *            If {@code true} public attributes are filled during blueprinting. otherwise they are ignored
	 * 
	 * @return the changed blueprint configuration.
	 */
	BlueprintConfiguration withPublicAttributes(final boolean withPublicAttributes);

}
