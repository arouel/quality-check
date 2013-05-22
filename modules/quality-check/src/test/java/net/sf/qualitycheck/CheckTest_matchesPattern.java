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

import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalPatternArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_matchesPattern {

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

}
