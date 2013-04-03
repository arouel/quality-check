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

import java.util.Random;

import net.sf.qualitytest.blueprint.ValueCreationStrategy;

/**
 * Strategy to create a random boolean value.
 * 
 * @author Dominik Seichter
 */
public class RandomBooleanValueCreationStrategy implements ValueCreationStrategy<Boolean> {

	private final Random random = new Random();

	@Override
	public Boolean createValue() {
		return Boolean.valueOf(random.nextBoolean());
	}
}
