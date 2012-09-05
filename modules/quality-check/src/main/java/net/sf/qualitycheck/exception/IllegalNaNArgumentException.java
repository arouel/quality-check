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
package net.sf.qualitycheck.exception;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method has been passed with an argument that was equal to NaN (not a number), which is not
 * a valid value for double or float values.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalNaNArgumentException extends RuntimeException {

	private static final long serialVersionUID = -508838759905305955L;

	/**
	 * Default message to indicate that the a given argument must not be NaN.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed argument must not be NaN.";

	/**
	 * Message to indicate that the the given argument with <em>name</em> must not be NaN.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed argument '%s' must not be NaN.";

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
	private static final String determineMessage(@Nullable final String argumentName) {
		return argumentName != null && !argumentName.isEmpty() ? format(argumentName) : DEFAULT_MESSAGE;
	}

	/**
	 * Returns the formatted string {@link IllegalNaNArgumentException#MESSAGE_WITH_NAME} with the given
	 * {@code argumentName}.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final String argumentName) {
		return String.format(MESSAGE_WITH_NAME, argumentName);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the default message
	 * {@link IllegalNaNArgumentException#DEFAULT_MESSAGE}.
	 */
	public IllegalNaNArgumentException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the message
	 * {@link IllegalNaNArgumentException#MESSAGE_WITH_NAME} including the given name of the argument as string
	 * representation.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 */
	public IllegalNaNArgumentException(@Nullable final String argumentName) {
		super(determineMessage(argumentName));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalNaNArgumentException#MESSAGE_WITH_NAME} including the
	 * given name as string representation and cause.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNaNArgumentException(@Nullable final String argumentName, @Nullable final Throwable cause) {
		super(determineMessage(argumentName), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalNaNArgumentException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNaNArgumentException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
