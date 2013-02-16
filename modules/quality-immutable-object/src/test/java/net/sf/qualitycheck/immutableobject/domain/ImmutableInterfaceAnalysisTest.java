package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.Lists;

public final class ImmutableInterfaceAnalysisTest {

	private static final class Blueprint {

		private List<Annotation> annotations = Lists.newArrayList();

		private List<Interface> extends1 = Lists.newArrayList();

		private List<Import> imports = Lists.newArrayList();

		private String interfaceName = "TestInterface";

		private List<Method> methods = Lists.newArrayList();

		private Package package1 = Package.UNDEFINED;

		public Blueprint() {
			// default constructor
		}

		public Blueprint annotations(final List<Annotation> annotations) {
			this.annotations = annotations;
			return this;
		}

		@Nonnull
		public ImmutableInterfaceAnalysis build() {
			return new ImmutableInterfaceAnalysis(annotations, extends1, imports, interfaceName, methods, package1);
		}

		public Blueprint extends1(final List<Interface> extends1) {
			this.extends1 = extends1;
			return this;
		}

		public Blueprint imports(final List<Import> imports) {
			this.imports = imports;
			return this;
		}

		public Blueprint interfaceName(final String interfaceName) {
			this.interfaceName = interfaceName;
			return this;
		}

		public Blueprint methods(final List<Method> methods) {
			this.methods = methods;
			return this;
		}

		public Blueprint pkg(final Package package1) {
			this.package1 = package1;
			return this;
		}

	}

	@Test
	public void equals_different_ANNOTATIONS() {
		final ImmutableInterfaceAnalysis a = new Blueprint().annotations(Lists.newArrayList(Annotation.IMMUTABLE)).build();
		final ImmutableInterfaceAnalysis b = new Blueprint().annotations(Lists.newArrayList(Annotation.NOT_THREAD_SAFE)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_EXTENDS() {
		final ImmutableInterfaceAnalysis a = new Blueprint().extends1(Lists.newArrayList(Interface.of(Serializable.class))).build();
		final ImmutableInterfaceAnalysis b = new Blueprint().extends1(new ArrayList<Interface>(0)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_IMPORTS() {
		final ImmutableInterfaceAnalysis a = new Blueprint().imports(Lists.newArrayList(Import.of(Collections.class))).build();
		final ImmutableInterfaceAnalysis b = new Blueprint().imports(Lists.newArrayList(Import.of(ArrayList.class))).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_INTERFACENAME() {
		final ImmutableInterfaceAnalysis a = new Blueprint().interfaceName("test1").build();
		final ImmutableInterfaceAnalysis b = new Blueprint().interfaceName("test2").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_METHODS() {
		final List<Annotation> annotations = Lists.newArrayList();
		final List<Attribute> attributes = Lists.newArrayList();
		final Method m = new Method("testMethod", ReturnType.INT, Method.NOT_IMPLEMENTED, attributes, Visibility.PUBLIC, Final.FINAL,
				Static.STATIC, annotations);
		final ImmutableInterfaceAnalysis a = new Blueprint().methods(Lists.newArrayList(m)).build();
		final ImmutableInterfaceAnalysis b = new Blueprint().methods(new ArrayList<Method>(0)).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_PACKAGE() {
		final ImmutableInterfaceAnalysis a = new Blueprint().pkg(new Package("net.org.dev")).build();
		final ImmutableInterfaceAnalysis b = new Blueprint().pkg(new Package("org.net.dev")).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final ImmutableInterfaceAnalysis a = new Blueprint().build();
		final ImmutableInterfaceAnalysis b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final ImmutableInterfaceAnalysis a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final ImmutableInterfaceAnalysis a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final ImmutableInterfaceAnalysis a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_ANNOTATIONS() {
		new Blueprint().annotations(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_EXTENDS() {
		new Blueprint().extends1(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_IMPORTS() {
		new Blueprint().imports(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_INTERFACENAME() {
		new Blueprint().interfaceName(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_METHODS() {
		new Blueprint().methods(null).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_PACKAGE() {
		new Blueprint().pkg(null).build();
	}

}
