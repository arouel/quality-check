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

public class RuntimeInstantiationExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new RuntimeInstantiationException("className");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new RuntimeInstantiationException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		final RuntimeInstantiationException e = new RuntimeInstantiationException("", null);
		Assert.assertEquals("The passed class cannot be instantiated.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		final RuntimeInstantiationException e = new RuntimeInstantiationException("className", new NumberFormatException());
		Assert.assertEquals("The passed class 'className' cannot be instantiated.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final RuntimeInstantiationException e = new RuntimeInstantiationException("className", null);
		Assert.assertEquals("The passed class 'className' cannot be instantiated.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new RuntimeInstantiationException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new RuntimeInstantiationException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		final RuntimeInstantiationException e = new RuntimeInstantiationException((String) null, null);
		Assert.assertEquals("The passed class cannot be instantiated.", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		new RuntimeInstantiationException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		final RuntimeInstantiationException e = new RuntimeInstantiationException();
		Assert.assertEquals("The passed class cannot be instantiated.", e.getMessage());
	}

}
