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
package net.sf.qualitytest.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNonFinalClassExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalNonFinalClassException("argName");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalNonFinalClassException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		final IllegalNonFinalClassException e = new IllegalNonFinalClassException("", null);
		Assert.assertEquals("The passed class must be final.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		final IllegalNonFinalClassException e = new IllegalNonFinalClassException("argName", new NumberFormatException());
		Assert.assertEquals("The passed class 'argName' must be final.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalNonFinalClassException e = new IllegalNonFinalClassException("argName", null);
		Assert.assertEquals("The passed class 'argName' must be final.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNonFinalClassException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalNonFinalClassException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		final IllegalNonFinalClassException e = new IllegalNonFinalClassException((String) null, null);
		Assert.assertEquals("The passed class must be final.", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNonFinalClassException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalNonFinalClassException e = new IllegalNonFinalClassException();
		Assert.assertEquals("The passed class must be final.", e.getMessage());
	}

}
