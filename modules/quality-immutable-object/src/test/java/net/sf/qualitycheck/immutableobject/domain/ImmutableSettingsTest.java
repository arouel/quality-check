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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.Lists;

public final class ImmutableSettingsTest {

	private static final class Blueprint {

		private boolean builderCopyConstructor = false;

		private boolean builderFlatMutators = false;

		private boolean builderFluentMutators = false;

		private boolean builderImplementsInterface = false;

		private String builderName = "TestBuilder";

		private boolean copyMethods = false;

		private String fieldPrefix = "_";

		private List<Field> fields = Lists.newArrayList();

		private boolean guava = false;

		private boolean hashCodeAndEquals = false;

		private boolean hashCodePrecomputation = false;

		private String immutableName = "ImmutableTestObject";

		private List<Import> imports = Lists.newArrayList();

		private List<Interface> interfaces = Lists.newArrayList();

		private boolean jsr305Annotations = false;

		private Interface mainInterface = Interface.UNDEFINED;

		private Package packageDeclaration = new Package("net.sf.qualitycheck.test");

		private boolean qualityCheck = false;

		private boolean replacement = false;

		private boolean serializable = false;

		private boolean toString = false;

		public Blueprint() {
			// default constructor
		}

		@Nonnull
		public ImmutableSettings build() {
			return new ImmutableSettings(builderName, fieldPrefix, fields, immutableName, imports, interfaces, mainInterface,
					packageDeclaration, builderCopyConstructor, builderFlatMutators, builderFluentMutators, builderImplementsInterface,
					copyMethods, guava, hashCodeAndEquals, hashCodePrecomputation, jsr305Annotations, qualityCheck, toString, replacement,
					serializable);
		}

		public Blueprint builderCopyConstructor(final boolean builderCopyConstructor) {
			this.builderCopyConstructor = builderCopyConstructor;
			return this;
		}

		public Blueprint builderFlatMutators(final boolean builderFlatMutators) {
			this.builderFlatMutators = builderFlatMutators;
			return this;
		}

		public Blueprint builderFluentMutators(final boolean builderFluentMutators) {
			this.builderFluentMutators = builderFluentMutators;
			return this;
		}

		public Blueprint builderImplementsInterface(final boolean builderImplementsInterface) {
			this.builderImplementsInterface = builderImplementsInterface;
			return this;
		}

		public Blueprint builderName(final String builderName) {
			this.builderName = builderName;
			return this;
		}

		public Blueprint copyMethods(final boolean copyMethods) {
			this.copyMethods = copyMethods;
			return this;
		}

		public Blueprint fieldPrefix(final String fieldPrefix) {
			this.fieldPrefix = fieldPrefix;
			return this;
		}

		public Blueprint fields(final List<Field> fields) {
			this.fields = fields;
			return this;
		}

		public Blueprint guava(final boolean guava) {
			this.guava = guava;
			return this;
		}

		public Blueprint hashCodeAndEquals(final boolean hashCodeAndEquals) {
			this.hashCodeAndEquals = hashCodeAndEquals;
			return this;
		}

		public Blueprint hashCodePrecomputation(final boolean hashCodePrecomputation) {
			this.hashCodePrecomputation = hashCodePrecomputation;
			return this;
		}

		public Blueprint immutableName(final String immutableName) {
			this.immutableName = immutableName;
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

		public Blueprint jsr305Annotations(final boolean jsr305Annotations) {
			this.jsr305Annotations = jsr305Annotations;
			return this;
		}

		public Blueprint mainInterface(final Interface mainInterface) {
			this.mainInterface = mainInterface;
			return this;
		}

		public Blueprint packageDeclaration(final Package packageDeclaration) {
			this.packageDeclaration = packageDeclaration;
			return this;
		}

		public Blueprint qualityCheck(final boolean qualityCheck) {
			this.qualityCheck = qualityCheck;
			return this;
		}

		public Blueprint replacement(final boolean replacement) {
			this.replacement = replacement;
			return this;
		}

		public Blueprint serializable(final boolean serializable) {
			this.serializable = serializable;
			return this;
		}

		public Blueprint toString(final boolean toString) {
			this.toString = toString;
			return this;
		}

	}

	@Test(expected = IllegalNullArgumentException.class)
	public void copyOf_argIsNull() {
		ImmutableSettings.copyOf(null);
	}

	@Test
	public void copyOf_successful() {
		assertNotNull(ImmutableSettings.copyOf(new Blueprint().build()));
	}

