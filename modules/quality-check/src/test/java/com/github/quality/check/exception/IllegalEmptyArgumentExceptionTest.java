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
package com.github.quality.check.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalEmptyArgumentExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalEmptyArgumentException("argName");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalEmptyArgumentException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		final IllegalEmptyArgumentException e = new IllegalEmptyArgumentException("", null);
		Assert.assertEquals("The passed argument must not be empty.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		final IllegalEmptyArgumentException e = new IllegalEmptyArgumentException("argName", new NumberFormatException());
		Assert.assertEquals("The passed argument 'argName' must not be empty.", e.getMessage());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalEmptyArgumentException e = new IllegalEmptyArgumentException("argName", null);
		Assert.assertEquals("The passed argument 'argName' must not be empty.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalEmptyArgumentException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalEmptyArgumentException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		final IllegalEmptyArgumentException e = new IllegalEmptyArgumentException((String) null, null);
		Assert.assertEquals("The passed argument must not be empty.", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalEmptyArgumentException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalEmptyArgumentException e = new IllegalEmptyArgumentException();
		Assert.assertEquals("The passed argument must not be empty.", e.getMessage());
	}

}
