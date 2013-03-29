/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.qualitytest.exception;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a class has a public default constructor which should be private.
 * 
 * @author Dominik Seichter
 * 
 */
public class IllegalClassWithPublicDefaultConstructorException extends RuntimeException {

	private static final long serialVersionUID = -210874646549124053L;

	/**
	 * Default message to indicate that the a given argument must not be empty.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed class must not have a public default constructor.";

	/**
	 * Message to indicate that the the given argument with <em>name</em> must not be empty.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed class '%s' must not have a public default constructor.";

	/**
	 * Determines the message to be used, depending on the passed argument name. If if the given argument name is
	 * {@code null} or empty {@code DEFAULT_MESSAGE} will be returned, otherwise a formatted {@code MESSAGE_WITH_NAME}
	 * with the passed name.
	 * 
	 * @param name
	 *            the name of the class
	 * @return {@code DEFAULT_MESSAGE} if the given name is {@code null} or empty, otherwise a formatted
	 *         {@code MESSAGE_WITH_NAME}
	 */
	private static String determineMessage(@Nullable final String name) {
		return name != null && !name.isEmpty() ? format(name) : DEFAULT_MESSAGE;
	}

	/**
	 * Returns the formatted string {@link IllegalNonFinalClassException#MESSAGE_WITH_NAME} with the given
	 * {@code argumentName}.
	 * 
	 * @param name
	 *            the name of the class
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final String name) {
		return String.format(MESSAGE_WITH_NAME, name);
	}

	/**
	 * Constructs an {@code IllegalClassWithPublicDefaultConstructorException} with the default message
	 * {@link IllegalClassWithPublicDefaultConstructorException#DEFAULT_MESSAGE}.
	 */
	public IllegalClassWithPublicDefaultConstructorException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalClassWithPublicDefaultConstructorException} with the message
	 * {@link IllegalClassWithPublicDefaultConstructorException#MESSAGE_WITH_NAME} including the given name of the class
	 * as string representation.
	 * 
	 * @param name
	 *            the name of the class
	 */
	public IllegalClassWithPublicDefaultConstructorException(@Nullable final String name) {
		super(determineMessage(name));
	}

	/**
	 * Constructs a new exception with the message
	 * {@link IllegalClassWithPublicDefaultConstructorException#MESSAGE_WITH_NAME} including the given name as string
	 * representation and cause.
	 * 
	 * @param name
	 *            the name of the class
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalClassWithPublicDefaultConstructorException(@Nullable final String name, @Nullable final Throwable cause) {
		super(determineMessage(name), cause);
	}

	/**
	 * Constructs a new exception with the default message
	 * {@link IllegalClassWithPublicDefaultConstructorException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalClassWithPublicDefaultConstructorException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
