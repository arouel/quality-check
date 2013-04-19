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
package net.sf.qualitytest.blueprint;

import net.sf.qualitytest.CoverageForPrivateConstructor;
import net.sf.qualitytest.blueprint.SafeInvoke.ExceptionRunnable;
import net.sf.qualitytest.exception.BlueprintException;

import org.junit.Assert;
import org.junit.Test;

public class SafeInvokeTest {

	public static class DefaultConstructorOnlyException extends RuntimeException {
		private static final long serialVersionUID = 2298713774855747747L;

		public DefaultConstructorOnlyException() {
			super();
		}
	}

	public static class MoreParameterConstructorException extends RuntimeException {
		private static final long serialVersionUID = 4887418387361390043L;

		private MoreParameterConstructorException(final String msg, final Throwable e) {
			super(msg, e);
		}
	}

	public static class NoConstructorException extends RuntimeException {
		private static final long serialVersionUID = 4887418387361390043L;

		private NoConstructorException() {

		}
	}

	public static class StringConstructorException extends RuntimeException {

		private static final long serialVersionUID = -1228987309626172055L;

		private StringConstructorException(final String msg) {
			super(msg);
		}
	}

	@Test
	public void coverPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(SafeInvoke.class);
	}

	@Test(expected = BlueprintException.class)
	public void testSaveInfokeNoConstructorInException() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				throw new IllegalArgumentException();
			}
		}, NoConstructorException.class));
	}

	@Test
	public void testSaveInfokeReturn() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				return "quality-test";
			}
		}, BlueprintException.class));
	}

	@Test(expected = DefaultConstructorOnlyException.class)
	public void testSaveInfokeThrowsExceptionWithDefaultConstructor() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				throw new IllegalArgumentException();
			}
		}, DefaultConstructorOnlyException.class));
	}

	@Test(expected = BlueprintException.class)
	public void testSaveInfokeThrowsExceptionWithNoMatchingConstructor() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				throw new IllegalArgumentException();
			}
		}, MoreParameterConstructorException.class));
	}

	@Test(expected = BlueprintException.class)
	public void testSaveInfokeThrowsExceptionWithNoMatchingConstructor2() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				throw new IllegalArgumentException();
			}
		}, StringConstructorException.class));
	}

	@Test(expected = BlueprintException.class)
	public void testSaveInfokeThrowsExpectedException() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				throw new BlueprintException();
			}
		}, BlueprintException.class));
	}

	@Test(expected = BlueprintException.class)
	public void testSaveInfokeThrowsOtherException() {
		Assert.assertEquals("quality-test", SafeInvoke.invoke(new ExceptionRunnable<String>() {

			@Override
			public String run() throws Exception {
				throw new IllegalArgumentException();
			}
		}, BlueprintException.class));
	}
}
