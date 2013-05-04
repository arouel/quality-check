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
package net.sf.qualitytest.blueprint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Definition of an interface to create values for attribute assignments.
 * 
 * @author Dominik Seichter
 */
public interface CreationStrategy<T> {

	/**
	 * Create a new value which can be assigned to an attribute.
	 * 
	 * @param <T>
	 *            Type parameter of the return type
	 * @param expectedClazz
	 *            which is requested.
	 * @param config
	 *            a {@code BlueprintConfiguration}
	 * @param session
	 *            A {@code BlueprintSession} * @return a new value
	 */
	@Nullable
	T createValue(@Nonnull final Class<?> expectedClazz, @Nonnull final BlueprintConfiguration config,
			@Nonnull final BlueprintSession session);

}
