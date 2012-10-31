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

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method has been passed with a number which is not in the range of the given datatype..
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalNumberRangeException extends RuntimeException {

	private static final long serialVersionUID = 8948110906636813037L;

	/**
	 * Default message to indicate that the a given arguments are not in the valid range of the datatype.
	 */
	protected static final String DEFAULT_MESSAGE = "Number must be in a valid range for the given datatype.";

	/**
	 * Message to indicate that the the given arguments must be in the valid
	 * range for the datatype.
	 */
	protected static final String MESSAGE_WITH_VALUES = "Argument value '%s' must be in the range '%s' to '%s'.";

	private static String format(final String value, final BigDecimal min, final BigDecimal max) {
		return String.format(MESSAGE_WITH_VALUES, value, min.toString(), max.toString());
	}

	/**
	 * Constructs an {@code IllegalNumberRangeException} with the default message
	 * {@link IllegalNumberRangeException#DEFAULT_MESSAGE}.
	 */
	public IllegalNumberRangeException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs an {@code IllegalNumberRangeException} with the message {@link IllegalNumberRangeException#MESSAGE_WITH_VALUES}
	 * including the given values of the arguments.
	 * 
	 * @param value
	 *            the value which is not in the range as {@code String}
	 * @param min
	 *            the min value of the range
	 * @param max
	 *            the max value of the range
	 */
	public IllegalNumberRangeException(final String value, final BigDecimal min, final BigDecimal max) {
		super(format(value, min, max));
	}

	/**
	 * Constructs an {@code IllegalNumberRangeException} with the message {@link IllegalNumberRangeException#MESSAGE_WITH_VALUES}
	 * including the given values of the arguments.
	 * 
	 * @param value
	 *            the value which is not in the range as {@code String}
	 * @param min
	 *            the min value of the range
	 * @param max
	 *            the max value of the range
	 */
	public IllegalNumberRangeException(final String value, final BigInteger min, final BigInteger max) {
		super(format(value, new BigDecimal(min), new BigDecimal(max)));
	}

	/**
	 * Constructs a new exception with the message {@link IllegalNumberRangeException#MESSAGE_WITH_VALUES} including the given
	 * values of the arguments.
	 * 
	 * @param value
	 *            the value which is not in the range as {@code String}
	 * @param min
	 *            the min value of the range
	 * @param max
	 *            the max value of the range
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNumberRangeException(final String value, final BigInteger min, final BigInteger max, @Nullable final Throwable cause) {
		super(format(value, new BigDecimal(min), new BigDecimal(max)), cause);
	}
	
	/**
	 * Constructs a new exception with the message {@link IllegalNumberRangeException#MESSAGE_WITH_VALUES} including the given
	 * values of the arguments.
	 * 
	 * @param value
	 *            the value which is not in the range as {@code String}
	 * @param min
	 *            the min value of the range
	 * @param max
	 *            the max value of the range
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNumberRangeException(final String value, final BigDecimal min, final BigDecimal max, @Nullable final Throwable cause) {
		super(format(value, min, max), cause);
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalNumberRangeException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalNumberRangeException(final  @Nullable Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
