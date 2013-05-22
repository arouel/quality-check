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

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Thrown to indicate that an annotation is missing on a public method of a class.
 * 
 * @author Dominik Seichter
 * 
 */
public class IllegalMissingAnnotationOnMethodException extends RuntimeException {

	private static final long serialVersionUID = 2122431252273528917L;
	/**
	 * Default message to indicate that the a given class must have an annotation.
	 */
	protected static final String DEFAULT_MESSAGE = "Annotation is required on all public methods of the passed class.";

	/**
	 * Message to indicate that the the given class with <em>name</em> must be annotated with annotation '%s' on method
	 * <name>method</name>.
	 */
	protected static final String MESSAGE_WITH_METHOD_ANNOTATION_AND_CLASS = "Class '%s' must have annotation '%s' on method '%s'.";

	/**
	 * Returns the formatted string {@link IllegalMissingAnnotationOnMethodException#MESSAGE_WITH_ANNOTATION} with the
	 * given {@code annotation}.
	 * 
	 * @param clazz
	 *            the name of the class causing the error
	 * @param annotation
	 *            the name of the required annotation
	 * @param method
	 *            the method causing the exception
	 * @return a formatted string of message with the given argument name
	 */
	private static String format(@Nonnull final Class<?> clazz, @Nonnull final Class<? extends Annotation> annotation,
			@Nonnull final Method method) {
		if (annotation == null || method == null || clazz == null) {
			throw new IllegalArgumentException("annotation");
		}
		return String.format(MESSAGE_WITH_METHOD_ANNOTATION_AND_CLASS, clazz.getName(), annotation.getName(), method.getName());
	}

	/**
	 * Annotation to search on a class
	 */
	@Nullable
	private final Class<? extends Annotation> annotation;

	/**
	 * Class to check for an annotation
	 */
	@Nullable
	private final Class<?> clazz;

	/**
	 * Method without annotation.
	 */
	@Nullable
	private final Method method;

	/**
	 * Constructs an {@code IllegalMissingAnnotationOnMethodException} with the default message
	 * {@link IllegalMissingAnnotationOnMethodException#DEFAULT_MESSAGE}.
	 */
	public IllegalMissingAnnotationOnMethodException() {
		super(DEFAULT_MESSAGE);
		annotation = null;
		clazz = null;
		method = null;
	}

	/**
	 * Constructs an {@code IllegalMissingAnnotationOnMethodException} with the message
	 * {@link IllegalMissingAnnotationOnMethodException#MESSAGE_WITH_ANNOTATION} including the name of the missing
	 * annotation.
	 * 
	 * @param clazz
	 *            the name of the class causing the error
	 * @param annotation
	 *            the name of the required annotation
	 * @param method
	 *            the method causing the exception
	 */
	public IllegalMissingAnnotationOnMethodException(@Nonnull final Class<?> clazz, @Nonnull final Class<? extends Annotation> annotation,
			@Nonnull final Method method) {
		super(format(clazz, annotation, method));
		this.annotation = annotation;
		this.clazz = clazz;
		this.method = method;
	}

	/**
	 * Constructs an {@code IllegalMissingAnnotationOnMethodException} with the message
	 * {@link IllegalMissingAnnotationOnMethodException#MESSAGE_WITH_ANNOTATION} including the name of the missing
	 * annotation.
	 * 
	 * @param clazz
	 *            the name of the class causing the error
	 * @param annotation
	 *            the name of the required annotation
	 * @param method
	 *            the method causing the exception
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalMissingAnnotationOnMethodException(@Nonnull final Class<?> clazz, @Nonnull final Class<? extends Annotation> annotation,
			@Nonnull final Method method, @Nullable final Throwable cause) {
		super(format(clazz, annotation, method), cause);
		this.annotation = annotation;
		this.clazz = clazz;
		this.method = method;
	}

	/**
	 * Constructs a new exception with the default message
	 * {@link IllegalMissingAnnotationOnMethodException#DEFAULT_MESSAGE}.
	 * 
	 * @param cause
	 *            the cause (which is saved for later retrieval by the {@link Throwable#getCause()} method). (A
	 *            {@code null} value is permitted, and indicates that the cause is nonexistent or unknown.)
	 */
	public IllegalMissingAnnotationOnMethodException(@Nullable final Throwable cause) {
		super(DEFAULT_MESSAGE, cause);
		annotation = null;
		clazz = null;
		method = null;
	}

	/**
	 * Gives access to the class which does not have a required annotation.
	 * 
	 * @return the class which caused the exception by not having a required annotation
	 */
	public Class<?> getClassWithoutAnnotation() {
		return clazz;
	}

	/**
	 * Gives access to the method which does not have a required annotation.
	 * 
	 * @return the method which caused the exception.
	 */
	public Method getMethodWithoutAnnotation() {
		return method;
	}

	/**
	 * Gives access to the required annotation.
	 * 
	 * @return the annotation which was expected on a class and was not found
	 */
	public Class<? extends Annotation> getMissingAnnotation() {
		return annotation;
	}
}
