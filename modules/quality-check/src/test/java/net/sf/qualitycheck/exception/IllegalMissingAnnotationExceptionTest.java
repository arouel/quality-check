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

import java.lang.annotation.Annotation;

import net.sf.qualitycheck.ArgumentsChecked;

import org.junit.Assert;
import org.junit.Test;

public class IllegalMissingAnnotationExceptionTest {

	@Test
	public void construct_withAnnotationAndClass_successful() {
		new IllegalMissingAnnotationException(ArgumentsChecked.class, IllegalMissingAnnotationExceptionTest.class);
	}

	@Test
	public void construct_withAnnotationAndNullClassAndNullCause() {
		final IllegalMissingAnnotationException e = new IllegalMissingAnnotationException(ArgumentsChecked.class, (Class<?>) null,
				(Throwable) null);
		Assert.assertEquals("Class must have annotation 'net.sf.qualitycheck.ArgumentsChecked'.", e.getMessage());
		Assert.assertEquals(ArgumentsChecked.class, e.getMissingAnnotation());
		Assert.assertNull(e.getClassWithoutAnnotation());
	}

	@Test
	public void construct_withArgName_successful() {
		new IllegalMissingAnnotationException(ArgumentsChecked.class);
	}

	@Test
	public void construct_withArgNameAndClass_successful() {
		new IllegalMissingAnnotationException(ArgumentsChecked.class, IllegalMissingAnnotationExceptionTest.class);
	}

	@Test
	public void construct_withFilledAnnotationAndClassAndFilledCause() {
		new IllegalMissingAnnotationException(ArgumentsChecked.class, IllegalMissingAnnotationExceptionTest.class,
				new ClassNotFoundException());
	}

	@Test
	public void construct_withFilledAnnotationAndClauseAndNullCause() {
		final IllegalMissingAnnotationException e = new IllegalMissingAnnotationException(ArgumentsChecked.class,
				IllegalMissingAnnotationExceptionTest.class, (Throwable) null);
		Assert.assertEquals(
				"Class 'net.sf.qualitycheck.exception.IllegalMissingAnnotationExceptionTest' must have annotation 'net.sf.qualitycheck.ArgumentsChecked'.",
				e.getMessage());
		Assert.assertEquals(ArgumentsChecked.class, e.getMissingAnnotation());
		Assert.assertEquals(IllegalMissingAnnotationExceptionTest.class, e.getClassWithoutAnnotation());
	}

	@Test
	public void construct_withFilledAnnotationAndFilledCause() {
		new IllegalMissingAnnotationException(ArgumentsChecked.class, new ClassNotFoundException());
	}

	@Test
	public void construct_withFilledAnnotationAndNullCause() {
		final IllegalMissingAnnotationException e = new IllegalMissingAnnotationException(ArgumentsChecked.class, (Throwable) null);
		Assert.assertEquals("Class must have annotation 'net.sf.qualitycheck.ArgumentsChecked'.", e.getMessage());
		Assert.assertEquals(ArgumentsChecked.class, e.getMissingAnnotation());
		Assert.assertNull(e.getClassWithoutAnnotation());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullAnnotation_notSuccessful() {
		new IllegalMissingAnnotationException((Class<? extends Annotation>) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullAnnotationAndFilledCause() {
		new IllegalMissingAnnotationException(null, new ClassNotFoundException());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullAnnotationAndNullCause() {
		new IllegalMissingAnnotationException(null, (Throwable) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullAnnotationAndNullCause_notSuccessfull() {
		new IllegalMissingAnnotationException((Class<? extends Annotation>) null, (Throwable) null);
	}

	@Test
	public void construct_withNullCause() {
		final IllegalMissingAnnotationException e = new IllegalMissingAnnotationException((Throwable) null);
		Assert.assertEquals("Annotation is required on the passed class.", e.getMessage());
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalMissingAnnotationException e = new IllegalMissingAnnotationException();
		Assert.assertEquals("Annotation is required on the passed class.", e.getMessage());
	}

}
