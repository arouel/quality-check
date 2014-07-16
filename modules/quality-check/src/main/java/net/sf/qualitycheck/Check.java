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
package net.sf.qualitycheck;

import java.lang.annotation.Annotation;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalEqualException;
import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;
import net.sf.qualitycheck.exception.IllegalMissingAnnotationException;
import net.sf.qualitycheck.exception.IllegalNaNArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNotContainedArgumentException;
import net.sf.qualitycheck.exception.IllegalNotEqualException;
import net.sf.qualitycheck.exception.IllegalNotGreaterOrEqualThanException;
import net.sf.qualitycheck.exception.IllegalNotGreaterThanException;
import net.sf.qualitycheck.exception.IllegalNotLesserThanException;
import net.sf.qualitycheck.exception.IllegalNotNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumericArgumentException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;
import net.sf.qualitycheck.exception.IllegalPositiveArgumentException;
import net.sf.qualitycheck.exception.IllegalRangeException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.qualitycheck.exception.RuntimeInstantiationException;

/**
 * This class offers simple static methods to test your arguments to be valid.
 * <p>
 * Checks should be added to all arguments of all public methods in your implementation to ensure that only expected
 * values will taken for ongoing processing. This is major step to deal with technical errors early (fail-fast) and
 * should avoid throwing of {@code NullPointerException}s or {@code IndexOutOfBoundsException}s etc. that needs to be
 * analyzed deeply why they occur.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public final class Check {

	/**
	 * Holder for the regular expression to determine numeric values. Using the holder pattern guarantees that the
	 * regular expression is initialized before the first use (thread safe!) and that it is only initialized if it is
	 * needed. So, we do not pay any performance bounty for regular expressions when using other checks.
	 */
	protected static final class NumericRegularExpressionHolder {

		private static final Pattern NUMERIC_REGEX = Pattern.compile("[0-9]+");

		@Nonnull
		public static Pattern getPattern() {
			return NUMERIC_REGEX;
		}

	}

	/**
	 * Representation of an empty argument name.
	 */
	private static final String EMPTY_ARGUMENT_NAME = "";

	/**
	 * Checks the passed {@code value} against the ranges of the given datatype.
	 * 
	 * @param value
	 *            value which must be a number and in the range of the given datatype.
	 * @param type
	 *            requested return value type, must be a subclass of {@code Number}
	 * @return a number
	 * 
	 * @throws NumberFormatException
	 *             if the given value can not be parsed as a number
	 */
	private static <T> Number checkNumberInRange(final String value, final Class<T> type) {
		final Number ret;
		if (type.equals(Byte.class)) {
			final Number number = new BigInteger(value);
			NumberInRange.checkByte(number);
			ret = Byte.valueOf(number.byteValue());
		} else if (type.equals(Double.class)) {
			final Number number = new BigDecimal(value);
			NumberInRange.checkDouble(number);
			ret = Double.valueOf(number.doubleValue());
		} else if (type.equals(Float.class)) {
			final Number number = new BigDecimal(value);
			NumberInRange.checkFloat(number);
			ret = Float.valueOf(number.floatValue());
		} else if (type.equals(Integer.class)) {
			final Number number = new BigInteger(value);
			NumberInRange.checkInteger(number);
			ret = Integer.valueOf(number.intValue());
		} else if (type.equals(Long.class)) {
			final Number number = new BigInteger(value);
			NumberInRange.checkLong(number);
			ret = Long.valueOf(number.longValue());
		} else if (type.equals(Short.class)) {
			final Number number = new BigInteger(value);
			NumberInRange.checkShort(number);
			ret = Short.valueOf(number.shortValue());
		} else if (type.equals(BigInteger.class)) {
			ret = new BigInteger(value);
		} else if (type.equals(BigDecimal.class)) {
			ret = new BigDecimal(value);
		} else {
			throw new IllegalNumberArgumentException("Return value is no known subclass of 'java.lang.Number': " + type.getName());
		}
		return ret;
	}

	/**
	 * Ensures that an element {@code needle} is contained in a collection {@code haystack}.
	 * 
	 * <p>
	 * This is in particular useful if you want to check whether an enum value is contained in an {@code EnumSet}. The
	 * check is implemented using {@link java.util.Collection#contains(Object)}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#contains(Collection, Object, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param haystack
	 *            A collection which must contain {@code needle}
	 * @param needle
	 *            An object that must be contained into a collection.
	 * @return the passed argument {@code needle}
	 * 
	 * @throws IllegalNotContainedArgumentException
	 *             if the passed {@code needle} can not be found in {@code haystack}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotContainedArgumentException.class })
	public static <T extends Object> T contains(@Nonnull final Collection<T> haystack, @Nonnull final T needle) {
		Check.notNull(haystack, "haystack");
		Check.notNull(needle, "needle");

		if (!haystack.contains(needle)) {
			throw new IllegalNotContainedArgumentException(needle);
		}

		return needle;
	}

	/**
	 * Ensures that an element {@code needle} is contained in a collection {@code haystack}.
	 * 
	 * <p>
	 * This is in particular useful if you want to check whether an enum value is contained in an {@code EnumSet}. The
	 * check is implemented using {@link java.util.Collection#contains(Object)}.
	 * 
	 * @param haystack
	 *            A collection which must contain {@code needle}
	 * @param needle
	 *            An object that must be contained into a collection.
	 * @param name
	 *            name of argument of {@code needle}
	 * @return the passed argument {@code needle}
	 * 
	 * @throws IllegalNotContainedArgumentException
	 *             if the passed {@code needle} can not be found in {@code haystack}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotContainedArgumentException.class })
	public static <T extends Object> T contains(@Nonnull final Collection<T> haystack, @Nonnull final T needle, @Nonnull final String name) {
		Check.notNull(haystack, "haystack");
		Check.notNull(needle, "needle");

		if (!haystack.contains(needle)) {
			throw new IllegalNotContainedArgumentException(name, needle);
		}

		return needle;
	}

	/**
	 * Checks if the given array contains {@code null}.
	 * 
	 * @param array
	 *            reference to an array
	 * @return {@code true} if the array contains {@code null}, otherwise {@code false}
	 */
	private static boolean containsNullElements(@Nonnull final Object[] array) {
		boolean containsNull = false;
		for (final Object o : array) {
			if (o == null) {
				containsNull = true;
				break;
			}
		}
		return containsNull;
	}

	/**
	 * Ensures that a passed boolean is equal to another boolean. The comparison is made using
	 * <code>expected != check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(boolean, boolean, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            boolean to be checked
	 * @return the passed boolean argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static boolean equals(final boolean expected, final boolean check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed boolean is equal to another boolean. The comparison is made using
	 * <code>expected != check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            boolean to be checked
	 * @param message
	 *            an error message describing why the booleans must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed boolean argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static boolean equals(final boolean expected, final boolean check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed byte is equal to another byte. The comparison is made using <code>expected != check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(byte, byte, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            byte to be checked
	 * @return the passed byte argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static byte equals(final byte expected, final byte check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed byte is equal to another byte. The comparison is made using <code>expected != check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            byte to be checked
	 * @param message
	 *            an error message describing why the bytes must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the byte boolean argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static byte equals(final byte expected, final byte check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed char is equal to another char. The comparison is made using <code>expected != check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(char, char, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            char to be checked
	 * @return the passed char argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static char equals(final char expected, final char check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed char is equal to another char. The comparison is made using <code>expected != check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            char to be checked
	 * @param message
	 *            an error message describing why the chars must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed char argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static char equals(final char expected, final char check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed intH is equal to another int. The comparison is made using <code>expected != check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(int, int, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            int to be checked
	 * @return the passed int argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static int equals(final int expected, final int check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed int is equal to another int. The comparison is made using <code>expected != check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            int to be checked
	 * @param message
	 *            an error message describing why the ints must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed int argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static int equals(final int expected, final int check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed long is equal to another long. The comparison is made using <code>expected != check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(long, long, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            long to be checked
	 * @return the passed long argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static long equals(final long expected, final long check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed long is equal to another long. The comparison is made using <code>expected != check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            long to be checked
	 * @param message
	 *            an error message describing why the longs must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed long argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static long equals(final long expected, final long check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed short is equal to another short. The comparison is made using
	 * <code>expected != check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(short, short, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            short to be checked
	 * @return the passed short argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static short equals(final short expected, final short check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed short is equal to another short. The comparison is made using
	 * <code>expected != check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            short to be checked
	 * @param message
	 *            an error message describing why the shorts must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed short {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalNotEqualException.class)
	public static short equals(final short expected, final short check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (expected != check) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is equal to another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) != 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(Comparable, Comparable, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotEqualException.class })
	public static <T extends Comparable<T>> T equals(@Nonnull final T expected, @Nonnull final T check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) != 0) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed object is equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check) }.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#equals(Object, Object, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @return the passed argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotEqualException.class })
	public static <T extends Object> T equals(@Nonnull final T expected, @Nonnull final T check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (!expected.equals(check)) {
			throw new IllegalNotEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is equal to another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) != 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the <a>s must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotEqualException.class })
	public static <T extends Comparable<T>> T equals(@Nonnull final T expected, @Nonnull final T check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) != 0) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed object is equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check) }.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @param message
	 *            an error message describing why the objects must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return the passed argument {@code check}
	 * 
	 * @throws IllegalNotEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotEqualException.class })
	public static <T extends Object> T equals(@Nonnull final T expected, @Nonnull final T check, @Nonnull final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (!expected.equals(check)) {
			throw new IllegalNotEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is greater or equal compared to another {@code Comparable}. The
	 * comparison is made using {@code expected.compareTo(check) > 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterOrEqualThanException
	 *             if the argument value {@code check} is not greater or equal than the value {@code expected} when
	 *             using method {@code compareTo}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotGreaterOrEqualThanException.class })
	public static <T extends Comparable<T>> T greaterOrEqualThan(@Nonnull final T expected, @Nonnull final T check) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) > 0) {
			throw new IllegalNotGreaterOrEqualThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is greater or equal compared to another {@code Comparable}. The
	 * comparison is made using {@code expected.compareTo(check) > 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterOrEqualThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterOrEqualThanException
	 *             if the argument value {@code check} is not greater or equal than the value {@code expected} when
	 *             using method {@code compareTo}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotGreaterOrEqualThanException.class })
	public static <T extends Comparable<T>> T greaterOrEqualThan(@Nonnull final T expected, @Nonnull final T check,
			@Nonnull final String message) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) > 0) {
			throw new IllegalNotGreaterOrEqualThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code byte} is greater to another {@code byte}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static byte greaterThan(final byte expected, final byte check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code byte} is greater than another {@code byte}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static byte greaterThan(final byte expected, final byte check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code char} is greater to another {@code char}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static char greaterThan(final char expected, final char check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code char} is greater than another {@code char}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static char greaterThan(final char expected, final char check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code double} is greater to another {@code double}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static double greaterThan(final double expected, final double check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code double} is greater than another {@code double}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static double greaterThan(final double expected, final double check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code float} is greater to another {@code float}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static float greaterThan(final float expected, final float check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code float} is greater than another {@code float}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static float greaterThan(final float expected, final float check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code int} is greater to another {@code int}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static int greaterThan(final int expected, final int check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code int} is greater than another {@code int}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static int greaterThan(final int expected, final int check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code long} is greater to another {@code long}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static long greaterThan(final long expected, final long check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code long} is greater than another {@code long}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static long greaterThan(final long expected, final long check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code short} is greater to another {@code short}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static short greaterThan(final short expected, final short check) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code short} is greater than another {@code short}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotGreaterThanException.class)
	public static short greaterThan(final short expected, final short check, @Nonnull final String message) {
		if (expected >= check) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is greater to another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) >= 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected} when using method
	 *             {@code compareTo}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotGreaterThanException.class })
	public static <T extends Comparable<T>> T greaterThan(@Nonnull final T expected, @Nonnull final T check) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) >= 0) {
			throw new IllegalNotGreaterThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is greater than another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) >= 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparable must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotGreaterThanException
	 *             if the argument value {@code check} is not greater than value {@code expected} when using method
	 *             {@code compareTo}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotGreaterThanException.class })
	public static <T extends Comparable<T>> T greaterThan(@Nonnull final T expected, @Nonnull final T check, @Nonnull final String message) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) >= 0) {
			throw new IllegalNotGreaterThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed class has an annotation of a specific type
	 * 
	 * @param clazz
	 *            the class that must have a required annotation
	 * @param annotation
	 *            the type of annotation that is required on the class
	 * @return the given annotation which is present on the checked class
	 * 
	 * @throws IllegalMissingAnnotationException
	 *             if the passed annotation is not annotated at the given class
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalMissingAnnotationException.class })
	public static Annotation hasAnnotation(@Nonnull final Class<?> clazz, @Nonnull final Class<? extends Annotation> annotation) {
		Check.notNull(clazz, "clazz");
		Check.notNull(annotation, "annotation");
		if (!clazz.isAnnotationPresent(annotation)) {
			throw new IllegalMissingAnnotationException(annotation, clazz);
		}

		return clazz.getAnnotation(annotation);
	}

	/**
	 * Ensures that a passed argument is a member of a specific type.
	 * 
	 * @param type
	 *            class that the given object is a member of
	 * @param obj
	 *            the object reference that should be a member of a specific {@code type}
	 * @return the given object cast to type
	 * 
	 * @throws IllegalInstanceOfArgumentException
	 *             if the given argument {@code obj} is not a member of {@code type}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalInstanceOfArgumentException.class })
	@SuppressWarnings("unchecked")
	public static <T> T instanceOf(@Nonnull final Class<?> type, @Nonnull final Object obj) {
		return (T) instanceOf(type, obj, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a passed argument is a member of a specific type.
	 * 
	 * @param type
	 *            class that the given object is a member of
	 * @param obj
	 *            the object reference that should be a member of a specific {@code type}
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given object cast to type
	 * 
	 * @throws IllegalInstanceOfArgumentException
	 *             if the given argument {@code obj} is not a member of {@code type}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalInstanceOfArgumentException.class })
	@SuppressWarnings("unchecked")
	public static <T> T instanceOf(@Nonnull final Class<?> type, @Nonnull final Object obj, @Nullable final String name) {
		Check.notNull(type, "type");
		Check.notNull(obj, "obj");
		if (!type.isInstance(obj)) {
			throw new IllegalInstanceOfArgumentException(name, type, obj.getClass());
		}
		return (T) obj;
	}

	/**
	 * Ensures that a given argument is {@code null}.
	 * 
	 * Normally, the usage of {@code null} arguments is disregarded by the authors of quality-check. Still, there are
	 * certain circumstances where null is required, e.g. the primary key of an entity before it is written to the
	 * database for the first time. In such cases it is ok to use null values and there should also be checks for them.
	 * For example, to avoid overwriting an existing primary key with a new one.
	 * 
	 * @param reference
	 *            reference which must be null
	 * 
	 * @throws IllegalNotNullArgumentException
	 *             if the given argument {@code reference} is not null
	 */
	@Throws(IllegalNotNullArgumentException.class)
	public static void isNull(@Nullable final Object reference) {
		if (reference != null) {
			throw new IllegalNotNullArgumentException(reference);
		}
	}

	/**
	 * Ensures that a given argument is {@code null}.
	 * 
	 * Normally, the usage of {@code null} arguments is disregarded by the authors of quality-check. Still, there are
	 * certain circumstances where null is required, e.g. the primary key of an entity before it is written to the
	 * database for the first time. In such cases it is ok to use null values and there should also be checks for them.
	 * For example, to avoid overwriting an existing primary key with a new one.
	 * 
	 * @param reference
	 *            reference which must be null.
	 * @param name
	 *            name of object reference (in source code)
	 * 
	 * @throws IllegalNotNullArgumentException
	 *             if the given argument {@code reference} is not null
	 */
	@Throws(IllegalNotNullArgumentException.class)
	public static void isNull(@Nullable final Object reference, @Nullable final String name) {
		if (reference != null) {
			throw new IllegalNotNullArgumentException(name, reference);
		}
	}

	/**
	 * Ensures that a String argument is a number.
	 * 
	 * @param value
	 *            value which must be a number
	 * @return the given string argument converted to an int
	 * 
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is not a number
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNumberArgumentException.class })
	public static int isNumber(@Nonnull final String value) {
		Check.notNull(value, "value");
		return Check.isNumber(value, Integer.class).intValue();
	}

	/**
	 * Ensures that a String argument is a number. This overload supports all subclasses of {@code Number}. The number
	 * is first converted to a BigInteger
	 * 
	 * @param value
	 *            value which must be a number
	 * @param type
	 *            requested return value type, must be a subclass of {@code Number}, i.e. one of {@code BigDecimal,
	 *            BigInteger, Byte, Double, Float, Integer, Long, Short}
	 * @return the given string argument converted to a number of the requested type
	 * 
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNumberArgumentException.class })
	public static <T extends Number> T isNumber(@Nonnull final String value, @Nonnull final Class<T> type) {
		return isNumber(value, null, type);
	}

	/**
	 * Ensures that a string argument is a number according to {@code Integer.parseInt}
	 * 
	 * @param value
	 *            value which must be a number
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given string argument converted to an int
	 * 
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNumberArgumentException.class })
	public static int isNumber(@Nonnull final String value, @Nullable final String name) {
		Check.notNull(value, "value");
		return Check.isNumber(value, name, Integer.class).intValue();
	}

	/**
	 * Ensures that a String argument is a number. This overload supports all subclasses of {@code Number}. The number
	 * is first converted to a {@code BigDecimal} or {@code BigInteger}. Floating point types are only supported if the
	 * {@code type} is one of {@code Float, Double, BigDecimal}.
	 * 
	 * <p>
	 * This method does also check against the ranges of the given datatypes.
	 * 
	 * @param value
	 *            value which must be a number and in the range of the given datatype.
	 * @param name
	 *            (optional) name of object reference (in source code).
	 * @param type
	 *            requested return value type, must be a subclass of {@code Number}, i.e. one of {@code BigDecimal,
	 *            BigInteger, Byte, Double, Float, Integer, Long, Short}
	 * @return the given string argument converted to a number of the requested type
	 * 
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNumberArgumentException.class })
	public static <T extends Number> T isNumber(@Nonnull final String value, @Nullable final String name, @Nonnull final Class<T> type) {
		Check.notNull(value, "value");
		Check.notNull(type, "type");

		final Number ret;
		try {
			ret = checkNumberInRange(value, type);
		} catch (final NumberFormatException nfe) {
			if (name == null) {
				throw new IllegalNumberArgumentException(value, nfe);
			} else {
				throw new IllegalNumberArgumentException(name, value, nfe);
			}
		}

		return type.cast(ret);
	}

	/**
	 * Ensures that a readable sequence of {@code char} values is numeric. Numeric arguments consist only of the
	 * characters 0-9 and may start with 0 (compared to number arguments, which must be valid numbers - think of a bank
	 * account number).
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#isNumeric(CharSequence, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a readable sequence of {@code char} values which must be a number
	 * @return the given string argument
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNumberArgumentException.class })
	public static <T extends CharSequence> T isNumeric(@Nonnull final T value) {
		return isNumeric(value, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a readable sequence of {@code char} values is numeric. Numeric arguments consist only of the
	 * characters 0-9 and may start with 0 (compared to number arguments, which must be valid numbers - think of a bank
	 * account number).
	 * 
	 * @param value
	 *            a readable sequence of {@code char} values which must be a number
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given string argument
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNumericArgumentException.class })
	public static <T extends CharSequence> T isNumeric(@Nonnull final T value, @Nullable final String name) {
		Check.notNull(value, "value");
		if (!matches(NumericRegularExpressionHolder.getPattern(), value)) {
			throw new IllegalNumericArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that a passed {@code byte} is less than another {@code byte}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code byte} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static byte lesserThan(final byte expected, final byte check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code byte} is less than another {@code byte}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static byte lesserThan(final byte expected, final byte check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code char} is less than another {@code char}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code char} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static char lesserThan(final char expected, final char check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code char} is less than another {@code char}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static char lesserThan(final char expected, final char check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code double} is less than another {@code double}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code double} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static double lesserThan(final double expected, final double check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code double} is less than another {@code double}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static double lesserThan(final double expected, final double check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code float} is less than another {@code float}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code float} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static float lesserThan(final float expected, final float check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code float} is less than another {@code float}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static float lesserThan(final float expected, final float check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code int} is less than another {@code int}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code int} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static int lesserThan(final int expected, final int check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code int} is less than another {@code int}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static int lesserThan(final int expected, final int check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code long} is less than another {@code long}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code long} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static long lesserThan(final long expected, final long check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code long} is less than another {@code long}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static long lesserThan(final long expected, final long check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code short} is less than another {@code short}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code short} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static short lesserThan(final short expected, final short check) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code short} is less than another {@code short}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected}
	 */
	@ArgumentsChecked
	@Throws(IllegalNotLesserThanException.class)
	public static short lesserThan(final short expected, final short check, @Nonnull final String message) {
		if (expected <= check) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is less than another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) <= 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected} when using method
	 *             {@code compareTo}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotLesserThanException.class })
	public static <T extends Comparable<T>> T lesserThan(@Nonnull final T expected, @Nonnull final T check) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) <= 0) {
			throw new IllegalNotLesserThanException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is less than another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) <= 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalNotLesserThanException
	 *             if the argument value {@code check} is not lesser than value {@code expected} when using method
	 *             {@code compareTo}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotLesserThanException.class })
	public static <T extends Comparable<T>> T lesserThan(@Nonnull final T expected, @Nonnull final T check, @Nonnull final String message) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) <= 0) {
			throw new IllegalNotLesserThanException(message, check);
		}

		return check;
	}

	/**
	 * Checks whether a character sequence matches against a specified pattern or not.
	 * 
	 * @param pattern
	 *            pattern, that the {@code chars} must correspond to
	 * @param chars
	 *            a readable sequence of {@code char} values which should match the given pattern
	 * @return {@code true} when {@code chars} matches against the passed {@code pattern}, otherwise {@code false}
	 */
	private static boolean matches(@Nonnull final Pattern pattern, @Nonnull final CharSequence chars) {
		return pattern.matcher(chars).matches();
	}

	/**
	 * Ensures that a readable sequence of {@code char} values matches a specified pattern. If the given character
	 * sequence does not match against the passed pattern, an {@link IllegalPatternArgumentException} will be thrown.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#matchesPattern(Pattern, CharSequence, String)} and pass as
	 * second argument the name of the parameter to enhance the exception message.
	 * 
	 * @param pattern
	 *            pattern, that the {@code chars} must correspond to
	 * @param chars
	 *            a readable sequence of {@code char} values which should match the given pattern
	 * @return the passed {@code chars} that matches the given pattern
	 * 
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code chars} is {@code null}
	 * @throws IllegalPatternArgumentException
	 *             if the given {@code chars} that does not match the {@code pattern}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalPatternArgumentException.class })
	public static <T extends CharSequence> T matchesPattern(@Nonnull final Pattern pattern, @Nonnull final T chars) {
		return matchesPattern(pattern, chars, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a readable sequence of {@code char} values matches a specified pattern. If the given character
	 * sequence does not match against the passed pattern, an {@link IllegalPatternArgumentException} will be thrown.
	 * 
	 * @param pattern
	 *            pattern, that the {@code chars} must correspond to
	 * @param chars
	 *            a readable sequence of {@code char} values which should match the given pattern
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed {@code chars} that matches the given pattern
	 * 
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code chars} is {@code null}
	 * @throws IllegalPatternArgumentException
	 *             if the given {@code chars} that does not match the {@code pattern}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalPatternArgumentException.class })
	public static <T extends CharSequence> T matchesPattern(@Nonnull final Pattern pattern, @Nonnull final T chars,
			@Nullable final String name) {
		Check.notNull(pattern, "pattern");
		Check.notNull(chars, "chars");
		if (!matches(pattern, chars)) {
			throw new IllegalPatternArgumentException(name, pattern, chars);
		}
		return chars;
	}

	/**
	 * Ensures that an iterable reference is neither {@code null} nor contains any elements that are {@code null}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#noNullElements(Iterable, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param iterable
	 *            the iterable reference which should not contain {@code null}
	 * @return the passed reference which contains no elements that are {@code null}
	 * 
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code iterable} contains elements that are {@code null}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNullElementsException.class })
	public static <T extends Iterable<?>> T noNullElements(@Nonnull final T iterable) {
		return noNullElements(iterable, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that an iterable reference is neither {@code null} nor contains any elements that are {@code null}.
	 * 
	 * @param iterable
	 *            the iterable reference which should not contain {@code null}
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code iterable} contains elements that are {@code null}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNullElementsException.class })
	public static <T extends Iterable<?>> T noNullElements(@Nonnull final T iterable, final String name) {
		Check.notNull(iterable, "iterable");
		for (final Object element : iterable) {
			if (element == null) {
				throw new IllegalNullElementsException(name);
			}
		}
		return iterable;
	}

	/**
	 * Ensures that an array does not contain {@code null}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#noNullElements(Object[], String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param array
	 *            reference to an array
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code array} contains {@code null}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNullElementsException.class })
	public static <T> T[] noNullElements(@Nonnull final T[] array) {
		return noNullElements(array, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that an array does not contain {@code null}.
	 * 
	 * @param array
	 *            reference to an array
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code array} contains {@code null}
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNullElementsException.class })
	public static <T> T[] noNullElements(@Nonnull final T[] array, @Nullable final String name) {
		Check.notNull(array, "array");
		if (containsNullElements(array)) {
			throw new IllegalNullElementsException(name);
		}
		return array;
	}

	/**
	 * Ensures that a passed parameter of the calling method is not empty, using the passed expression to evaluate the
	 * emptiness.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(boolean, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param expression
	 *            the result of the expression to verify the emptiness of a reference ({@code true} means empty,
	 *            {@code false} means not empty)
	 * 
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static void notEmpty(final boolean expression) {
		notEmpty(expression, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a passed parameter of the calling method is not empty, using the passed expression to evaluate the
	 * emptiness.
	 * 
	 * @param expression
	 *            the result of the expression to verify the emptiness of a reference ({@code true} means empty,
	 *            {@code false} means not empty)
	 * @param name
	 *            name of object reference (in source code)
	 * 
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static void notEmpty(final boolean expression, @Nullable final String name) {
		if (expression) {
			throw new IllegalEmptyArgumentException(name);
		}
	}

	/**
	 * Ensures that a passed string as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(CharSequence, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param chars
	 *            a readable sequence of {@code char} values which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends CharSequence> T notEmpty(@Nonnull final T chars) {
		notNull(chars);
		notEmpty(chars, chars.length() == 0, EMPTY_ARGUMENT_NAME);
		return chars;
	}

	/**
	 * Ensures that a passed collection as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param collection
	 *            a collection which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code collection} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code collection} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Collection<?>> T notEmpty(@Nonnull final T collection) {
		notNull(collection);
		notEmpty(collection, collection.isEmpty(), EMPTY_ARGUMENT_NAME);
		return collection;
	}

	/**
	 * Ensures that a passed iterable as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Iterable, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param iterable
	 *            an iterable which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code iterable} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code iterable} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Iterable<?>> T notEmpty(@Nonnull final T iterable) {
		notNull(iterable);
		notEmpty(iterable, !iterable.iterator().hasNext(), EMPTY_ARGUMENT_NAME);
		return iterable;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param map
	 *            a map which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code map} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code map} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Map<?, ?>> T notEmpty(@Nonnull final T map) {
		notNull(map);
		notEmpty(map, map.isEmpty(), EMPTY_ARGUMENT_NAME);
		return map;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not empty. The passed boolean
	 * value is the result of checking whether the reference is empty or not.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setText(String text) {
	 * 	Check.notEmpty(text, text.isEmpty(), &quot;text&quot;);
	 * 	this.text = text;
	 * }
	 * </pre>
	 * 
	 * @param reference
	 *            an object reference which should not be empty
	 * @param expression
	 *            the result of the expression to verify the emptiness of a reference ({@code true} means empty,
	 *            {@code false} means not empty)
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code reference} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T> T notEmpty(@Nonnull final T reference, final boolean expression, @Nullable final String name) {
		notNull(reference, name);
		if (expression) {
			throw new IllegalEmptyArgumentException(name);
		}
		return reference;
	}

	/**
	 * Ensures that a passed string as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setText(String text) {
	 * 	this.text = Check.notEmpty(text, &quot;text&quot;);
	 * }
	 * </pre>
	 * 
	 * @param chars
	 *            a readable sequence of {@code char} values which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code string} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code string} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends CharSequence> T notEmpty(@Nonnull final T chars, @Nullable final String name) {
		notNull(chars, name);
		notEmpty(chars, chars.length() == 0, name);
		return chars;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param map
	 *            a map which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code map} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code map} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Map<?, ?>> T notEmpty(@Nonnull final T map, @Nullable final String name) {
		notNull(map);
		notEmpty(map, map.isEmpty(), name);
		return map;
	}

	/**
	 * Ensures that a passed collection as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setCollection(Collection&lt;String&gt; collection) {
	 * 	this.collection = Check.notEmpty(collection, &quot;collection&quot;);
	 * }
	 * </pre>
	 * 
	 * @param collection
	 *            a collection which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code collection} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code collection} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Collection<?>> T notEmpty(@Nonnull final T collection, @Nullable final String name) {
		notNull(collection, name);
		notEmpty(collection, collection.isEmpty(), name);
		return collection;
	}

	/**
	 * Ensures that a passed iterable as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * The following example describes how to use it.
	 * 
	 * <pre>
	 * &#064;ArgumentsChecked
	 * public setIterable(Iterable&lt;String&gt; iterable) {
	 * 	this.iterable = Check.notEmpty(iterable, &quot;iterable&quot;);
	 * }
	 * </pre>
	 * 
	 * @param iterable
	 *            an iterable which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code iterable} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code iterable} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T extends Iterable<?>> T notEmpty(@Nonnull final T iterable, @Nullable final String name) {
		notNull(iterable, name);
		notEmpty(iterable, !iterable.iterator().hasNext(), name);
		return iterable;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Object[], String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param array
	 *            a map which should not be empty
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code array} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code array} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T> T[] notEmpty(@Nonnull final T[] array) {
		notNull(array);
		notEmpty(array, array.length == 0, EMPTY_ARGUMENT_NAME);
		return array;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * @param array
	 *            a map which should not be empty
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference that is not empty
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code array} is {@code null}
	 * @throws IllegalEmptyArgumentException
	 *             if the given argument {@code array} is empty
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public static <T> T[] notEmpty(@Nonnull final T[] array, @Nullable final String name) {
		notNull(array);
		notEmpty(array, array.length == 0, EMPTY_ARGUMENT_NAME);
		return array;
	}

	/**
	 * Ensures that a passed boolean is not equal to another boolean. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(boolean, boolean, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            boolean to be checked
	 * @return the passed boolean argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static boolean notEquals(final boolean expected, final boolean check) {
		if (expected == check) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed boolean is not equal to another boolean. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            boolean to be checked
	 * @param message
	 *            an error message describing why the booleans must equal (will be passed to
	 *            {@code IllegalEqualException})
	 * @return the passed boolean argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static boolean notEquals(final boolean expected, final boolean check, @Nonnull final String message) {
		if (expected == check) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed byte is not equal to another byte. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(byte, byte, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            byte to be checked
	 * @return the passed byte argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static byte notEquals(final byte expected, final byte check) {
		if (expected == check) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed byte is not equal to another byte. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            byte to be checked
	 * @param message
	 *            an error message describing why the bytes must equal (will be passed to {@code IllegalEqualException})
	 * @return the byte boolean argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static byte notEquals(final byte expected, final byte check, @Nonnull final String message) {
		if (expected == check) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed char is not equal to another char. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(char, char, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            char to be checked
	 * @return the passed char argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static char notEquals(final char expected, final char check) {
		if (expected == check) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed char is not equal to another char. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            char to be checked
	 * @param message
	 *            an error message describing why the chars must equal (will be passed to {@code IllegalEqualException})
	 * @return the passed char argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static char notEquals(final char expected, final char check, @Nonnull final String message) {
		if (expected == check) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed intH is not equal to another int. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(int, int, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            int to be checked
	 * @return the passed int argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static int notEquals(final int expected, final int check) {
		if (expected == check) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed int is not equal to another int. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            int to be checked
	 * @param message
	 *            an error message describing why the ints must equal (will be passed to {@code IllegalEqualException})
	 * @return the passed int argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static int notEquals(final int expected, final int check, @Nonnull final String message) {
		if (expected == check) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed long is not equal to another long. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(long, long, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            long to be checked
	 * @return the passed long argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static long notEquals(final long expected, final long check) {
		if (expected == check) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed long is not equal to another long. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            long to be checked
	 * @param message
	 *            an error message describing why the longs must equal (will be passed to {@code IllegalEqualException})
	 * @return the passed long argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static long notEquals(final long expected, final long check, @Nonnull final String message) {
		if (expected == check) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed short is not equal to another short. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(short, short, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            short to be checked
	 * @return the passed short argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static short notEquals(final short expected, final short check) {
		if (expected == check) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed short is not equal to another short. The comparison is made using
	 * <code>expected == check</code>.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            short to be checked
	 * @param message
	 *            an error message describing why the shorts must equal (will be passed to {@code IllegalEqualException}
	 *            )
	 * @return the passed short {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@Throws(IllegalEqualException.class)
	public static short notEquals(final short expected, final short check, @Nonnull final String message) {
		if (expected == check) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is not equal to another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) == 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(Comparable, Comparable, String)} and pass as
	 * second argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEqualException.class })
	public static <T extends Comparable<T>> T notEquals(@Nonnull final T expected, @Nonnull final T check) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) == 0) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed object is not equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check) }.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEquals(Object, Object, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @return the passed argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEqualException.class })
	public static <T extends Object> T notEquals(@Nonnull final T expected, @Nonnull final T check) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.equals(check)) {
			throw new IllegalEqualException(check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is not equal to another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) == 0}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the <a>s must equal (will be passed to {@code IllegalEqualException})
	 * @return the passed {@code Comparable} argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEqualException.class })
	public static <T extends Comparable<T>> T notEquals(@Nonnull final T expected, @Nonnull final T check, @Nonnull final String message) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.compareTo(check) == 0) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed object is not equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check)}.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @param message
	 *            an error message describing why the objects must equal (will be passed to
	 *            {@code IllegalEqualException})
	 * @return the passed argument {@code check}
	 * 
	 * @throws IllegalEqualException
	 *             if both argument values are not equal
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalEqualException.class })
	public static <T extends Object> T notEquals(@Nonnull final T expected, @Nonnull final T check, @Nonnull final String message) {
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (expected.equals(check)) {
			throw new IllegalEqualException(message, check);
		}

		return check;
	}

	/**
	 * Do not perform any check and just return {@code t}.
	 * 
	 * This is useful if you have several checks on some arguments, but do not check other arguments on purpose. This
	 * checks helps to document that a check was omitted on purpose instead of forgotten.
	 * 
	 * @param t
	 *            any object
	 * @return t
	 */
	public static <T> T nothing(final T t) {
		return t;
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNaN(double, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @see java.lang.Double#NaN
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws(IllegalNaNArgumentException.class)
	public static double notNaN(final double value) {
		return notNaN(value, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see java.lang.Double#NaN
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws(IllegalNaNArgumentException.class)
	public static double notNaN(final double value, @Nullable final String name) {
		// most efficient check for NaN, see Double.isNaN(value))
		if (value != value) {
			throw new IllegalNaNArgumentException(name);
		}
		return value;
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNaN(float, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @see java.lang.Float#NaN
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws(IllegalNaNArgumentException.class)
	public static float notNaN(final float value) {
		return notNaN(value, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see java.lang.Float#NaN
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given float value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws(IllegalNaNArgumentException.class)
	public static float notNaN(final float value, @Nullable final String name) {
		// most efficient check for NaN, see Float.isNaN(value))
		if (value != value) {
			throw new IllegalNaNArgumentException(name);
		}
		return value;
	}

	/**
	 * Ensures that an double reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNegative(double, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalNegativeArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static double notNegative(final double value) {
		if (value < 0.0) {
			throw new IllegalNegativeArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an double reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static double notNegative(final double value, @Nullable final String name) {
		if (value < 0.0) {
			throw new IllegalNegativeArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an float reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNegative(float, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalNegativeArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static float notNegative(final float value) {
		if (value < 0.0f) {
			throw new IllegalNegativeArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an float reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static float notNegative(final float value, @Nullable final String name) {
		if (value < 0.0f) {
			throw new IllegalNegativeArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an integer reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNegative(int, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalNegativeArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static int notNegative(final int value) {
		if (value < 0) {
			throw new IllegalNegativeArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an integer reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static int notNegative(final int value, @Nullable final String name) {
		if (value < 0) {
			throw new IllegalNegativeArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an long reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNegative(long, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalNegativeArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static long notNegative(final long value) {
		if (value < 0L) {
			throw new IllegalNegativeArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an long reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static long notNegative(final long value, @Nullable final String name) {
		if (value < 0L) {
			throw new IllegalNegativeArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an short reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNegative(short, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalNegativeArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static short notNegative(final short value) {
		if (value < (short) 0) {
			throw new IllegalNegativeArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an short reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static short notNegative(final short value, @Nullable final String name) {
		if (value < (short) 0) {
			throw new IllegalNegativeArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not {@code null}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notNull(Object, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param reference
	 *            an object reference
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(@Nonnull final T reference) {
		if (reference == null) {
			throw new IllegalNullArgumentException();
		}
		return reference;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not {@code null}.
	 * 
	 * @param reference
	 *            an object reference
	 * @param name
	 *            name of object reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(@Nonnull final T reference, @Nullable final String name) {
		if (reference == null) {
			throw new IllegalNullArgumentException(name);
		}
		return reference;
	}

	/**
	 * Ensures that an double reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notPositive(double, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalPositiveArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static double notPositive(final double value) {
		if (value > 0.0) {
			throw new IllegalPositiveArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an double reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static double notPositive(final double value, @Nullable final String name) {
		if (value > 0.0) {
			throw new IllegalPositiveArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an float reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notPositive(float, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalPositiveArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static float notPositive(final float value) {
		if (value > 0.0f) {
			throw new IllegalPositiveArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an float reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static float notPositive(final float value, @Nullable final String name) {
		if (value > 0.0f) {
			throw new IllegalPositiveArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an integer reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notPositive(int, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalPositiveArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static int notPositive(final int value) {
		if (value > 0) {
			throw new IllegalPositiveArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an integer reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static int notPositive(final int value, @Nullable final String name) {
		if (value > 0) {
			throw new IllegalPositiveArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an long reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notPositive(long, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalPositiveArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static long notPositive(final long value) {
		if (value > 0L) {
			throw new IllegalPositiveArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an long reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static long notPositive(final long value, @Nullable final String name) {
		if (value > 0L) {
			throw new IllegalPositiveArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that an short reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notPositive(short, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a number
	 * @return the non-null reference that was validated
	 * @throws IllegalPositiveArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static short notPositive(final short value) {
		if (value > (short) 0) {
			throw new IllegalPositiveArgumentException(value);
		}
		return value;
	}

	/**
	 * Ensures that an short reference passed as a parameter to the calling method is not greater than {@code 0}.
	 * 
	 * @param value
	 *            a number
	 * @param name
	 *            name of the number reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalPositiveArgumentException.class)
	public static short notPositive(final short value, @Nullable final String name) {
		if (value > (short) 0) {
			throw new IllegalPositiveArgumentException(name, value);
		}
		return value;
	}

	/**
	 * Ensures that a given position index is valid within the size of an array, list or string ...
	 * 
	 * @param index
	 *            index of an array, list or string
	 * @param size
	 *            size of an array list or string
	 * @return the index
	 * 
	 * @throws IllegalPositionIndexException
	 *             if the index is not a valid position index within an array, list or string of size <em>size</em>
	 * 
	 */
	@Throws(IllegalPositionIndexException.class)
	public static int positionIndex(final int index, final int size) {
		final boolean isIndexValid = (size >= 0) && (index >= 0) && (index < size);

		if (!isIndexValid) {
			throw new IllegalPositionIndexException(index, size);
		}

		return index;
	}

	/**
	 * Ensures that the given arguments are a valid range.
	 * 
	 * A range (<em>start</em>, <em>end</em>, <em>size</em>) is valid if the following conditions are {@code true}:
	 * <ul>
	 * <li>start <= size</li>
	 * <li>end <= size</li>
	 * <li>start <= end</li>
	 * <li>size >= 0</li>
	 * <li>start >= 0</li>
	 * <li>end >= 0</li>
	 * </ul>
	 * 
	 * @param start
	 *            the start value of the range (must be a positive integer or 0)
	 * @param end
	 *            the end value of the range (must be a positive integer or 0)
	 * @param size
	 *            the size value of the range (must be a positive integer or 0)
	 * 
	 * @throws IllegalRangeException
	 *             if the given arguments do not form a valid range
	 */
	@Throws(IllegalRangeException.class)
	public static void range(@Nonnegative final int start, @Nonnegative final int end, @Nonnegative final int size) {
		final boolean rangeIsValid = (start <= size) && (end <= size) && (start <= end);
		final boolean inputValuesAreValid = (size >= 0) && (start >= 0) && (end >= 0);

		if (!rangeIsValid || !inputValuesAreValid) {
			throw new IllegalRangeException(start, end, size);
		}
	}

	/**
	 * Ensures that a given state is {@code true}.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#stateIsTrue(boolean, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message. A better way is to create specific exceptions (with a
	 * good wording) for your case and to use the overloaded method {@link Check#stateIsTrue(boolean, Class)} and pass
	 * as second argument your exception.
	 * 
	 * @param expression
	 *            an expression that must be true to indicate a valid state
	 * 
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	@Throws(IllegalStateOfArgumentException.class)
	public static void stateIsTrue(final boolean expression) {
		if (!expression) {
			throw new IllegalStateOfArgumentException();
		}
	}

	/**
	 * Ensures that a given state is {@code true} and allows to specify the class of exception which is thrown in case
	 * the state is not {@code true}.
	 * 
	 * @param expression
	 *            an expression that must be {@code true} to indicate a valid state
	 * @param clazz
	 *            an subclass of {@link RuntimeException} which will be thrown if the given state is not valid
	 * @throws clazz
	 *             a new instance of {@code clazz} if the given arguments caused an invalid state
	 * @throws RuntimeInstantiationException
	 *             <strong>Attention</strong>: Be aware, that a {@code RuntimeInstantiationException} can be thrown when
	 *             the given {@code clazz} cannot be instantiated
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, RuntimeInstantiationException.class })
	public static void stateIsTrue(final boolean expression, final Class<? extends RuntimeException> clazz) {
		Check.notNull(clazz, "clazz");

		if (!expression) {
			RuntimeException re;
			try {
				re = clazz.newInstance();
			} catch (final InstantiationException e) {
				throw new RuntimeInstantiationException(clazz.getSimpleName(), e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeInstantiationException(clazz.getSimpleName(), e);
			}
			throw re;
		}
	}

	/**
	 * Ensures that a given state is {@code true}.
	 * 
	 * @param expression
	 *            an expression that must be {@code true} to indicate a valid state
	 * @param description
	 *            will be used in the error message to describe why the arguments caused an invalid state
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	@Throws(IllegalStateOfArgumentException.class)
	public static void stateIsTrue(final boolean expression, @Nonnull final String description) {
		if (!expression) {
			throw new IllegalStateOfArgumentException(description);
		}
	}

	/**
	 * Ensures that a given state is {@code true}
	 * 
	 * @param expression
	 *            an expression that must be {@code true} to indicate a valid state
	 * @param descriptionTemplate
	 *            format string template that explains why the state is invalid
	 * @param descriptionTemplateArgs
	 *            format string template arguments to explain why the state is invalid
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	@Throws(IllegalStateOfArgumentException.class)
	public static void stateIsTrue(final boolean expression, @Nonnull final String descriptionTemplate,
			final Object... descriptionTemplateArgs) {
		if (!expression) {
			throw new IllegalStateOfArgumentException(descriptionTemplate, descriptionTemplateArgs);
		}
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private Check() {
		// This class is not intended to create objects from it.
	}

}
