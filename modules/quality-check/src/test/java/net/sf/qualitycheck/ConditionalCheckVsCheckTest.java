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
package net.sf.qualitycheck;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

/**
 * This test compares the implemented checks in the class {@code Check} and in the class {@code ConditionCheck} and
 * assures that every check in one class is also present in the other class.
 * 
 * @author dominik.seichter
 * 
 */
public class ConditionalCheckVsCheckTest {

	/**
	 * Tests if a certain modifier is set into a bitmask.
	 * 
	 * @param modifiers
	 *            bitmask of modifiers
	 * @param modifier
	 *            bit of the modifier to be queried
	 * @return true if the bit is set
	 */
	private static boolean isModifierBitSet(final int modifiers, final int modifier) {
		return (modifiers & modifier) == modifier;
	}

	/**
	 * Creates a string list of methods names and the number of parameters appended. This is similar to C++ name
	 * mangling and is a smart guess on comparing methods without having to compare each parameter.
	 * 
	 * @param methods
	 *            list of methods
	 * @param additionalParameterCount
	 *            this is added to the parameter count of each method
	 * @return list of names in the form <methodName>_<methodParameterCount + additionalParameterCount>
	 */
	private List<String> extractNamesAndParemeterCount(final List<Method> methods, final int additionalParameterCount) {
		final List<String> names = new ArrayList<String>();

		for (final Method m : methods) {
			names.add(m.getName() + "_" + (m.getGenericParameterTypes().length + additionalParameterCount));
		}

		return names;
	}

	private final List<Method> retrievePublicStaticMethods(final Class<?> clazz) {
		final List<String> ignoreList = new ArrayList<String>();
		ignoreList.add("nothing");

		final List<Method> publicMethods = new ArrayList<Method>();
		final Method[] methods = clazz.getMethods();
		for (final Method m : methods) {
			if (isModifierBitSet(m.getModifiers(), Modifier.STATIC) && isModifierBitSet(m.getModifiers(), Modifier.PUBLIC)) {
				if (!ignoreList.contains(m.getName())) {
					publicMethods.add(m);
				}
			}
		}

		return publicMethods;
	}

	@Test
	public void testMakeSureChecksAreImplementedInBothClasses() {
		final List<String> publicMethodsCheck = extractNamesAndParemeterCount(retrievePublicStaticMethods(Check.class), 1);
		final List<String> publicMethodsConditionalCheck = extractNamesAndParemeterCount(
				retrievePublicStaticMethods(ConditionalCheck.class), 0);

		final Set<String> notPresentInConditionalCheck = new HashSet<String>();
		for (final String name : publicMethodsCheck) {
			if (!publicMethodsConditionalCheck.contains(name)) {
				notPresentInConditionalCheck.add(name);
				System.out.println("> Missing in ConditionalCheck: " + name);
			}
		}

		final Set<String> notPresentInCheck = new HashSet<String>();
		for (final String name : publicMethodsConditionalCheck) {
			if (!publicMethodsCheck.contains(name)) {
				notPresentInCheck.add(name);
				System.out.println("> Missing in Check: " + name);
			}
		}

		Assert.assertEquals(0, notPresentInCheck.size());
		Assert.assertEquals(0, notPresentInConditionalCheck.size());
	}
}
