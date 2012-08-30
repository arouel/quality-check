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
package com.github.quality.check.exception;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method was passed arguments which caused an invalid state.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalStateOfArgumentException extends RuntimeException {

	private static final long serialVersionUID = -1782626786560016442L;

	/**
	 * Default message to indicate that the given arguments caused an invalid state.
	 */
	protected static final String DEFAULT_MESSAGE = "Arguments must be valid with the current state.";

	/**
	 * Message to indicate that an invalid state was caused due to the passed arguments, which also 
	 * provides an explanation why the state is invalid.
	 */
	protected static final String MESSAGE_DESCRIPTION = "The passed arguments have caused an invalid state: '%s'";
	
	private static String format(final @Nonnull String descriptionTemplate, Object... descriptionTemplateArgs) {
		return String.format(descriptionTemplate, descriptionTemplateArgs);
	}
	
	/**
	 * Constructs an {@code IllegalStateOfArgumentException} with the default message
	 * {@link IllegalStateOfArgumentException#DEFAULT_MESSAGE}.
	 */
	public IllegalStateOfArgumentException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalStateOfArgumentException} with the message {@link IllegalStateOfArgumentException#MESSAGE_DESCRIPTION}
	 * including the given values of the arguments.
	 * 
	 * @param description
	 *            explains why the state is invalid
	 */
	public IllegalStateOfArgumentException(final @Nonnull String description) {
		super(format(description));
	}

	/**
	 * Constructs an {@code IllegalStateOfArgumentException} with the message {@link IllegalStateOfArgumentException#MESSAGE_DESCRIPTION}
	 * including the given values of the arguments.
	 * 
	 * @param description
	 *            format string template that explains why the state is invalid
	 * @param descriptionTemplateArgs
	 *            format string template arguments to explain why the state is invalid
	 */
	public IllegalStateOfArgumentException(final @Nonnull String description, Object... descriptionTemplateArgs) {
		super(format(description, descriptionTemplateArgs));
	}
	
	/**
	 * Constructs a new exception with the message {@link IllegalStateOfArgumentException#MESSAGE_DESCRIPTION} including the given
	 * values of the arguments.
	 * 
	 * @param description
	 *            explains why the state is invalid
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalStateOfArgumentException(final @Nonnull String description, final @Nullable Throwable cause) {
		super(format(description), cause);
	}
	
	/**
	 * Constructs a new exception with the message {@link IllegalStateOfArgumentException#MESSAGE_DESCRIPTION} including the given
	 * values of the arguments.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 * @param description
	 *            format string template that explains why the state is invalid
	 * @param descriptionTemplateArgs
	 *            format string template arguments to explain why the state is invalid
	 */
	public IllegalStateOfArgumentException(final @Nullable Throwable cause, final @Nonnull String description, Object... descriptionTemplateArgs) {
		super(format(description, descriptionTemplateArgs), cause);
	}
	
	/**
	 * Constructs a new exception with the default message {@link IllegalStateOfArgumentException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalStateOfArgumentException(final @Nullable Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
