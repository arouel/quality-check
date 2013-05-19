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
package net.sf.qualitytest.blueprint.strategy.matching;

import java.lang.reflect.Method;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.MatchingStrategy;

/**
 * Matches all methods, which belong to a class implementing the builder pattern. Matching methods are public methods of
 * any class ending in 'Builder' (configurable) and that take exactly one argument.
 * 
 * This matching strategy does never match by type.
 * 
 * @author Dominik Seichter
 * 
 */
public class BuilderMethodMatchingStrategy implements MatchingStrategy {

	private static final String BUILDER_SUFFIX = "Builder";

	private final String builderSuffix;

	/**
	 * Create a {@code BuilerMethodMatchingStrategy} with the default suffix 'Builder' to detect classes implementing
	 * the builder pattern.
	 */
	public BuilderMethodMatchingStrategy() {
		builderSuffix = BUILDER_SUFFIX;
	}

	/**
	 * Create a {@code BuilerMethodMatchingStrategy} with a configured suffix to detect classes implementing the builder
	 * pattern.
	 * 
	 * @param suffix
	 *            Suffix of builder classes. Must not be null.
	 */
	public BuilderMethodMatchingStrategy(@Nonnull final String suffix) {
		builderSuffix = Check.notNull(suffix, "suffix");
	}

	@Override
	public boolean matchesByMethod(final Method method) {
		Check.notNull(method, "method");

		final boolean takesOneParameter = (method.getParameterTypes().length == 1);
		final boolean isOnBuilder = method.getDeclaringClass().getName().endsWith(builderSuffix);

		return takesOneParameter && isOnBuilder;
	}

	@Override
	public boolean matchesByType(final Class<?> clazz) {
		return false;
	}

}
