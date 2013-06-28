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
package net.sf.qualitytest.blueprint.strategy.cycle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CycleHandlingStrategy;

/**
 * A simple strategy which set's the inner-object of a detected cycle to null.
 * 
 * @author dominik.seichter
 * 
 * @param <T>
 */
public class NullCycleHandlingStrategy<T> implements CycleHandlingStrategy<T> {

	private final Class<T> clazz;

	@ArgumentsChecked
	public NullCycleHandlingStrategy(@Nonnull final Class<T> clazz) {
		this.clazz = Check.notNull(clazz, "clazz");
	}

	@Override
	@Nullable
	public T handleCycle(final BlueprintSession session, final Class<?> clazz) {
		return null;
	}

	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	@Override
	public boolean isActiveForType(final Class<?> clazz) {
		Check.notNull(clazz, "clazz");
		return this.clazz.equals(clazz);
	}

}
