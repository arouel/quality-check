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
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.Lists;

import com.google.common.collect.ImmutableList;

public class ClazzTest {

	private static final class Blueprint {

		private Abstract abstract1 = Abstract.UNDEFINED;

		private List<Annotation> annotations = Lists.newArrayList();

		private List<Constructor> constructors = Lists.newArrayList();

		private List<Field> fields = Lists.newArrayList();

		private Final final1 = Final.UNDEFINED;

		private List<Import> imports = Lists.newArrayList();

		private List<Interface> interfaces = Lists.newArrayList();

		private List<Method> methods = Lists.newArrayList();

		private String name = "ClazzTest";

		private Package package1 = new Package("net.sf.qualitycheck.immutableobject.domain");

		private Visibility visibility = Visibility.PUBLIC;

		public Blueprint() {
			// default constructor
		}

		public Blueprint abstractDeclaration(final Abstract abstract1) {
			this.abstract1 = abstract1;
			return this;
		}

		public Blueprint annotations(final List<Annotation> annotations) {
			this.annotations = annotations;
			return this;
		}

		@Nonnull
		public Clazz build() {
			return new Clazz(name, package1, fields, constructors, methods, visibility, final1, abstract1, interfaces, imports, annotations);
		}

		public Blueprint constructors(final List<Constructor> constructors) {
			this.constructors = constructors;
			return this;
		}

		public Blueprint fields(final List<Field> fields) {
			this.fields = fields;
			return this;
		}

		public Blueprint final1(final Final final1) {
			this.final1 = final1;
			return this;
		}

		public Blueprint imports(final List<Import> imports) {
			this.imports = imports;
			return this;
		}

		public Blueprint interfaces(final List<Interface> interfaces) {
			this.interfaces = interfaces;
			return this;
		}

		public Blueprint methods(final List<Method> methods) {
			this.methods = methods;
			return this;
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		public Blueprint package1(final Package package1) {
			this.package1 = package1;
			return this;
		}

		public Blueprint visibility(final Visibility visibility) {
			this.visibility = visibility;
			return this;
		}

	}

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Clazz.class, areImmutable(), provided(ImmutableList.class, Annotations.class, Imports.class).isAlsoImmutable());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_abtractAndFinal() {
		final String name = "AbtractAndFinalTestClazz";
		final Package pkg = new Package(getClass().getPackage().getName());
		final List<Field> field = new ImmutableList.Builder<Field>().build();
		final List<Constructor> constructors = new ImmutableList.Builder<Constructor>().build();
		final List<Method> methods = new ImmutableList.Builder<Method>().build();
		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.ABSTRACT;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of();
		final List<Annotation> annotations = new ImmutableList.Builder<Annotation>().add(Annotation.of(Immutable.class)).build();
		final List<Import> imports = ImmutableList.of();
		new Clazz(name, pkg, field, constructors, methods, visibility, finalModifier, abstractModifier, interfaces, imports, annotations);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final Package pkg = Package.UNDEFINED;
		final List<Field> field = ImmutableList.of();
		final List<Constructor> constructors = ImmutableList.of();
		final List<Method> methods = ImmutableList.of();
		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.UNDEFINED;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		final List<Import> imports = ImmutableList.of();
		new Clazz(name, pkg, field, constructors, methods, visibility, finalModifier, abstractModifier, interfaces, imports, annotations);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final Package pkg = Package.UNDEFINED;
		final List<Field> field = ImmutableList.of();
		final List<Constructor> constructors = ImmutableList.of();
		final List<Method> methods = ImmutableList.of();
		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.UNDEFINED;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		final List<Import> imports = ImmutableList.of();
		new Clazz(name, pkg, field, constructors, methods, visibility, finalModifier, abstractModifier, interfaces, imports, annotations);
	}

	@Test
	public void equals_different_ABSTRACT() {
		final Clazz a = new Blueprint().abstractDeclaration(Abstract.ABSTRACT).build();
		final Clazz b = new Blueprint().abstractDeclaration(Abstract.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_ANNOTATIONS() {
		final Clazz a = new Blueprint().annotations(Lists.newArrayList(Annotation.IMMUTABLE)).build();
		final Clazz b = new Blueprint().annotations(Lists.newArrayList(Annotation.NONNULL)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_CONSTRUCTORS() {
		final Constructor c1 = new Constructor("Test1", new ArrayList<Attribute>(), Visibility.PUBLIC, new ArrayList<Annotation>());
		final Constructor c2 = new Constructor("Test2", new ArrayList<Attribute>(), Visibility.PUBLIC, new ArrayList<Annotation>());
		final Clazz a = new Blueprint().constructors(Lists.newArrayList(c1)).build();
		final Clazz b = new Blueprint().constructors(Lists.newArrayList(c2)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FIELDS() {
		final Field f1 = new Field("text", Type.of(String.class), Visibility.PUBLIC, Final.UNDEFINED, Static.UNDEFINED,
				new ArrayList<Annotation>(), "", AccessorPrefix.GET);
		final Field f2 = new Field("checked", Type.BOOLEAN, Visibility.PUBLIC, Final.UNDEFINED, Static.UNDEFINED,
				new ArrayList<Annotation>(), "", AccessorPrefix.IS);
		final Clazz a = new Blueprint().fields(Lists.newArrayList(f1)).build();
		final Clazz b = new Blueprint().fields(Lists.newArrayList(f2)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FINAL() {
		final Clazz a = new Blueprint().final1(Final.FINAL).build();
		final Clazz b = new Blueprint().final1(Final.UNDEFINED).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_IMPORTS() {
		final Clazz a = new Blueprint().imports(Lists.newArrayList(Import.of(ArrayList.class))).build();
		final Clazz b = new Blueprint().imports(Lists.newArrayList(Import.of(HashSet.class))).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_INTERFACES() {
		final Clazz a = new Blueprint().interfaces(Lists.newArrayList(Interface.of(Serializable.class))).build();
		final Clazz b = new Blueprint().interfaces(Lists.newArrayList(Interface.of(Comparable.class))).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_METHODS() {
		final Method m1 = new Method("getA", ReturnType.of(String.class), new ArrayList<Attribute>(), Visibility.PUBLIC, Final.UNDEFINED,
				Static.UNDEFINED, new ArrayList<Annotation>());
		final Method m2 = new Method("getB", ReturnType.of(String.class), new ArrayList<Attribute>(), Visibility.PUBLIC, Final.UNDEFINED,
				Static.UNDEFINED, new ArrayList<Annotation>());
		final Clazz a = new Blueprint().methods(Lists.newArrayList(m1)).build();
		final Clazz b = new Blueprint().methods(Lists.newArrayList(m2)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		final Clazz a = new Blueprint().name("Test1").build();
		final Clazz b = new Blueprint().name("Test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PACKAGE() {
		final Clazz a = new Blueprint().package1(new Package("test1")).build();
		final Clazz b = new Blueprint().package1(new Package("test2")).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_VISIBILITY() {
		final Clazz a = new Blueprint().visibility(Visibility.UNDEFINED).build();
		final Clazz b = new Blueprint().visibility(Visibility.PUBLIC).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Clazz a = new Blueprint().build();
		final Clazz b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Clazz a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Clazz a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Clazz a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void toString_notEmpty() {
		assertFalse(new Blueprint().build().toString().isEmpty());
	}

}
