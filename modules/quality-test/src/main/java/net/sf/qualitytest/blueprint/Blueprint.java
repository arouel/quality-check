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
package net.sf.qualitytest.blueprint;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.text.MessageFormat;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.ModifierBits;
import net.sf.qualitytest.blueprint.configuration.DefaultBlueprintConfiguration;
import net.sf.qualitytest.blueprint.configuration.RandomBlueprintConfiguration;
import net.sf.qualitytest.blueprint.invocationhandler.ProxyInvocationHandler;
import net.sf.qualitytest.blueprint.invocationhandler.RefreshingBlueprintInvocationHandler;
import net.sf.qualitytest.exception.BlueprintException;
import net.sf.qualitytest.exception.NoPublicConstructorException;

/**
 * Blueprinting is a technique that makes writing test easier. For unit-testing you often need data-objects, where the
 * actual content of the objects does not matter. {@code Blueprint} creates data-objects filled with random or defined
 * data automatically based on the "Blue-Print" which is the Class itself.
 * <p>
 * Blueprinting makes tests more maintainable as they depend less on test-data. Imagine, you add a new required
 * attribute to a class. Usually, you have to add this to all tests using this class. With blueprinting you just have to
 * add it to certain tests where the contents of the logic does actually matter. Most of the time the randomly generated
 * value by {@code Blueprint} is just fine.
 * <p>
 * {@code Blueprint} is similar to C#'s AutoFixture (https://github.com/AutoFixture/AutoFixture#readme).
 * <p>
 * A simple example:
 * 
 * <pre>
 * final BlueprintConfiguration config = new RandomBlueprintConfiguration().with(&quot;email&quot;, &quot;mail@example.com&quot;);
 * final User user = Blueprint.construct(User.class, config);
 * </pre>
 * 
 * or simpler
 * 
 * <pre>
 * final User user = Blueprint.random().with(&quot;email&quot;, &quot;mail@example.com&quot;).construct(User.class);
 * </pre>
 * 
 * {@code Blueprint} offers two custom configurations. A {@code DefaultBlueprintConfiguration} which fills any object
 * using default, empty or 0 values. The second configuration, {@code RandomBlueprintConfiguration} will always generate
 * a random value. Both fill child objects using a deep-tree-search.
 * <p>
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
	private static <T> T bean(@Nonnull final Class<T> clazz, @Nonnull final BlueprintConfiguration config,
			@Nonnull final BlueprintSession session) {
		Check.notNull(clazz, "clazz");
		Check.notNull(config, "config");
		Check.notNull(session, "sesion");

		final T obj = safeNewInstance(session, clazz);
		blueprintPublicMethods(obj, clazz, config, session);
		blueprintPublicAttributes(obj, clazz, config, session);
		return obj;
	}

	/**
	 * Blueprint all attributes of an object.
	 * <p>
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
	private static <T> void blueprintAllAttributes(final T obj, final Class<T> clazz, final BlueprintConfiguration config,
			final BlueprintSession session) {
		for (final Field f : clazz.getDeclaredFields()) {
			final boolean isStatic = ModifierBits.isModifierBitSet(f.getModifiers(), Modifier.STATIC);
			if (!isStatic) {
				blueprintField(obj, f, config, session);
			}
		}
	}

	/**
	 * Blueprint a field.
	 * 
	 * @param that
	 *            Instance of the object
	 * @param field
	 *            Accessible field
	 * @param config
	 *            configuration to use.
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static void blueprintField(final Object that, final Field field, final BlueprintConfiguration config,
			final BlueprintSession session) {
		CreationStrategy<?> creator = config.findCreationStrategyForField(field);
		if (creator == null) {
			creator = config.findCreationStrategyForType(field.getType());
		}
		final Object value = blueprintObject(field.getType(), config, creator, session);

		final String action = MessageFormat.format("Setting field {0} to {1}.", field.getName(), value);
		SafeInvoke.invoke(new BlueprintExceptionRunnable<Object>(session, action) {
			@Override
			public Object runInternal() throws Exception {
				field.setAccessible(true);
				field.set(that, value);
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
	 *            setter method
	 * @param config
	 *            configuration to use.
	 * @param session
	 *            A {@code BlueprintSession}
	 */
	private static void blueprintMethod(final Object that, final Method m, final BlueprintConfiguration config,
			final BlueprintSession session) {
		final CreationStrategy<?> creator = config.findCreationStrategyForMethod(m);
		if (creator != null) {
			final Class<?>[] parameterTypes = m.getParameterTypes();
			final Object[] values = new Object[parameterTypes.length];
			for (int i = 0; i < parameterTypes.length; i++) {
				values[i] = creator.createValue(parameterTypes[i], config, session);
			}

			final String action = MessageFormat.format("Invoking method {0} with arguments {1}.", m.getName(), values);
			SafeInvoke.invoke(new BlueprintExceptionRunnable<Object>(session, action) {
				@Override
				public Object runInternal() throws Exception {
					m.setAccessible(true);
					m.invoke(that, values);
					return null;
				}

			}, BlueprintException.class);
		}
	}

	@Nullable
	@SuppressWarnings({ "unchecked" })
	private static <T> T blueprintObject(@Nonnull final Class<T> clazz, @Nonnull final BlueprintConfiguration config,
			@Nullable final CreationStrategy<?> creator, @Nonnull final BlueprintSession session) {
		final boolean cycle = session.push(clazz);
		final T ret;
		if (cycle) {
			ret = (T) config.handleCycle(session, clazz);
		} else {
			if (creator != null) {
				ret = (T) creator.createValue(clazz, config, session);
			} else if (clazz.isInterface()) {
				ret = (T) proxy(clazz, config, session);
			} else if (hasPublicDefaultConstructor(clazz)) {
				ret = bean(clazz, config, session);
			} else {
				ret = immutable(clazz, config, session);
			}
		}
		session.pop();
		return ret;
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
	private static <T> void blueprintPublicAttributes(final T obj, final Class<T> clazz, final BlueprintConfiguration config,
			final BlueprintSession session) {
		if (!config.isWithPublicAttributes()) {
			return;
		}

		for (final Field f : clazz.getFields()) {
			final boolean isStatic = ModifierBits.isModifierBitSet(f.getModifiers(), Modifier.STATIC);
			final boolean isFinal = ModifierBits.isModifierBitSet(f.getModifiers(), Modifier.FINAL);
			if (!isStatic && !isFinal) {
				blueprintField(obj, f, config, session);
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
	private static <T> void blueprintPublicMethods(final T obj, final Class<T> clazz, final BlueprintConfiguration config,
			final BlueprintSession session) {
		for (final Method m : clazz.getMethods()) {
			if (isRelevant(m)) {
				blueprintMethod(obj, m, config, session);
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
	@Nullable
	@Throws(IllegalNullArgumentException.class)
	public static <T> T construct(@Nonnull final Class<T> clazz) {
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
	@Nullable
	@Throws(IllegalNullArgumentException.class)
	public static <T> T construct(@Nonnull final Class<T> clazz, @Nonnull final BlueprintConfiguration config) {
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
	@Nullable
	@Throws(IllegalNullArgumentException.class)
	public static <T> T construct(@Nonnull final Class<T> clazz, @Nonnull final BlueprintConfiguration config,
			@Nonnull final BlueprintSession session) {
		Check.notNull(clazz, "clazz");
		Check.notNull(config, "config");
		Check.notNull(session, "session");

		final CreationStrategy<?> creator = config.findCreationStrategyForType(clazz);
		return blueprintObject(clazz, config, creator, session);
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
			return c;
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
			if (c.getParameterTypes().length == 0) {
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
			final BlueprintException b = new NoPublicConstructorException(clazz.getSimpleName());
			final String action = MessageFormat.format("Finding public constructor in {0}", clazz.getName());
			session.setLastAction(action);
			b.setSession(session);
			throw b;
		}

		final Class<?>[] parameterTypes = constructor.getParameterTypes();
		final Object[] parameters = new Object[parameterTypes.length];
		for (int i = 0; i < parameterTypes.length; i++) {
			parameters[i] = construct(parameterTypes[i], config, session);
		}

		@SuppressWarnings("unchecked")
		final T obj = (T) safeNewInstance(session, constructor, parameters);
		blueprintAllAttributes(obj, clazz, config, session);

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
		ProxyInvocationHandler invocationHandler = config.findInvocationHandlerForClass(iface);
		if (invocationHandler == null) {
			invocationHandler = new RefreshingBlueprintInvocationHandler();
		}

		final InvocationHandler handler = new BlueprintInvocationHandler(config, session, invocationHandler);
		return (T) Proxy.newProxyInstance(iface.getClassLoader(), new Class[] { iface }, handler);
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
	private static <T> T safeNewInstance(final BlueprintSession session, final Class<T> clazz) {
		final String action = MessageFormat.format("Creating clazz {0} with default constructor.", clazz.getName());
		return SafeInvoke.invoke(new BlueprintExceptionRunnable<T>(session, action) {

			@Override
			public T runInternal() throws Exception {
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
	private static <T> T safeNewInstance(final BlueprintSession session, final Constructor<?> constructor, final Object[] parameters) {
		final String action = MessageFormat.format("Creating clazz {0} with constructor {1}.", constructor.getClass().getName(),
				constructor.toGenericString());
		return SafeInvoke.invoke(new BlueprintExceptionRunnable<T>(session, action) {

			@Override
			public T runInternal() throws Exception {
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
