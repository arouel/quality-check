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
package net.sf.qualitytest.exception;

import net.sf.qualitycheck.Check;

/**
 * Thrown to indicate a cycle in the graph while blueprinting. As a workaround
 * you should add a hint on how to blueprint the class using
 * {@code BlueprintConfiguration.with}.
 * 
 * @author Dominik Seichter
 */
public class BlueprintCycleException extends RuntimeException {

	private static final long serialVersionUID = -7011658424196608479L;
	private static final String DEFAULT_MESSAGE = "Error during blueprinting class '%s'.";

	/**
	 * Constructs a {@code BlueprintCycleException} with the default message
	 * {@link BlueprintCycleException#DEFAULT_MESSAGE}.
	 * 
	 * @param clazz
	 *            The class causing the cycle.
	 */
	public BlueprintCycleException(final Class<?> clazz) {
		super(String.format(DEFAULT_MESSAGE, Check.notNull(clazz, "clazz")));
	}

	/**
	 * Constructs a {@code BlueprintCycleException} with a message.
	 * 
	 * @param clazz
	 *            The class causing the cycle.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the
	 *            {@link Throwable#getCause()} method). (A {@code null} value is
	 *            permitted, and indicates that the cause is nonexistent or
	 *            unknown.)
	 */
	public BlueprintCycleException(final Class<?> clazz, final Throwable cause) {
		super(String.format(DEFAULT_MESSAGE, Check.notNull(clazz, "clazz")),
				cause);
	}

}
