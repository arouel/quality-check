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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.exception.BlueprintException;

/**
 * Invoke a method or execute code without having to care for checked (and unchecked) exceptions. All exceptions can be
 * converted to a defined runtime exception.
 * 
 * @author Dominik Seichter
 * 
 */
final class SafeInvoke {

	/**
	 * A runnable which is allowed to throw any kind of exceptions.
	 * 
	 * @author Dominik Seichter
	 * 
	 */
	public interface ExceptionRunnable<T> {

		/**
		 * Run the {@code Runnable}.
		 * 
		 * @return T a return value
		 * @throws Exception
		 */
		T run() throws Exception;
	}

	private static RuntimeException createException(final Throwable e, final Class<? extends RuntimeException> exceptionClass) {
		try {
			return createExceptionInternal(e, exceptionClass);
		} catch (final Exception exception) {
			throw new BlueprintException(exception);
		}
	}

	private static RuntimeException createExceptionInternal(final Throwable e, final Class<? extends RuntimeException> exceptionClass)
			throws InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		final Constructor<?>[] constructors = exceptionClass.getDeclaredConstructors();
		for (final Constructor<?> c : constructors) {
			final Class<?>[] parameterTypes = c.getParameterTypes();
			if (parameterTypes.length == 1 && parameterTypes[0] == Throwable.class) {
				return (RuntimeException) c.newInstance(e);
			}
		}

		return exceptionClass.newInstance();
	}

	/**
	 * Safely invoke a method on an object without having to care about checked exceptions. The runnable is executed and
	 * every exception is converted into a {@code BlueprintException}.
	 * 
	 * @param runnable
	 *            An {@code ExceptionRunnable}
	 * @throws Throwable
	 * 
	 * @throws BlueprintException
	 *             in case of any error
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T> T invoke(@Nonnull final ExceptionRunnable<T> runnable, @Nonnull final Class<? extends RuntimeException> exceptionClass) {
		Check.notNull(runnable, "runnable");
		Check.notNull(exceptionClass, "exceptionClass");

		try {
			return runnable.run();
		} catch (final Exception t) {
			final RuntimeException e = createException(t, exceptionClass);
			throw e;
		}
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private SafeInvoke() {
		// This class is not intended to create objects from it.
	}

}
