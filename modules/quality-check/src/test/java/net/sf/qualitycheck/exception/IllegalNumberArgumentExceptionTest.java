/*******************************************************************************
 * Copyright 2012 André Rouél
 * Copyright 2012 Dominik Seichter
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

public class IllegalNumberArgumentExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalNumberArgumentException("argName");
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalNumberArgumentException("");
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		new IllegalNumberArgumentException("", null);
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		new IllegalNumberArgumentException("argName", new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalNumberArgumentException e = new IllegalNumberArgumentException("argName", null);
		Assert.assertEquals("The passed argument 'argName' must be a number.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNumberArgumentException(new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalNumberArgumentException((String) null);
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		new IllegalNumberArgumentException((String) null, null);
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNumberArgumentException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNumberArgumentException();
	}

}
