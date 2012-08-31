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

public class IllegalRangeExceptionTest {

	@Test
	public void construct_withArgs_successful() {
		new IllegalRangeException(5, 4, 3);
	}

	@Test
	public void construct_withZeroArgs_successful() {
		new IllegalRangeException(0, 0, 0);
	}

	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalRangeException(2, 1, 3, null);
	}

	@Test
	public void construct_withFilledArgsAndFilledCause() {
		new IllegalRangeException(2, 1, 3, new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalRangeException e = new IllegalRangeException(2, 1, 3, null);
		Assert.assertEquals("Arguments start='2', end='1' and size='3' must be a valid range.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalRangeException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalRangeException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalRangeExceptionTest();
	}

	@Test
	public void construct_withoutArgs_successfulAndCheckMessage() {
		final IllegalRangeException e = new IllegalRangeException();
		Assert.assertEquals("Arguments must be a valid range.", e.getMessage());
	}
}
