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
 * Thrown to indicate that a class cannot be instantiated.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class RuntimeInstantiationException extends RuntimeException {

	private static final long serialVersionUID = 7304261330061136504L;

	/**
	 * Default message to indicate that the a given class cannot be instantiated.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed class cannot be instantiated.";

	/**
	 * Message to indicate that the the given class with <em>name</em> cannot be instantiated.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed class '%s' cannot be instantiated.";

	/**
	 * Determines the message to be used, depending on the passed argument name. If if the given argument name is
	 * {@code null} or empty {@code DEFAULT_MESSAGE} will be returned, otherwise a formatted {@code MESSAGE_WITH_NAME}
	 * with the passed name.
	 * 
	 * @param className
	 *            the name of the passed {@link Class}
	 * @return {@code DEFAULT_MESSAGE} if the given argument name is {@code null} or empty, otherwise a formatted
	 *         {@code MESSAGE_WITH_NAME}
	 */
	private static String determineMessage(@Nullable final String className) {
		return className != null && !className.isEmpty() ? format(className) : DEFAULT_MESSAGE;
	}

	/**
	 * Returns the formatted string {@link RuntimeInstantiationException#MESSAGE_WITH_NAME} with the given
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
	 * {@link RuntimeInstantiationException#DEFAULT_MESSAGE}.
	 */
	public RuntimeInstantiationException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code RuntimeInstantiationException} with the message
	 * {@link RuntimeInstantiationException#MESSAGE_WITH_NAME} including the given name of the argument as string
	 * representation.
	 * 
	 * @param className
	 *            the name of the {@link Class}
	 */
	public RuntimeInstantiationException(@Nullable final String className) {
		super(determineMessage(className));
	}

	/**
	 * Constructs a new exception with the message {@link RuntimeInstantiationException#MESSAGE_WITH_NAME} including the
	 * given name as string representation and cause.
	 * 
	 * @param className
	 *            the name of the {@link Class}
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public RuntimeInstantiationException(@Nullable final String className, @Nullable final Throwable cause) {
		super(determineMessage(className), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link RuntimeInstantiationException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public RuntimeInstantiationException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
