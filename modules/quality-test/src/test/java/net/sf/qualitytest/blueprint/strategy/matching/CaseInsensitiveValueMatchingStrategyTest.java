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
package net.sf.qualitytest.blueprint.strategy.matching;

import java.lang.reflect.Method;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.MatchingStrategy;

import org.junit.Assert;
import org.junit.Test;

public class CaseInsensitiveValueMatchingStrategyTest {

	public static class MyClass {
		private String email;

		public String getEmail() {
			return email;
		}

		public void setEmail(final String email) {
			this.email = email;
		}

	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testConstructWithNullThrows() {
		new CaseInsensitiveMethodNameMatchingStrategy(null);
	}

	@Test
	public void testMatchesByField_fieldExists() throws SecurityException, NoSuchFieldException {
		final MatchingStrategy s = new CaseInsensitiveMethodNameMatchingStrategy("hash");
		Assert.assertTrue(s.matchesByField(String.class.getDeclaredField("hash")));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testMatchesByField_fieldIsNull() throws SecurityException, NoSuchFieldException {
		new CaseInsensitiveMethodNameMatchingStrategy("myField").matchesByField(null);
	}

	@Test
	public void testMatchesByField_fieldNotExists() throws SecurityException, NoSuchFieldException {
		final MatchingStrategy s = new CaseInsensitiveMethodNameMatchingStrategy("id");
		Assert.assertFalse(s.matchesByField(String.class.getDeclaredField("hash")));
	}

	@Test
	public void testMatchesCaseInsensitveOk() throws SecurityException, NoSuchMethodException {
		final MatchingStrategy strategy = new CaseInsensitiveMethodNameMatchingStrategy("getEMail");
		Assert.assertTrue(strategy.matchesByMethod(MyClass.class.getDeclaredMethod("getEmail")));
	}

	@Test
	public void testMatchesCaseInsensitveOk_prefix() throws SecurityException, NoSuchMethodException {
		final MatchingStrategy strategy = new CaseInsensitiveMethodNameMatchingStrategy("EMail", "get");
		Assert.assertTrue(strategy.matchesByMethod(MyClass.class.getDeclaredMethod("getEmail")));
	}

	@Test
	public void testMatchesOk() throws SecurityException, NoSuchMethodException {
		final MatchingStrategy strategy = new CaseInsensitiveMethodNameMatchingStrategy("EMail");
		Assert.assertTrue(strategy.matchesByMethod(MyClass.class.getDeclaredMethod("setEmail", String.class)));
	}

	@Test
	public void testMatchesSetterOk() throws SecurityException, NoSuchMethodException {
		final MatchingStrategy strategy = new CaseInsensitiveMethodNameMatchingStrategy("email");
		Assert.assertTrue(strategy.matchesByMethod(MyClass.class.getDeclaredMethod("setEmail", String.class)));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void testMatchesWithNullThrows() {
		final MatchingStrategy strategy = new CaseInsensitiveMethodNameMatchingStrategy("EMail");
		strategy.matchesByMethod((Method) null);
	}

	@Test
	public void testNotEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMethodNameMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMethodNameMatchingStrategy("EMail12");

		Assert.assertNotEquals(strategy1, strategy2);
	}

	@Test
	public void testNotHashCodeEquals() {
		final MatchingStrategy strategy1 = new CaseInsensitiveMethodNameMatchingStrategy("EMail");
		final MatchingStrategy strategy2 = new CaseInsensitiveMethodNameMatchingStrategy("EMail12");

		Assert.assertNotEquals(strategy1.hashCode(), strategy2.hashCode());
	}

	@Test
	public void testNotMatchesType() {
		final MatchingStrategy strategy = new CaseInsensitiveMethodNameMatchingStrategy("EMail");
		Assert.assertFalse(strategy.matchesByType(String.class));
	}

}
