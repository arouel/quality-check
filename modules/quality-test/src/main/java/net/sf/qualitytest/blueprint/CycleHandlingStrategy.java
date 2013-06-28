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
package net.sf.qualitytest.blueprint;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Interface for strategies to decide how cycles in the blueprinting graph are supposed to be handled.
 * 
 * @author dominik.seichter
 * 
 * @param <T>
 */
public interface CycleHandlingStrategy<T> {

	/**
	 * Handle the situation that a BlueprintCycle was detected for a particular class. Is only called if
	 * {@code isActiveForType} is true for a specific class.
	 * 
	 * @see Blueprint
	 * 
	 * @param <T>
	 * @param session
	 *            The current {@link BlueprintSession}
	 * @param clazz
	 *            The class which caused cycle in the blueprinting graph
	 * @return a blue printed instance of {@code T}
	 */
	@Nullable
	T handleCycle(@Nonnull final BlueprintSession session, @Nonnull final Class<?> clazz);

	/**
	 * Decide whether this strategy is active for a given type.
	 * 
	 * @see Blueprint
	 * 
	 * @param <T>
	 * @param clazz
	 *            The class which caused cycle in the blueprinting graph
	 * @return a blue printed instance of {@code T}
	 */
	boolean isActiveForType(@Nonnull final Class<?> clazz);
}
