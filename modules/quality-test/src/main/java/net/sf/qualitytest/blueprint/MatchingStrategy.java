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

import net.sf.qualitytest.blueprint.strategy.creation.ValueCreationStrategy;

/**
 * Strategy to determine if an attribute matches and should be replaced with a certain value.
 * 
 * An attribute can be matched by name or type.
 * 
 * A {@link MatchingStrategy} should implement hashCode so that matching strategies for the same object/type can be
 * detected and the last one added can be used.
 * 
 * @see ValueCreationStrategy
 * 
 * @author Dominik Seichter
 */
public interface MatchingStrategy {

	/**
	 * Test if a method matches this strategy.
	 * 
	 * @param method
	 *            A method
	 * 
	 * @return true if the strategy matches and the {@code ValueCreationStrategy} should be applied
	 */
	boolean matchesByMethod(final Method method);

	/**
	 * Test if a type matches this strategy.
	 * 
	 * @param clazz
	 *            a clazz type
	 * 
	 * @return true if the strategy matches and the {@code ValueCreationStrategy} should be applied
	 */
	boolean matchesByType(final Class<?> clazz);
}
