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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nonnull;

import net.sf.qualitytest.exception.CoverageForPrivateConstructorException;

/**
 * Code coverage often report missing line coverage if your utility classes contain private constructors. Still, it is
 * good practice to assure that utility classes cannot be constructed. This utility class will execute a private default
 * constructor in a class and therefore remove this noise from your code coverage reports.
 * 
 * @author Dominik Seichter
 * 
 */
public final class CoverageForPrivateConstructor {

	/**
	 * Reduce noise in code coverage reports by executing the private default constructor of a utility class.
	 * 
	 * @param clazz
	 *            The private default constructor of this class is executed.
	 */
	public static void giveMeCoverage(@Nonnull final Class<?> clazz) {
		// reduces only some noise in coverage report
		try {
			giveMeCoverageInternal(clazz);
		} catch (final SecurityException e) {
			throw new CoverageForPrivateConstructorException(e);
		} catch (final NoSuchMethodException e) {
			throw new CoverageForPrivateConstructorException(e);
		} catch (final IllegalArgumentException e) {
			throw new CoverageForPrivateConstructorException(e);
		} catch (final InstantiationException e) {
			throw new CoverageForPrivateConstructorException(e);
		} catch (final IllegalAccessException e) {
			throw new CoverageForPrivateConstructorException(e);
		} catch (final InvocationTargetException e) {
			throw new CoverageForPrivateConstructorException(e);
		}
	}

	/**
	 * Internal method which we can mock to simulate different exceptions.
	 * 
	 * @param clazz
	 * @throws NoSuchMethodException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 */
	protected static void giveMeCoverageInternal(@Nonnull final Class<?> clazz) throws NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		final Constructor<?> constructor = clazz.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
		constructor.setAccessible(false);
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private CoverageForPrivateConstructor() {
		// This class is not intended to create objects from it.
	}
}
