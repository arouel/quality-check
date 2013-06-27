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

import javax.annotation.Nullable;

import net.sf.qualitytest.blueprint.BlueprintSession;

/**
 * @author Dominik Seichter
 */
public class BlueprintException extends RuntimeException {

	private static final long serialVersionUID = -7011658424196608479L;
	private static final String DEFAULT_MESSAGE = "Error during blueprinting.";

	private BlueprintSession session;

	/**
	 * Constructs a {@code BlueprintException} with the default message {@link BlueprintException#DEFAULT_MESSAGE}.
	 */
	public BlueprintException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs a {@code BlueprintException} with a message.
	 * 
	 * @param msg
	 *            A message describing the error.
	 */
	public BlueprintException(final String msg) {
		super(msg);
	}

	/**
	 * Constructs a {@code BlueprintException} with a message.
	 * 
	 * @param msg
	 *            A message describing the error.
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public BlueprintException(final String msg, final Throwable cause) {
		super(msg, cause);
	}

	/**
	 * Constructs a new exception with the default message {@link BlueprintException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public BlueprintException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

	@Override
	public String getMessage() {
		final String message = super.getMessage();
		if (session != null) {
			return message + " " + session.getContext();
		} else {
			return message;
		}
	}

	/**
	 * Access the {@link BlueprintSession} during which the exception has occurred.
	 * 
	 * @return {@code BlueprintSession}
	 */
	public @Nullable
	BlueprintSession getSession() {
		return session;
	}

	public void setSession(final BlueprintSession session) {
		this.session = session;
	}
}
