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

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.Lists;

import com.google.common.collect.ImmutableList;

public final class MethodTest {

	private static final class Blueprint {

		private List<Annotation> annotations = Lists.newArrayList();

		private List<Attribute> attributes = Lists.newArrayList();

		private Final final1 = Final.UNDEFINED;

		private String name = "MethodTest";

		private ReturnType returnType = ReturnType.of(String.class);

		private Static static1 = Static.UNDEFINED;

		private Visibility visibility = Visibility.PUBLIC;

		public Blueprint() {
			// default constructor
		}

		public Blueprint annotations(final List<Annotation> annotations) {
			this.annotations = annotations;
			return this;
		}

		public Blueprint attributes(final List<Attribute> attributes) {
			this.attributes = attributes;
			return this;
		}

		@Nonnull
		public Method build() {
			return new Method(name, returnType, attributes, visibility, final1, static1, annotations);
		}

		public Blueprint final1(final Final final1) {
			this.final1 = final1;
			return this;
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		public Blueprint returnType(final ReturnType returnType) {
			this.returnType = returnType;
			return this;
		}

		public Blueprint static1(final Static static1) {
			this.static1 = static1;
			return this;
		}

		public Blueprint visibility(final Visibility visibility) {
			this.visibility = visibility;
			return this;
		}

	}

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Method.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final ReturnType returnType = ReturnType.BOOLEAN;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of();
		new Method(name, returnType, attributes, visibility, finalModifier, staticModifier, annotations);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final ReturnType returnType = ReturnType.BOOLEAN;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of();
		new Method(name, returnType, attributes, visibility, finalModifier, staticModifier, annotations);
	}

	@Test
	public void equals_different_ANNOTATIONS() {
		final Method a = new Blueprint().annotations(Lists.newArrayList(Annotation.IMMUTABLE)).build();
		final Method b = new Blueprint().annotations(Lists.newArrayList(Annotation.NONNULL)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ATTRIBUTES() {
		final Attribute a1 = new Attribute("attr1", Type.of(String.class), Final.UNDEFINED, new ArrayList<Annotation>());
		final Attribute a2 = new Attribute("attr2", Type.of(String.class), Final.UNDEFINED, new ArrayList<Annotation>());
		final Method a = new Blueprint().attributes(Lists.newArrayList(a1)).build();
		final Method b = new Blueprint().attributes(Lists.newArrayList(a2)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FINAL() {
		final Method a = new Blueprint().final1(Final.FINAL).build();
		final Method b = new Blueprint().final1(Final.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		final Method a = new Blueprint().name("Test1").build();
		final Method b = new Blueprint().name("Test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_RETURNTYPE() {
		final Method a = new Blueprint().returnType(ReturnType.BOOLEAN).build();
		final Method b = new Blueprint().returnType(ReturnType.INT).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_STATIC() {
		final Method a = new Blueprint().static1(Static.STATIC).build();
		final Method b = new Blueprint().static1(Static.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_VISIBILITY() {
		final Method a = new Blueprint().visibility(Visibility.PUBLIC).build();
		final Method b = new Blueprint().visibility(Visibility.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Method a = new Blueprint().build();
		final Method b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Method a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Method a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Method a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ANNOTATIONS() {
		new Blueprint().annotations(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ATTRIBUTES() {
		new Blueprint().attributes(null).build();
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
	public void precondition_RETURNTYPE() {
		new Blueprint().returnType(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_STATIC() {
		new Blueprint().static1(null).build();
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
