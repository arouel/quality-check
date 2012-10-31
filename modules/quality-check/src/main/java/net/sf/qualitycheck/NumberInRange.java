package net.sf.qualitycheck;

/**
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumberRangeException;

/**
 * Check if number are in a given range
 * 
 * @author Dominik Seichter
 * 
 * Based on code from
 * @author Cedric Chabanois (cchabanois at gmail.com)
 */
public final  class NumberInRange {

	public static final BigInteger BYTE_MIN = BigInteger.valueOf((long) Byte.MIN_VALUE);
	public static final BigInteger BYTE_MAX = BigInteger.valueOf((long) Byte.MAX_VALUE);
	public static final BigInteger SHORT_MIN = BigInteger.valueOf((long) Short.MIN_VALUE);
	public static final BigInteger SHORT_MAX = BigInteger.valueOf((long) Short.MAX_VALUE);
	public static final BigInteger INTEGER_MIN = BigInteger.valueOf((long) Integer.MIN_VALUE);
	public static final BigInteger INTEGER_MAX = BigInteger.valueOf((long) Integer.MAX_VALUE);
	public static final BigInteger LONG_MIN = BigInteger.valueOf(Long.MIN_VALUE);
	public static final BigInteger LONG_MAX = BigInteger.valueOf(Long.MAX_VALUE);

	public static final BigDecimal FLOAT_MAX = new BigDecimal(Float.MAX_VALUE);
	public static final BigDecimal FLOAT_MIN = new BigDecimal(-Float.MAX_VALUE);
	public static final BigDecimal DOUBLE_MAX = new BigDecimal(Double.MAX_VALUE);
	public static final BigDecimal DOUBLE_MIN = new BigDecimal(-Double.MAX_VALUE);

	/**
	 * Checks if a given number is in the range of a byte.
	 * @param number a number which should be in the range of a byte (positive or negative)
	 * 	
	 * @see {@code Byte.MIN}
	 * @see {@code Byte.MAX}
	 * 
	 * @return number as a byte (rounding might occur)
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static byte checkByte(@Nonnull final Number number) {
		Check.notNull(number);
		if( !isInByteRange(number) ) {
			throw new IllegalNumberRangeException(number.toString(), BYTE_MIN, BYTE_MAX);
		}
		
		return number.byteValue();
	}

	/**
	 * Checks if a given number is in the range of a short.
	 * @param number a number which should be in the range of a short (positive or negative)
	 * 	
	 * @see {@code Short.MIN}
	 * @see {@code Short.MAX}
	 * 
	 * @return number as a short (rounding might occur)
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static short checkShort(@Nonnull final Number number) {
		Check.notNull(number);
		if( !isInShortRange(number) ) {
			throw new IllegalNumberRangeException(number.toString(), SHORT_MIN, SHORT_MAX);
		}
		
		return number.shortValue();
	}

	/**
	 * Checks if a given number is in the range of an integer.
	 * @param number a number which should be in the range of an integer (positive or negative)
	 * 	
	 * @see {@code Integer.MIN}
	 * @see {@code Integer.MAX}
	 * 
	 * @return number as an integer (rounding might occur)
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static int checkInteger(@Nonnull final Number number) {
		Check.notNull(number);
		if( !isInIntegerRange(number) ) {
			throw new IllegalNumberRangeException(number.toString(), INTEGER_MIN, INTEGER_MAX);
		}
		
		return number.intValue();
	}

	/**
	 * Checks if a given number is in the range of a long.
	 * @param number a number which should be in the range of a long (positive or negative)
	 * 	
	 * @see {@code Long.MIN}
	 * @see {@code Long.MAX}
	 * 
	 * @return number as a long (rounding might occur)
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static int checkLong(@Nonnull final Number number) {
		Check.notNull(number);
		if( !isInLongRange(number) ) {
			throw new IllegalNumberRangeException(number.toString(), LONG_MIN, LONG_MAX);
		}
		
		return number.intValue();
	}

	/**
	 * Checks if a given number is in the range of a float.
	 * @param number a number which should be in the range of a float (positive or negative)
	 * 	
	 * @see {@code Float.MIN}
	 * @see {@code Float.MAX}
	 * 
	 * @return number as a float
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static float checkFloat(@Nonnull final Number number) {
		Check.notNull(number);
		if( !isInFloatRange(number) ) {
			throw new IllegalNumberRangeException(number.toString(), FLOAT_MIN, FLOAT_MAX);
		}
		
		return number.floatValue();
	}
	
	/**
	 * Checks if a given number is in the range of a double.
	 * @param number a number which should be in the range of a double (positive or negative)
	 * 	
	 * @see {@code Double.MIN}
	 * @see {@code Double.MAX}
	 * 
	 * @return number as a double
	 */
	@ArgumentsChecked(IllegalNullArgumentException.class)
	public static double checkDouble(@Nonnull final Number number) {
		Check.notNull(number);
		if( !isInDoubleRange(number) ) {
			throw new IllegalNumberRangeException(number.toString(), DOUBLE_MIN, DOUBLE_MAX);
		}
		
		return number.doubleValue();
	}
	
	public static boolean isInByteRange(Number number) {
		return isInRange(number, BYTE_MIN, BYTE_MAX);
	}

	public static boolean isInShortRange(Number number) {
		return isInRange(number, SHORT_MIN, SHORT_MAX);
	}

	public static boolean isInIntegerRange(Number number) {
		return isInRange(number, INTEGER_MIN, INTEGER_MAX);
	}

	public static boolean isInLongRange(Number number) {
		return isInRange(number, LONG_MIN, LONG_MAX);
	}

	public static boolean isInRange(Number number, BigInteger min, BigInteger max) {
		BigInteger bigInteger = null;
		if (number instanceof Byte || number instanceof Short || number instanceof Integer || number instanceof Long) {
			bigInteger = BigInteger.valueOf(number.longValue());
		} else if (number instanceof Float || number instanceof Double) {
			bigInteger = new BigDecimal(number.doubleValue()).toBigInteger();
		} else if (number instanceof BigInteger) {
			bigInteger = (BigInteger) number;
		} else if (number instanceof BigDecimal) {
			bigInteger = ((BigDecimal) number).toBigInteger();
		} else {
			throw new IllegalNumberArgumentException("Return value is no known subclass of 'java.lang.Number': " + number.getClass().getName());
		}
		return max.compareTo(bigInteger) >= 0 && min.compareTo(bigInteger) <= 0;
	}

	public static boolean isInRange(Number number, BigDecimal min, BigDecimal max) {
		BigDecimal bigDecimal = null;
		if (number instanceof Byte || number instanceof Short || number instanceof Integer || number instanceof Long) {
			bigDecimal = new BigDecimal(number.longValue());
		} else if (number instanceof Float || number instanceof Double) {
			bigDecimal = new BigDecimal(number.doubleValue());
		} else if (number instanceof BigInteger) {
			bigDecimal = new BigDecimal((BigInteger) number);
		} else if (number instanceof BigDecimal) {
			bigDecimal = (BigDecimal) number;
		} else {
			throw new IllegalNumberArgumentException("Return value is no known subclass of 'java.lang.Number': " + number.getClass().getName());
		}
		return max.compareTo(bigDecimal) >= 0 && min.compareTo(bigDecimal) <= 0;
	}

	public static boolean isInFloatRange(Number number) {
		return isInRange(number, FLOAT_MIN, FLOAT_MAX);
	}

	public static boolean isInDoubleRange(Number number) {
		return isInRange(number, DOUBLE_MIN, DOUBLE_MAX);
	}
	
	private NumberInRange() {
		// Do not instantiate utility class
	}
}
