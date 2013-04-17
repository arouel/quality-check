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
 * Match a value based on its type or supertype.
 * 
 * @author Dominik Seichter
 */
public class InterfaceOfTypeValueMatchingStrategy implements MatchingStrategy {

	private final Class<?> clazz;

	public InterfaceOfTypeValueMatchingStrategy(final Class<?> clazz) {
		this.clazz = Check.notNull(clazz);
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
		final InterfaceOfTypeValueMatchingStrategy other = (InterfaceOfTypeValueMatchingStrategy) obj;
		if (clazz == null) {
			if (other.clazz != null) {
				return false;
			}
		} else if (!clazz.equals(other.clazz)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		return result;
	}

	@Override
	public boolean matches(final Class<?> clazz) {
		Check.notNull(clazz, "clazz");
		return this.clazz.isAssignableFrom(clazz);
	}

	@Override
	public boolean matches(final String method) {
		return false;
	}

}
