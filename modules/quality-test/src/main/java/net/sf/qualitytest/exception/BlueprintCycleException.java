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
package net.sf.qualitytest.exception;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.BlueprintSession;

/**
 * Thrown to indicate a cycle in the graph while blueprinting. As a workaround you should add a hint on how to blueprint
 * the class using {@code BlueprintConfiguration.with}.
 * 
 * @author Dominik Seichter
 */
public class BlueprintCycleException extends RuntimeException {

	private static final long serialVersionUID = -7011658424196608479L;
	private static final String DEFAULT_MESSAGE = "Error during blueprinting class '%s': %s";

	private final BlueprintSession session;

	/**
	 * Constructs a {@code BlueprintCycleException} with the default message
	 * {@link BlueprintCycleException#DEFAULT_MESSAGE}.
	 * 
	 * @param session
	 *            The current BlueprintSession giving access to the context of the error.
	 * @param clazz
	 *            The class causing the cycle.
	 */
	public BlueprintCycleException(@Nonnull final BlueprintSession session, @Nonnull final Class<?> clazz) {
		super(String.format(DEFAULT_MESSAGE, Check.notNull(clazz, "clazz").getName(), Check.notNull(session, "session").getContext()));

		this.session = session;
	}

	/**
	 * Constructs a {@code BlueprintCycleException} with a message.
	 * 
	 * @param session
	 *            The current BlueprintSession giving access to the context of the error.
	 * @param clazz
	 *            The class causing the cycle.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public BlueprintCycleException(@Nonnull final BlueprintSession session, @Nonnull final Class<?> clazz, final Throwable cause) {
		super(String.format(DEFAULT_MESSAGE, Check.notNull(clazz, "clazz").getName(), Check.notNull(session, "session").getContext()),
				cause);

		this.session = session;
	}

	/**
	 * Access the {@link BlueprintSession} during which the exception has occurred.
	 * 
	 * @return {@code BlueprintSession}
	 */
	public @Nonnull
	BlueprintSession getSession() {
		return session;
	}

}
