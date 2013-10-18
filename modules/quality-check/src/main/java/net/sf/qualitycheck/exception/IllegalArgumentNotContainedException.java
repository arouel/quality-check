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
 * Thrown to indicate that a method has been passed with an argument that is not part of a collection but must be.
 * 
 * @author Dominik Seichter
 */
public class IllegalArgumentNotContainedException extends RuntimeException implements IllegalArgumentHolder<Object> {

	private static final long serialVersionUID = 8389358566804494876L;

	/**
	 * Default message to indicate that the a given argument be contained in a collection.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed argument must be contained in a defined collection.";

	/**
	 * Message to indicate that the the given argument with <em>name</em> must be contained in a defined collection.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed argument '%s' must be contained in a defined collection.";

	/**
	 * Returns the formatted string {@link IllegalArgumentNotContainedException#MESSAGE_WITH_NAME} with the given
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
	 * The illegal value which was not contained in a collection and by that caused this exception to be thrown.
	 */
	private final Object illegalArgumentValue;

	/**
	 * Constructs an {@code IllegalArgumentNotContainedException} with the default message
	 * {@link IllegalArgumentNotContainedException#DEFAULT_MESSAGE}.
	 * 
	 * @param illegalArgumentValue
	 *            The illegal value which was not contained in a collection and by that caused this exception to be
	 *            thrown.
	 */
	public IllegalArgumentNotContainedException(@Nullable final Object illegalArgumentValue) {
		super(DEFAULT_MESSAGE);
		this.illegalArgumentValue = illegalArgumentValue;
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalArgumentNotContainedException#DEFAULT_MESSAGE}.
	 * 
	 * @param illegalArgumentValue
	 *            The illegal value which was not contained in a collection and by that caused this exception to be
	 *            thrown.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalArgumentNotContainedException(@Nullable final Object illegalArgumentValue, @Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
		this.illegalArgumentValue = illegalArgumentValue;
	}

	/**
	 * Constructs an {@code IllegalArgumentNotContainedException} with the message
	 * {@link IllegalArgumentNotContainedException#MESSAGE_WITH_NAME} including the given name of the argument as string
	 * representation.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param illegalArgumentValue
	 *            The illegal value which was not contained in a collection and by that caused this exception to be
	 *            thrown.
	 */
	public IllegalArgumentNotContainedException(@Nullable final String argumentName, @Nullable final Object illegalArgumentValue) {
		super(format(argumentName));
		this.illegalArgumentValue = illegalArgumentValue;
	}

	/**
	 * Constructs a new exception with the message {@link IllegalArgumentNotContainedException#MESSAGE_WITH_NAME}
	 * including the given name as string representation and cause.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param illegalArgumentValue
	 *            The illegal value which was not contained in a collection and by that caused this exception to be
	 *            thrown.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalArgumentNotContainedException(@Nullable final String argumentName, @Nullable final Object illegalArgumentValue,
			@Nullable final Throwable cause) {
		super(format(argumentName), cause);
		this.illegalArgumentValue = illegalArgumentValue;
	}

	@Override
	public Object getIllegalArgument() {
		return illegalArgumentValue;
	}

}
