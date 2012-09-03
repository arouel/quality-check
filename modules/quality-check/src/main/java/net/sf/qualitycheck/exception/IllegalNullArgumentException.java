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
package net.sf.qualitycheck.exception;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method has been passed with an illegal {@code null} reference as argument that does not
 * accept it as valid.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalNullArgumentException extends RuntimeException {

	private static final long serialVersionUID = -6988558700678645359L;

	/**
	 * Default message to indicate that the a given argument must not be a {@code null} reference.
	 */
	protected static final String DEFAULT_MESSAGE = "Argument must not be null.";

	/**
	 * Message to indicate that the the given argument with <em>name</em> must not be a {@code null} reference.
	 */
	protected static final String MESSAGE_WITH_NAME = "Argument '%s' must not be null.";

	/**
	 * Returns the formatted string {@link IllegalNullArgumentException#MESSAGE_WITH_NAME} with the given
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
	 * {@link IllegalNullArgumentException#DEFAULT_MESSAGE}.
	 */
	public IllegalNullArgumentException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the message
	 * {@link IllegalNullArgumentException#MESSAGE_WITH_NAME} including the given name of the argument as string
	 * representation.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 */
	public IllegalNullArgumentException(@Nullable final String argumentName) {
		super(format(argumentName));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalNullArgumentException#MESSAGE_WITH_NAME} including the
	 * given name as string representation and cause.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNullArgumentException(@Nullable final String argumentName, @Nullable final Throwable cause) {
		super(format(argumentName), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalNullArgumentException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNullArgumentException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
