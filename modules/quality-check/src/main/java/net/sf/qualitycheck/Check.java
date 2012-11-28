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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;
import net.sf.qualitycheck.exception.IllegalMissingAnnotationException;
import net.sf.qualitycheck.exception.IllegalNaNArgumentException;
import net.sf.qualitycheck.exception.IllegalNotEqualException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumberRangeException;
import net.sf.qualitycheck.exception.IllegalNumericArgumentException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;
import net.sf.qualitycheck.exception.IllegalRangeException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

/**
 * This class offers simple static methods to test your arguments to be valid.
 * 
 * Checks should be added to all arguments of all public methods in your class to assure that only valid values can be
 * encountered within your class. This is major step to avoid technical errors like NullPointerExceptions or
 * IndexOutOfBoundsException.
 * 
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public final class Check {

	/**
	 * Holder for the regular expression to determine numeric values. Using the holder pattern guarantees that the
	 * regular expression is initialized before the first used (thread safe!) and that it is only initialized if it is
	 * used.
	 * 
	 * So we do not pay any performance bounty for regular expressions when using other checks.
	 */
	protected static final class NumericRegularExpressionHolder {

		private static Pattern NUMERIC_REGEX = Pattern.compile("[0-9]+");

		@Nonnull
		public static Pattern getPattern() {
			return NUMERIC_REGEX;
		}

	}

	/**
	 * Representation of an empty argument name
	 */
	private static String EMPTY_ARGUMENT_NAME = "";

	/**
	 * Checks if the given array contains {@code null}.
	 * 
	 * @param array
	 *            reference to an array
	 * @return {@code true} if the array contains {@code null}, otherwise {@code false}
	 * 
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
	 * Ensures that a passed object is equal to another object. The comparison is made using a call to
	 * {@code expected.equals(check) }.
	 * 
	 * @param expected
	 *            Expected value
	 * @param check
	 *            Object to be checked
	 * @return {@code check} The checked object
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Object> T equals(@Nonnull final T expected, @Nonnull final T check) {
		Check.notNull(expected);
		Check.notNull(check);

		if (!expected.equals(check)) {
			throw new IllegalNotEqualException();
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
	 * @return {@code check} The checked object
	 */
	@ArgumentsChecked
	@Throws({ IllegalNullArgumentException.class, IllegalNotEqualException.class })
	public static <T extends Object> T equals(@Nonnull final T expected, @Nonnull final T check, final String message) {
		Check.notNull(expected);
		Check.notNull(check);

		if (!expected.equals(check)) {
			throw new IllegalNotEqualException(message);
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
	 * @return the annotation which is present on the checked class
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
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
	 * @throws IllegalInstanceOfArgumentException
	 *             if the given argument {@code obj} is not a member of {@code type}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static void instanceOf(@Nonnull final Class<?> type, @Nullable final Object obj) {
		instanceOf(type, obj, EMPTY_ARGUMENT_NAME);
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
	 * @throws IllegalInstanceOfArgumentException
	 *             if the given argument {@code obj} is not a member of {@code type}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static void instanceOf(@Nonnull final Class<?> type, @Nullable final Object obj, @Nullable final String name) {
		Check.notNull(type);
		Check.notNull(obj);
		if (!type.isInstance(obj)) {
			throw new IllegalInstanceOfArgumentException(name, type, obj.getClass());
		}
	}

	/**
	 * Ensures that a String argument is a number.
	 * 
	 * @param value
	 *            value which must be a number
	 * @return the given string argument converted to an int
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static int isNumber(@Nullable final String value) {
		Check.notNull(value);
		return Check.isNumber(value, Integer.class).intValue();
	}

	/**
	 * Ensures that a string argument is a number according to {@code Integer.parseInt}
	 * 
	 * @param value
	 *            value which must be a number
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given string argument converted to an int
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static int isNumber(@Nullable final String value, @Nullable final String name) {
		Check.notNull(value);
		return Check.isNumber(value, name, Integer.class).intValue();
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
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Number> T isNumber(@Nonnull final String value, @Nonnull final Class<T> type) {
		return isNumber(value, null, type);
	}

	/**
	 * Ensures that a String argument is a number. This overload supports all subclasses of {@code Number}. The number
	 * is first converted to a {@code BigDecimal} or {@code BigInteger}. Floating point types are only supported if the
	 * {@code type} is one of {@code Float, Double, BigDecimal}.
	 * 
	 * This method does also check against the ranges of the given datatypes.
	 * 
	 * @param value
	 *            value which must be a number and in the range of the given datatype.
	 * @param name
	 *            name of object reference (in source code)
	 * @param type
	 *            requested return value type, must be a subclass of {@code Number}, i.e. one of {@code BigDecimal,
	 *            BigInteger, Byte, Double, Float, Integer, Long, Short}
	 * @return the given string argument converted to a number of the requested type
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(value = { IllegalNullArgumentException.class, IllegalNumberRangeException.class })
	public static <T extends Number> T isNumber(@Nonnull final String value, @Nullable final String name, @Nonnull final Class<T> type) {
		Check.notNull(value, "value");
		Check.notNull(type, "type");

		final Number ret;
		try {
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
		} catch (final NumberFormatException nfe) {
			if (name == null) {
				throw new IllegalNumberArgumentException(nfe);
			} else {
				throw new IllegalNumberArgumentException(name, nfe);
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
	 * We recommend to use the overloaded method {@link Check#isNumeric(String, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
	 * 
	 * @param value
	 *            a readable sequence of {@code char} values which must be a number
	 * @return the given string argument
	 * @throws IllegalNumberArgumentException
	 *             if the given argument {@code value} is no number
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T isNumeric(@Nullable final T value) {
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
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T isNumeric(@Nullable final T value, @Nullable final String name) {
		Check.notNull(value);
		if (!matches(NumericRegularExpressionHolder.getPattern(), value)) {
			throw new IllegalNumericArgumentException(name);
		}
		return value;
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
	public static <T extends CharSequence> T matchesPattern(@Nonnull final Pattern pattern, @Nullable final T chars) {
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
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code chars} is {@code null}
	 * @throws IllegalPatternArgumentException
	 *             if the given {@code chars} that does not match the {@code pattern}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends CharSequence> T matchesPattern(@Nonnull final Pattern pattern, @Nullable final T chars,
			@Nullable final String name) {
		Check.notNull(pattern);
		Check.notNull(chars);
		if (!matches(pattern, chars)) {
			throw new IllegalPatternArgumentException(name, pattern);
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
	 * @throws IllegalNullElementsException
	 *             if the given argument {@code iterable} contains elements that are {@code null}
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Iterable<?>> T noNullElements(@Nullable final T iterable) {
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
	@Throws(IllegalNullArgumentException.class)
	public static <T extends Iterable<?>> T noNullElements(@Nullable final T iterable, final String name) {
		Check.notNull(iterable);
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
	@Throws(IllegalNullArgumentException.class)
	public static <T> T[] noNullElements(@Nullable final T[] array) {
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
	@Throws(IllegalNullArgumentException.class)
	public static <T> T[] noNullElements(@Nullable final T[] array, @Nullable final String name) {
		Check.notNull(array);
		if (containsNullElements(array)) {
			throw new IllegalNullElementsException(name);
		}
		return array;
	}

	/**
	 * Ensures that a passed a parameter of the calling method is not empty, using the passed expression to evaluate the
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
	@Throws(IllegalEmptyArgumentException.class)
	public static void notEmpty(final boolean expression) {
		notEmpty(expression, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a passed a parameter of the calling method is not empty, using the passed expression to evaluate the
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
	@Throws(IllegalEmptyArgumentException.class)
	public static void notEmpty(final boolean expression, @Nullable final String name) {
		if (expression) {
			throw new IllegalEmptyArgumentException(name);
		}
	}

	/**
	 * Ensures that a passed string as a parameter of the calling method is not empty.
	 * 
	 * <p>
	 * We recommend to use the overloaded method {@link Check#notEmpty(String, String)} and pass as second argument the
	 * name of the parameter to enhance the exception message.
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
	public static <T extends CharSequence> T notEmpty(@Nullable final T chars) {
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
	public static <T extends Collection<?>> T notEmpty(@Nullable final T collection) {
		notNull(collection);
		notEmpty(collection, collection.isEmpty(), EMPTY_ARGUMENT_NAME);
		return collection;
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
	public static <T extends Map<?, ?>> T notEmpty(@Nullable final T map) {
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
	public static <T> T notEmpty(@Nullable final T reference, final boolean expression, @Nullable final String name) {
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
	public static <T extends CharSequence> T notEmpty(@Nullable final T chars, @Nullable final String name) {
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
	public static <T extends Map<?, ?>> T notEmpty(@Nullable final T map, @Nullable final String name) {
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
	public static <T extends Collection<?>> T notEmpty(@Nullable final T collection, @Nullable final String name) {
		notNull(collection, name);
		notEmpty(collection, collection.isEmpty(), name);
		return collection;
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
	public static <T> T[] notEmpty(@Nullable final T[] array) {
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
	public static <T> T[] notEmpty(@Nullable final T[] array, @Nullable final String name) {
		notNull(array);
		notEmpty(array, array.length == 0, EMPTY_ARGUMENT_NAME);
		return array;
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see {@code Double.NaN}
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static double notNaN(final double value) {
		return notNaN(value, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see {@code Double.NaN}
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static double notNaN(final double value, @Nullable final String name) {
		if (value != value) { // most efficient check for NaN, see Double.isNaN(value))
			throw new IllegalNaNArgumentException(name);
		}

		return value;
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see {@code Float.NaN}
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @return the given double value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static float notNaN(final float value) {
		return notNaN(value, EMPTY_ARGUMENT_NAME);
	}

	/**
	 * Ensures that a double argument is not NaN (not a number).
	 * 
	 * @see {@code Float.NaN}
	 * 
	 * @param value
	 *            value which should not be NaN
	 * @param name
	 *            name of object reference (in source code)
	 * @return the given float value
	 * @throws IllegalNaNArgumentException
	 *             if the given argument {@code value} is NaN
	 */
	@Throws({ IllegalNaNArgumentException.class })
	public static float notNaN(final float value, @Nullable final String name) {
		if (value != value) { // most efficient check for NaN, see Float.isNaN(value))
			throw new IllegalNaNArgumentException(name);
		}
		return value;
	}

	/**
	 * Ensures that an object reference passed as a parameter to the calling method is not {@code null}.
	 * 
	 * @param reference
	 *            an object reference
	 * @return the non-null reference that was validated
	 * @throws IllegalNullArgumentException
	 *             if the given argument {@code reference} is {@code null}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T notNull(@Nullable final T reference) {
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
	public static <T> T notNull(@Nullable final T reference, @Nullable final String name) {
		if (reference == null) {
			throw new IllegalNullArgumentException(name);
		}
		return reference;
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
	public static void range(final @Nonnegative int start, final @Nonnegative int end, final @Nonnegative int size) {
		final boolean rangeIsValid = (start <= size) && (end <= size) && (start <= end);
		final boolean inputValuesAreValid = (size >= 0) && (start >= 0) && (end >= 0);

		if (!rangeIsValid || !inputValuesAreValid) {
			throw new IllegalRangeException(start, end, size);
		}
	}

	/**
	 * Ensures that a given state is {@code true}.
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
	 *            an subclass of RuntimeException which will be thrown if the given state is not valid
	 * @throws clazz
	 *             a new instance of {@code clazz} if the given arguments caused an invalid state
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public static void stateIsTrue(final boolean expression, final Class<? extends RuntimeException> clazz) {
		Check.notNull(clazz);

		if (!expression) {
			RuntimeException re;
			try {
				re = clazz.newInstance();
			} catch (final InstantiationException e) {
				throw new RuntimeException(e);
			} catch (final IllegalAccessException e) {
				throw new RuntimeException(e);
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
