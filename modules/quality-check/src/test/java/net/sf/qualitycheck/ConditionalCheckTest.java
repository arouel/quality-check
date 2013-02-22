/*******************************************************************************
 * Copyright 2013 André Rouél
 * Copyright 2013 Dominik Seichter
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

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;
import net.sf.qualitycheck.exception.IllegalMissingAnnotationException;
import net.sf.qualitycheck.exception.IllegalNaNArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;
import net.sf.qualitycheck.exception.IllegalNotEqualException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNullElementsException;
import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumericArgumentException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;
import net.sf.qualitycheck.exception.IllegalRangeException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

public class ConditionalCheckTest {

	@Resource
	private static class FakeAnnotatedClass {
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<ConditionalCheck> constructor = ConditionalCheck.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

	@Test
	public void testEquals_Negative() {
		ConditionalCheck.equals(false, Long.valueOf(412), Long.valueOf(42));
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testEquals_Positive_Failure() {
		ConditionalCheck.equals(true, Long.valueOf(1543), Long.valueOf(42));
	}

	@Test
	public void testEquals_Positive_NoFailure() {
		ConditionalCheck.equals(true, Long.valueOf(42), Long.valueOf(42));
	}

	@Test
	public void testEqualsMsg_Negative() {
		ConditionalCheck.equals(false, Long.valueOf(42), Long.valueOf(42), "msg");
	}

	@Test(expected = IllegalNotEqualException.class)
	public void testEqualsMsg_Positive_Failure() {
		ConditionalCheck.equals(true, Long.valueOf(1543), Long.valueOf(42), "msg");
	}

	@Test
	public void testEqualsMsg_Positive_NoFailure() {
		ConditionalCheck.equals(true, Long.valueOf(42), Long.valueOf(42), "msg");
	}

	@Test
	public void testHasAnnotation_Negative() {
		ConditionalCheck.hasAnnotation(false, ConditionalCheck.class, Test.class);
	}

	@Test(expected = IllegalMissingAnnotationException.class)
	public void testHasAnnotation_Positive_Failure() {
		ConditionalCheck.hasAnnotation(true, ConditionalCheck.class, Test.class);
	}

	@Test
	public void testHasAnnotation_Positive_NoFailure() {
		ConditionalCheck.hasAnnotation(true, FakeAnnotatedClass.class, Resource.class);
	}

	@Test
	public void testInstanceOf_Negative() {
		ConditionalCheck.instanceOf(false, Integer.class, Long.valueOf(3));
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void testInstanceOf_Positive_Failure() {
		ConditionalCheck.instanceOf(true, Integer.class, Long.valueOf(3));
	}

	@Test
	public void testInstanceOf_Positive_NoFailure() {
		ConditionalCheck.instanceOf(true, Long.class, Long.valueOf(3));
	}

	@Test
	public void testInstanceOfArgName_Negative() {
		ConditionalCheck.instanceOf(false, Integer.class, Long.valueOf(3), "arg");
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void testInstanceOfArgName_Positive_Failure() {
		ConditionalCheck.instanceOf(true, Integer.class, Long.valueOf(3), "arg");
	}

	@Test
	public void testInstanceOfArgName_Positive_NoFailure() {
		ConditionalCheck.instanceOf(true, Long.class, Long.valueOf(3), "arg");
	}

	@Test
	public void testIsNumber_Negative() {
		ConditionalCheck.isNumber(false, "Quality-Check");
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void testIsNumber_Positive_Failure() {
		ConditionalCheck.isNumber(true, "Quality-Check");
	}

	@Test
	public void testIsNumber_Positive_NoFailure() {
		ConditionalCheck.isNumber(true, "42");
	}

	@Test
	public void testIsNumberArgName_Negative() {
		ConditionalCheck.isNumber(false, "Quality-Check", "arg");
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void testIsNumberArgName_Positive_Failure() {
		ConditionalCheck.isNumber(true, "Quality-Check", "arg");
	}

	@Test
	public void testIsNumberArgName_Positive_NoFailure() {
		ConditionalCheck.isNumber(true, "42", "arg");
	}

	@Test
	public void testIsNumberArgNameClass_Negative() {
		ConditionalCheck.isNumber(false, "Quality-Check", "arg", Short.class);
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void testIsNumberArgNameClass_Positive_Failure() {
		ConditionalCheck.isNumber(true, "Quality-Check", "arg", Short.class);
	}

	@Test
	public void testIsNumberArgNameClass_Positive_NoFailure() {
		ConditionalCheck.isNumber(true, "42", "arg", Short.class);
	}

	@Test
	public void testIsNumberClass_Negative() {
		ConditionalCheck.isNumber(false, "Quality-Check", Short.class);
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void testIsNumberClass_Positive_Failure() {
		ConditionalCheck.isNumber(true, "Quality-Check", Short.class);
	}

	@Test
	public void testIsNumberClass_Positive_NoFailure() {
		ConditionalCheck.isNumber(true, "42", Short.class);
	}

	@Test
	public void testIsNumeric_Negative() {
		ConditionalCheck.isNumeric(false, "Quality-Check");
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void testIsNumeric_Positive_Failure() {
		ConditionalCheck.isNumeric(true, "Quality-Check");
	}

	@Test
	public void testIsNumeric_Positive_NoFailure() {
		ConditionalCheck.isNumeric(true, "042");
	}

	@Test
	public void testIsNumericArgName_Negative() {
		ConditionalCheck.isNumeric(false, "Quality-Check", "arg");
	}

	@Test(expected = IllegalNumericArgumentException.class)
	public void testIsNumericArgName_Positive_Failure() {
		ConditionalCheck.isNumeric(true, "Quality-Check", "arg");
	}

	@Test
	public void testIsNumericArgName_Positive_NoFailure() {
		ConditionalCheck.isNumeric(true, "042", "arg");
	}

	@Test
	public void testMatchesPattern_Negative() {
		ConditionalCheck.matchesPattern(false, Pattern.compile("PLZ \\d{5}"), "Hallo");
	}

	@Test(expected = IllegalPatternArgumentException.class)
	public void testMatchesPattern_Positive_Failure() {
		ConditionalCheck.matchesPattern(true, Pattern.compile("PLZ \\d{5}"), "Hallo");
	}

	@Test
	public void testMatchesPattern_Positive_NoFailure() {
		ConditionalCheck.matchesPattern(true, Pattern.compile("PLZ \\d{5}"), "PLZ 83410");
	}

	@Test
	public void testMatchesPatternArgName_Negative() {
		ConditionalCheck.matchesPattern(false, Pattern.compile("PLZ \\d{5}"), "Hallo", "arg");
	}

	@Test(expected = IllegalPatternArgumentException.class)
	public void testMatchesPatternArgName_Positive_Failure() {
		ConditionalCheck.matchesPattern(true, Pattern.compile("PLZ \\d{5}"), "Hallo", "arg");
	}

	@Test
	public void testMatchesPatternArgName_Positive_NoFailure() {
		ConditionalCheck.matchesPattern(true, Pattern.compile("PLZ \\d{5}"), "PLZ 83410", "arg");
	}

	@Test
	public void testNaNDouble_Negative() {
		ConditionalCheck.notNaN(false, Double.NaN);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNDouble_Positive_Failure() {
		ConditionalCheck.notNaN(true, Double.NaN);
	}

	@Test
	public void testNaNDouble_Positive_NoFailure() {
		ConditionalCheck.notNaN(true, 3.14);
	}

	@Test
	public void testNaNDoubleArgName_Negative() {
		ConditionalCheck.notNaN(false, Double.NaN, "arg");
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNDoubleArgName_Positive_Failure() {
		ConditionalCheck.notNaN(true, Double.NaN, "arg");
	}

	@Test
	public void testNaNDoubleArgName_Positive_NoFailure() {
		ConditionalCheck.notNaN(true, 3.14, "arg");
	}

	@Test
	public void testNaNFloat_Negative() {
		ConditionalCheck.notNaN(false, Float.NaN);
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNFloat_Positive_Failure() {
		ConditionalCheck.notNaN(true, Float.NaN);
	}

	@Test
	public void testNaNFloat_Positive_NoFailure() {
		ConditionalCheck.notNaN(true, 3.14f);
	}

	@Test
	public void testNaNFloatArgName_Negative() {
		ConditionalCheck.notNaN(false, Float.NaN, "arg");
	}

	@Test(expected = IllegalNaNArgumentException.class)
	public void testNaNFloatArgName_Positive_Failure() {
		ConditionalCheck.notNaN(true, Float.NaN, "arg");
	}

	@Test
	public void testNaNFloatArgName_Positive_NoFailure() {
		ConditionalCheck.notNaN(true, 3.14f, "arg");
	}

	@Test
	public void testNoNullElements_Negative() {
		final List<Long> list = new ArrayList<Long>();
		list.add(Long.valueOf(42));
		list.add(null);
		ConditionalCheck.noNullElements(false, list);
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testNoNullElements_Positive_Failure() {
		final List<Long> list = new ArrayList<Long>();
		list.add(Long.valueOf(42));
		list.add(null);
		ConditionalCheck.noNullElements(true, list);
	}

	@Test
	public void testNoNullElements_Positive_NoFailure() {
		final List<Long> list = new ArrayList<Long>();
		list.add(Long.valueOf(42));
		ConditionalCheck.noNullElements(true, list);
	}

	@Test
	public void testNoNullElementsArgName_Negative() {
		final List<Long> list = new ArrayList<Long>();
		list.add(Long.valueOf(42));
		list.add(null);
		ConditionalCheck.noNullElements(false, list, "arg");
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testNoNullElementsArgName_Positive_Failure() {
		final List<Long> list = new ArrayList<Long>();
		list.add(Long.valueOf(42));
		list.add(null);
		ConditionalCheck.noNullElements(true, list, "arg");
	}

	@Test
	public void testNoNullElementsArgName_Positive_NoFailure() {
		final List<Long> list = new ArrayList<Long>();
		list.add(Long.valueOf(42));
		ConditionalCheck.noNullElements(true, list, "arg");
	}

	@Test
	public void testNoNullElementsArray_Negative() {
		final Long[] list = new Long[2];
		list[0] = Long.valueOf(42);
		list[1] = null;
		ConditionalCheck.noNullElements(false, list);
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testNoNullElementsArray_Positive_Failure() {
		final Long[] list = new Long[2];
		list[0] = Long.valueOf(42);
		list[1] = null;
		ConditionalCheck.noNullElements(true, list);
	}

	@Test
	public void testNoNullElementsArray_Positive_NoFailure() {
		final Long[] list = new Long[2];
		list[0] = Long.valueOf(42);
		list[1] = Long.valueOf(1234);
		ConditionalCheck.noNullElements(true, list);
	}

	@Test
	public void testNoNullElementsArrayArgName_Negative() {
		final Long[] list = new Long[2];
		list[0] = Long.valueOf(42);
		list[1] = null;
		ConditionalCheck.noNullElements(false, list, "arg");
	}

	@Test(expected = IllegalNullElementsException.class)
	public void testNoNullElementsArrayArgName_Positive_Failure() {
		final Long[] list = new Long[2];
		list[0] = Long.valueOf(42);
		list[1] = null;
		ConditionalCheck.noNullElements(true, list, "arg");
	}

	@Test
	public void testNoNullElementsArrayArgName_Positive_NoFailure() {
		final Long[] list = new Long[2];
		list[0] = Long.valueOf(42);
		list[1] = Long.valueOf(1234);
		ConditionalCheck.noNullElements(true, list, "arg");
	}

	@Test
	public void testNotEmpty_Negative() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(false, list);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmpty_Positive_Failure() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(true, list);
	}

	@Test
	public void testNotEmpty_Positive_NoFailure() {
		final List<String> list = new ArrayList<String>();
		list.add("Quality-Check");
		ConditionalCheck.notEmpty(true, list);
	}

	@Test
	public void testNotEmptyArgName_Negative() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(false, list, "arg");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyArgName_Positive_Failure() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(true, list, "arg");
	}

	@Test
	public void testNotEmptyArgName_Positive_NoFailure() {
		final List<String> list = new ArrayList<String>();
		list.add("Quality-Check");
		ConditionalCheck.notEmpty(true, list, "arg");
	}

	@Test
	public void testNotEmptyArray_Negative() {
		final Long[] list = new Long[0];
		ConditionalCheck.notEmpty(false, list);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyArray_Positive_Failure() {
		final Long[] list = new Long[0];
		ConditionalCheck.notEmpty(true, list);
	}

	@Test
	public void testNotEmptyArray_Positive_NoFailure() {
		final Long[] list = new Long[1];
		list[0] = Long.valueOf(42);
		ConditionalCheck.notEmpty(true, list);
	}

	@Test
	public void testNotEmptyArrayArgName_Negative() {
		final Long[] list = new Long[0];
		ConditionalCheck.notEmpty(false, list, "arg");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyArrayArgName_Positive_Failure() {
		final Long[] list = new Long[0];
		ConditionalCheck.notEmpty(true, list, "arg");
	}

	@Test
	public void testNotEmptyArrayArgName_Positive_NoFailure() {
		final Long[] list = new Long[1];
		list[0] = Long.valueOf(42);
		ConditionalCheck.notEmpty(true, list, "arg");
	}

	@Test
	public void testNotEmptyBoolean_Negative() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(false, list.isEmpty());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyBoolean_Positive_Failure() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(true, list.isEmpty());
	}

	@Test
	public void testNotEmptyBoolean_Positive_NoFailure() {
		final List<String> list = new ArrayList<String>();
		list.add("Quality-Check");
		ConditionalCheck.notEmpty(true, list.isEmpty());
	}

	@Test
	public void testNotEmptyBooleanArgName_Negative() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(false, list.isEmpty(), "arg");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyBooleanArgName_Positive_Failure() {
		final List<String> list = new ArrayList<String>();
		ConditionalCheck.notEmpty(true, list.isEmpty(), "arg");
	}

	@Test
	public void testNotEmptyBooleanArgName_Positive_NoFailure() {
		final List<String> list = new ArrayList<String>();
		list.add("Quality-Check");
		ConditionalCheck.notEmpty(true, list.isEmpty(), "arg");
	}

	@Test
	public void testNotEmptyMap_Negative() {
		final Map<Long, String> map = new HashMap<Long, String>();
		ConditionalCheck.notEmpty(false, map);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyMap_Positive_Failure() {
		final Map<Long, String> map = new HashMap<Long, String>();
		ConditionalCheck.notEmpty(true, map);
	}

	@Test
	public void testNotEmptyMap_Positive_NoFailure() {
		final Map<Long, String> map = new HashMap<Long, String>();
		map.put(42l, "Quality-Check");
		ConditionalCheck.notEmpty(true, map);
	}

	@Test
	public void testNotEmptyMapArgName_Negative() {
		final Map<Long, String> map = new HashMap<Long, String>();
		ConditionalCheck.notEmpty(false, map, "arg");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyMapArgName_Positive_Failure() {
		final Map<Long, String> map = new HashMap<Long, String>();
		ConditionalCheck.notEmpty(true, map, "arg");
	}

	@Test
	public void testNotEmptyMapArgName_Positive_NoFailure() {
		final Map<Long, String> map = new HashMap<Long, String>();
		map.put(42l, "Quality-Check");
		ConditionalCheck.notEmpty(true, map, "arg");
	}

	@Test
	public void testNotEmptyReferenceArgName_Negative() {
		final String str = "";
		ConditionalCheck.notEmpty(false, str, str.isEmpty(), "arg");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyReferenceArgName_Positive_Failure() {
		final String str = "";
		ConditionalCheck.notEmpty(true, str, str.isEmpty(), "arg");
	}

	@Test
	public void testNotEmptyReferenceArgName_Positive_NoFailure() {
		final String str = "Quality-Check";
		ConditionalCheck.notEmpty(true, str, str.isEmpty(), "arg");
	}

	@Test
	public void testNotEmptyString_Negative() {
		ConditionalCheck.notEmpty(false, "");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyString_Positive_Failure() {
		ConditionalCheck.notEmpty(true, "");
	}

	@Test
	public void testNotEmptyString_Positive_NoFailure() {
		ConditionalCheck.notEmpty(true, "Quality-Check");
	}

	@Test
	public void testNotEmptyStringArgName_Negative() {
		ConditionalCheck.notEmpty(false, "", "arg");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void testNotEmptyStringArgName_Positive_Failure() {
		ConditionalCheck.notEmpty(true, "", "arg");
	}

	@Test
	public void testNotEmptyStringArgName_Positive_NoFailure() {
		ConditionalCheck.notEmpty(true, "Quality-Check", "arg");
	}

	@Test
	public void testNotNegative_Negative() {
		ConditionalCheck.notNegative(false, -42);
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void testNotNegative_Positive_Failure() {
		ConditionalCheck.notNegative(true, -42);
	}

	@Test
	public void testNotNegative_Positive_NoFailure() {
		ConditionalCheck.notNegative(true, 42);
	}

	@Test
	public void testNotNegativeArgName_Negative() {
		ConditionalCheck.notNegative(false, -42, "arg");
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void testNotNegativeArgName_Positive_Failure() {
		ConditionalCheck.notNegative(true, -42, "arg");
	}

	@Test
	public void testNotNegativeArgName_Positive_NoFailure() {
		ConditionalCheck.notNegative(true, 42, "arg");
	}

	@Test
	public void testNotNull_Negative() {
		ConditionalCheck.notNull(false, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testNotNull_Positive_Failure() {
		ConditionalCheck.notNull(true, null);
	}

	@Test
	public void testNotNull_Positive_NoFailure() {
		ConditionalCheck.notNull(true, "Non Null Object");
	}

	@Test
	public void testNotNullArgName_Negative() {
		ConditionalCheck.notNull(false, null, "arg");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testNotNullArgName_Positive_Failure() {
		ConditionalCheck.notNull(true, null, "arg");
	}

	@Test
	public void testNotNullArgName_Positive_NoFailure() {
		ConditionalCheck.notNull(true, "Non Null Object", "arg");
	}

	@Test
	public void testPositionIndex_Negative() {
		ConditionalCheck.positionIndex(false, 5, 2);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void testPositionIndex_Positive_Failure() {
		ConditionalCheck.positionIndex(true, 5, 2);
	}

	@Test
	public void testPositionIndex_Positive_NoFailure() {
		ConditionalCheck.positionIndex(true, 2, 5);
	}

	@Test
	public void testRange_Negative() {
		ConditionalCheck.range(false, 5, 2, 7);
	}

	@Test(expected = IllegalRangeException.class)
	public void testRange_Positive_Failure() {
		ConditionalCheck.range(true, 5, 2, 7);
	}

	@Test
	public void testRange_Positive_NoFailure() {
		ConditionalCheck.range(true, 2, 5, 7);
	}

	@Test
	public void testState_Negative() {
		ConditionalCheck.stateIsTrue(false, 4 < 2);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testState_Positive_Failure() {
		ConditionalCheck.stateIsTrue(true, 4 < 2);
	}

	@Test
	public void testState_Positive_NoFailure() {
		ConditionalCheck.stateIsTrue(true, 2 < 4);
	}

	@Test
	public void testStateArgName_Negative() {
		ConditionalCheck.stateIsTrue(false, 4 < 2, "arg");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testStateArgName_Positive_Failure() {
		ConditionalCheck.stateIsTrue(true, 4 < 2, "arg");
	}

	@Test
	public void testStateArgName_Positive_NoFailure() {
		ConditionalCheck.stateIsTrue(true, 2 < 4, "arg");
	}

	@Test
	public void testStateException_Negative() {
		ConditionalCheck.stateIsTrue(false, 4 < 2, NullPointerException.class);
	}

	@Test(expected = NullPointerException.class)
	public void testStateException_Positive_Failure() {
		ConditionalCheck.stateIsTrue(true, 4 < 2, NullPointerException.class);
	}

	@Test
	public void testStateException_Positive_NoFailure() {
		ConditionalCheck.stateIsTrue(true, 2 < 4, NullPointerException.class);
	}

	@Test
	public void testStateMessage_Negative() {
		ConditionalCheck.stateIsTrue(false, 4 < 2, "arg {0}", Long.valueOf(4));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void testStateMessage_Positive_Failure() {
		ConditionalCheck.stateIsTrue(true, 4 < 2, "arg {0}", Long.valueOf(4));
	}

	@Test
	public void testStateMessage_Positive_NoFailure() {
		ConditionalCheck.stateIsTrue(true, 2 < 4, "arg {0}", Long.valueOf(4));
	}

	@Test(expected = java.lang.IllegalAccessException.class)
	public void testValidatesThatClassConditionalCheckIsNotInstantiable() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		final Class<?> cls = Class.forName("net.sf.qualitycheck.ConditionalCheck");
		cls.newInstance(); // exception here
	}

}
