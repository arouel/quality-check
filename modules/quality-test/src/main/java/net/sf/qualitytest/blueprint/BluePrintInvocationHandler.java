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
 * Invocation handler which is used to BluePrint objects returned from dynamic interface proxies created during
 * blueprinting,
 * 
 * @author Dominik Seichter
 * 
 */
class BluePrintInvocationHandler implements InvocationHandler {

	private final BluePrintConfiguration config;

	public BluePrintInvocationHandler(final BluePrintConfiguration config) {
		this.config = Check.notNull(config, "config");
	}

	@Override
	public Object invoke(final Object instance, final Method method, final Object[] parameters) throws Throwable {
		return BluePrint.object(method.getReturnType(), config);
	}

}
