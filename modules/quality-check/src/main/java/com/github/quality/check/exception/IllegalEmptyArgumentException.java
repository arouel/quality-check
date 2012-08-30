/*******************************************************************************
 * Copyright 2012 André Rouél
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
package com.github.quality.check.exception;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method has been passed with an empty reference as argument that does not accept it as
 * valid. For example, if a method needs a string with a length greater than 0 and is passed with an empty string.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalEmptyArgumentException extends RuntimeException {

	private static final long serialVersionUID = -6988558700678645359L;

	/**
	 * Default message to indicate that the a given argument must not be empty.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed argument must not be empty.";

	/**
	 * Message to indicate that the the given argument with <em>name</em> must not be empty.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed argument '%s' must not be empty.";

	/**
	 * Determines the message to be used, depending on the passed argument name. If if the given argument name is
	 * {@code null} or empty {@code DEFAULT_MESSAGE} will be returned, otherwise a formatted {@code MESSAGE_WITH_NAME}
	 * with the passed name.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @return {@code DEFAULT_MESSAGE} if the given argument name is {@code null} or empty, otherwise a formatted
	 *         {@code MESSAGE_WITH_NAME}
	 */
	private static final String determineMessage(final @Nullable String argumentName) {
		return argumentName != null && !argumentName.isEmpty() ? format(argumentName) : DEFAULT_MESSAGE;
	}

	/**
	 * Returns the formatted string {@link IllegalEmptyArgumentException#MESSAGE_WITH_NAME} with the given
	 * {@code argumentName}.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(final @Nullable String argumentName) {
		return String.format(MESSAGE_WITH_NAME, argumentName);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the default message
	 * {@link IllegalEmptyArgumentException#DEFAULT_MESSAGE}.
	 */
	public IllegalEmptyArgumentException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the message
	 * {@link IllegalEmptyArgumentException#MESSAGE_WITH_NAME} including the given name of the argument as string
	 * representation.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 */
	public IllegalEmptyArgumentException(final @Nullable String argumentName) {
		super(determineMessage(argumentName));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalEmptyArgumentException#MESSAGE_WITH_NAME} including the
	 * given name as string representation and cause.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalEmptyArgumentException(final @Nullable String argumentName, final @Nullable Throwable cause) {
		super(determineMessage(argumentName), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalEmptyArgumentException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalEmptyArgumentException(final @Nullable Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
