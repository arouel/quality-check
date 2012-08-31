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
 * Thrown to indicate that a method has been passed with a range as arguments.
 * 
 * A range (<em>start</em>, <em>end</em>, <em>size</em>) is valid if the following conditions are true: - start < size -
 * end < size - start < end
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalRangeException extends RuntimeException {

	private static final long serialVersionUID = 4515679658955102518L;

	/**
	 * Default message to indicate that the a given arguments are no valid range.
	 */
	protected static final String DEFAULT_MESSAGE = "Arguments must be a valid range.";

	/**
	 * Message to indicate that the the given arguments <em>start</em>, <em>end</em> and <em>size</em> must be a valid
	 * range.
	 */
	protected static final String MESSAGE_WITH_VALUES = "Arguments start='%d', end='%d' and size='%d' must be a valid range.";

	private static String format(final int start, final int end, final int size) {
		return String.format(MESSAGE_WITH_VALUES, start, end, size);
	}

	/**
	 * Constructs an {@code IllegalRangeException} with the default message
	 * {@link IllegalRangeException#DEFAULT_MESSAGE}.
	 */
	public IllegalRangeException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalRangeException} with the message {@link IllegalRangeException#MESSAGE_WITH_VALUES}
	 * including the given values of the arguments.
	 * 
	 * @param start
	 *            the start value of the invalid range
	 * @param end
	 *            the end value of the invalid range
	 * @param size
	 *            the size value of the invalid range
	 */
	public IllegalRangeException(final int start, final int end, final int size) {
		super(format(start, end, size));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalRangeException#MESSAGE_WITH_VALUES} including the given
	 * values of the arguments.
	 * 
	 * @param start
	 *            the start value of the invalid range
	 * @param end
	 *            the end value of the invalid range
	 * @param size
	 *            the size value of the invalid range
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalRangeException(final int start, final int end, final int size, final @Nullable Throwable cause) {
		super(format(start, end, size), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalRangeException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalRangeException(final  @Nullable Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
