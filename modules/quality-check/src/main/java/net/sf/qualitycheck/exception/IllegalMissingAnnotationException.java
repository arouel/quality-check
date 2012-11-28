/*******************************************************************************
 * Copyright 2012 André Rouél
 * Copyright 2012 Dominik Seichter
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
package net.sf.qualitycheck.exception;

import java.lang.annotation.Annotation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Throws;

/**
 * Thrown to indicate that a method has been passed with a class that does not have a required annotation.
 * 
 * @author André Rouél
 * @author Dominik Seichter
 */
public class IllegalMissingAnnotationException extends RuntimeException {

	private static final long serialVersionUID = -8428891146741574807L;

	/**
	 * Default message to indicate that the a given class must have an annotation.
	 */
	protected static final String DEFAULT_MESSAGE = "Annotation is required on the passed class.";

	/**
	 * Message to indicate that the the given class must be annotated with annotation '%s'.
	 */
	protected static final String MESSAGE_WITH_ANNOTATION = "Class must have annotation '%s'.";

	/**
	 * Message to indicate that the the given class with <em>name</em> must be annotated with annotation '%s'.
	 */
	protected static final String MESSAGE_WITH_ANNOTATION_AND_CLASS = "Class '%s' must have annotation '%s'.";

	private final Class<? extends Annotation> annotation;
	private final Class<?> clazz;

	/**
	 * Returns the formatted string {@link IllegalMissingAnnotationException#MESSAGE_WITH_ANNOTATION} with the given
	 * {@code annotation}.
	 * 
	 * @param annotation
	 *            the name of the required annotation
	 * @return a formatted string of message with the given argument name
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	private static String format(@Nonnull final Class<? extends Annotation> annotation) {
		if (annotation == null) {
			throw new IllegalNullArgumentException("annotation");
		}
		return String.format(MESSAGE_WITH_ANNOTATION, annotation.getName());
	}

	/**
	 * Returns the formatted string {@link IllegalMissingAnnotationException#MESSAGE_WITH_ANNOTATION_AND_CLASS} with the
	 * given {@code annotation} and {@code clazz}.
	 * 
	 * @param annotation
	 *            the required annotation
	 * @param clazz
	 *            the name of the class which does not have the required annotation
	 * @return a formatted string of message with the given argument name
	 */
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	private static String format(@Nonnull final Class<? extends Annotation> annotation, @Nullable final Class<?> clazz) {
		if (annotation == null) {
			throw new IllegalNullArgumentException("annotation");
		}

		if (clazz != null) {
			return String.format(MESSAGE_WITH_ANNOTATION_AND_CLASS, clazz.getName(), annotation.getName());
		} else {
			return format(annotation);
		}
	}

	/**
	 * Constructs an {@code IllegalMissingAnnotationException} with the default message
	 * {@link IllegalMissingAnnotationException#DEFAULT_MESSAGE}.
	 */
	public IllegalMissingAnnotationException() {
		super(DEFAULT_MESSAGE);
		this.annotation = null;
		this.clazz = null;
	}

	/**
	 * Constructs an {@code IllegalMissingAnnotationException} with the message
	 * {@link IllegalMissingAnnotationException#MESSAGE_WITH_ANNOTATION} including the name of the missing annotation.
	 * 
	 * @param annotation
	 *            the required annotation
	 */
	public IllegalMissingAnnotationException(@Nonnull final Class<? extends Annotation> annotation) {
		super(format(annotation));
		this.annotation = annotation;
		this.clazz = null;
	}

	/**
	 * Constructs a new exception with the message {@link IllegalMissingAnnotationException#MESSAGE_WITH_ANNOTATION}
	 * including the name of the missing annotation.
	 * 
	 * @param annotation
	 *            the required annotation
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalMissingAnnotationException(@Nullable final Class<? extends Annotation> annotation, @Nullable final Throwable cause) {
		super(format(annotation), cause);
		this.annotation = annotation;
		this.clazz = null;
	}

	/**
	 * Constructs an {@code IllegalMissingAnnotationException} with the message
	 * {@link IllegalMissingAnnotationException#MESSAGE_WITH_ANNOTATION} including the name of the missing annotation.
	 * 
	 * @param annotation
	 *            the required annotation
	 * @param clazz
	 *            the name of the class which does not have the required annotation
	 */
	public IllegalMissingAnnotationException(@Nonnull final Class<? extends Annotation> annotation, @Nullable final Class<?> clazz) {
		super(format(annotation, clazz));
		this.annotation = annotation;
		this.clazz = clazz;
	}

	/**
	 * Constructs a new exception with the message {@link IllegalMissingAnnotationException#MESSAGE_WITH_ANNOTATION}
	 * including the name of the missing annotation.
	 * 
	 * @param annotation
	 *            the required annotation
	 * @param clazz
	 *            the name of the class which does not have the required annotation
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalMissingAnnotationException(@Nullable final Class<? extends Annotation> annotation, @Nullable final Class<?> clazz,
			@Nullable final Throwable cause) {
		super(format(annotation, clazz), cause);
		this.annotation = annotation;
		this.clazz = clazz;
	}

	/**
	 * Constructs a new exception with the default message {@link IllegalMissingAnnotationException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalMissingAnnotationException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
		this.annotation = null;
		this.clazz = null;
	}

	/**
	 * Gives access to the required annotation.
	 * 
	 * @return the annotation which was expected on a class and was not found
	 */
	public Class<? extends Annotation> getMissingAnnotation() {
		return annotation;
	}

	/**
	 * Gives access to the class which does not have a required annotation.
	 * 
	 * @return the class which caused the exception by not having a required annotation
	 */
	public Class<?> getClassWithoutAnnotation() {
		return this.clazz;
	}

}
