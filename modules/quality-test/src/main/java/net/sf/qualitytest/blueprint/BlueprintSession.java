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

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.exception.BlueprintCycleException;

/**
 * A {@code BlueprintSession} holds information acquired while doing a blueprint of a class. This includes cycle
 * detection as well as statistical information.
 * 
 * @author Dominik Seichter
 * 
 */
public final class BlueprintSession {

	private final Stack<Class<?>> stack = new Stack<Class<?>>();
	private final Set<Class<?>> classes = new HashSet<Class<?>>();
	private int blueprintCount = 0;

	/**
	 * Detect cycles in the blueprinting-graph. A cycle occurs when a class is blueprinted within a scope where the same
	 * class has been blueprinted before.
	 * 
	 * @param clazz
	 *            a class
	 * @throws {@code BlueprintCycleException} if a cycle has been detected
	 */
	private void detectCycles(@Nonnull final Class<?> clazz) {
		if (stack.contains(clazz)) {
			throw new BlueprintCycleException(clazz);
		}
	}

	/**
	 * Retrieve all classes that have been blueprinted in the current session.
	 * 
	 * @return a set of classes encountered while creating the blueprint
	 */
	public Set<Class<?>> getBlueprintClasses() {
		return Collections.unmodifiableSet(classes);
	}

	/**
	 * Retrieve the number of objects which have been blueprinted in the current session.
	 * 
	 * @return number of objects that have been blueprinted in the current session
	 */
	public int getBlueprintCount() {
		return blueprintCount;
	}

	/**
	 * Call after creating a blueprint of a class.
	 */
	public void pop() {
		stack.pop();

		blueprintCount++;
	}

	/**
	 * Call before creating a blueprint of a class.
	 * 
	 * The internal stack is used to do cycle detection in the blueprinting graph.
	 * 
	 * @param clazz
	 *            the class for which a blueprint is created
	 * @throws {@code BlueprintCycleException} if a cycle has been detected
	 * 
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	public void push(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		detectCycles(clazz);

		stack.push(clazz);
		classes.add(clazz);
	}
}
