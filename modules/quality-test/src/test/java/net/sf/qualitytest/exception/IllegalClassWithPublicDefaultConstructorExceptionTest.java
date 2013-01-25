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
package net.sf.qualitytest.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalClassWithPublicDefaultConstructorExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalClassWithPublicDefaultConstructorException("argName");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalClassWithPublicDefaultConstructorException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		final IllegalClassWithPublicDefaultConstructorException e = new IllegalClassWithPublicDefaultConstructorException("", null);
		Assert.assertEquals("The passed class must not have a public default constructor.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		final IllegalClassWithPublicDefaultConstructorException e = new IllegalClassWithPublicDefaultConstructorException("argName", new NumberFormatException());
		Assert.assertEquals("The passed class 'argName' must not have a public default constructor.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalClassWithPublicDefaultConstructorException e = new IllegalClassWithPublicDefaultConstructorException("argName", null);
		Assert.assertEquals("The passed class 'argName' must not have a public default constructor.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalClassWithPublicDefaultConstructorException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalClassWithPublicDefaultConstructorException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		final IllegalClassWithPublicDefaultConstructorException e = new IllegalClassWithPublicDefaultConstructorException((String) null, null);
		Assert.assertEquals("The passed class must not have a public default constructor.", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalClassWithPublicDefaultConstructorException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalClassWithPublicDefaultConstructorException e = new IllegalClassWithPublicDefaultConstructorException();
		Assert.assertEquals("The passed class must not have a public default constructor.", e.getMessage());
	}

}