	@Test
	public void equals_different_BUILDERCOPYCONSTRUCTOR() {
		final ImmutableSettings a = new Blueprint().builderCopyConstructor(true).build();
		final ImmutableSettings b = new Blueprint().builderCopyConstructor(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BUILDERFLATMUTATORS() {
		final ImmutableSettings a = new Blueprint().builderFlatMutators(true).build();
		final ImmutableSettings b = new Blueprint().builderFlatMutators(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BUILDERFLUENTMUTATORS() {
		final ImmutableSettings a = new Blueprint().builderFluentMutators(true).build();
		final ImmutableSettings b = new Blueprint().builderFluentMutators(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BUILDERIMPLEMENTSINTERFACE() {
		final ImmutableSettings a = new Blueprint().builderImplementsInterface(true).build();
		final ImmutableSettings b = new Blueprint().builderImplementsInterface(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_BUILDERNAME() {
		final ImmutableSettings a = new Blueprint().builderName("Builder1").build();
		final ImmutableSettings b = new Blueprint().builderName("Builder2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_COPYMETHODS() {
		final ImmutableSettings a = new Blueprint().copyMethods(true).build();
		final ImmutableSettings b = new Blueprint().copyMethods(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FIELDPREFIX() {
		final ImmutableSettings a = new Blueprint().fieldPrefix("prefix1").build();
		final ImmutableSettings b = new Blueprint().fieldPrefix("prefix2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_FIELDS() {
		final List<Field> fields1 = Lists.newArrayList(new Field("field1", Type.of(String.class), Visibility.PRIVATE, Final.FINAL,
				Static.UNDEFINED, new ArrayList<Annotation>(0), "", AccessorPrefix.GET));
		final List<Field> fields2 = Lists.newArrayList(new Field("field2", Type.of(String.class), Visibility.PRIVATE, Final.FINAL,
				Static.UNDEFINED, new ArrayList<Annotation>(0), "", AccessorPrefix.GET));
		final ImmutableSettings a = new Blueprint().fields(fields1).build();
		final ImmutableSettings b = new Blueprint().fields(fields2).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_GUAVA() {
		final ImmutableSettings a = new Blueprint().guava(true).build();
		final ImmutableSettings b = new Blueprint().guava(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_HASHCODEANDEQUALS() {
		final ImmutableSettings a = new Blueprint().hashCodeAndEquals(true).build();
		final ImmutableSettings b = new Blueprint().hashCodeAndEquals(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_HASHCODEPRECOMPUTATION() {
		final ImmutableSettings a = new Blueprint().hashCodePrecomputation(true).build();
		final ImmutableSettings b = new Blueprint().hashCodePrecomputation(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_IMMUTABLENAME() {
		final ImmutableSettings a = new Blueprint().immutableName("Name1").build();
		final ImmutableSettings b = new Blueprint().immutableName("Name2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_IMPORTS() {
		final List<Import> imports1 = Lists.newArrayList(Import.of(String.class));
		final List<Import> imports2 = Lists.newArrayList(Import.of(String.class), Import.of(Long.class));
		final ImmutableSettings a = new Blueprint().imports(imports1).build();
		final ImmutableSettings b = new Blueprint().imports(imports2).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_INTERFACES() {
		final List<Interface> interfaces1 = Lists.newArrayList(new Interface(Type.of(Serializable.class)));
		final List<Interface> interfaces2 = Lists.newArrayList(new Interface(Type.of(Comparable.class)));
		final ImmutableSettings a = new Blueprint().interfaces(interfaces1).build();
		final ImmutableSettings b = new Blueprint().interfaces(interfaces2).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_JSR305ANNOTATIONS() {
		final ImmutableSettings a = new Blueprint().jsr305Annotations(true).build();
		final ImmutableSettings b = new Blueprint().jsr305Annotations(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_MAININTERFACE() {
		final ImmutableSettings a = new Blueprint().mainInterface(Interface.of("Interface1")).build();
		final ImmutableSettings b = new Blueprint().mainInterface(Interface.of("Interface2")).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PACKAGEDECLARATION() {
		final ImmutableSettings a = new Blueprint().packageDeclaration(new Package("com.github")).build();
		final ImmutableSettings b = new Blueprint().packageDeclaration(new Package("net.sf")).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_QUALITYCHECK() {
		final ImmutableSettings a = new Blueprint().qualityCheck(true).build();
		final ImmutableSettings b = new Blueprint().qualityCheck(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_REPLACEMENT() {
		final ImmutableSettings a = new Blueprint().replacement(true).build();
		final ImmutableSettings b = new Blueprint().replacement(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_SERIALIZABLE() {
		final ImmutableSettings a = new Blueprint().serializable(true).build();
		final ImmutableSettings b = new Blueprint().serializable(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_TOSTRING() {
		final ImmutableSettings a = new Blueprint().toString(true).build();
		final ImmutableSettings b = new Blueprint().toString(false).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final ImmutableSettings a = new Blueprint().build();
		final ImmutableSettings b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final ImmutableSettings a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final ImmutableSettings a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final ImmutableSettings a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_BUILDERNAME() {
		new Blueprint().builderName(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FIELDPREFIX() {
		new Blueprint().fieldPrefix(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_FIELDS() {
		new Blueprint().fields(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_IMMUTABLENAME() {
		new Blueprint().immutableName(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_IMPORTS() {
		new Blueprint().imports(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_INTERFACES() {
		new Blueprint().interfaces(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_MAININTERFACE() {
		new Blueprint().mainInterface(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PACKAGEDECLARATION() {
		new Blueprint().packageDeclaration(null).build();
	}

}
