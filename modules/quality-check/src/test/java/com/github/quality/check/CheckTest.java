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
package com.github.quality.check;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.github.quality.check.exception.IllegalEmptyArgumentException;
import com.github.quality.check.exception.IllegalNullArgumentException;
import com.github.quality.check.exception.IllegalNullElementsException;
import com.github.quality.check.exception.IllegalPositionIndexException;
import com.github.quality.check.exception.IllegalRangeException;
import com.github.quality.check.exception.IllegalStateOfArgumentException;

/**
 * Modul Test for the class {@link com.github.quality.check.Check}
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class CheckTest {

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
	public void noNullElements_emptyArray_ok() {
		Check.noNullElements(new Object[] {});
	}

	@Test
	public void noNullElements_emptyArrayWithName_ok() {
		Check.noNullElements(new Object[] {}, "obj");
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullAtEndArray_fail() {
		Check.noNullElements(new Integer[] { 1, 2, 3, 4, null });
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullAtEndArrayWithName_fail() {
		Check.noNullElements(new Integer[] { 1, 2, 3, 4, null }, "obj");
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullOnlyArray_fail() {
		Check.noNullElements(new String[] { null });
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullOnlyArrayWithName_fail() {
		Check.noNullElements(new String[] { null }, "obj");
	}

	@Test
	public void noNullElements_stringArray_ok() {
		Check.noNullElements(new String[] { "Hello", "World" });
	}

	@Test
	public void noNullElements_stringArrayWithName_ok() {
		Check.noNullElements(new String[] { "Hello", "World" }, "obj");
	}

	@Test
	public void notEmpty_checkReferenceIsSame_withString() {
		final String text = "beer tastes good";
		Assert.assertSame(text, Check.notEmpty(text));
		Assert.assertSame(text, Check.notEmpty(text, "text"));
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyCollection_isInvalid() {
		final Set<String> collection = new HashSet<String>();
		Check.notEmpty(collection);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyCollection_withArgName_isInvalid() {
		final Set<String> collection = new HashSet<String>();
		Check.notEmpty(collection, "collection");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyString_isInvalid() {
		final String text = "";
		Check.notEmpty(text);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyString_withArgName_isInvalid() {
		final String text = "";
		Check.notEmpty(text, "text");
	}

	@Test
	public void notEmpty_filledCollection_isValid() {
		final Set<String> collection = new HashSet<String>();
		collection.add("hmm, what a tasty ice cream");
		final Set<String> nonEmptySet = Check.notEmpty(collection);
		Assert.assertSame(collection, nonEmptySet);
	}

	@Test
	public void notEmpty_filledCollection_withArgName_isValid() {
		final Set<String> collection = new HashSet<String>();
		collection.add("hmm, what a tasty ice cream");
		final Set<String> nonEmptySet = Check.notEmpty(collection, "collection");
		Assert.assertSame(collection, nonEmptySet);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_withEmptyReference_isEmpty_withArgName_isInvalid() {
		final String text = "";
		Check.notEmpty(text, text.isEmpty(), "text");
	}

	@Test
	public void notEmpty_withFilledReference_notEmpty_withArgName_isValid() {
		final String text = "strawberries tastes also good";
		Assert.assertSame(text, Check.notEmpty(text, text.isEmpty(), "text"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullCollection_withArgName_isInvalid() {
		Check.notEmpty((Collection<?>) null, "text");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullReference_isEmpty_withArgName_isInvalid() {
		Check.notEmpty(null, true, "argName");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullReference_isInvalid() {
		Check.notEmpty((String) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullString_withArgName_isInvalid() {
		Check.notEmpty((String) null, "text");
	}

	@Test
	public void notEmpty_withText_withArgName_isValid() {
		final String text = "strawberries tastes also good";
		Assert.assertSame(text, Check.notEmpty(text, "text"));
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

	@Test(expected = java.lang.IllegalAccessException.class)
	public void testValidatesThatClassCheckIsNotInstantiable() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		final Class<?> cls = Class.forName("com.github.quality.check.Check");
		cls.newInstance(); // exception here
	}
}
