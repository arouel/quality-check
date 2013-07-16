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
package net.sf.qualitytest.blueprint;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNotNullArgumentException;
import net.sf.qualitytest.blueprint.strategy.matching.BuilderMethodMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.CaseInsensitiveMethodNameMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.InstanceOfTypeMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.SetterMethodMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeMatchingStrategy;

/**
 * A utility class to have a more readable API. This class holds possible method matching strategies.
 * 
 * @author Dominik Seichter
 * 
 */
public final class Match {

	/**
	 * Match all public methods of classes with a name ending in "Builder".
	 */
	public static final MatchingStrategy BUILDER_METHODS = new BuilderMethodMatchingStrategy();

	/**
	 * Match all methods which are setter-methods.
	 */
	public static final MatchingStrategy SETTER_METHODS = new SetterMethodMatchingStrategy();

	/**
	 * Match all classes that are instances of a type.
	 * 
	 * @param clazz
	 *            Supertype that is matched
	 * @return a {@code MatchingStrategy} which matches the given type.
	 */
	@ArgumentsChecked
	@Throws(IllegalNotNullArgumentException.class)
	public static MatchingStrategy instanceOf(final Class<?> clazz) {
		return new InstanceOfTypeMatchingStrategy(Check.notNull(clazz, "clazz"));
	}

	/**
	 * Match by method or attribute name..
	 * 
	 * @param name
	 *            Name of an attribute or method that is matched.
	 * @return a {@code MatchingStrategy} which matches the given type.
	 */
	@ArgumentsChecked
	@Throws(IllegalEmptyArgumentException.class)
	public static MatchingStrategy name(final String name) {
		return new CaseInsensitiveMethodNameMatchingStrategy(Check.notEmpty(name, "name"));
	}

	/**
	 * Match a type.
	 * 
	 * @param clazz
	 *            Type that is matched
	 * @return a {@code MatchingStrategy} which matches the given type.
	 */
	@ArgumentsChecked
	@Throws(IllegalNotNullArgumentException.class)
	public static MatchingStrategy type(final Class<?> clazz) {
		return new TypeMatchingStrategy(Check.notNull(clazz, "clazz"));
	}

	private Match() {
		// Do not create instances of this class
	}
}
