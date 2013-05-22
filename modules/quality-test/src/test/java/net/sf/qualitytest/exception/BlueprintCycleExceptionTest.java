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

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintCycleExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new BlueprintCycleException(String.class);
	}

	@Test(expected = BlueprintCycleException.class)
	public void construct_withEmptyArgName_successful() {
		throw new BlueprintCycleException(String.class);
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final BlueprintCycleException e = new BlueprintCycleException(
				String.class, null);
		Assert.assertEquals(
				"Error during blueprinting class 'java.lang.String'.",
				e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new BlueprintCycleException(String.class, new NumberFormatException());
	}

	@Test
	public void construct_withMessageAndFilledCause() {
		final BlueprintCycleException e = new BlueprintCycleException(
				BlueprintCycleExceptionTest.class, new NumberFormatException());
		Assert.assertEquals(
				"Error during blueprinting class 'net.sf.qualitytest.exception.BlueprintCycleExceptionTest'.",
				e.getMessage());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullClass() {
		new BlueprintCycleException(null);
	}

}
