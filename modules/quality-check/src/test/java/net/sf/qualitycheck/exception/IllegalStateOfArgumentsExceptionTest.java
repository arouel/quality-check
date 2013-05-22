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

public class IllegalStateOfArgumentsExceptionTest {
	

	@Test
	public void construct_withArgs_successful() {
		new IllegalStateOfArgumentException("a^2 + b^2 = c^2");
	}

	@Test
	public void message_without_template() {
		final IllegalStateOfArgumentException e = new IllegalStateOfArgumentException("a=%d");
		Assert.assertEquals("The passed arguments have caused an invalid state: a=%d", e.getMessage());
	}
	
	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalStateOfArgumentException("a^2 + b^2 = c^2", (Throwable)null);
	}

	@Test
	public void construct_withArgsAndNullCauseAndFormatValue() {
		new IllegalStateOfArgumentException("%d^2 + %d^2 = %d^2", 1, 2, 3, (Throwable)null);
	}
	
	@Test
	public void message_with_null_template() {
		final IllegalStateOfArgumentException e = new IllegalStateOfArgumentException("a=%d", (Object[])null);
		Assert.assertEquals("The passed arguments have caused an invalid state: a=%d", e.getMessage());
	}	
	
	@Test
	public void construct_withFilledArgsAndFilledCause() {
		new IllegalStateOfArgumentException(new NumberFormatException(), "a^2 + b^2 = c^2");
	}
	
	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalStateOfArgumentException e = new IllegalStateOfArgumentException("a != b", (Throwable)null);
		Assert.assertEquals("The passed arguments have caused an invalid state: a != b", e.getMessage());
	}

	@Test
	public void construct_withFilledArgsAndNullCauseAndFormatValue() {
		final IllegalStateOfArgumentException e = new IllegalStateOfArgumentException("%d^2 + %d^2 = %d^2", 1, 2, 3, (Throwable)null);
		Assert.assertEquals("1^2 + 2^2 = 3^2", e.getMessage());
	}
	
	@Test
	public void construct_withFilledCause() {
		new IllegalStateOfArgumentException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalStateOfArgumentException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalStateOfArgumentException();
	}

	@Test
	public void construct_withoutArgs_successfulAndCheckMessage() {
		final IllegalStateOfArgumentException e = new IllegalStateOfArgumentException();
		Assert.assertEquals("Arguments must be valid with the current state.", e.getMessage());
	}	
}
