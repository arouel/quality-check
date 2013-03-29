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
package net.sf.qualitytest;

import net.sf.qualitytest.exception.IllegalClassWithPublicDefaultConstructorException;

import org.junit.Assert;
import org.junit.Test;

public class StaticCheckTest {

	private abstract class Useless {

	}

	public static class WithPublicConstructor {
		public WithPublicConstructor() {
		}
	}

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		CoverageForPrivateConstructor.giveMeCoverage(StaticCheck.class);
	}

	@Test(expected = IllegalClassWithPublicDefaultConstructorException.class)
	public void testInstantiationException() {
		StaticCheck.noPublicDefaultConstructor(Useless.class);
	}

	@Test(expected = IllegalClassWithPublicDefaultConstructorException.class)
	public void testInstantiationException2() {
		StaticCheck.noPublicDefaultConstructor(WithPublicConstructor.class);
	}

	@Test
	public void testValidatesThatClassCheckIsNotInstantiable() {
		// You cannot use StaticCheck to check itself, because it is able to call
		// its own private constructor.
		// StaticCheck.noPublicDefaultConstructor(StaticCheck.class);
		final Class<?> clazz = StaticCheck.class;
		try {
			clazz.newInstance();
			throw new IllegalClassWithPublicDefaultConstructorException(clazz.getName());
		} catch (final InstantiationException e) {
			throw new IllegalClassWithPublicDefaultConstructorException(clazz.getName(), e);
		} catch (final IllegalAccessException e) {
			// This the exception we want.
			Assert.assertTrue(true);
		}
	};

	@Test
	public void testValidatesThatClassIsFinal() {
		StaticCheck.classIsFinal(StaticCheck.class);
	}
}
