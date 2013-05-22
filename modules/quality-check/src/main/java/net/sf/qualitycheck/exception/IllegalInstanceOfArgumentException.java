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
package net.sf.qualitycheck.exception;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method has been passed with a reference of an unexpected type.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalInstanceOfArgumentException extends RuntimeException {

	private static final long serialVersionUID = -1886931952915327794L;

	/**
	 * Default message to indicate that a given argument must is a member of an unexpected type
	 */
	protected static final String DEFAULT_MESSAGE = "The passed argument is a member of an unexpected type.";

	/**
	 * Message to indicate that a given argument with must is a member of an unexpected type (with current and expected
	 * type information)
	 */
	protected static final String MESSAGE_WITH_TYPES = "The passed argument is a member of an unexpected type (expected type: %s, actual: %s).";

	/**
	 * Message to indicate that a given argument with <em>name</em> must is a member of an unexpected type (with current
	 * and expected type information)
	 */
	protected static final String MESSAGE_WITH_NAME_AND_TYPES = "The passed argument '%s' is a member of an unexpected type (expected type: %s, actual: %s).";

	/**
	 * Placeholder for not set types to format a message human readable
	 */
	protected static final String NO_TYPE_PLACEHOLDER = "(not set)";

	/**
	 * Determines the message to be used, depending on the passed argument name. If if the given argument name is
	 * {@code null} or empty {@code DEFAULT_MESSAGE} will be returned, otherwise a formatted {@code MESSAGE_WITH_NAME}
	 * with the passed name.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param expectedType
	 *            the expected class of the given argument
	 * @param actualType
	 *            the actual class of the given argument
	 * @return {@code MESSAGE_WITH_TYPES} if the given argument name is {@code null} or empty, otherwise a formatted
	 *         {@code MESSAGE_WITH_NAME}
	 */
	private static String determineMessage(@Nullable final String argumentName, @Nullable final Class<?> expectedType,
			@Nullable final Class<?> actualType) {
		return argumentName != null && !argumentName.isEmpty() ? format(argumentName, expectedType, actualType) : format(expectedType,
				actualType);
	}

	/**
	 * Returns the formatted string {@link IllegalInstanceOfArgumentException#MESSAGE_WITH_TYPES} with the given types.
	 * 
	 * @param expectedType
	 *            the expected class of the given argument
	 * @param actualType
	 *            the actual class of the given argument
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final Class<?> expectedType, @Nullable final Class<?> actualType) {
		final String expected = expectedType != null ? expectedType.getName() : NO_TYPE_PLACEHOLDER;
		final String actual = actualType != null ? actualType.getName() : NO_TYPE_PLACEHOLDER;
		return String.format(MESSAGE_WITH_TYPES, expected, actual);
	}

	/**
	 * Returns the formatted string {@link IllegalInstanceOfArgumentException#MESSAGE_WITH_NAME_AND_TYPES} with the
	 * given {@code argumentName}.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param expectedType
	 *            the expected class of the given argument
	 * @param actualType
	 *            the actual class of the given argument
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final String argumentName, @Nullable final Class<?> expectedType,
			@Nullable final Class<?> actualType) {
		final String expected = expectedType != null ? expectedType.getName() : NO_TYPE_PLACEHOLDER;
		final String actual = actualType != null ? actualType.getName() : NO_TYPE_PLACEHOLDER;
		return String.format(MESSAGE_WITH_NAME_AND_TYPES, argumentName, expected, actual);
	}

	/**
	 * Constructs an {@code IllegalInstanceOfArgumentException} with the default message
	 * {@link IllegalInstanceOfArgumentException#DEFAULT_MESSAGE}.
	 */
	public IllegalInstanceOfArgumentException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalInstanceOfArgumentException} with the message
	 * {@link IllegalInstanceOfArgumentException#MESSAGE_WITH_NAME_AND_TYPES} including the given name of the argument
	 * as string representation.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param expectedType
	 *            the expected class of the given argument
	 * @param actualType
	 *            the actual class of the given argument
	 */
	public IllegalInstanceOfArgumentException(@Nullable final String argumentName, @Nullable final Class<?> expectedType,
			@Nullable final Class<?> actualType) {
		super(determineMessage(argumentName, expectedType, actualType));
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalInstanceOfArgumentException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalInstanceOfArgumentException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
