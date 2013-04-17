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
 * Match all class types that are arrays.
 * 
 * @author Dominik Seichter
 */
public class ArrayTypeMatchingStrategy implements MatchingStrategy {

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

		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		return prime;
	}

	@Override
	public boolean matches(final Class<?> clazz) {
		Check.notNull(clazz, "clazz");
		return clazz.isArray();
	}

	@Override
	public boolean matches(final String method) {
		return false;
	}

}
