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

import java.lang.annotation.Documented;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.ImmutableList;
import org.mutabilitydetector.repackaged.com.google.common.collect.Lists;

public class ConstructorTest {

	private static final class Blueprint {

		private String name = "TestCon";

		private List<Attribute> attributes = Lists.newArrayList();

		private Visibility visibility = Visibility.PUBLIC;

		private List<Annotation> annotations = Lists.newArrayList();

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
		public Constructor build() {
			return new Constructor(name, attributes, visibility, annotations);
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		public Blueprint visibility(final Visibility visibility) {
			this.visibility = visibility;
			return this;
		}

	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		new Constructor(name, attributes, visibility, annotations);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		new Constructor(name, attributes, visibility, annotations);
	}

	@Test
	public void equals_different_ANNOTATIONS() {
		final Constructor a = new Blueprint().annotations(Lists.newArrayList(Annotation.of(Documented.class))).build();
		final Constructor b = new Blueprint().annotations(Lists.newArrayList(Annotation.of(SuppressWarnings.class))).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ATTRIBUTES() {
		final Attribute attr1 = new Attribute("attr1", Type.of(String.class), Final.FINAL, Lists.newArrayList(Annotation.NONNULL));
		final Attribute attr2 = new Attribute("attr2", Type.of(String.class), Final.FINAL, Lists.newArrayList(Annotation.NONNULL));
		final Constructor a = new Blueprint().attributes(Lists.newArrayList(attr1)).build();
		final Constructor b = new Blueprint().attributes(Lists.newArrayList(attr2)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		final Constructor a = new Blueprint().name("test1").build();
		final Constructor b = new Blueprint().name("test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_VISIBILITY() {
		final Constructor a = new Blueprint().visibility(Visibility.PUBLIC).build();
		final Constructor b = new Blueprint().visibility(Visibility.PRIVATE).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Constructor a = new Blueprint().build();
		final Constructor b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Constructor a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Constructor a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Constructor a = new Blueprint().build();
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
	public void precondition_NAME() {
		new Blueprint().name(null).build();
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
