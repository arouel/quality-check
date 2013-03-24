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
package net.sf.qualitycheck;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalArgumentNotContainedException;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;
import net.sf.qualitycheck.exception.IllegalNaNArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNotEqualException;
import net.sf.qualitycheck.exception.IllegalNotNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumberRangeException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;
import net.sf.qualitycheck.exception.IllegalRangeException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.qualitycheck.exception.RuntimeInstantiationException;

/**
 * This class adds conditional checks to test your arguments to be valid. The checks are the same as offered in
 * {@code Check}, but all have an additional condition. The check is only executed if the condition is @code
 * <code>true</code>.
 * 
 * Examples for ConditionalCheck's are parameters that must be checked if they are not null. E.g. <code>
 *   public void method(@Nullable final String numericArgument, @Nullable final List<Value> list) {
 *   	ConditionalCheck.isNumeric( numericArgument != null, numericArgument, "numericArgument" );
 *   	ConditionalCheck.isNumeric( list != null, list, "list" );
 *   }
 * </code>
 * 
 * There are also cases where you can do more optimized technical checks: <code>
 *   public void method(final String address) {
 *   	ConditionalCheck.matchesPattern(address.length() <= 15,
 *   			Pattern.compile("\\d{3}.\\d{3}.\\d{3}.\\d{3}"), address);
 *   	ConditionalCheck.matchesPattern(address.length() > 15, 
 *   			Pattern.compile("[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}:[a-f0-9]{2}"), address);
 *   }
 * 
 * </code>
 * 
 * Use {@code ConditionalCheck} with care! Coditional checks are often an indicator for bad design. Good APIs accept
 * only values that are always valid. Seldom there are arguments which must be checked only in certain cases.
 * Additionally, take care that you do not mix up technical and functional checks. Conditional checks often tend to be
 * more functional than the technical checks offered by {@code Check}.
 * 
 * Logical checks should not be done using {@code ConditionalCheck}, because the way checks work one cannot be sure if
 * every branch is covered by measuring the branch coverage.
 * 
 * @author Dominik Seichter
 */
public final class ConditionalCheck {

