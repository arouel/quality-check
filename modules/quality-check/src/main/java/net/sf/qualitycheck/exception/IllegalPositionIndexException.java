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
 * Thrown to indicate that a passed position index was out of the bounds of a string, list, array ... .
 * 
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalPositionIndexException extends RuntimeException {

	private static final long serialVersionUID = 1012569127264822249L;

	/**
	 * Default message to indicate that the a given position index is not valid.
	 */
	protected static final String DEFAULT_MESSAGE = "Position index must be within the defined bounds.";

	/**
	 * Default message to indicate that the a given position index is not valid within the given bounds.
	 */
	protected static final String MESSAGE_WITH_VALUES = "Position index '%d' must be within the defined bounds [0,%d].";

	private static String format(final int index, final int size) {
		return String.format(MESSAGE_WITH_VALUES, index, size);
	}

	/**
	 * Constructs an {@code IllegalPositionIndexException} with the default message
	 * {@link IllegalPositionIndexException#DEFAULT_MESSAGE}.
	 */
	public IllegalPositionIndexException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalPositionIndexException} with the message
	 * {@link IllegalPositionIndexException#MESSAGE_WITH_VALUES} including the given values of the arguments.
	 * 
	 * @param index
	 *            an index in an array, list or string
	 * @param size
	 *            the size of an array, list or string
	 */
	public IllegalPositionIndexException(final int index, final int size) {
		super(format(index, size));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalPositionIndexException#MESSAGE_WITH_VALUES} including
	 * the given values of the arguments.
	 * 
	 * @param index
	 *            an index in an array, list or string
	 * @param size
	 *            the size of an array, list or string
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalPositionIndexException(final int index, final int size, final @Nullable Throwable cause) {
		super(format(index, size), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalPositionIndexException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalPositionIndexException(final @Nullable Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
