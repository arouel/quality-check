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
package net.sf.qualitytest.blueprint.strategy.matching;

import java.lang.reflect.Method;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.MatchingStrategy;

/**
 * This value matching strategy matches all methods that are setters, i.e. start with the prefix 'set'.
 * 
 * This matching strategy does never match by type.
 * 
 * @author Dominik Seichter
 */
public class SetterMethodMatchingStrategy implements MatchingStrategy {

	private static final String SETTER_PREFIX = "set";

	@Override
	public boolean matchesByMethod(final Method method) {
		Check.notNull(method, "method");

		return method.getName().startsWith(SETTER_PREFIX);
	}

	@Override
	public boolean matchesByType(final Class<?> clazz) {
		return false;
	}

}
