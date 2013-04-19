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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.qualitycheck.Check;

/**
 * Invocation handler which is used to blueprint objects returned from dynamic
 * interface proxies created during blueprinting.
 * 
 * @author Dominik Seichter
 */
class BlueprintInvocationHandler implements InvocationHandler {

	private final BlueprintConfiguration config;
	private final BlueprintSession session;

	/**
	 * Create a new {@link BlueprintInvocationHandler}
	 * 
	 * @param config
	 *            A {@link BlueprintConfiguration}
	 * @param session
	 *            A {@link BlueprintSession}
	 */
	public BlueprintInvocationHandler(final BlueprintConfiguration config,
			final BlueprintSession session) {
		this.config = Check.notNull(config, "config");
		this.session = Check.notNull(session, "session");
	}

	@Override
	public Object invoke(final Object instance, final Method method,
			final Object[] parameters) throws Throwable { // NOSONAR
		return Blueprint.construct(method.getReturnType(), config, session);
	}

}
