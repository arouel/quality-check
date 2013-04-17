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
package net.sf.qualitytest.blueprint.strategy.creation;

import net.sf.qualitytest.blueprint.ValueCreationStrategy;

/**
 * Always use the same value. This strategy simply returns the same instance of the same object every time it is called.
 * 
 * @param <T>
 *            a specific type
 * 
 * @author Dominik Seichter
 */
public class SingleValueCreationStrategy<T> implements ValueCreationStrategy<T> {

	private final T obj;

	public SingleValueCreationStrategy(final T obj) {
		this.obj = obj;
	}

	@Override
	public T createValue(final Class<?> expectedClass) {
		return obj;
	}

}
