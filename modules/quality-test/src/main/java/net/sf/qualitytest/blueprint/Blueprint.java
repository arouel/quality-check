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

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.ModifierBits;
import net.sf.qualitytest.blueprint.SafeInvoke.ExceptionRunnable;
import net.sf.qualitytest.blueprint.configuration.DefaultBlueprintConfiguration;
import net.sf.qualitytest.blueprint.configuration.RandomBlueprintConfiguration;
import net.sf.qualitytest.exception.BlueprintException;

/**
 * Blueprinting is a technique that makes writing test easier. For unit-testing you often need data-objects, where the
 * actual content of the objects does not matter. {@code Blueprint} creates data-objects filled with random or defined
 * data automatically based on the "Blue-Print" which is the Class itself.
 * 
 * Blueprinting makes tests more maintainable as they depend less on test-data. Imagine, you add a new required
 * attribute to a class. Usually, you have to add this to all tests using this class. With blueprinting you just have to
 * add it to certain tests where the contents of the logic does actually matter. Most of the time the randomly generated
 * value by {@code Blueprint} is just fine.
 * 
 * {@code Blueprint} is similar to C#'s AutoFixture (https://github.com/AutoFixture/AutoFixture#readme).
 * 
 * A simple example:
 * 
 * <code>
 * 	final BlueprintConfiguration config = new RandomBlueprintConfiguration().with("email", "mail@example.com");
 *  final User user = Blueprint.construct(User.class, config);
 * </code>
 * 
 * or simpler
 * 
 * <code>
 *  final User user = Blueprint.random().with("email", "mail@example.com").construct(User.class);
 * </code>
 * 
 * {@code Blueprint} offers to custome configurations. A {@code DefaultBlueprintConfiguration} which fills any object
 * using default, empty or 0 values. The second configuration, {@code RandomBlueprintConfiguration} will always generate
 * a random value. Both fill child objects using a deep-tree-search.
 * 
 * Utilities for collections can be found in {@code CollectionBlueprint}.
 * 
 * @see DefaultBlueprintConfiguration
 * @see RandomBlueprintConfiguration
 * 
 * @author Dominik Seichter
 */
public final class Blueprint {

	private static final BlueprintConfiguration DEFAULT_CONFIG = new DefaultBlueprintConfiguration();

