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
 * Thrown to indicate that a method has been passed with an array or a collection which contains a {@code null} element.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalNullElementsException extends RuntimeException {

	private static final long serialVersionUID = -1957077437070375885L;

	/**
	 * Default message to indicate that a given array or {@code Iterable} parameter must not contain {@code null}.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed parameter must not contain elements that are null.";

	/**
	 * Message to indicate that the the given array or {@code Iterable} parameter with <em>name</em> must not contain
	 * {@code null}.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed parameter '%s' must not contain elements that are null.";

	/**
	 * Determines the message to be used, depending on the passed parameter name. If if the given parameter name is
	 * {@code null} or empty {@code DEFAULT_MESSAGE} will be returned, otherwise a formatted {@code MESSAGE_WITH_NAME}
	 * with the passed name.
	 * 
	 * @param parameterName
	 *            the name of the passed parameter
	 * @return {@code DEFAULT_MESSAGE} if the given parameter name is {@code null} or empty, otherwise a formatted
	 *         {@code MESSAGE_WITH_NAME}
	 */
	private static final String determineMessage(@Nullable final String parameterName) {
		return parameterName != null && !parameterName.isEmpty() ? format(parameterName) : DEFAULT_MESSAGE;
	}

	/**
	 * Returns the formatted string {@link IllegalEmptyArgumentException#MESSAGE_WITH_NAME} with the given
	 * {@code parameterName}.
	 * 
	 * @param parameterName
	 *            the name of the passed parameter
	 * @return a formatted string of message with the given parameter name
	 */
	private static String format(@Nullable final String parameterName) {
		return String.format(MESSAGE_WITH_NAME, parameterName);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the default message
	 * {@link IllegalEmptyArgumentException#DEFAULT_MESSAGE}.
	 */
	public IllegalNullElementsException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the message
	 * {@link IllegalEmptyArgumentException#MESSAGE_WITH_NAME} including the given name of the parameter as string
	 * representation.
	 * 
	 * @param parameterName
	 *            the name of the passed parameter
	 */
	public IllegalNullElementsException(@Nullable final String parameterName) {
		super(determineMessage(parameterName));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalEmptyArgumentException#MESSAGE_WITH_NAME} including the
	 * given name as string representation and cause.
	 * 
	 * @param parameterName
	 *            the name of the passed parameter
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNullElementsException(@Nullable final String parameterName, @Nullable final Throwable cause) {
		super(determineMessage(parameterName), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalEmptyArgumentException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNullElementsException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
