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

import javax.annotation.Nonnull;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNotNullArgumentException;
import net.sf.qualitytest.blueprint.invocationhandler.CachedBlueprintInvocationHandler;
import net.sf.qualitytest.blueprint.invocationhandler.ProxyInvocationHandler;
import net.sf.qualitytest.blueprint.invocationhandler.RefreshingBlueprintInvocationHandler;
import net.sf.qualitytest.blueprint.strategy.creation.NullValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomBooleanValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomByteValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomCharValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomDoubleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomEnumCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomFloatValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomIntValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomLongValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.RandomShortValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;

/**
 * A utility class to have a more readable API. This class holds possible creation strategies.
 * 
 * @author Dominik Seichter
 * 
 */
public final class Create {

	/**
	 * Create a proxy implementation for any interface which caches generated method return values and returns that way
	 * always the same value for a certain method call.
	 * 
	 * @return {@code ProxyInvocationHandler} which creates cached proxies.
	 */
	public static ProxyInvocationHandler cachedProxy() {
		return new CachedBlueprintInvocationHandler();
	}

	/**
	 * Always create {@code null}.
	 * 
	 * @return {@code CreationStrategy} which always returns {@code null}.
	 */
	public static <T> CreationStrategy<T> nil() {
		return new NullValueCreationStrategy<T>();
	}

	/**
	 * Create a random boolean (true or false).
	 * 
	 * @return {@code CreationStrategy} which always returns a random boolean.
	 */
	public static CreationStrategy<Boolean> randomBoolean() {
		return new RandomBooleanValueCreationStrategy();
	}

	/**
	 * Create a random byte.
	 * 
	 * @return {@code CreationStrategy} which always returns a random byte.
	 */
	public static CreationStrategy<Byte> randomByte() {
		return new RandomByteValueCreationStrategy();
	}

	/**
	 * Create a random character.
	 * 
	 * @return {@code CreationStrategy} which always returns a random character.
	 */
	public static CreationStrategy<Character> randomChar() {
		return new RandomCharValueCreationStrategy();
	}

	/**
	 * Create a random double.
	 * 
	 * @return {@code CreationStrategy} which always returns a random double.
	 */
	public static CreationStrategy<Double> randomDouble() {
		return new RandomDoubleValueCreationStrategy();
	}

	/**
	 * Create a random enum constant whenever an enumeration is requested.
	 * 
	 * @return {@code CreationStrategy} which always returns a random enum constact.
	 */
	public static CreationStrategy<Enum<?>> randomEnum() {
		return new RandomEnumCreationStrategy();
	}

	/**
	 * Create a random float.
	 * 
	 * @return {@code CreationStrategy} which always returns a random float.
	 */
	public static CreationStrategy<Float> randomFloat() {
		return new RandomFloatValueCreationStrategy();
	}

	/**
	 * Create a random integer.
	 * 
	 * @return {@code CreationStrategy} which always returns a random integer.
	 */
	public static CreationStrategy<Integer> randomInteger() {
		return new RandomIntValueCreationStrategy();
	}

	/**
	 * Create a random long.
	 * 
	 * @return {@code CreationStrategy} which always returns a random long.
	 */
	public static CreationStrategy<Long> randomLong() {
		return new RandomLongValueCreationStrategy();
	}

	/**
	 * Create a random short.
	 * 
	 * @return {@code CreationStrategy} which always returns a random short.
	 */
	public static CreationStrategy<Short> randomShort() {
		return new RandomShortValueCreationStrategy();
	}

	/**
	 * Create a proxy implementation for any interface which refreshes generated method return values when possible.
	 * 
	 * @return {@code ProxyInvocationHandler} which creates refreshing proxies.
	 */
	public static ProxyInvocationHandler refreshingProxy() {
		return new RefreshingBlueprintInvocationHandler();
	}

	/**
	 * Always create this value, when requested.
	 * 
	 * @param value
	 *            a value
	 * @return {@code CreationStrategy} which always returns value.
	 */
	@ArgumentsChecked
	@Throws(IllegalNotNullArgumentException.class)
	public static <T> CreationStrategy<T> value(@Nonnull final T value) {
		return new SingleValueCreationStrategy<T>(Check.notNull(value, "value"));
	}

	private Create() {
		// Do not create instances of this class
	}
}
