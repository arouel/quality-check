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
package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.Lists;

import com.google.common.collect.ImmutableList;

public class FieldTest {

	private static final class Blueprint {

		private AccessorPrefix accessorPrefix = AccessorPrefix.GET;

		private List<Annotation> annotations = Lists.newArrayList();

		private Final final1 = Final.UNDEFINED;

		private String name = "field";

		private Static static1 = Static.UNDEFINED;

		private Type type = Type.BOOLEAN;

		private String value = "false";

		private Visibility visibility = Visibility.PUBLIC;

		public Blueprint() {
			// default constructor
		}

		public Blueprint accessorPrefix(final AccessorPrefix accessorPrefix) {
			this.accessorPrefix = accessorPrefix;
			return this;
		}

		public Blueprint annotations(final List<Annotation> annotations) {
			this.annotations = annotations;
			return this;
		}

		@Nonnull
		public Field build() {
			return new Field(name, type, visibility, final1, static1, annotations, value, accessorPrefix);
		}

		public Blueprint final1(final Final final1) {
			this.final1 = final1;
			return this;
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		public Blueprint static1(final Static static1) {
			this.static1 = static1;
			return this;
		}

		public Blueprint type(final Type type) {
			this.type = type;
			return this;
		}

		public Blueprint value(final String value) {
			this.value = value;
			return this;
		}

		public Blueprint visibility(final Visibility visibility) {
			this.visibility = visibility;
			return this;
		}

	}

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Field.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final Type type = Type.of(String.class);
		final Visibility visibility = Visibility.PUBLIC;
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of(Annotation.of(Nullable.class));
		final String value = "";
		final AccessorPrefix accessorPrefix = AccessorPrefix.GET;
		new Field(name, type, visibility, finalModifier, staticModifier, annotations, value, accessorPrefix).isNullable();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final Type type = Type.of(String.class);
		final Visibility visibility = Visibility.PUBLIC;
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of(Annotation.of(Nullable.class));
		final String value = "";
		final AccessorPrefix accessorPrefix = AccessorPrefix.GET;
		new Field(name, type, visibility, finalModifier, staticModifier, annotations, value, accessorPrefix).isNullable();
	}

	@Test
	public void equals_different_ACCESSORPREFIX() {
		final Field a = new Blueprint().accessorPrefix(AccessorPrefix.GET).build();
		final Field b = new Blueprint().accessorPrefix(AccessorPrefix.HAS).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ANNOTATIONS() {
		final Field a = new Blueprint().annotations(Lists.newArrayList(Annotation.of(Nonnull.class))).build();
		final Field b = new Blueprint().annotations(Lists.newArrayList(Annotation.of(Nullable.class))).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FINAL() {
		final Field a = new Blueprint().final1(Final.FINAL).build();
		final Field b = new Blueprint().final1(Final.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		final Field a = new Blueprint().name("test1").build();
		final Field b = new Blueprint().name("test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_STATIC() {
		final Field a = new Blueprint().static1(Static.STATIC).build();
		final Field b = new Blueprint().static1(Static.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_TYPE() {
		final Field a = new Blueprint().type(Type.of(String.class)).build();
		final Field b = new Blueprint().type(Type.of(Integer.class)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_VALUE() {
		final Field a = new Blueprint().value("test1").build();
		final Field b = new Blueprint().value("test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_VISIBILITY() {
		final Field a = new Blueprint().visibility(Visibility.PRIVATE).build();
		final Field b = new Blueprint().visibility(Visibility.PUBLIC).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Field a = new Blueprint().build();
		final Field b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Field a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Field a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Field a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void isNullable() {
		final String name = "field";
		final Type type = Type.of(String.class);
		final Visibility visibility = Visibility.PUBLIC;
		final Final finalMod = Final.UNDEFINED;
		final Static staticMod = Static.UNDEFINED;
		final String value = "";
		final AccessorPrefix prefix = AccessorPrefix.GET;

		final List<Annotation> annotationsEmpty = ImmutableList.of();
		final List<Annotation> annotationsWithNullable = ImmutableList.of(Annotation.of(Nullable.class));
		final List<Annotation> annotationsWithNonnegative = ImmutableList.of(Annotation.of(Nonnegative.class));
		final List<Annotation> annotationsWithNonnull = ImmutableList.of(Annotation.of(Nonnull.class));

		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsEmpty, value, prefix).isNullable());
		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNullable, value, prefix).isNullable());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnegative, value, prefix).isNullable());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnull, value, prefix).isNullable());

		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsEmpty, value, prefix).isNonnegative());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNullable, value, prefix).isNonnegative());
		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnegative, value, prefix).isNonnegative());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnull, value, prefix).isNonnegative());

		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsEmpty, value, prefix).isNonnull());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNullable, value, prefix).isNonnull());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnegative, value, prefix).isNonnull());
		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnull, value, prefix).isNonnull());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ACCESSORPREFIX() {
		new Blueprint().accessorPrefix(null).build();
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
	public void precondition_STATIC() {
		new Blueprint().static1(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_TYPE() {
		new Blueprint().type(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_VALUE() {
		new Blueprint().value(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_VISIBILITY() {
		new Blueprint().visibility(null).build();
	}

	@Test
	public void toString_notEmpty() {
		assertFalse(new Blueprint().build().toString().isEmpty());
	}

}