	/**
	 * Ensures that an elemen {@code needle} is contained in a collection {@code hackstack}.
	 * 
	 * This is in particular useful if you want to check whether an enum value is contained in an {@code EnumSet}. The
	 * check is implemented using {@code Collection.contains}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param haystack
	 *            A collection which must contain {@code needle}
	 * @param needle
	 *            An object that must be contained into a collection.
	 * @return {@code needle}
	 * 
	 * @throws IllegalArgumentNotContainedException
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Object> T contains(final boolean condition, @Nonnull final Collection<T> haystack, @Nonnull final T needle) {
		if (condition) {
			Check.contains(haystack, needle);
		}

		return needle;
	}

	/**
	 * Ensures that an elemen {@code needle} is contained in a collection {@code hackstack}.
	 * 
	 * This is in particular useful if you want to check whether an enum value is contained in an {@code EnumSet}. The
	 * check is implemented using {@code Collection.contains}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param haystack
	 *            A collection which must contain {@code needle}
	 * @param needle
	 *            An object that must be contained into a collection.
	 * @param name
	 *            name of argument of {@code needle}
	 * @return {@code needle}
	 * 
	 * @throws IllegalArgumentNotContainedException
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Object> T contains(final boolean condition, @Nonnull final Collection<T> haystack, @Nonnull final T needle,
			@Nonnull final String name) {
		if (condition) {
			Check.contains(haystack, needle, name);
		}

		return needle;
	}

	/**
	 * Ensures that a passed boolean is equal to another boolean. The comparison is made using
	 * {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            boolean to be checked
	 * @return {@code check} The checked boolean
	 */
	public static boolean equals(final boolean condition, @Nonnull final boolean expected, @Nonnull final boolean check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed boolean is equal to another boolean. The comparison is made using
	 * {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            boolean to be checked
	 * @param message
	 *            an error message describing why the booleans must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked boolean
	 */
	public static boolean equals(final boolean condition, @Nonnull final boolean expected, @Nonnull final boolean check,
			final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed byteH is equal to another byte. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            byte to be checked
	 * @return {@code check} The checked byte
	 */
	public static byte equals(final boolean condition, @Nonnull final byte expected, @Nonnull final byte check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed byte is equal to another byte. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            byte to be checked
	 * @param message
	 *            an error message describing why the bytes must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked byte
	 */
	public static byte equals(final boolean condition, @Nonnull final byte expected, @Nonnull final byte check, final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed char is equal to another char. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            char to be checked
	 * @return {@code check} The checked char
	 */
	public static char equals(final boolean condition, @Nonnull final char expected, @Nonnull final char check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed char is equal to another char. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            char to be checked
	 * @param message
	 *            an error message describing why the chars must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked char
	 */
	public static char equals(final boolean condition, @Nonnull final char expected, @Nonnull final char check, final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed intH is equal to another int. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            int to be checked
	 * @return {@code check} The checked int
	 */
	public static int equals(final boolean condition, @Nonnull final int expected, @Nonnull final int check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed int is equal to another int. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            int to be checked
	 * @param message
	 *            an error message describing why the ints must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked int
	 */
	public static int equals(final boolean condition, @Nonnull final int expected, @Nonnull final int check, final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed long is equal to another long. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            long to be checked
	 * @return {@code check} The checked long
	 */
	public static long equals(final boolean condition, @Nonnull final long expected, @Nonnull final long check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed long is equal to another long. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            long to be checked
	 * @param message
	 *            an error message describing why the longs must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked long
	 */
	public static long equals(final boolean condition, @Nonnull final long expected, @Nonnull final long check, final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed short is equal to another short. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            short to be checked
	 * @return {@code check} The checked short
	 */
	public static short equals(final boolean condition, @Nonnull final short expected, @Nonnull final short check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed short is equal to another short. The comparison is made using {@code expected != check }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            short to be checked
	 * @param message
	 *            an error message describing why the shorts must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked short
	 */
	public static short equals(final boolean condition, @Nonnull final short expected, @Nonnull final short check, final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar

		if (condition) {
			Check.equals(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed object is equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check) }.
	 * 
	 * The condition must evaluate to {@code true} so that the check is executed.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @return {@code check} The checked object
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Object> T equals(final boolean condition, @Nonnull final T expected, @Nonnull final T check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		if (condition) {
			return Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed object is equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check) }.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @param message
	 *            an error message describing why the objects must equal (will be passed to
	 *            {@code IllegalNotEqualException})
	 * @return {@code check} The checked object
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotEqualException.class })
	public static <T extends Object> T equals(final boolean condition, @Nonnull final T expected, @Nonnull final T check,
			final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		if (condition) {
			return Check.equals(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is greater to another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) >= 0}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return {@code check} The checked {@code Comparable}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Comparable<T>> T greaterThan(final boolean condition, @Nonnull final T expected, @Nonnull final T check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (condition) {
			Check.greaterThan(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is greater than another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) >= 0}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be greater than a value (will be passed to
	 *            {@code IllegalNotGreaterThanException})
	 * @return {@code check} The checked {@code Comparable}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Comparable<T>> T greaterThan(final boolean condition, @Nonnull final T expected, @Nonnull final T check,
			final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (condition) {
			Check.greaterThan(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a passed class has an annotation of a specific type
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param clazz
	 *            the class that must have a required annotation
	 * @param annotation
	 *            the type of annotation that is required on the class
	 * @return the annotation which is present on the checked class
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static Annotation hasAnnotation(final boolean condition, @Nonnull final Class<?> clazz,
			@Nonnull final Class<? extends Annotation> annotation) {
		Check.notNull(clazz, "clazz");
		Check.notNull(annotation, "annotation");

		if (condition) {
			return Check.hasAnnotation(clazz, annotation);
		}

		return clazz.getAnnotation(annotation);
	}

	/**
	 * Ensures that a passed argument is a member of a specific type.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param type
	 *            class that the given object is a member of
	 * @param obj
	 *            the object reference that should be a member of a specific {@code type}
	 * @throws IllegalInstanceOfArgumentException
	 *             if the given argument {@code obj} is not a member of {@code type}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	@SuppressWarnings("unchecked")
	public static <T> T instanceOf(final boolean condition, @Nonnull final Class<?> type, @Nonnull final Object obj) {
		if (condition) {
			return (T) Check.instanceOf(type, obj);
		}

		return (T) obj;
	}

	/**
	 * Ensures that a passed argument is a member of a specific type.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param type
	 *            class that the given object is a member of
	 * @param obj
	 *            the object reference that should be a member of a specific {@code type}
	 * @param name
	 *            name of object reference (in source code)
	 * @throws IllegalInstanceOfArgumentException
	 *             if the given argument {@code obj} is not a member of {@code type}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	@SuppressWarnings("unchecked")
	public static <T> T instanceOf(final boolean condition, @Nonnull final Class<?> type, @Nonnull final Object obj,
			@Nullable final String name) {
		if (condition) {
			return (T) Check.instanceOf(type, obj, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param reference
	 *            reference which must be null.
	 * @throws IllegalNotNullArgumentException
	 *             if the given argument {@code reference} is not null
	 */
	public static void isNull(final boolean condition, @Nullable final Object reference) {
		if (condition) {
			Check.isNull(reference);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param reference
	 *            reference which must be null.
	 * @param name
	 *            name of object reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNotNullArgumentException
	 *             if the given argument {@code reference} is not null
	 */
	public static void isNull(final boolean condition, @Nullable final Object reference, @Nullable final String name) {
		if (condition) {
			Check.isNull(reference, name);
		}
	}

	/**
	 * Ensures that a String argument is a number.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which must be a number
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	public static void isNumber(final boolean condition, @Nonnull final String value) {
		if (condition) {
			Check.isNumber(value);
		}
	}

	/**
	 * Ensures that a String argument is a number. This overload supports all subclasses of {@code Number}. The number
	 * is first converted to a BigInteger
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which must be a number
	 * @param type
	 *            requested return value type, must be a subclass of {@code Number}, i.e. one of {@code BigDecimal,
	 *            BigInteger, Byte, Double, Float, Integer, Long, Short}
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Number> void isNumber(final boolean condition, @Nonnull final String value, @Nonnull final Class<T> type) {
		if (condition) {
			Check.isNumber(value, type);
		}
	}

	/**
	 * Ensures that a string argument is a number according to {@code Integer.parseInt}
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which must be a number
	 * @param name
	 *            name of object reference (in source code)
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static void isNumber(final boolean condition, @Nonnull final String value, @Nullable final String name) {
		if (condition) {
			Check.isNumber(value, name);
		}
	}

	/**
	 * Ensures that a String argument is a number. This overload supports all subclasses of {@code Number}. The number
	 * is first converted to a {@code BigDecimal} or {@code BigInteger}. Floating point types are only supported if the
	 * {@code type} is one of {@code Float, Double, BigDecimal}.
	 * 
	 * This method does also check against the ranges of the given datatypes.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which must be a number and in the range of the given datatype.
	 * @param name
	 *            (optional) name of object reference (in source code).
	 * @param type
	 *            requested return value type, must be a subclass of {@code Number}, i.e. one of {@code BigDecimal,
	 *            BigInteger, Byte, Double, Float, Integer, Long, Short}
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(value = { IllegalNullArgumentException.class, IllegalNumberRangeException.class })
	public static <T extends Number> void isNumber(final boolean condition, @Nonnull final String value, @Nullable final String name,
			@Nonnull final Class<T> type) {
		if (condition) {
			Check.isNumber(value, name, type);
		}
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            a readable sequence of {@code char} values which must be a number
	 * @return the given string argument
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T isNumeric(final boolean condition, @Nonnull final T value) {
		if (condition) {
			return (T) Check.isNumeric(value);
		}

		return (T) value;
	}

	/**
	 * Ensures that a readable sequence of {@code char} values is numeric. Numeric arguments consist only of the
	 * characters 0-9 and may start with 0 (compared to number arguments, which must be valid numbers - think of a bank
	 * account number).
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            a readable sequence of {@code char} values which must be a number
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given string argument
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T isNumeric(final boolean condition, @Nonnull final T value, @Nullable final String name) {
		if (condition) {
			return (T) Check.isNumeric(value, name);
		}

		return (T) value;
	}

	/**
	 * Ensures that a passed {@code Comparable} is less than another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) <= 0}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @return {@code check} The checked {@code Comparable}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Comparable<T>> T lesserThan(final boolean condition, @Nonnull final T expected, @Nonnull final T check) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (condition) {
			Check.lesserThan(expected, check);
		}

		return check;
	}

	/**
	 * Ensures that a passed {@code Comparable} is less than another {@code Comparable}. The comparison is made using
	 * {@code expected.compareTo(check) <= 0}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Comparable to be checked
	 * @param message
	 *            an error message describing why the comparables must be less than a value (will be passed to
	 *            {@code IllegalNotLessThanException})
	 * @return {@code check} The checked {@code Comparable}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Comparable<T>> T lesserThan(final boolean condition, @Nonnull final T expected, @Nonnull final T check,
			final String message) { // NOSONAR
		// Sonar warns about suspicious equals method name, as the name is intended deactivate sonar
		Check.notNull(expected, "expected");
		Check.notNull(check, "check");

		if (condition) {
			Check.lesserThan(expected, check, message);
		}

		return check;
	}

	/**
	 * Ensures that a readable sequence of {@code char} values matches a specified pattern. If the given character
	 * sequence does not match against the passed pattern, an {@link IllegalPatternArgumentException} will be thrown.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#matchesPattern(Pattern, CharSequence, String)} and pass as
	 * second argument the name of the parameter to enhance the exception message.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param pattern
	 *            pattern, that the {@code chars} must correspond to
	 * @param chars
	 *            a readable sequence of {@code char} values which should match the given pattern
	 * @return the passed {@code chars} that matches the given pattern
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code chars} is {@code null}
	 * @throws IllegalPatternArgumentException
	 *             if the given {@code chars} that does not match the {@code pattern}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T matchesPattern(final boolean condition, @Nonnull final Pattern pattern, @Nonnull final T chars) {
		if (condition) {
			return Check.matchesPattern(pattern, chars);
		}

		return chars;
	}

	/**
	 * Ensures that a readable sequence of {@code char} values matches a specified pattern. If the given character
	 * sequence does not match against the passed pattern, an {@link IllegalPatternArgumentException} will be thrown.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param pattern
	 *            pattern, that the {@code chars} must correspond to
	 * @param chars
	 *            a readable sequence of {@code char} values which should match the given pattern
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed {@code chars} that matches the given pattern
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code chars} is {@code null}
	 * @throws IllegalPatternArgumentException
	 *             if the given {@code chars} that does not match the {@code pattern}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T matchesPattern(final boolean condition, @Nonnull final Pattern pattern,
			@Nonnull final T chars, @Nullable final String name) {
		if (condition) {
			return Check.matchesPattern(pattern, chars, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param iterable
	 *            the iterable reference which should not contain {@code null}
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code iterable} contains elements that are {@code null}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Iterable<?>> T noNullElements(final boolean condition, @Nonnull final T iterable) {
		if (condition) {
			return Check.noNullElements(iterable);
		}

		return iterable;
	}

	/**
	 * Ensures that an iterable reference is neither {@code null} nor contains any elements that are {@code null}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param iterable
	 *            the iterable reference which should not contain {@code null}
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code iterable} contains elements that are {@code null}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Iterable<?>> T noNullElements(final boolean condition, @Nonnull final T iterable, final String name) {
		if (condition) {
			return Check.noNullElements(iterable, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param array
	 *            reference to an array
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code array} contains {@code null}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T> T[] noNullElements(final boolean condition, @Nonnull final T[] array) {
		if (condition) {
			return Check.noNullElements(array);
		}

		return array;
	}

	/**
	 * Ensures that an array does not contain {@code null}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param array
	 *            reference to an array
	 * @param name
	 *            name of object reference (in source code)
	 * @return the passed reference which contains no elements that are {@code null}
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code array} contains {@code null}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T> T[] noNullElements(final boolean condition, @Nonnull final T[] array, @Nullable final String name) {
		if (condition) {
			return Check.noNullElements(array, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	@Throws(IllegalEmptyArgumentException.class)
	public static void notEmpty(final boolean condition, final boolean expression) {
		if (condition) {
			Check.notEmpty(expression);
		}
	}

	/**
	 * Ensures that a passed parameter of the calling method is not empty, using the passed expression to evaluate the
	 * emptiness.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	@Throws(IllegalEmptyArgumentException.class)
	public static void notEmpty(final boolean condition, final boolean expression, @Nullable final String name) {
		if (condition) {
			Check.notEmpty(expression, name);
		}
	}

	/**
	 * Ensures that a passed string as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(CharSequence, String)} and pass as second
	 * argument the name of the parameter to enhance the exception message.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T extends CharSequence> T notEmpty(final boolean condition, @Nonnull final T chars) {
		if (condition) {
			return Check.notEmpty(chars);
		}

		return chars;
	}

	/**
	 * Ensures that a passed collection as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T extends Collection<?>> T notEmpty(final boolean condition, @Nonnull final T collection) {
		if (condition) {
			return Check.notEmpty(collection);
		}

		return collection;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T extends Map<?, ?>> T notEmpty(final boolean condition, @Nonnull final T map) {
		if (condition) {
			return Check.notEmpty(map);
		}

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
	 * 	ConditionalCheck.notEmpty(true, text, text.isEmpty(), &quot;text&quot;);
	 * 	this.text = text;
	 * }
	 * </pre>
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T> T notEmpty(final boolean condition, @Nonnull final T reference, final boolean expression, @Nullable final String name) {
		if (condition) {
			return Check.notEmpty(reference, expression, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T extends CharSequence> T notEmpty(final boolean condition, @Nonnull final T chars, @Nullable final String name) {
		if (condition) {
			return Check.notEmpty(chars, name);
		}

		return chars;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Collection, String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T extends Map<?, ?>> T notEmpty(final boolean condition, @Nonnull final T map, @Nullable final String name) {
		if (condition) {
			return Check.notEmpty(map, name);
		}

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
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T extends Collection<?>> T notEmpty(final boolean condition, @Nonnull final T collection, @Nullable final String name) {
		if (condition) {
			return Check.notEmpty(collection, name);
		}

		return collection;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(Object[], String)} and pass as second argument
	 * the name of the parameter to enhance the exception message.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T> T[] notEmpty(final boolean condition, @Nonnull final T[] array) {
		if (condition) {
			return Check.notEmpty(array);
		}

		return array;
	}

	/**
	 * Ensures that a passed map as a parameter of the calling method is not empty.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static <T> T[] notEmpty(final boolean condition, @Nonnull final T[] array, @Nullable final String name) {
		if (condition) {
			return Check.notEmpty(array, name);
		}

		return array;
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which should not be NaN
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static double notNaN(final boolean condition, final double value) {
		if (condition) {
			return Check.notNaN(value);
		}

		return value;
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see java.lang.Double#NaN
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which should not be NaN
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static double notNaN(final boolean condition, final double value, @Nullable final String name) {
		if (condition) {
			return Check.notNaN(value, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which should not be NaN
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static float notNaN(final boolean condition, final float value) {
		if (condition) {
			return Check.notNaN(value);
		}

		return value;
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see java.lang.Float#NaN
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            value which should not be NaN
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given float value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static float notNaN(final boolean condition, final float value, @Nullable final String name) {
		if (condition) {
			return Check.notNaN(value, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            a number
	 * @param name
	 *            name of object reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNegativeArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static int notNegative(final boolean condition, @Nonnull final int value) {
		if (condition) {
			return Check.notNegative(value);
		}

		return value;
	}

	/**
	 * Ensures that an integer reference passed as a parameter to the calling method is not smaller than {@code 0}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param value
	 *            a number
	 * @param name
	 *            name of object reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is smaller than {@code 0}
	 */
	@Throws(IllegalNegativeArgumentException.class)
	public static int notNegative(final boolean condition, @Nonnull final int value, @Nullable final String name) {
		if (condition) {
			return Check.notNegative(value, name);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param reference
	 *            an object reference
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(final boolean condition, @Nonnull final T reference) {
		if (condition) {
			return Check.notNull(reference);
		}

		return reference;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not {@code null}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param reference
	 *            an object reference
	 * @param name
	 *            name of object reference (in source code)
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(final boolean condition, @Nonnull final T reference, @Nullable final String name) {
		if (condition) {
			return Check.notNull(reference, name);
		}

		return reference;
	}

	/**
	 * Ensures that a given position index is valid within the size of an array, list or string ...
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static int positionIndex(final boolean condition, final int index, final int size) {
		if (condition) {
			return Check.positionIndex(index, size);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static void range(final boolean condition, @Nonnegative final int start, @Nonnegative final int end, @Nonnegative final int size) {
		if (condition) {
			Check.range(start, end, size);
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
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expression
	 *            an expression that must be true to indicate a valid state
	 * 
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	@Throws(IllegalStateOfArgumentException.class)
	public static void stateIsTrue(final boolean condition, final boolean expression) {
		if (condition) {
			Check.stateIsTrue(expression);
		}
	}

	/**
	 * Ensures that a given state is {@code true} and allows to specify the class of exception which is thrown in case
	 * the state is not {@code true}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static void stateIsTrue(final boolean condition, final boolean expression, final Class<? extends RuntimeException> clazz) {
		if (condition) {
			Check.stateIsTrue(expression, clazz);
		}

	}

	/**
	 * Ensures that a given state is {@code true}.
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
	 * @param expression
	 *            an expression that must be {@code true} to indicate a valid state
	 * @param description
	 *            will be used in the error message to describe why the arguments caused an invalid state
	 * @throws IllegalStateOfArgumentException
	 *             if the given arguments caused an invalid state
	 */
	@Throws(IllegalStateOfArgumentException.class)
	public static void stateIsTrue(final boolean condition, final boolean expression, @Nonnull final String description) {
		if (condition) {
			Check.stateIsTrue(expression, description);
		}
	}

	/**
	 * Ensures that a given state is {@code true}
	 * 
	 * @param condition
	 *            condition must be true so that the check is performed.
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
	public static void stateIsTrue(final boolean condition, final boolean expression, @Nonnull final String descriptionTemplate,
			final Object... descriptionTemplateArgs) {
		if (condition) {
			Check.stateIsTrue(expression, descriptionTemplate, descriptionTemplateArgs);
		}

	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private ConditionalCheck() {
		// This class is not intended to create objects from it.
	}

}
