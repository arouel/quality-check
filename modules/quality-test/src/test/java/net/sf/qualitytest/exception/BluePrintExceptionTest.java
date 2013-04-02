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

public class BluePrintExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new BluePrintException("Message");
	}

	@Test(expected = BluePrintException.class)
	public void construct_withEmptyArgName_successful() {
		throw new BluePrintException("");
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final BluePrintException e = new BluePrintException("msg", null);
		Assert.assertEquals("msg", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new BluePrintException(new NumberFormatException());
	}

	@Test
	public void construct_withMessageAndFilledCause() {
		final BluePrintException e = new BluePrintException("Cannot access field.", new NumberFormatException());
		Assert.assertEquals("Cannot access field.", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		new BluePrintException((Throwable) null);
	}

	@Test
	public void construct_withNullCauseAndCheckMessage() {
		final BluePrintException e = new BluePrintException((Throwable) null);
		Assert.assertEquals("Error during blueprinting.", e.getMessage());
	}

	@Test
	public void construct_withNullMessage() {
		new BluePrintException((String) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		final BluePrintException e = new BluePrintException();
		Assert.assertEquals("Error during blueprinting.", e.getMessage());
	}

}
