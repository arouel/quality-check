/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

public class AnnotationTest {

	@Retention(RetentionPolicy.RUNTIME)
	private static @interface TestAnnotation {
	}

	@TestAnnotation
	private static class TestClass {
	}

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Annotation.class, areImmutable());
	}

	@Test
	public void construct_isAnnotation() {
		assertNotNull(Annotation.of(Immutable.class));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_isNotAnnotation() {
		assertNotNull(Annotation.of(String.class));
	}

	@Test
	public void construct_withAnnotation() throws SecurityException, NoSuchMethodException {
		assertEquals(Annotation.of(TestAnnotation.class), Annotation.of(TestClass.class.getAnnotation(TestAnnotation.class)));
	}

	@Test
	public void construct_withQualifiedName() {
		assertNotNull(Annotation.of(Immutable.class.getName()));
	}

	@Test
	public void equals_differentType() {
		final Annotation immutable = Annotation.of(Immutable.class);
		final Annotation nonnull = Annotation.of(Nonnull.class);
		assertTrue(!immutable.equals(nonnull));
	}

	@Test
	public void equals_identical() {
		final Annotation immutable1 = Annotation.of(Immutable.class);
		final Annotation immutable2 = Annotation.of(Immutable.class);
		assertEquals(immutable1, immutable2);
		assertEquals(Annotation.IMMUTABLE, immutable2);
	}

	@Test
	public void equals_null() {
		final Annotation a = Annotation.of(Nonnull.class);
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Annotation a = Annotation.of(Nullable.class);
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Annotation immutable = Annotation.of(Immutable.class);
		assertEquals(immutable, immutable);
		assertSame(immutable, immutable);
	}

	@Test
	public void isImmutable() {
		assertTrue(Annotation.of(Immutable.class).isImmutable());
		assertFalse(Annotation.of(Nonnull.class).isImmutable());
	}

	@Test
	public void isNonnegative() {
		assertTrue(Annotation.of(Nonnegative.class).isNonnegative());
		assertFalse(Annotation.of(Nonnull.class).isNonnegative());
	}

	@Test
	public void isNonnull() {
		assertTrue(Annotation.of(Nonnull.class).isNonnull());
		assertFalse(Annotation.of(Deprecated.class).isNonnull());
	}

	@Test
	public void isNotThreadSafe() {
		assertTrue(Annotation.of(NotThreadSafe.class).isNotThreadSafe());
		assertFalse(Annotation.of(SuppressWarnings.class).isNotThreadSafe());
	}

	@Test
	public void isNullable() {
		assertTrue(Annotation.of(Nullable.class).isNullable());
		assertFalse(Annotation.of(Override.class).isNullable());
	}

	@Test
	public void toString_notEmpty() {
		assertEquals("@Nullable", Annotation.of(Nullable.class).toString());
		assertEquals("@Override", Annotation.of(Override.class).toString());
	}

}
