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

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.annotation.Nonnull;

import net.sf.qualitytest.exception.IllegalClassWithPublicDefaultConstructorException;
import net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodException;
import net.sf.qualitytest.exception.IllegalNonFinalClassException;
import net.sf.qualitytest.exception.IllegalNonFinalStaticException;

/**
 * This class offers simple static methods to test static properties of your classes.
 * 
 * These checks are typically only called from within unit tests, because they are costly. Static tests in unit tests
 * ensure that certain intended properties of a class (e.g. immutability, thread-safeness, no non static finals, etc.)
 * are still true after future changes.
 * 
 * @author Dominik Seichter
 */
public final class StaticCheck {

	/**
	 * Check if a class is final.
	 * 
	 * @param clazz
	 *            A class that must be final.
	 * @return clazz The class that was passed to this method.
	 * 
	 * @throws IllegalNonFinalClassException
	 *             If the passed class is not final.
	 */
	public static Class<?> classIsFinal(@Nonnull final Class<?> clazz) {
		final boolean isFinal = isModifierBitSet(clazz.getModifiers(), Modifier.FINAL);
		if (!isFinal) {
			throw new IllegalNonFinalClassException(clazz.getName());
		}

		return clazz;
	}

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
	 * Checks if a field is static and not final according to its modifiers. *
	 * 
	 * @param f
	 *            a java Field
	 * @return true if the field is static but is not final
	 */
	private static boolean isStaticAndNotFinal(final Field f) {
		final int modifiers = f.getModifiers();
		final boolean isStatic = isModifierBitSet(modifiers, Modifier.STATIC);
		final boolean isFinal = isModifierBitSet(modifiers, Modifier.FINAL);
		return isStatic && !isFinal;
	}

	/**
	 * Check if a class contains a non-final static variable. Non-final static fields are dangerous in multi-threaded
	 * environments and should therefore not be used.
	 * 
	 * This method only checks the passed class and not any super-classes.
	 * 
	 * @param clazz
	 *            A class which is checked for non-final statics.
	 * @return clazz The class that was passed to this method.
	 * 
	 * @throws IllegalNonFinalStaticException
	 *             If the passed class contains and non-final static field.
	 */
	public static Class<?> noNonFinalStatic(@Nonnull final Class<?> clazz) {
		final Field[] fields = clazz.getDeclaredFields();
		for (final Field f : fields) {
			if (!f.isSynthetic() && isStaticAndNotFinal(f)) {
				throw new IllegalNonFinalStaticException(clazz.getName(), f.getName());
			}
		}

		return clazz;
	}

	/**
	 * Check if a class or super-class contains a non-final static variable. Non-final static fields are dangerous in
	 * multi-threaded environments and should therefore not be used.
	 * 
	 * @param clazz
	 *            A class which is checked for non-final statics.
	 * @return clazz The class that was passed to this method.
	 * 
	 * @throws IllegalNonFinalStaticException
	 *             If the passed class contains and non-final static field.
	 */
	public static Class<?> noNonFinalStaticInHierarchy(@Nonnull final Class<?> clazz) {
		Class<?> obj = clazz;
		do {
			StaticCheck.noNonFinalStatic(obj);
			obj = obj.getSuperclass();
		} while (obj != null);

		return clazz;
	}

	/**
	 * Check that a class contains no public default constructor. It is recommended to hide the public default
	 * constructor of utility classes by providing a private default construct.
	 * 
	 * This method assures that the given class cannot be intantiated
	 * 
	 * @param clazz
	 *            A class which is checked for not having a public default constructor
	 * @return clazz The class that was passed to this method.
	 * 
	 * @throws IllegalClassWithPublicDefaultConstructorException
	 *             If the passed class contains a public default constructor.
	 */
	public static Class<?> noPublicDefaultConstructor(@Nonnull final Class<?> clazz) {
		try {
			clazz.newInstance();
			throw new IllegalClassWithPublicDefaultConstructorException(clazz.getName());
		} catch (final InstantiationException e) {
			throw new IllegalClassWithPublicDefaultConstructorException(clazz.getName(), e);
		} catch (final IllegalAccessException e) {
			// This the exception we want.
			return clazz;
		}
	}

	/**
	 * Check that all declared public methods of a class are annotated using a certain annotation.
	 * 
	 * @param clazz
	 *            A class that must have annotations on all public methods.
	 * @param annotation
	 *            An annotation that must be present on all public methods in a class
	 * @return The checked class.
	 */
	public static Class<?> publicMethodsAnnotated(@Nonnull final Class<?> clazz, @Nonnull final Class<? extends Annotation> annotation) {
		final Method[] methods = clazz.getDeclaredMethods();
		for (final Method m : methods) {
			if (!m.isAnnotationPresent(annotation)) {
				throw new IllegalMissingAnnotationOnMethodException(clazz, annotation, m);
			}
		}
		return clazz;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private StaticCheck() {
		// This class is not intended to create objects from it.
	}

}
