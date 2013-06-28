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

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNotNullArgumentException;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintExceptionRunnable;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CreationStrategy;
import net.sf.qualitytest.blueprint.SafeInvoke;
import net.sf.qualitytest.blueprint.SafeInvoke.ExceptionRunnable;
import net.sf.qualitytest.exception.BlueprintException;

/**
 * Strategy to create values and increment each new value by 1. This is useful to create tests which are not random but
 * use not the same value for each type every time.
 * 
 * @author Dominik Seichter
 */
public class IncrementValueCreationStrategy<T extends Number> implements CreationStrategy<T> {

	private static final Map<Class<?>, Class<? extends Number>> PRIMITIVE_MAPPING = new HashMap<Class<?>, Class<? extends Number>>();

	static {
		PRIMITIVE_MAPPING.put(Integer.TYPE, Integer.class);
		PRIMITIVE_MAPPING.put(Long.TYPE, Long.class);
		PRIMITIVE_MAPPING.put(Double.TYPE, Double.class);
		PRIMITIVE_MAPPING.put(Float.TYPE, Float.class);
		PRIMITIVE_MAPPING.put(Byte.TYPE, Byte.class);
		PRIMITIVE_MAPPING.put(Short.TYPE, Short.class);
	};

	private static final long DEFAULT_OFFSET = 1L;
	private T currentValue;
	private final long offset;
	private final long maxValueOfType;

	@ArgumentsChecked
	public IncrementValueCreationStrategy(@Nonnull final T initialValue) {
		this.currentValue = Check.notNull(initialValue, "initialValue");
		this.offset = DEFAULT_OFFSET;
		this.maxValueOfType = readMaxValueOfType();
	}

	@ArgumentsChecked
	public IncrementValueCreationStrategy(@Nonnull final T initialValue, final long offset) {
		this.currentValue = Check.notNull(initialValue, "initialValue");
		this.offset = offset;
		this.maxValueOfType = readMaxValueOfType();
	}

	@ArgumentsChecked
	@Throws(IllegalNotNullArgumentException.class)
	public T createValue(final Class<?> expectedClazz, final BlueprintConfiguration config, final BlueprintSession session) {
		Check.notNull(expectedClazz, "expectedClazz");

		final long value = (currentValue.longValue() % maxValueOfType) + offset;
		final String action = MessageFormat.format("Creating value {0} for type {1}.", value, expectedClazz);
		currentValue = SafeInvoke.invoke(new BlueprintExceptionRunnable<T>(session, action) {
			@SuppressWarnings("unchecked")
			@Override
			public T runInternal() throws Exception {
				if (expectedClazz.isPrimitive()) {
					final Class<?> clazz = PRIMITIVE_MAPPING.get(expectedClazz);
					return (T) clazz.getConstructor(String.class).newInstance(String.valueOf(value));
				} else {
					return (T) expectedClazz.getConstructor(String.class).newInstance(String.valueOf(value));
				}
			}

		}, BlueprintException.class);

		return currentValue;
	}

	private long readMaxValueOfType() {
		final Number n = SafeInvoke.invoke(new ExceptionRunnable<Number>() {
			@Override
			public Number run() throws Exception {
				return (Number) currentValue.getClass().getField("MAX_VALUE").get(null);
			}

		}, BlueprintException.class);
		return n.longValue();
	}
}
