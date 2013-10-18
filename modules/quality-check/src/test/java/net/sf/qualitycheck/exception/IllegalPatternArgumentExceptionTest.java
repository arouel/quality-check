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

import org.junit.Assert;
import org.junit.Test;

public class IllegalPatternArgumentExceptionTest {

	private static final Pattern TEST_PATTERN = Pattern.compile("[a-z]+");

	@Test
	public void construct_withArgName_successful() {
		new IllegalPatternArgumentException("argName", TEST_PATTERN, "value");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalPatternArgumentException("", TEST_PATTERN, "value");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException("", null, "value", (Throwable) null);
		Assert.assertEquals("The passed argument must match against the specified pattern: [not set]", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException("argName", TEST_PATTERN, "value",
				new NumberFormatException());
		Assert.assertEquals("The passed argument 'argName' must match against the specified pattern: [a-z]+ (flags: 0)", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException("argName", null, "value", (Throwable) null);
		Assert.assertEquals("The passed argument 'argName' must match against the specified pattern: [not set]", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalPatternArgumentException(TEST_PATTERN, "value", new NumberFormatException());
	}

	@Test
	public void construct_withModifier() {
		final Pattern pattern = Pattern.compile("[a-z]+", Pattern.CASE_INSENSITIVE);
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException("argName", pattern, "value",
				new NumberFormatException());
		Assert.assertEquals("The passed argument 'argName' must match against the specified pattern: [a-z]+ (flags: 2)", e.getMessage());
	}

	@Test
	public void construct_withNullArgName() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException((String) null, TEST_PATTERN, "value");
		Assert.assertEquals("The passed argument must match against the specified pattern: [a-z]+ (flags: 0)", e.getMessage());
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException((String) null, null, null);
		Assert.assertEquals("The passed argument must match against the specified pattern: [not set]", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException(TEST_PATTERN, null, (Throwable) null);
		Assert.assertEquals("The passed argument must match against the specified pattern: [a-z]+ (flags: 0)", e.getMessage());
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalPatternArgumentException e = new IllegalPatternArgumentException(TEST_PATTERN, "value");
		Assert.assertEquals("The passed argument must match against the specified pattern: [a-z]+ (flags: 0)", e.getMessage());
	}

	@Test
	public void testGetIllegalArgument() {
		final IllegalArgumentHolder<CharSequence> iah = new IllegalPatternArgumentException(TEST_PATTERN, "value");
		Assert.assertEquals("value", iah.getIllegalArgument());
	}
}
