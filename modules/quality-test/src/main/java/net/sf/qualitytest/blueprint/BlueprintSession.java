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
package net.sf.qualitytest.blueprint;

import java.util.Stack;

import net.sf.qualitytest.exception.BlueprintCycleException;

/**
 * A {@code BlueprintSession} holds information acquired while doing a blueprint
 * of a class. This includes cycle detection as well as statistical information.
 * 
 * @author Dominik Seichter
 * 
 */
public final class BlueprintSession {

	private final Stack<Class<?>> stack = new Stack<Class<?>>();

	public void pop() {
		stack.pop();
	}

	public void push(final Class<?> clazz) {
		if (stack.contains(clazz)) {
			throw new BlueprintCycleException(clazz);
		}
		stack.push(clazz);
	}
}
