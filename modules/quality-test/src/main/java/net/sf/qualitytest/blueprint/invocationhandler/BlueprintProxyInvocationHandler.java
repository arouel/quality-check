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

import javax.annotation.Nonnull;

import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CreationStrategy;

/**
 * Invocation handler which is used to blueprint objects returned from dynamic interface proxies created during
 * blueprinting.
 * 
 * This invocation handler tries first to find a {@code CreationStrategy} with a {@code MatchingStrategy} for the
 * particular method. If this is found. The {@code CreationStrategy} is used to create the return value. Otherwise
 * {@code Blueprint} is called to create a blueprint for the methods return-value.
 * 
 * @author Dominik Seichter
 */
abstract class BlueprintProxyInvocationHandler implements ProxyInvocationHandler {

	/**
	 * Actually create a new value without using the internal cache.
	 * 
	 * @param config
	 *            Current configuration
	 * @param session
	 *            Current session
	 * @param method
	 *            Method for which a return value must be created
	 * @return the created value
	 */
	protected Object createNewValue(@Nonnull final BlueprintConfiguration config, @Nonnull final BlueprintSession session,
			@Nonnull final Method method) {
		final Object result;
		final CreationStrategy<?> creator = config.findCreationStrategyForMethod(method);

		if (creator != null) {
			result = creator.createValue(method.getReturnType(), config, session);
		} else {
			result = Blueprint.construct(method.getReturnType(), config, session);
		}
		return result;
	}

}
