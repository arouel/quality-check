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
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;
import java.util.regex.Pattern;

import javax.annotation.Generated;
import javax.annotation.Resource;

import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;
import net.sf.qualitycheck.exception.IllegalMissingAnnotationException;
import net.sf.qualitycheck.exception.IllegalNaNArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;
import net.sf.qualitycheck.exception.IllegalRangeException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Modul Test for the class {@link net.sf.qualitycheck.Check}
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class CheckTest {

	@Resource
	private static class FakeAnnotatedClass {
	}

	private static class FakeIllegalAccessException extends RuntimeException {
		private static final long serialVersionUID = 3810373199918408287L;

		@SuppressWarnings("unused")
		public FakeIllegalAccessException() throws IllegalAccessException {
			throw new IllegalAccessException();
		}
	}

	private static class FakeInstantiationException extends RuntimeException {
		private static final long serialVersionUID = -7585963509351269594L;

		@SuppressWarnings("unused")
		public FakeInstantiationException() throws InstantiationException {
			throw new InstantiationException();
		}
	}

	@Generated(value = { "2001-07-04T12:08:56.235-0700" })
	private static class FakeSourceAnnotatedClass {
	}

	@Test
	public void checkEmptyRange() {
		Check.range(0, 0, 0);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidEndBeforeStartRange() {
		Check.range(2, 1, 3);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidEndBiggerThanSizeRange() {
		Check.range(1, 4, 3);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidNegativeEndRange() {
		Check.range(1, -2, 5);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidNegativeRange() {
		Check.range(-2, -3, -4);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidNegativeSizeRange() {
		Check.range(1, 2, -5);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidNegativeStartRange() {
		Check.range(-1, 2, 5);
	}

	@Test(expected = IllegalRangeException.class)
	public void checkInvalidStartBiggerThanSizeRange() {
		Check.range(4, 2, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionAllZero() {
		Check.positionIndex(0, 0);
	}

	@Test
	public void checkPositionIndex_ok_highest() {
		final int ret = Check.positionIndex(2, 3);
		Assert.assertEquals(2, ret);
	}

	@Test
	public void checkPositionIndex_ok_lowest() {
		final int ret = Check.positionIndex(0, 3);
		Assert.assertEquals(0, ret);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionIndexEqualsSize() {
		Check.positionIndex(3, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionIndexNegative() {
		Check.positionIndex(-1, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionIndexToBig() {
		Check.positionIndex(4, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionSizeNegative() {
		Check.positionIndex(0, -1);
	}

	@Test
	public void checkRange() {
		Check.range(3, 5, 10);
	}

	@Test
	public void checkRangeBoundariesAllUpper() {
		Check.range(10, 10, 10);
	}

	@Test
	public void checkRangeBoundariesLower() {
		Check.range(0, 2, 4);
	}

	@Test
	public void checkRangeBoundariesUpper() {
		Check.range(0, 10, 10);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void checkStateIsTrue_False() {
		Check.stateIsTrue(false);
	}

	@Test
	public void checkStateIsTrue_True() {
		Check.stateIsTrue(true);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void checkStateIsTrueWithMessage_False() {
		Check.stateIsTrue(false, "False is not allowed.");
	}

	@Test
	public void checkStateIsTrueWithMessage_True() {
		Check.stateIsTrue(true, "False is not allowed.");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void checkStateIsTrueWithMessageArguments_False() {
		Check.stateIsTrue(false, "Value '%d' is not allowed.", 42);
	}

	@Test
	public void checkStateIsTrueWithMessageArguments_True() {
		Check.stateIsTrue(true, "Value '%d' is not allowed.", 42);
	}

	@Test(expected = NullPointerException.class)
	public void checkStateIsTrueWithThrowable_False() {
		Check.stateIsTrue(false, NullPointerException.class);
	}

	@Test
	public void checkStateIsTrueWithThrowable_True() {
		Check.stateIsTrue(true, NullPointerException.class);
	}

	@Test(expected = RuntimeException.class)
	public void checkStateIsTrueWithThrowableAndIllegalAccessException_False() {
		Check.stateIsTrue(false, FakeIllegalAccessException.class);
	}

	@Test(expected = RuntimeException.class)
	public void checkStateIsTrueWithThrowableAndInstantiationException_False() {
		Check.stateIsTrue(false, FakeInstantiationException.class);
	}

	@Test
	public void checkValidRanges() {
		Check.range(0, 0, 0);
		Check.range(0, 0, 1);
		Check.range(0, 1, 1);
		Check.range(1, 1, 1);
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<Check> constructor = Check.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test(expected = IllegalMissingAnnotationException.class)
	public void hasAnnoation_annotation_fail() {
		Check.hasAnnotation(CheckTest.class, ArgumentsChecked.class);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void hasAnnoation_annotation_isNull() {
		Check.hasAnnotation(CheckTest.class, null);
	}

	@Test
	public void hasAnnoation_annotation_ok() {
		final Annotation annotation = Check.hasAnnotation(FakeAnnotatedClass.class, Resource.class);
		Assert.assertTrue(annotation instanceof Resource);
	}

	@Test(expected = IllegalMissingAnnotationException.class)
	public void hasAnnoation_annotationRetentionSource_fail() {
		Check.hasAnnotation(FakeSourceAnnotatedClass.class, Generated.class);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void hasAnnotation_class_isNull() {
		Check.hasAnnotation(null, ArgumentsChecked.class);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_obj_isNull() {
		Check.instanceOf(Integer.class, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_type_isNull() {
		Check.instanceOf(null, "");
	}

	@Test
	public void instanceOf_type_isValid() {
		final Long id = 12376L;
		Check.instanceOf(Long.class, id);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_withArgName_obj_isNull() {
		Check.instanceOf(Collection.class, null, "argName");
	}

	@Test
	public void instanceOf_withArgName_subtype_isValid() {
		final List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 4, null });
		Check.instanceOf(Collection.class, list, "list");
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void instanceOf_withArgName_type_isInvalid() {
		IllegalInstanceOfArgumentException actual = null;
		try {
			final List<Integer> list = new ArrayList<Integer>();
			Check.instanceOf(Vector.class, list, "list");
		} catch (final IllegalInstanceOfArgumentException e) {
			actual = e;
			throw e;
		} finally {
			final String expected = "The passed parameter 'list' is a member of an unexpected type (expected type: java.util.Vector, actual: java.util.ArrayList).";
			if (actual != null) {
				Assert.assertEquals(expected, actual.getMessage());
			}
		}
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_withArgName_type_isNull() {
		Check.instanceOf(null, "", "argName");
	}

	@Test
	public void instanceOf_withArgName_type_isValid() {
		final Long id = 12376L;
		Check.instanceOf(Long.class, id, "id");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void matchesPattern_chars_isNull() {
		Check.matchesPattern(Pattern.compile("abc"), null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void matchesPattern_pattern_isNull() {
		Check.matchesPattern(null, "abc");
	}

	@Test
	public void matchesPattern_stringbuilder_isValid() {
		final StringBuilder builder = new StringBuilder();
		builder.append("abc");
		Check.matchesPattern(Pattern.compile("abc"), builder);
	}

	@Test(expected = IllegalPatternArgumentException.class)
	public void matchesPattern_text_isInvalid() {
		final String text = "def";
		Assert.assertSame(text, Check.matchesPattern(Pattern.compile("abc"), text));
	}

	@Test
	public void matchesPattern_text_isValid() {
		final String text = "abc";
		Assert.assertSame(text, Check.matchesPattern(Pattern.compile("abc"), text));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void matchesPattern_withArgName_chars_isNull() {
		final String text = null;
		Check.matchesPattern(Pattern.compile("abc"), text, "text");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void matchesPattern_withArgName_pattern_isNull() {
		final String text = "abc";
		Check.matchesPattern(null, text, "text");
	}

	@Test(expected = IllegalPatternArgumentException.class)
	public void matchesPattern_withArgName_stringbuilder_isInvalid() {
		final StringBuilder builder = new StringBuilder();
		builder.append("xyz");
		Check.matchesPattern(Pattern.compile("abc"), builder, "builder");
	}

	@Test
	public void matchesPattern_withArgName_stringbuilder_isValid() {
		final StringBuilder builder = new StringBuilder();
		builder.append("abc");
		Check.matchesPattern(Pattern.compile("abc"), builder, "builder");
	}

	@Test
	public void matchesPattern_withArgName_text_isValid() {
		final String text = "abc";
		Assert.assertSame(text, Check.matchesPattern(Pattern.compile("abc"), text, "text"));
	}

	@Test
	public void notNull_checkReferenceIsSame() {
		final String text = "beer tastes good";
		Assert.assertSame(text, Check.notNull(text));
		Assert.assertSame(text, Check.notNull(text, "text"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notNull_withReference_isInvalid() {
		Check.notNull(null);
	}

	@Test
	public void notNull_withReference_isValid() {
		Check.notNull("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notNull_withReference_withName_isInvalid() {
		Check.notNull(null, "foo");
	}

	@Test
	public void notNull_withReference_withName_isValid() {
		Check.notNull("", "foo");
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNDouble_Fail() {
		Check.notNaN(Double.NaN);
	}

	@Test
	public void testNaNDouble_Ok() {
		Assert.assertEquals(1.0d, Check.notNaN(1.0d), 0.0d);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNDoubleArgument_Fail() {
		Check.notNaN(Double.NaN, "float");
	}

	@Test
	public void testNaNDoubleArgument_Ok() {
		Assert.assertEquals(1.0d, Check.notNaN(1.0d, "double"), 0.0d);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNFloat_Fail() {
		Check.notNaN(Float.NaN);
	}

	@Test
	public void testNaNFloat_Ok() {
		Assert.assertEquals(1.0f, Check.notNaN(1.0f), 0.0f);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNFloatArgument_Fail() {
		Check.notNaN(Float.NaN, "float");
	}

	@Test
	public void testNaNFloatArgument_Ok() {
		Assert.assertEquals(1.0f, Check.notNaN(1.0f, "float"), 0.0f);
	}

	@Test(expected = java.lang.IllegalAccessException.class)
	public void testValidatesThatClassCheckIsNotInstantiable() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		final Class<?> cls = Class.forName("net.sf.qualitycheck.Check");
		cls.newInstance(); // exception here
	}

}
