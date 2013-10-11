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
package net.sf.qualitytest.blueprint.invocationhandler;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintSession;

/**
 * Invocation handler which is used to blueprint objects returned from dynamic interface proxies created during
 * blueprinting.
 * 
 * This invocation handler tries first to find a {@code CreationStrategy} with a {@code MatchingStrategy} for the
 * particular method. If this is found. The {@code CreationStrategy} is used to create the return value. Otherwise
 * {@code Blueprint} is called to create a blueprint for the methods return-value.
 * 
 * Values once created are stored in a cache and then reused.
 * 
 * @author Dominik Seichter
 */
public final class CachedBlueprintInvocationHandler extends BlueprintProxyInvocationHandler {

	private final Map<Method, Object> returnValueCache = new ConcurrentHashMap<Method, Object>();

	/**
	 * Actually create a new value using the internal cache.
	 * 
	 * @param method
	 *            Method for which a return value must be created
	 * @return the created value
	 */
	private Object createNewValueUsingCache(@Nonnull final BlueprintConfiguration config, @Nonnull final BlueprintSession session,
			@Nonnull final Method method) {
		final Object result;
		final boolean isCached = returnValueCache.containsKey(method);
		if (!isCached) {
			result = createNewValue(config, session, method);
			returnValueCache.put(method, result);
		} else {
			result = returnValueCache.get(method);
		}
		return result;
	}

	@Override
	public Object invoke(@Nonnull final BlueprintConfiguration config, @Nonnull final BlueprintSession session,
			@Nonnull final Object instance, @Nonnull final Method method, @Nullable final Object[] parameters) throws Throwable { // NOSONAR
		return createNewValueUsingCache(config, session, method);
	}
}
