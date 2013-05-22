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
package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

public class CheckTest_stateIsTrue {

	private static class FakeIllegalAccessException extends RuntimeException {
		private static final long serialVersionUID = 3810373199918408287L;

		@SuppressWarnings("unused")
		public FakeIllegalAccessException() throws IllegalAccessException {
			throw new IllegalAccessException();
		}
	}

	private static class FakeInstantiationException extends RuntimeException {
		private static final long serialVersionUID = -7585963509351269594L;

		@SuppressWarnings("unused")
		public FakeInstantiationException() throws InstantiationException {
			throw new InstantiationException();
		}
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void checkStateIsTrue_False() {
		Check.stateIsTrue(false);
	}

	@Test
	public void checkStateIsTrue_True() {
		Check.stateIsTrue(true);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void checkStateIsTrueWithMessage_False() {
		Check.stateIsTrue(false, "False is not allowed.");
	}

	@Test
	public void checkStateIsTrueWithMessage_True() {
		Check.stateIsTrue(true, "False is not allowed.");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void checkStateIsTrueWithMessageArguments_False() {
		Check.stateIsTrue(false, "Value '%d' is not allowed.", 42);
	}

	@Test
	public void checkStateIsTrueWithMessageArguments_True() {
		Check.stateIsTrue(true, "Value '%d' is not allowed.", 42);
	}

	@Test(expected = NullPointerException.class)
	public void checkStateIsTrueWithThrowable_False() {
		Check.stateIsTrue(false, NullPointerException.class);
	}

	@Test
	public void checkStateIsTrueWithThrowable_True() {
		Check.stateIsTrue(true, NullPointerException.class);
	}

	@Test(expected = RuntimeException.class)
	public void checkStateIsTrueWithThrowableAndIllegalAccessException_False() {
		Check.stateIsTrue(false, FakeIllegalAccessException.class);
	}

	@Test(expected = RuntimeException.class)
	public void checkStateIsTrueWithThrowableAndInstantiationException_False() {
		Check.stateIsTrue(false, FakeInstantiationException.class);
	}

}
