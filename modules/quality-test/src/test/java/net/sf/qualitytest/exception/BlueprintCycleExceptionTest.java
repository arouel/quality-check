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

import java.util.ArrayList;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.BlueprintSession;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintCycleExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new BlueprintCycleException(new BlueprintSession(), String.class);
	}

	@Test(expected = BlueprintCycleException.class)
	public void construct_withEmptyArgName_successful() {
		throw new BlueprintCycleException(new BlueprintSession(), String.class);
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final BlueprintCycleException e = new BlueprintCycleException(new BlueprintSession(), String.class, null);
		Assert.assertEquals("Error during blueprinting class 'java.lang.String': ", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new BlueprintCycleException(new BlueprintSession(), String.class, new NumberFormatException());
	}

	@Test
	public void construct_withMessageAndFilledCause() {
		final BlueprintCycleException e = new BlueprintCycleException(new BlueprintSession(), BlueprintCycleExceptionTest.class,
				new NumberFormatException());
		Assert.assertEquals("Error during blueprinting class 'net.sf.qualitytest.exception.BlueprintCycleExceptionTest': ", e.getMessage());
	}

	@Test
	public void construct_withMessageAndFilledCauseAndSession() {
		final BlueprintSession session = new BlueprintSession();
		session.push(ArrayList.class);
		session.push(String.class);
		final BlueprintCycleException e = new BlueprintCycleException(session, BlueprintCycleExceptionTest.class,
				new NumberFormatException());
		Assert.assertEquals(
				"Error during blueprinting class 'net.sf.qualitytest.exception.BlueprintCycleExceptionTest': java.util.ArrayList->java.lang.String",
				e.getMessage());
	}

	@Test
	public void construct_withMessageAndFilledCauseAndSessionAndLastAction() {
		final BlueprintSession session = new BlueprintSession();
		session.push(ArrayList.class);
		session.push(String.class);
		session.setLastAction("Hello World");
		final BlueprintCycleException e = new BlueprintCycleException(session, BlueprintCycleExceptionTest.class,
				new NumberFormatException());
		Assert.assertEquals(
				"Error during blueprinting class 'net.sf.qualitytest.exception.BlueprintCycleExceptionTest': java.util.ArrayList->java.lang.String {Hello World}",
				e.getMessage());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullClass() {
		new BlueprintCycleException(new BlueprintSession(), null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullSession() {
		new BlueprintCycleException(null, String.class);
	}

	@Test
	public void testGetSession() {
		final BlueprintSession s = new BlueprintSession();
		Assert.assertSame(s, new BlueprintCycleException(s, String.class).getSession());
	}
}
