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

public class IllegalNaNArgumentExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalNaNArgumentException("argName");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalNaNArgumentException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		new IllegalNaNArgumentException("", null);
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		new IllegalNaNArgumentException("argName", new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalNaNArgumentException e = new IllegalNaNArgumentException("argName", null);
		Assert.assertEquals("The passed argument 'argName' must not be NaN.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNaNArgumentException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalNaNArgumentException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		new IllegalNaNArgumentException((String) null, null);
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNaNArgumentException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNaNArgumentException();
	}

}
