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
package net.sf.qualitytest.exception;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a class contains a non-final static field.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalNonFinalStaticException extends RuntimeException {

	private static final long serialVersionUID = -4469726272965012829L;

	/**
	 * Default message to indicate that the a given class must not contain non-final statics.
	 */
	protected static final String DEFAULT_MESSAGE = "The given class contains a non-final static variable.";

	/**
	 * Message to indicate that the the given class contains a non-final static field.
	 */
	protected static final String MESSAGE_WITH_NAME = "The given class '%s' contains a non-final static variable '%s'.";

	/**
	 * Returns the formatted string {@link IllegalNonFinalStaticException#MESSAGE_WITH_NAME} with the given
	 * {@code argumentName}.
	 * 
	 * @param className
	 *            the name of the passed class
	 * @param fieldName
	 *            the name of the non-final static field
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final String className, @Nullable final String fieldName) {
		if (className != null && fieldName != null) {
			return String.format(MESSAGE_WITH_NAME, className, fieldName);
		} else {
			return DEFAULT_MESSAGE;
		}
	}

	/**
	 * Constructs an {@code IllegalNonStaticFinalException} with the default message
	 * {@link IllegalNonFinalStaticException#DEFAULT_MESSAGE}.
	 */
	public IllegalNonFinalStaticException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalNonStaticFinalException} with the message
	 * {@link IllegalNonFinalStaticException#MESSAGE_WITH_NAME} including the given name of the class as string
	 * representation.
	 * 
	 * @param className
	 *            the name of the passed class
	 * @param fieldName
	 *            the name of the non-final static field
	 */
	public IllegalNonFinalStaticException(@Nullable final String className, @Nullable final String fieldName) {
		super(format(className, fieldName));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalNonFinalStaticException#MESSAGE_WITH_NAME} including
	 * the given name as string representation and cause.
	 * 
	 * @param className
	 *            the name of the passed class
	 * @param fieldName
	 *            the name of the non-final static field
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNonFinalStaticException(@Nullable final String className, @Nullable final String fieldName,
			@Nullable final Throwable cause) {
		super(format(className, fieldName), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalNonFinalStaticException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNonFinalStaticException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}
}
