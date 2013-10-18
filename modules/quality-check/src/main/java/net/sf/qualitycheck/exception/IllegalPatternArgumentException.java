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

import java.util.regex.Pattern;

import javax.annotation.Nullable;

/**
 * Thrown to indicate that a method has been passed a sequence of {@code char} values as argument that does not matches
 * against the specified pattern. For example, if a method needs a string with a exactly four alphanumeric characters
 * and is passed with a five characters long string.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalPatternArgumentException extends RuntimeException implements IllegalArgumentHolder<CharSequence> {

	private static final long serialVersionUID = -6741481389295600427L;

	/**
	 * Default message to indicate that the a given argument must match against the specified pattern.
	 */
	protected static final String DEFAULT_MESSAGE = "The passed argument must match against the specified pattern: %s";

	/**
	 * Message to indicate that the the given argument with <em>name</em> must match against the specified pattern.
	 */
	protected static final String MESSAGE_WITH_NAME = "The passed argument '%s' must match against the specified pattern: %s";

	/**
	 * Placeholder for an unset pattern to format a message human readable
	 */
	protected static final String NO_PATTERN_PLACEHOLDER = "[not set]";

	/**
	 * Determines the message to be used, depending on the passed argument name. If if the given argument name is
	 * {@code null} or empty {@code DEFAULT_MESSAGE} will be returned, otherwise a formatted {@code MESSAGE_WITH_NAME}
	 * with the passed name and pattern which the argument must match.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @return {@code DEFAULT_MESSAGE} if the given argument name is {@code null} or empty, otherwise a formatted
	 *         {@code MESSAGE_WITH_NAME}
	 */
	private static String determineMessage(@Nullable final String argumentName, @Nullable final Pattern pattern) {
		return argumentName != null && !argumentName.isEmpty() ? format(argumentName, pattern) : format(pattern);
	}

	/**
	 * Returns the formatted string {@link IllegalPatternArgumentException#DEFAULT_MESSAGE} with the given pattern which
	 * the argument must match.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final Pattern pattern) {
		return String.format(DEFAULT_MESSAGE, patternToString(pattern));
	}

	/**
	 * Returns the formatted string {@link IllegalPatternArgumentException#MESSAGE_WITH_NAME} with the given
	 * {@code argumentName} and pattern which the argument must match.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nullable final String argumentName, @Nullable final Pattern pattern) {
		final String p = patternToString(pattern);
		return String.format(MESSAGE_WITH_NAME, argumentName, p);
	}

	/**
	 * Converts a {@link Pattern} to a string representation.
	 * 
	 * @param pattern
	 *            Pattern
	 * @return string representation of a pattern
	 */
	private static String patternToString(@Nullable final Pattern pattern) {
		return pattern != null ? pattern.pattern() + " (flags: " + pattern.flags() + ")" : NO_PATTERN_PLACEHOLDER;
	}

	/**
	 * The illegal value which caused this exception to be thrown.
	 */
	private final CharSequence illegalArgumentValue;

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the default message
	 * {@link IllegalPatternArgumentException#DEFAULT_MESSAGE} including the pattern which the argument must match.
	 * 
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @param illegalArgumentValue
	 *            The illegal value which caused this exception to be thrown.
	 */
	public IllegalPatternArgumentException(@Nullable final Pattern pattern, @Nullable final CharSequence illegalArgumentValue) {
		super(format(pattern));
		this.illegalArgumentValue = illegalArgumentValue;
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalPatternArgumentException#DEFAULT_MESSAGE}
	 * including the pattern which the argument must match.
	 * 
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @param illegalArgumentValue
	 *            The illegal value which caused this exception to be thrown.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalPatternArgumentException(@Nullable final Pattern pattern, @Nullable final CharSequence illegalArgumentValue,
			@Nullable final Throwable cause) {
		super(format(pattern), cause);
		this.illegalArgumentValue = illegalArgumentValue;
	}

	/**
	 * Constructs an {@code IllegalNullArgumentException} with the message
	 * {@link IllegalPatternArgumentException#MESSAGE_WITH_NAME} including the given name of the argument as string
	 * representation and pattern which the argument must match.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @param illegalArgumentValue
	 *            The illegal value which caused this exception to be thrown.
	 */
	public IllegalPatternArgumentException(@Nullable final String argumentName, @Nullable final Pattern pattern,
			@Nullable final CharSequence illegalArgumentValue) {
		super(determineMessage(argumentName, pattern));
		this.illegalArgumentValue = illegalArgumentValue;
	}

	/**
	 * Constructs a new exception with the message {@link IllegalPatternArgumentException#MESSAGE_WITH_NAME} including
	 * the given name as string representation, the pattern which the argument must match and cause.
	 * 
	 * @param argumentName
	 *            the name of the passed argument
	 * @param pattern
	 *            Pattern, that a string or character sequence should correspond to
	 * @param illegalArgumentValue
	 *            The illegal value which caused this exception to be thrown.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalPatternArgumentException(@Nullable final String argumentName, @Nullable final Pattern pattern,
			@Nullable final CharSequence illegalArgumentValue, @Nullable final Throwable cause) {
		super(determineMessage(argumentName, pattern), cause);
		this.illegalArgumentValue = illegalArgumentValue;
	}

	@Override
	public CharSequence getIllegalArgument() {
		return illegalArgumentValue;
	}

}
