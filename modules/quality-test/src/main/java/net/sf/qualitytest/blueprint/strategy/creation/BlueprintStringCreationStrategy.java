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

import java.util.UUID;


/**
 * Strategy which creates random strings using {@code Blueprint.string()}.
 * 
 * @author Dominik Seichter
 */
public class BlueprintStringCreationStrategy extends ValueCreationStrategy<String> {

	@Override
	public String createValue(final Class<?> expectedClass) {
		return UUID.randomUUID().toString();
	}

}
