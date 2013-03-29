/*******************************************************************************
 * Copyright 2013 André Rouél
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

import java.lang.reflect.Method;

import net.sf.qualitytest.LongRunning;

import org.junit.Assert;
import org.junit.Test;

public class IllegalMissingAnnotationOnMethodExceptionTest {

	@Test
	public void construct_withCause() {
		new IllegalMissingAnnotationOnMethodException(new ClassNotFoundException());
	}

	@Test
	public void construct_withMethodAnnotationAndClass_successful() throws SecurityException, NoSuchMethodException {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethod("tetstGetters");
		new IllegalMissingAnnotationOnMethodException(IllegalMissingAnnotationOnMethodExceptionTest.class, LongRunning.class, m);
	}

	@Test
	public void construct_withMethodAnnotationAndClassAndCause_successful() throws SecurityException, NoSuchMethodException {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethod("tetstGetters");
		final IllegalMissingAnnotationOnMethodException e = new IllegalMissingAnnotationOnMethodException(
				IllegalMissingAnnotationOnMethodExceptionTest.class, LongRunning.class, m, null);
		Assert.assertEquals(
				"Class 'net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodExceptionTest' must have annotation 'net.sf.qualitytest.LongRunning' on method 'tetstGetters'.",
				e.getMessage());
		Assert.assertEquals(LongRunning.class, e.getMissingAnnotation());
		Assert.assertEquals(m, e.getMethodWithoutAnnotation());
		Assert.assertEquals(IllegalMissingAnnotationOnMethodExceptionTest.class, e.getClassWithoutAnnotation());
		Assert.assertNull(e.getCause());
	}

	@Test
	public void construct_withMethodAnnotationAndClassAndNullCause_successful() throws SecurityException, NoSuchMethodException {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethod("tetstGetters");
		final IllegalMissingAnnotationOnMethodException e = new IllegalMissingAnnotationOnMethodException(
				IllegalMissingAnnotationOnMethodExceptionTest.class, LongRunning.class, m, new ClassNotFoundException());
		Assert.assertEquals(
				"Class 'net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodExceptionTest' must have annotation 'net.sf.qualitytest.LongRunning' on method 'tetstGetters'.",
				e.getMessage());
		Assert.assertEquals(LongRunning.class, e.getMissingAnnotation());
		Assert.assertEquals(m, e.getMethodWithoutAnnotation());
		Assert.assertEquals(IllegalMissingAnnotationOnMethodExceptionTest.class, e.getClassWithoutAnnotation());
		Assert.assertNotNull(e.getCause());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_withNullAnnotation_notSuccessful() {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethods()[0];
		new IllegalMissingAnnotationOnMethodException(IllegalMissingAnnotationOnMethodExceptionTest.class, null, m);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_withNullAnnotationAndFilledCause() {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethods()[0];
		new IllegalMissingAnnotationOnMethodException(IllegalMissingAnnotationOnMethodExceptionTest.class, null, m,
				new ClassNotFoundException());
	}

	@Test
	public void construct_withNullCause() {
		final IllegalMissingAnnotationOnMethodException e = new IllegalMissingAnnotationOnMethodException((Throwable) null);
		Assert.assertEquals("Annotation is required on all public methods of the passed class.", e.getMessage());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_withNullClass_notSuccessful() {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethods()[0];
		new IllegalMissingAnnotationOnMethodException(null, LongRunning.class, m);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_withNullClassAndFilledCause() {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethods()[0];
		new IllegalMissingAnnotationOnMethodException(null, LongRunning.class, m, new ClassNotFoundException());
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_withNullMethod_notSuccessful() {
		new IllegalMissingAnnotationOnMethodException(IllegalMissingAnnotationOnMethodExceptionTest.class, LongRunning.class, null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void construct_withNullMethodAndFilledCause() {
		new IllegalMissingAnnotationOnMethodException(IllegalMissingAnnotationOnMethodExceptionTest.class, LongRunning.class,
				(Method) null, new ClassNotFoundException());
	}

	@Test
	public void construct_withoutArgs_successful() {
		final IllegalMissingAnnotationOnMethodException e = new IllegalMissingAnnotationOnMethodException();
		Assert.assertEquals("Annotation is required on all public methods of the passed class.", e.getMessage());
	}

	@Test
	public void tetstGetters() throws SecurityException, NoSuchMethodException {
		final Method m = IllegalMissingAnnotationOnMethodExceptionTest.class.getMethod("tetstGetters");
		final IllegalMissingAnnotationOnMethodException e = new IllegalMissingAnnotationOnMethodException(
				IllegalMissingAnnotationOnMethodExceptionTest.class, LongRunning.class, m, null);
		Assert.assertEquals(LongRunning.class, e.getMissingAnnotation());
		Assert.assertEquals(m, e.getMethodWithoutAnnotation());
		Assert.assertEquals(IllegalMissingAnnotationOnMethodExceptionTest.class, e.getClassWithoutAnnotation());
		Assert.assertNull(e.getCause());
	}

}
