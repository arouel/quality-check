/*******************************************************************************
 * Copyright 2012 André Rouél
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

public class IllegalNullElementsExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		final IllegalNullElementsException e = new IllegalNullElementsException("argName");
		final String expected = "The passed parameter 'argName' must not contain elements that are null.";
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException(new NullPointerException());
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		final IllegalNullElementsException e = new IllegalNullElementsException("");
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException("", null);
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException("argName", new NumberFormatException());
		final String expected = "The passed parameter 'argName' must not contain elements that are null.";
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException("argName", null);
		final String expected = "The passed parameter 'argName' must not contain elements that are null.";
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException(new NumberFormatException());
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withNullArgName() {
		final IllegalNullElementsException e = new IllegalNullElementsException((String) null);
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException((String) null, null);
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		final IllegalNullElementsException e = new IllegalNullElementsException((Throwable) null);
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalNullElementsException e = new IllegalNullElementsException();
		final String expected = IllegalNullElementsException.DEFAULT_MESSAGE;
		Assert.assertEquals(expected, e.getMessage());
	}

}
