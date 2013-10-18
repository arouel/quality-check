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

import org.junit.Assert;
import org.junit.Test;

public class IllegalNumericArgumentExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalNumericArgumentException("argName", "42a");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalNumericArgumentException("", "42a");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		new IllegalNumericArgumentException("", "42a", null);
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		new IllegalNumericArgumentException("argName", "42a", new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalNumericArgumentException e = new IllegalNumericArgumentException("argName", "42a", null);
		Assert.assertEquals("The passed argument 'argName' must be numeric.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNumericArgumentException("42a", new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalNumericArgumentException((String) null, "42a");
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		new IllegalNumericArgumentException((String) null, null, null);
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNumericArgumentException(null, (Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNumericArgumentException("42a");
	}

	@Test
	public void testGetIllegalArgument() {
		final IllegalArgumentHolder<CharSequence> iah = new IllegalNumericArgumentException("42a");
		Assert.assertEquals("42a", iah.getIllegalArgument());
	}
}
