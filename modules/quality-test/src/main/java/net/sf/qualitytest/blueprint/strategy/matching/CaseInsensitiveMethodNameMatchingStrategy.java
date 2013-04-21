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

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.MatchingStrategy;

/**
 * This value matching strategy matches string names case insensitively on the method name. This works for setter-based
 * blueprinting.
 * 
 * This {@code MatchingStrategy} does never match by type.
 * 
 * @author Dominik Seichter
 */
public class CaseInsensitiveMethodNameMatchingStrategy implements MatchingStrategy {

	private static final String SETTER_PREFIX = "set";

	private final String name;
	private final String prefix;

	/**
	 * Create a new {@code CaseInsensitiveMethodNameMatchingStrategy} which matches method names by default such as
	 * 'name' or 'setName' case insensitively.
	 * 
	 * @param name
	 *            The name to be matched (must not be empty or {@code null}.
	 */
	public CaseInsensitiveMethodNameMatchingStrategy(final String name) {
		this.name = Check.notEmpty(name, "name");
		prefix = SETTER_PREFIX;
	}

	/**
	 * Create a new {@code CaseInsensitiveMethodNameMatchingStrategy} which matches method names by default such as
	 * 'name' or prefix + 'Name' case insensitively.
	 * 
	 * @param name
	 *            The name to be matched (must not be empty or {@code null}.
	 * @param prefix
	 *            An optional prefix that is matched before the name (default is 'set'). Maybe empty but not
	 *            {@code null}.
	 */
	public CaseInsensitiveMethodNameMatchingStrategy(final String name, final String prefix) {
		this.name = Check.notEmpty(name);
		this.prefix = Check.notNull(prefix);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CaseInsensitiveMethodNameMatchingStrategy other = (CaseInsensitiveMethodNameMatchingStrategy) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equalsIgnoreCase(other.name)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.toLowerCase().hashCode());
		return result;
	}

	@Override
	public boolean matchesByMethod(final Method method) {
		Check.notNull(method, "method");
		final String methodName = method.getName();
		final String setterName = prefix + name;
		return name.equalsIgnoreCase(methodName) || setterName.equalsIgnoreCase(methodName);
	}

	/**
	 * This strategy does never match a type!
	 * 
	 * @return false
	 */
	@Override
	public boolean matchesByType(final Class<?> clazz) {
		return false;
	}

}