	/**
	 * Blueprint a Java-Bean.
	 * 
	 * This method will call the default constructor and fill all setters using blueprints.
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class, which must have a default constructor.
	 * @param config
	 *            a BlueprintConfiguration
	 * @param session
	 *            A {@code BlueprintSession}
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	private static <T> T bean(final Class<T> clazz, final BlueprintConfiguration config, final BlueprintSession session) {
		Check.notNull(clazz, "clazz");
		Check.notNull(config, "config");
		Check.notNull(session, "sesion");

		final T obj = safeNewInstance(clazz);
		bluePrintPublicMethods(obj, clazz, config, session);
		bluePrintPublicAttributes(obj, clazz, config, session);
		return obj;
	}

	/**
	 * Blueprint a field.
	 * 
	 * @param that
	 *            Instance of the object
	 * @param f
	 *            Accessible Field
	 * @param config
	 *            configuration to use.
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static void bluePrintField(final Object that, final Field f, final BlueprintConfiguration config, final BlueprintSession session) {
		final CreationStrategy<?> creator = config.findCreationStrategyForType(f.getType());
		final Object value = blueprintObject(f.getType(), config, creator, session);

		SafeInvoke.invoke(new ExceptionRunnable<Object>() {
			@Override
			public Object run() throws Exception {
				f.set(that, value);
				return null;
			}
		}, BlueprintException.class);

	}

	/**
	 * Blueprint a method.
	 * 
	 * @param that
	 *            Instance of the object
	 * @param m
	 *            Setter-Method
	 * @param config
	 *            configuration to use.
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static void bluePrintMethod(final Object that, final Method m, final BlueprintConfiguration config,
			final BlueprintSession session) {
		final CreationStrategy<?> creator = config.findCreationStrategyForMethod(m);
		if (creator != null) {
			final Class<?>[] parameterTypes = m.getParameterTypes();
			final Object[] values = new Object[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				values[i] = creator.createValue(parameterTypes[i], config, session);
			}

			SafeInvoke.invoke(new ExceptionRunnable<Object>() {

				@Override
				public Object run() throws Exception {
					m.invoke(that, values);
					return null;
				}
			}, BlueprintException.class);
		}
	}

	@SuppressWarnings({ "unchecked" })
	private static <T> T blueprintObject(final Class<T> clazz, final BlueprintConfiguration config, final CreationStrategy<?> creator,
			final BlueprintSession session) {
		if (creator != null) {
			return (T) creator.createValue(clazz, config, session);
		} else if (clazz.isInterface()) {
			return (T) proxy(clazz, config, session);
		} else if (hasPublicDefaultConstructor(clazz)) {
			return bean(clazz, config, session);
		} else {
			return immutable(clazz, config, session);
		}
	}

	/**
	 * Blueprint all public attributes in an object.
	 * 
	 * Does nothing if {@code config.isWithPublicAttributes} is false.
	 * 
	 * Static fields are ignored.
	 * 
	 * @param <T>
	 *            type of object
	 * @param obj
	 *            Instance of the object
	 * @param clazz
	 *            Class of the object
	 * @param config
	 *            Configuration to apply
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static <T> void bluePrintPublicAttributes(final T obj, final Class<T> clazz, final BlueprintConfiguration config,
			final BlueprintSession session) {
		if (!config.isWithPublicAttributes()) {
			return;
		}

		for (final Field f : clazz.getFields()) {
			final boolean isStatic = ModifierBits.isModifierBitSet(f.getModifiers(), Modifier.STATIC);
			final boolean isFinal = ModifierBits.isModifierBitSet(f.getModifiers(), Modifier.FINAL);
			if (!isStatic && !isFinal) {
				bluePrintField(obj, f, config, session);
			}
		}
	}

	/**
	 * Blueprint all non static public method in an object.
	 * 
	 * @param <T>
	 *            type of object
	 * @param obj
	 *            Instance of the object
	 * @param clazz
	 *            Class of the object
	 * @param config
	 *            Configuration to apply
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static <T> void bluePrintPublicMethods(final T obj, final Class<T> clazz, final BlueprintConfiguration config,
			final BlueprintSession session) {
		for (final Method m : clazz.getMethods()) {
			if (isRelevant(m)) {
				bluePrintMethod(obj, m, config, session);
			}
		}
	}

	/**
	 * Construct a Java-Object using a class as a blueprint.
	 * 
	 * If the object has a default constructor, it will be called and all setters will be called. If the object does not
	 * have a default constructor the first constructor is called and filled with all parameters. Afterwards all setters
	 * will be called.
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T construct(final Class<T> clazz) {
		Check.notNull(clazz, "clazz");

		return Blueprint.construct(clazz, DEFAULT_CONFIG, new BlueprintSession());
	}

	/**
	 * Construct a Java-Object using a class as a blueprint.
	 * 
	 * If the object has a default constructor, it will be called and all setters will be called. If the object does not
	 * have a default constructor the first constructor is called and filled with all parameters. Afterwards all setters
	 * will be called.
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class
	 * @param config
	 *            a {@code BlueprintConfiguration}
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T construct(final Class<T> clazz, final BlueprintConfiguration config) {
		Check.notNull(clazz, "clazz");
		Check.notNull(config, "config");

		return Blueprint.construct(clazz, config, new BlueprintSession());
	}

	/**
	 * Construct a Java-Object using a class as a blueprint.
	 * 
	 * If the object has a default constructor, it will be called and all setters will be called. If the object does not
	 * have a default constructor the first constructor is called and filled with all parameters. Afterwards all setters
	 * will be called.
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class
	 * @param config
	 *            a {@code BlueprintConfiguration}
	 * @param session
	 *            a {@code BlueprintSession}
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> T construct(final Class<T> clazz, final BlueprintConfiguration config, final BlueprintSession session) {
		Check.notNull(clazz, "clazz");
		Check.notNull(config, "config");

		final CreationStrategy<?> creator = config.findCreationStrategyForType(clazz);

		session.push(clazz);
		final T ret = blueprintObject(clazz, config, creator, session);
		session.pop();
		return ret;
	}

	/**
	 * Return a new configuration for default blueprinting with zero or empty default values.
	 * 
	 * @return a new {@code DefaultBlueprintConfiguration}
	 */
	public static BlueprintConfiguration def() {
		return new DefaultBlueprintConfiguration();
	}

	/**
	 * Find the first public constructor in a class.
	 * 
	 * @param <T>
	 *            type parameter of the class and constructor.
	 * @param clazz
	 *            the class object
	 */
	private static <T> Constructor<?> findFirstPublicConstructor(final Class<T> clazz) {
		final Constructor<?>[] constructors = clazz.getConstructors();
		for (final Constructor<?> c : constructors) {
			final boolean isPublic = ModifierBits.isModifierBitSet(c.getModifiers(), Modifier.PUBLIC);
			if (isPublic) {
				return c;
			}
		}

		return null;
	}

