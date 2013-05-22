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

import java.math.BigDecimal;
import java.math.BigInteger;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNumberRangeExceptionTest {

	@Test
	public void construct_withArgs_successful() {
		new IllegalNumberRangeException("5", BigDecimal.valueOf(4l), BigDecimal.valueOf(3l));
	}

	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalNumberRangeException("5", BigDecimal.valueOf(4l), BigDecimal.valueOf(3l), null);
	}

	@Test
	public void construct_withFilledArgsAndFilledCause() {
		new IllegalNumberRangeException("5", BigDecimal.valueOf(4l), BigDecimal.valueOf(3l), new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalNumberRangeException e = new IllegalNumberRangeException("2", BigDecimal.valueOf(4l), BigDecimal.valueOf(3l), null);
		Assert.assertEquals("Argument value '2' must be in the range '4' to '3'.", e.getMessage());
	}

	@Test
	public void construct_withIntFilledArgsAndNullCause() {
		final IllegalNumberRangeException e = new IllegalNumberRangeException("2", BigInteger.valueOf(4l), BigInteger.valueOf(3l), null);
		Assert.assertEquals("Argument value '2' must be in the range '4' to '3'.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNumberRangeException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNumberRangeException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNumberRangeExceptionTest();
	}

	@Test
	public void construct_withoutArgs_successfulAndCheckMessage() {
		final IllegalNumberRangeException e = new IllegalNumberRangeException();
		Assert.assertEquals("Number must be in a valid range for the given datatype.", e.getMessage());
	}
}
