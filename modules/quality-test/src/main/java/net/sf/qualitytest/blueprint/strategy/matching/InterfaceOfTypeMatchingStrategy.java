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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.MatchingStrategy;

/**
 * Match a value based on its type or supertype.
 * 
 * This {@code MatchingStrategy} does never match by method.
 * 
 * @author Dominik Seichter
 */
public class InterfaceOfTypeMatchingStrategy implements MatchingStrategy {

	private final Class<?> clazz;

	public InterfaceOfTypeMatchingStrategy(final Class<?> clazz) {
		this.clazz = Check.notNull(clazz);
	}

	@Override
	public boolean matchesByField(final Field field) {
		return false;
	}

	@Override
	public boolean matchesByMethod(final Method method) {
		return false;
	}

	@Override
	public boolean matchesByType(final Class<?> clazz) {
		Check.notNull(clazz, "clazz");
		return this.clazz.isAssignableFrom(clazz);
	}

}
