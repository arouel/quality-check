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

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.MatchingStrategy;

/**
 * This value matching strategy matches string names case insensitively on the method name. This works only for
 * setter-based blueprinting. In other cases you should use type based blueprinting as in
 * {@code TypeValueMatchingStrategy}.
 * 
 * 
 * @author Dominik Seichter
 */
public class CaseInsensitiveValueMatchingStrategy implements MatchingStrategy {

	private static final String SETTER_PREFIX = "set";

	private final String name;

	public CaseInsensitiveValueMatchingStrategy(final String name) {
		this.name = Check.notNull(name);
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
		final CaseInsensitiveValueMatchingStrategy other = (CaseInsensitiveValueMatchingStrategy) obj;
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

	/**
	 * This strategy does never match a type!
	 * 
	 * @return false
	 */
	@Override
	public boolean matches(final Class<?> clazz) {
		return false;
	}

	@Override
	public boolean matches(final String methodName) {
		Check.notNull(methodName, "methodName");
		final String setterName = SETTER_PREFIX + name;
		return name.equalsIgnoreCase(methodName) || setterName.equalsIgnoreCase(methodName);
	}

}
