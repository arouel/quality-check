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

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.mutabilitydetector.AnalysisResult;
import org.mutabilitydetector.AnalysisSession;
import org.mutabilitydetector.Configuration;
import org.mutabilitydetector.ThreadUnsafeAnalysisSession;
import org.mutabilitydetector.IsImmutable;
import org.mutabilitydetector.locations.Dotted;

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

	@Throws(IllegalNullArgumentException.class)
	@ArgumentsChecked
	public static Class<?> classIsFinal(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		final boolean isFinal = isModifierBitSet(clazz.getModifiers(), Modifier.FINAL);
		if (!isFinal) {
			// throw new IllegalNonFinalClassException();
			throw new IllegalArgumentException();
		}

		return clazz;
	}

	@Throws(IllegalNullArgumentException.class)
	@ArgumentsChecked
	public static Class<?> noNonStaticFinal(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		final Field[] fields = clazz.getFields();
		for (Field f : fields) {
			if (!f.isSynthetic() && isStaticAndNotFinal(f)) {
				// throw new IllegalNonStaticFinalException();
				throw new IllegalArgumentException();
			}
		}

		return clazz;
	}

	@ArgumentsChecked
	@Costly
	@Throws(IllegalNullArgumentException.class)
	public static Class<?> isImmutable(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz);
		StaticCheck.classIsFinal(clazz);
		
		final Configuration configuration = Configuration.JDK; // TODO: Create configuration from passed classes
		final AnalysisSession analysisSession = ThreadUnsafeAnalysisSession.createWithCurrentClassPath(configuration);
		final Dotted dottedClassName = Dotted.fromClass(clazz);
		final org.mutabilitydetector.AnalysisSession.RequestedAnalysis requested = analysisSession.resultFor(dottedClassName);
		final AnalysisResult result = requested.result;
		
        if (!result.isImmutable.equals(IsImmutable.IMMUTABLE)) {
        	throw new IllegalArgumentException();
        }
        
		return clazz;
	}

	public static Class<?> isSingleton(@Nonnull final Class<?> clazz) {
		return clazz;
	}

	public static Class<?> areArgumentsChecked(@Nonnull final Class<?> clazz) {
		return clazz;
	}

	/**
	 * Checks if a field is static and not final according to its modifiers.
*	 * 
	 * @param f
	 *            a java Field
	 * @return true if the field is static but is not final
	 */
	private static boolean isStaticAndNotFinal(Field f) {
		final int modifiers = f.getModifiers();
		final boolean isStatic = isModifierBitSet(modifiers, Modifier.STATIC);
		final boolean isFinal = isModifierBitSet(modifiers, Modifier.FINAL);
		return isStatic && !isFinal;
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
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private StaticCheck() {
		// This class is not intended to create objects from it.
	}

}
