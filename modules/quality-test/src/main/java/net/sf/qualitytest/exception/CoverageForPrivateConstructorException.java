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

/**
 * Thrown to indicate that an error occured while giving coverage to a private constructor.
 * 
 * @author Dominik Seichter
 * 
 */
public class CoverageForPrivateConstructorException extends RuntimeException {

	private static final long serialVersionUID = -210874646549124053L;

	/**
	 * Default message to indicate that the a given argument must not be empty.
	 */
	protected static final String DEFAULT_MESSAGE = "Error while giving coverage to the private constructor of a class.";

	/**
	 * Constructs an {@code CoverageForPrivateConstructorException} with the default message
	 * {@link CoverageForPrivateConstructorException#DEFAULT_MESSAGE}.
	 */
	public CoverageForPrivateConstructorException() {
		super(DEFAULT_MESSAGE);
	}

	/**
	 * Constructs a new exception with the default message
	 * {@link CoverageForPrivateConstructorException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public CoverageForPrivateConstructorException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
	}

}