	/**
	 * Test if a class has a public default constructor (i.e. a public constructor without constructor arguments).
	 * 
	 * @param clazz
	 *            the class object.
	 * @return true if the class has a public default constructor.
	 */
	private static boolean hasPublicDefaultConstructor(final Class<?> clazz) {
		final Constructor<?>[] constructors = clazz.getConstructors();
		for (final Constructor<?> c : constructors) {
			final boolean isPublic = ModifierBits.isModifierBitSet(c.getModifiers(), Modifier.PUBLIC);
			if (isPublic && c.getParameterTypes().length == 0) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Blueprint an immutable class based on the constructor parameters of the first public constructor.
	 * 
	 * @param <T>
	 *            type parameter of the blueprinted class
	 * @param clazz
	 *            the class object
	 * @param config
	 *            the configuration
	 * @param session
	 *            A {@code BlueprintSession}
	 * @return a new blueprint of the class with all constructor parameters to filled.
	 */
	private static <T> T immutable(final Class<T> clazz, final BlueprintConfiguration config, final BlueprintSession session) {
		final Constructor<?> constructor = findFirstPublicConstructor(clazz);
		if (constructor == null) {
			throw new BlueprintException("No public constructor found.");
		}

		final Class<?>[] parameterTypes = constructor.getParameterTypes();
		final Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameters[i] = construct(parameterTypes[i], config, session);
		}

		@SuppressWarnings("unchecked")
		final T obj = (T) safeNewInstance(constructor, parameters);
		bluePrintPublicMethods(obj, clazz, config, session);
		bluePrintPublicAttributes(obj, clazz, config, session);

		return obj;
	}

	/**
	 * Check if a method is setter according to the java
	 * 
	 * @param m
	 *            a method
	 * @return true if this is a setter method
	 */
	protected static boolean isRelevant(final Method m) {
		final boolean isNotStatic = !ModifierBits.isModifierBitSet(m.getModifiers(), Modifier.STATIC);
		final boolean isPublic = ModifierBits.isModifierBitSet(m.getModifiers(), Modifier.PUBLIC);
		return isNotStatic && isPublic;
	}

	/**
	 * Create a proxy for an interface, which does nothing.
	 * 
	 * @param <T>
	 *            Class of the interface
	 * @param iface
	 *            an interace
	 * @param config
	 *            {@code BlueprintConfiguration}
	 * @param session
	 *            {@code BlueprintSession}
	 * @return a new dynamic proxy
	 */
	@SuppressWarnings("unchecked")
	private static <T> T proxy(final Class<T> iface, final BlueprintConfiguration config, final BlueprintSession session) {
		return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class[] { iface }, new BlueprintInvocationHandler(config, session));
	}

	/**
	 * Return a new configuration for random blueprinting.
	 * 
	 * @return a new {@code RandomBlueprintConfiguration}
	 */
	public static BlueprintConfiguration random() {
		return new RandomBlueprintConfiguration();
	}

	/**
	 * Create a new instance of a class without having to care about the checked exceptions. The class must have an
	 * accessible default constructor.
	 * 
	 * @param <T>
	 *            type parameter of the class to create
	 * @param clazz
	 *            class-object
	 * @return a new instance of the class
	 * 
	 * @throws BlueprintException
	 *             in case of any error
	 */
	private static <T> T safeNewInstance(final Class<T> clazz) {
		return SafeInvoke.invoke(new ExceptionRunnable<T>() {

			@Override
			public T run() throws Exception {
				return (T) clazz.newInstance();
			}
		}, BlueprintException.class);
	}

	/**
	 * Create a new instance of a class without having to care about the checked exceptions using a given constructor.
	 * 
	 * @param constructor
	 *            constructor to call
	 * @param parameters
	 *            constructor arguments
	 * @throws BlueprintException
	 *             in case of any error
	 */
	@SuppressWarnings("unchecked")
	private static <T> T safeNewInstance(final Constructor<?> constructor, final Object[] parameters) {
		return SafeInvoke.invoke(new ExceptionRunnable<T>() {

			@Override
			public T run() throws Exception {
				return (T) constructor.newInstance(parameters);
			}
		}, BlueprintException.class);
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private Blueprint() {
		// This class is not intended to create objects from it.
	}
}
