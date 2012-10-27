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

import java.lang.reflect.Constructor;
import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;
import net.sf.qualitycheck.exception.IllegalPositionIndexException;

import org.junit.Assert;
import org.junit.Test;

/**
 * Modul Test for the class {@link net.sf.qualitycheck.Check}
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class CheckTest {

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
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<Check> constructor = Check.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
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

	@Test(expected = java.lang.IllegalAccessException.class)
	public void testValidatesThatClassCheckIsNotInstantiable() throws ClassNotFoundException, InstantiationException,
			IllegalAccessException {
		final Class<?> cls = Class.forName("net.sf.qualitycheck.Check");
		cls.newInstance(); // exception here
	}

}
