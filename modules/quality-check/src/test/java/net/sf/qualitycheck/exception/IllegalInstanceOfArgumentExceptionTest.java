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

public class IllegalInstanceOfArgumentExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalInstanceOfArgumentException("argName", Long.class, Integer.class);
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException("", Long.class, Integer.class);
		Assert.assertEquals(
				"The passed parameter is a member of an unexpected type (expected type: java.lang.Long, actual: java.lang.Integer).",
				e.getMessage());
	}

	@Test
	public void construct_withEmptyArgNameAndNullActualType() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException("", Long.class, null);
		Assert.assertEquals("The passed parameter is a member of an unexpected type (expected type: java.lang.Long, actual: (not set)).",
				e.getMessage());
	}

	@Test
	public void construct_withEmptyArgNameAndNullExpectedType() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException("", null, Integer.class);
		Assert.assertEquals(
				"The passed parameter is a member of an unexpected type (expected type: (not set), actual: java.lang.Integer).",
				e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullTypes() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException("argName", null, null);
		Assert.assertEquals("The passed parameter 'argName' is a member of an unexpected type (expected type: "
				+ IllegalInstanceOfArgumentException.NO_TYPE_PLACEHOLDER + ", actual: "
				+ IllegalInstanceOfArgumentException.NO_TYPE_PLACEHOLDER + ").", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndTypes() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException("argName", Long.class, Integer.class);
		Assert.assertEquals(
				"The passed parameter 'argName' is a member of an unexpected type (expected type: java.lang.Long, actual: java.lang.Integer).",
				e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException(new NumberFormatException());
		Assert.assertEquals(IllegalInstanceOfArgumentException.DEFAULT_MESSAGE, e.getMessage());
	}

	@Test
	public void construct_withNullArgs() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException((String) null, null, null);
		Assert.assertEquals("The passed parameter is a member of an unexpected type (expected type: (not set), actual: (not set)).",
				e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException((Throwable) null);
		Assert.assertEquals(IllegalInstanceOfArgumentException.DEFAULT_MESSAGE, e.getMessage());
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalInstanceOfArgumentException e = new IllegalInstanceOfArgumentException();
		Assert.assertEquals(IllegalInstanceOfArgumentException.DEFAULT_MESSAGE, e.getMessage());
	}

}
