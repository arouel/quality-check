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
 * Thrown to indicate that a class does not have a public constructor and therefore blueprinting is not available.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class NoPublicConstructorException extends BlueprintException {

	private static final long serialVersionUID = 5632735367544191025L;

	/**
	 * Default message to indicate that a given class has no public constructor.
	 */
	protected static final String DEFAULT_MESSAGE = "The given class has no public constructor.";

	/**
	 * Message to indicate that a given class has no public constructor.
	 */
	protected static final String MESSAGE_WITH_NAME = "The given class '%s' has no public constructor.";

	/**
	 * Returns the formatted string {@link NoPublicConstructorException#MESSAGE_WITH_NAME} with the given
	 * {@code argumentName}.
	 * 
	 * @param className
	 *            the name of the passed class
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final String className) {
		if (className != null) {
			return String.format(MESSAGE_WITH_NAME, className);
		} else {
			return DEFAULT_MESSAGE;
		}
	}

	/**
	 * Constructs an {@code NoPublicConstructorException} with the default message
	 * {@link NoPublicConstructorException#DEFAULT_MESSAGE}.
	 */
	public NoPublicConstructorException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code NoPublicConstructorException} with the message
	 * {@link NoPublicConstructorException#MESSAGE_WITH_NAME} including the given name of the class as string
	 * representation.
	 * 
	 * @param className
	 *            the name of the passed class
	 */
	public NoPublicConstructorException(@Nullable final String className) {
		super(format(className));
	}

	/**
	 * Constructs a new exception with the message {@link NoPublicConstructorException#MESSAGE_WITH_NAME} including the
	 * given name as string representation and cause.
	 * 
	 * @param className
	 *            the name of the passed class
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public NoPublicConstructorException(@Nullable final String className, @Nullable final Throwable cause) {
		super(format(className), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link NoPublicConstructorException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public NoPublicConstructorException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}
}
