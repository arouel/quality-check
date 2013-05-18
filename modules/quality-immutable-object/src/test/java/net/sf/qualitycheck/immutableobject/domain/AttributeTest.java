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
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.Lists;

public final class AttributeTest {

	private static final class Blueprint {

		private List<Annotation> annotations = Lists.newArrayList();

		private Final final1 = Final.UNDEFINED;

		private String name = "attribute";

		private Type type = Type.BOOLEAN;

		public Blueprint() {
			// default constructor
		}

		public Blueprint annotations(final List<Annotation> annotations) {
			this.annotations = annotations;
			return this;
		}

		@Nonnull
		public Attribute build() {
			return new Attribute(name, type, final1, annotations);
		}

		public Blueprint final1(final Final final1) {
			this.final1 = final1;
			return this;
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		public Blueprint type(final Type type) {
			this.type = type;
			return this;
		}

	}

	@Test
	public void equals_different_ANNOTATIONS() {
		final Attribute a = new Blueprint().annotations(Lists.newArrayList(Annotation.of(Nonnull.class))).build();
		final Attribute b = new Blueprint().annotations(Lists.newArrayList(Annotation.of(Nullable.class))).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FINAL() {
		final Attribute a = new Blueprint().final1(Final.FINAL).build();
		final Attribute b = new Blueprint().final1(Final.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		final Attribute a = new Blueprint().name("test1").build();
		final Attribute b = new Blueprint().name("test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_TYPE() {
		final Attribute a = new Blueprint().type(Type.of(String.class)).build();
		final Attribute b = new Blueprint().type(Type.of(Integer.class)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Attribute a = new Blueprint().build();
		final Attribute b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Attribute a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Attribute a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Attribute a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ANNOTATIONS() {
		new Blueprint().annotations(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FINAL() {
		new Blueprint().final1(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_NAME() {
		new Blueprint().name(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_TYPE() {
		new Blueprint().type(null).build();
	}

	@Test
	public void toString_notEmpty() {
		assertFalse(new Blueprint().build().toString().isEmpty());
	}

}
