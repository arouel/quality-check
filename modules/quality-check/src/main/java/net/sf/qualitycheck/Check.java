/*******************************************************************************
 * Copyright 2012 André Rouél
 * Copyright 2012 Dominik Seichter
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
package net.sf.qualitycheck;

import java.util.Collection;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;
import net.sf.qualitycheck.exception.IllegalRangeException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

/**
 * This class offers simple static methods to test your arguments to be valid.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public final class Check {

	/**
	 * Ensures that an array does not contain {@code null}.
	 * 
	 * @param array
	 *            reference to an array
	 * 
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code array} contains {@code null}
	 */
	@ArgumentsChecked(value = { IllegalNullArgumentException.class })
	public static void noNullElements(final @Nullable Object[] array) {
		Check.notNull(array);

		if (containsNullElements(array)) {
			throw new IllegalNullElementsException();
		}
	}

	/**
	 * Ensures that an array does not contain {@code null}.
	 * 
	 * @param array
	 *            reference to an array
	 * @param name
	 *            name of object reference (in source code)
	 * 
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code array} contains {@code null}
	 */
	@ArgumentsChecked(value = { IllegalNullArgumentException.class })
	public static void noNullElements(final @Nullable Object[] array, final @Nullable String name) {
		Check.notNull(array);

		if (containsNullElements(array)) {
			throw new IllegalNullElementsException(name);
		}
	}

	/**
	 * Checks if the given array contains {@code null}.
	 * 
	 * @param array
	 *            reference to an array
	 * @return {@code true} if the array contains {@code null}, otherwise {@code false}
	 * 
	 */
	private static boolean containsNullElements(final @Nullable Object[] array) {
		boolean containsNull = false;
		for (final Object o : array) {
			if (o == null) {
				containsNull = true;
				break;
			}
		}

		return containsNull;
	}

	/**
	 * Ensures that a passed string as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(String, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param reference
	 *            a string reference which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static String notEmpty(final @Nullable String reference) {
		notNull(reference);
		notEmpty(reference, reference.isEmpty(), null);
		return reference;
	}

	/**
	 * Ensures that a passed string as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setText(String text) {
	 * 	this.text = Check.notEmpty(text, &quot;text&quot;);
	 * }
	 * </pre>
	 * 
	 * @param reference
	 *            a string reference which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static String notEmpty(final @Nullable String reference, final @Nullable String name) {
		notNull(reference, name);
		notEmpty(reference, reference.isEmpty(), name);
		return reference;
	}

	/**
	 * Ensures that a passed collection as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param collection
	 *            a collection which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Collection<?>> T notEmpty(final @Nullable T collection) {
		notNull(collection);
		notEmpty(collection, collection.isEmpty(), null);
		return collection;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not empty. The passed boolean
	 * value is the result of checking whether the reference is empty or not.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setText(String text) {
	 * 	Check.notEmpty(text, text.isEmpty(), &quot;text&quot;);
	 * 	this.text = text;
	 * }
	 * </pre>
	 * 
	 * @param reference
	 *            an object reference which should not be empty
	 * @param expression
	 *            the result of the expression to verify the emptiness of a reference ({@code true} means empty,
	 *            {@code false} means not empty)
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T> T notEmpty(final @Nullable T reference, final boolean expression, final @Nullable String name) {
		notNull(reference, name);
		if (expression) {
			throw new IllegalEmptyArgumentException(name);
		}
		return reference;
	}

	/**
	 * Ensures that a passed collection as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setCollection(Collection&lt;String&gt; collection) {
	 * 	this.collection = Check.notEmpty(collection, &quot;collection&quot;);
	 * }
	 * </pre>
	 * 
	 * @param collection
	 *            a collection which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Collection<?>> T notEmpty(final @Nullable T collection, final @Nullable String name) {
		notNull(collection, name);
		notEmpty(collection, collection.isEmpty(), name);
		return collection;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not {@code null}.
	 * 
	 * @param reference
	 *            an object reference
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(final @Nullable T reference) {
		if (reference == null) {
			throw new IllegalNullArgumentException();
		}
		return reference;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not {@code null}.
	 * 
	 * @param reference
	 *            an object reference
	 * @param name
	 *            name of object reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(final @Nullable T reference, final @Nullable String name) {
		if (reference == null) {
			throw new IllegalNullArgumentException(name);
		}
		return reference;
	}

	/**
	 * Ensures that a given position index is valid within the size of an array, list or string ...
	 * 
	 * @param index
	 *            index of an array, list or string
	 * @param size
	 *            size of an array list or string
	 * @return the index
	 * 
	 * @throws IllegalPositionIndexException
	 *             if the index is not a valid position index within an array, list or string of size <em>size</em>
	 * 
	 */
	public static int positionIndex(final int index, final int size) {
		final boolean isIndexValid = (size >= 0) && (index >= 0) && (index < size);

		if (!isIndexValid) {
			throw new IllegalPositionIndexException(index, size);
		}

		return index;
	}

	/**
	 * Ensures that the given arguments are a valid range.
	 * 
	 * A range (<em>start</em>, <em>end</em>, <em>size</em>) is valid if the following conditions are true: - start <=
	 * size - end <= size - start <= end - size >= 0 - start >= 0 - end >= 0
	 * 
	 * If negative numbers are allowed in your range, you must check using { {@link #rangeNegativeOrPositive()}.
	 * 
	 * @param start
	 *            the start value of the range (must be a positive integer or 0)
	 * @param end
	 *            the end value of the range (must be a positive integer or 0)
	 * @param size
	 *            the size value of the range (must be a positive integer or 0)
	 * 
	 * 
	 * @throws IllegalRangeException
	 *             if the given arguments do not form a valid range
	 */
	public static void range(final @Nonnegative int start, final @Nonnegative int end, final @Nonnegative int size) {
		final boolean rangeIsValid = (start <= size) && (end <= size) && (start <= end);
		final boolean inputValuesAreValid = (size >= 0) && (start >= 0) && (end >= 0);

		if (!rangeIsValid || !inputValuesAreValid) {
			throw new IllegalRangeException(start, end, size);
		}
	}

	/**
	 * Ensures that a given state is true
	 * 
	 * @param expression
	 *            an expression that must be true to indicate a valid state
	 * 
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	public static void stateIsTrue(final boolean expression) {
		if (!expression) {
			throw new IllegalStateOfArgumentException();
		}
	}

	/**
	 * Ensures that a given state is true and allows to specify the class of exception which is thrown in case the state
	 * is not true
	 * 
	 * @param expression
	 *            an expression that must be true to indicate a valid state
	 * @param clazz
	 *            an subclass of RuntimeException which will be thrown if the given state is not valid
	 * @throws a
	 *             new instance of clazz if the given arguments caused an invalid state
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static void stateIsTrue(final boolean expression, final Class<? extends RuntimeException> clazz) {
		Check.notNull(clazz);

		if (!expression) {
			RuntimeException re;
			try {
				re = clazz.newInstance();
			} catch (final InstantiationException e) {
				throw new RuntimeException(e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
			}

			throw re;
		}
	}

	/**
	 * Ensures that a given state is true
	 * 
	 * @param expression
	 *            an expression that must be true to indicate a valid state
	 * @param description
	 *            will be used in the error message to describe why the arguments caused an invalid state
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	public static void stateIsTrue(final boolean expression, final @Nonnull String description) {
		if (!expression) {
			throw new IllegalStateOfArgumentException(description);
		}
	}

	/**
	 * Ensures that a given state is true
	 * 
	 * @param expression
	 *            an expression that must be true to indicate a valid state
	 * @param descriptionTemplate
	 *            format string template that explains why the state is invalid
	 * @param descriptionTemplateArgs
	 *            format string template arguments to explain why the state is invalid
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	public static void stateIsTrue(final boolean expression, final @Nonnull String descriptionTemplate,
			final Object... descriptionTemplateArgs) {
		if (!expression) {
			throw new IllegalStateOfArgumentException(descriptionTemplate, descriptionTemplateArgs);
		}
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private Check() {
		// This class is not intended to create objects from it.
	}

}
