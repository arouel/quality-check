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
package net.sf.qualitytest.blueprint.strategy.creation;

import javax.annotation.Nullable;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CreationStrategy;

/**
 * Strategy to iterate through a range of values, whenever a new value is requested. This is useful to create tests with
 * different values for a type, which are reproduceable.
 * 
 * 
 * @author Dominik Seichter
 */
public class IterateValueCreationStrategy<T> implements CreationStrategy<T> {

	private final T[] values;
	private int index = 0;

	@ArgumentsChecked
	public IterateValueCreationStrategy(final T... values) {
		this.values = Check.noNullElements(values, "values");
	}

	@Override
	@Nullable
	public T createValue(final Class<?> expectedClazz, final BlueprintConfiguration config, final BlueprintSession session) {
		return values[index++ % values.length];
	}

}
