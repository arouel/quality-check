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

import org.junit.Assert;
import org.junit.Test;

public class NoPublicConstructorExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new NoPublicConstructorException("Message");
	}

	@Test(expected = NoPublicConstructorException.class)
	public void construct_withEmptyArgName_successful() {
		throw new NoPublicConstructorException("");
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final NoPublicConstructorException e = new NoPublicConstructorException("clazzName", null);
		Assert.assertEquals("The given class 'clazzName' has no public constructor.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new NoPublicConstructorException(new NumberFormatException());
	}

	@Test
	public void construct_withMessageAndFilledCause() {
		final NoPublicConstructorException e = new NoPublicConstructorException("otherClazzName", new NumberFormatException());
		Assert.assertEquals("The given class 'otherClazzName' has no public constructor.", e.getMessage());
	}

	@Test
	public void construct_withNullCause() {
		new NoPublicConstructorException((Throwable) null);
	}

	@Test
	public void construct_withNullCauseAndCheckMessage() {
		final NoPublicConstructorException e = new NoPublicConstructorException((Throwable) null);
		Assert.assertEquals("The given class has no public constructor.", e.getMessage());
	}

	@Test
	public void construct_withNullMessage() {
		new NoPublicConstructorException((String) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		final NoPublicConstructorException e = new NoPublicConstructorException();
		Assert.assertEquals("The given class has no public constructor.", e.getMessage());
	}

}
