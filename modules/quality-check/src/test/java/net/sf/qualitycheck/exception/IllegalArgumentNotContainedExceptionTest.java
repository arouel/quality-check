/*******************************************************************************
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
package net.sf.qualitycheck.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalArgumentNotContainedExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalArgumentNotContainedException("argName");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalArgumentNotContainedException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		new IllegalArgumentNotContainedException("", null);
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		new IllegalArgumentNotContainedException("argName", new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalArgumentNotContainedException e = new IllegalArgumentNotContainedException("argName", null);
		Assert.assertEquals("The passed argument 'argName' must be contained in a defined collection.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalArgumentNotContainedException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalArgumentNotContainedException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		new IllegalArgumentNotContainedException((String) null, null);
	}

	@Test
	public void construct_withNullCause() {
		new IllegalArgumentNotContainedException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalArgumentNotContainedException();
	}

}
