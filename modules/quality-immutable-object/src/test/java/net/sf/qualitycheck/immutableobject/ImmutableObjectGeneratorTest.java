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
package net.sf.qualitycheck.immutableobject;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;

import org.junit.Before;
import org.junit.Test;

import com.google.common.io.CharStreams;

public class ImmutableObjectGeneratorTest {

	private final static String JAVA_ENDING = ".java";

	private ImmutableSettings.Builder settingsBuilder;

	@Before
	public void before() {
		settingsBuilder = new ImmutableSettings.Builder();

		// global settings
		settingsBuilder.fieldPrefix("");
		settingsBuilder.jsr305Annotations(false);
		settingsBuilder.guava(false);
		settingsBuilder.qualityCheck(false);

		// immutable settings
		settingsBuilder.serializable(false);

		// builder settings
		settingsBuilder.builderCopyConstructor(false);
		settingsBuilder.builderFlatMutators(false);
		settingsBuilder.builderFluentMutators(false);
		settingsBuilder.builderName("");
		settingsBuilder.builderImplementsInterface(false);

	}

	private String readInterfaceAndGenerate(final String name, final ImmutableSettings settings) throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		return ImmutableObjectGenerator.generate(CharStreams.toString(new InputStreamReader(stream)), settings).getImplCode();
	}

	private String readInterfaceAndGenerateTest(final String name, final ImmutableSettings settings) throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		return ImmutableObjectGenerator.generate(CharStreams.toString(new InputStreamReader(stream)), settings).getTestCode();
	}

	private String readReferenceImmutable(final String name) throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream("Immutable" + name);
		return CharStreams.toString(new InputStreamReader(stream));
	}

	private String readReferenceImmutableTest(final String name) throws IOException {
		final String nameWithOutJava = name.substring(0, name.length() - JAVA_ENDING.length());

		final InputStream stream = getClass().getClassLoader().getResourceAsStream("Immutable" + nameWithOutJava + "Test" + JAVA_ENDING);
		return CharStreams.toString(new InputStreamReader(stream));
	}

	@Test
	public void renderingOf_builder() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List<String> getNames();\n");
		b.append("}");

		final ImmutableSettings settingsWithoutBuilder = settingsBuilder.builderName("").build();
		final String generatedCodeWithoutBuilder = ImmutableObjectGenerator.generate(b.toString(), settingsWithoutBuilder).getImplCode();
		assertFalse(Pattern.compile("public\\s+static\\s+final\\s+class\\s+").matcher(generatedCodeWithoutBuilder).find());

		final ImmutableSettings settingsWithBuilder = settingsBuilder.builderName("Builder").build();
		final String generatedCodeWithBuilder = ImmutableObjectGenerator.generate(b.toString(), settingsWithBuilder).getImplCode();
		assertTrue(Pattern.compile("public\\s+static\\s+final\\s+class\\s+Builder\\s+").matcher(generatedCodeWithBuilder).find());
	}

	@Test
	public void renderingOf_copyMethods() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");
		final ImmutableSettings settingsWithCopyMethods = settingsBuilder.copyMethods(true).build();
		final String codeWithCopyMethods = ImmutableObjectGenerator.generate(b.toString(), settingsWithCopyMethods).getImplCode();
		assertTrue(codeWithCopyMethods.contains("public static ImmutableTestObject copyOf(final TestObject testObject) {"));
		assertTrue(codeWithCopyMethods.contains("return new ImmutableTestObject(testObject.getName());"));
		assertTrue(codeWithCopyMethods.contains("public static ImmutableTestObject copyOnlyIfNecessary(final TestObject testObject) {"));
		assertTrue(codeWithCopyMethods
				.contains("return testObject instanceof ImmutableTestObject ? (ImmutableTestObject) testObject : copyOf(testObject);"));

		final ImmutableSettings settingsWithoutCopyMethods = settingsBuilder.copyMethods(false).build();
		final String codeWithoutCopyMethods = ImmutableObjectGenerator.generate(b.toString(), settingsWithoutCopyMethods).getImplCode();
		assertFalse(codeWithoutCopyMethods.contains("public static ImmutableTestObject copyOf(final TestObject testObject) {"));
		assertFalse(codeWithoutCopyMethods.contains("return new ImmutableTestObject(testObject.getName());"));
		assertFalse(codeWithoutCopyMethods.contains("public static ImmutableTestObject copyOnlyIfNecessary(final TestObject testObject) {"));
		assertFalse(codeWithoutCopyMethods
				.contains("return testObject instanceof ImmutableTestObject ? (ImmutableTestObject) testObject : copyOf(testObject);"));
	}

	@Test
	public void renderingOf_fieldPrefix() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.fieldPrefix("_").build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("private final String _name;"));
	}

	@Test
	public void renderingOf_fieldPrefix_withHashCodePrecomputation() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.fieldPrefix("_").hashCodePrecomputation(true).hashCodeAndEquals(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		System.out.println(generatedCode);
		assertTrue(generatedCode.contains("private final String _name;"));
		assertTrue(generatedCode.contains("private final int _hash;"));
		assertTrue(generatedCode.contains("_hash = buildHashCode(_name);"));
		assertTrue(generatedCode.contains("return _hash;"));
	}

	@Test
	public void renderingOf_guava() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List<String> getNames();\n");
		b.append("}");

		final ImmutableSettings settingsWithGuava = settingsBuilder.guava(true).hashCodeAndEquals(true).build();
		final String generatedCodeWithGuava = ImmutableObjectGenerator.generate(b.toString(), settingsWithGuava).getImplCode();
		assertTrue(generatedCodeWithGuava.contains("this.names = ImmutableList.copyOf(names);"));
		assertTrue(generatedCodeWithGuava.contains("return Objects.equal(this.names, other.names);"));
		assertTrue(generatedCodeWithGuava.contains("return Objects.hashCode(names);"));

		final ImmutableSettings settingsWithoutGuava = settingsBuilder.guava(false).hashCodeAndEquals(true).build();
		final String generatedCodeWithoutGuava = ImmutableObjectGenerator.generate(b.toString(), settingsWithoutGuava).getImplCode();
		assertTrue(generatedCodeWithoutGuava.contains("this.names = Collections.unmodifiableList(new ArrayList<String>(names));"));
		assertTrue(generatedCodeWithoutGuava.contains("} else if (!names.equals(other.names))"));
		assertTrue(generatedCodeWithoutGuava.contains("result = prime * result + (names == null ? 0 : names.hashCode());"));
	}

	@Test
	public void renderingOf_hashCodeAndEquals() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");

		final ImmutableSettings settingsWithHaE = settingsBuilder.hashCodeAndEquals(true).build();
		final String generatedCodeWithHaE = ImmutableObjectGenerator.generate(b.toString(), settingsWithHaE).getImplCode();
		assertTrue(generatedCodeWithHaE.contains("public boolean equals(final Object obj) {"));
		assertTrue(generatedCodeWithHaE.contains("public int hashCode() {"));

		final ImmutableSettings settingsWithoutHaE = settingsBuilder.hashCodeAndEquals(false).build();
		final String generatedCodeWithoutHaE = ImmutableObjectGenerator.generate(b.toString(), settingsWithoutHaE).getImplCode();
		assertFalse(generatedCodeWithoutHaE.contains("public boolean equals(final Object obj) {"));
		assertFalse(generatedCodeWithoutHaE.contains("public int hashCode() {"));
	}

	@Test
	public void renderingOf_hashCodePrecomputation() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("List<String> getNames();\n");
		b.append("}");

		final ImmutableSettings settingsWithHashCodePrecomp = settingsBuilder.hashCodePrecomputation(true).hashCodeAndEquals(true).build();
		final String withHashCodePrecomp = ImmutableObjectGenerator.generate(b.toString(), settingsWithHashCodePrecomp).getImplCode();
		assertTrue(withHashCodePrecomp.contains("private static int buildHashCode(final String name, final List<String> names) {"));
		assertTrue(withHashCodePrecomp.contains("private final int hash;"));
		assertTrue(withHashCodePrecomp.contains("hash = buildHashCode(name, names);"));
		assertTrue(withHashCodePrecomp.contains("return hash;"));

		final ImmutableSettings settingsWithoutHashCodePrecomp = settingsBuilder.hashCodePrecomputation(false).hashCodeAndEquals(true)
				.build();
		final String withoutHashCodePrecomp = ImmutableObjectGenerator.generate(b.toString(), settingsWithoutHashCodePrecomp).getImplCode();
		assertFalse(withoutHashCodePrecomp.contains("private static int buildHashCode(final String name, final List<String> names) {"));
		assertFalse(withoutHashCodePrecomp.contains("private final int hash;"));
		assertFalse(withoutHashCodePrecomp.contains("hash = buildHashCode(name, names);"));
		assertFalse(withoutHashCodePrecomp.contains("return hash;"));
	}

	@Test
	public void renderingOf_jsr305Annotations() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import javax.annotation.Nonnull;");
		b.append("interface TestObject {\n");
		b.append("@Nonnull String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.jsr305Annotations(true).copyMethods(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("@Immutable\npublic final class"));
		assertTrue(generatedCode.contains("@Nonnull\n\tpublic static ImmutableTestObject copyOf(@Nonnull "));
		assertTrue(generatedCode.contains("public ImmutableTestObject(@Nonnull "));
		assertTrue(generatedCode.contains("@Nonnull\n\tprivate final String name;"));
		assertTrue(generatedCode.contains("@Nonnull\n\tpublic String getName() {"));
	}

	@Test
	public void renderingOf_packageDeclaration() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");

		final String generatedCodeWithUndefinedPackage = ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build())
				.getImplCode();
		assertFalse(generatedCodeWithUndefinedPackage.contains("package "));

		final String withPackage = "package net.sf.qualitycheck;\n";
		final String generatedCodeWithPackage = ImmutableObjectGenerator.generate(withPackage + b.toString(), settingsBuilder.build())
				.getImplCode();
		assertTrue(generatedCodeWithPackage.contains(withPackage));
	}

	@Test
	public void renderingOf_qualityCheck() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import javax.annotation.Nonnull;");
		b.append("interface TestObject {\n");
		b.append("@Nonnull String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.qualityCheck(true).copyMethods(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("Check.notNull(testObject, \"testObject\");"));
		assertTrue(generatedCode.contains("this.name = Check.notNull(name, \"name\");"));
	}

	@Test
	public void renderingOf_replacement() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.replacement(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("class TestObject {"));
		assertTrue(generatedCode.contains("public TestObject(final String name) {"));
	}

	@Test
	public void renderingOf_replacement_serializable() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.replacement(true).serializable(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("class TestObject implements Serializable {"));
		assertTrue(generatedCode.contains("private static final long serialVersionUID = 1L;"));
		assertTrue(generatedCode.contains("public TestObject(final String name) {"));
	}

	@Test
	public void renderingOf_serializable() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.serializable(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("class ImmutableTestObject implements TestObject, Serializable {"));
		assertTrue(generatedCode.contains("private static final long serialVersionUID = 1L;"));
	}

	@Test
	public void renderingOf_toString() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List<String> getNames();\n");
		b.append("int getCount();\n");
		b.append("}");

		final ImmutableSettings settingsWithToString = settingsBuilder.toString(true).hashCodeAndEquals(true).build();
		final String generatedCodeWithToString = ImmutableObjectGenerator.generate(b.toString(), settingsWithToString).getImplCode();
		assertTrue(generatedCodeWithToString.contains("public String toString() {"));
		assertTrue(generatedCodeWithToString
				.contains("return \"ImmutableTestObject [names=\" + this.names + \", count=\" + this.count + \"]\";"));

		final ImmutableSettings settingsWithoutToString = settingsBuilder.toString(false).hashCodeAndEquals(true).build();
		final String generatedCodeWithoutToString = ImmutableObjectGenerator.generate(b.toString(), settingsWithoutToString).getImplCode();
		assertFalse(generatedCodeWithoutToString.contains("public String toString() {"));
	}

	@Test
	public void renderingOf_withBuilder_withBuilderCopyConstructor() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List<String> getNamesOfFields();\n");
		b.append("}");

		final ImmutableSettings settings = settingsBuilder.builderName("Builder").builderCopyConstructor(true).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("public Builder(final TestObject testObject) {"));
		assertTrue(generatedCode.contains("this.namesOfFields = new ArrayList<String>(testObject.getNamesOfFields());"));
	}

	@Test
	public void renderingOf_withBuilder_withoutBuilderCopyConstructor() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List<String> getNamesOfFields();\n");
		b.append("}");

		final ImmutableSettings settings = settingsBuilder.builderName("Builder").builderCopyConstructor(false).build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertFalse(generatedCode.contains("public Builder(final TestObject testObject) {"));
	}

	@Test
	public void renderingOf_withCollection_withoutGeneric() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List getElements();\n");
		b.append("}");
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build()).getImplCode();
		assertTrue(generatedCode.contains("private final List elements;"));
		assertTrue(generatedCode.contains("this.elements = Collections.unmodifiableList(new ArrayList(elements));"));
		assertTrue(generatedCode.contains("public List getElements() {"));
	}

	@Test
	public void renderingOf_withConstants() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("String DEFAULT_NAME = \"default\";\n");
		b.append("String getName();\n");
		b.append("}");
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build()).getImplCode();
		assertFalse(generatedCode.contains("DEFAULT_NAME"));
	}

	@Test
	public void renderingOf_withCovariance() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("List<? extends TestObject> getChildren();\n");
		b.append("}");
		final ImmutableSettings settings = settingsBuilder.fieldPrefix("_").build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("private final List<? extends TestObject> _children;"));
		assertTrue(generatedCode.contains("public ImmutableTestObject(final List<? extends TestObject> children) {"));
		assertTrue(generatedCode.contains("this._children = Collections.unmodifiableList(new ArrayList<? extends TestObject>(children));"));
		assertTrue(generatedCode.contains("public List<? extends TestObject> getChildren() {"));
	}

	@Test
	public void renderingOf_withGeneric() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("List<String> getNames();\n");
		b.append("}");
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build()).getImplCode();
		assertTrue(generatedCode.contains("private final List<String> names;"));
		assertTrue(generatedCode.contains("public List<String> getNames() {"));
	}

	@Test
	public void renderingOf_withHashCodeAndEquals_withFieldPrefix() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("List<String> getNamesOfFields();\n");
		b.append("}");

		settingsBuilder.hashCodeAndEquals(true);
		settingsBuilder.fieldPrefix("_PREFIX_");
		final ImmutableSettings settings = settingsBuilder.build();
		final String code = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertFalse(code.contains("private static int buildHashCode(final String name, final List<String> namesOfFields) {"));
		assertTrue(code.contains("result = prime * result + (_PREFIX_name == null ? 0 : _PREFIX_name.hashCode());"));
		assertTrue(code.contains("result = prime * result + (_PREFIX_namesOfFields == null ? 0 : _PREFIX_namesOfFields.hashCode());"));
	}

	@Test
	public void renderingOf_withHashCodeAndEquals_withFieldPrefix_withGuava() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("List<String> getNamesOfFields();\n");
		b.append("}");

		settingsBuilder.hashCodeAndEquals(true);
		settingsBuilder.fieldPrefix("_PREFIX_");
		settingsBuilder.guava(true);
		final ImmutableSettings settings = settingsBuilder.build();
		final String code = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertFalse(code.contains("private static int buildHashCode(final String name, final List<String> namesOfFields) {"));
		assertTrue(code.contains("return Objects.hashCode(_PREFIX_name, _PREFIX_namesOfFields);"));
	}

	@Test
	public void renderingOf_withHashCodePrecomputation_withFieldPrefix() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("List<String> getNamesOfFields();\n");
		b.append("}");

		settingsBuilder.hashCodeAndEquals(true);
		settingsBuilder.hashCodePrecomputation(true);
		settingsBuilder.fieldPrefix("_PREFIX_");
		final ImmutableSettings settings = settingsBuilder.build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("private static int buildHashCode(final String name, final List<String> namesOfFields) {"));
		assertTrue(generatedCode.contains("result = prime * result + (name == null ? 0 : name.hashCode());"));
		assertTrue(generatedCode.contains("result = prime * result + (namesOfFields == null ? 0 : namesOfFields.hashCode());"));
	}

	@Test
	public void renderingOf_withHashCodePrecomputation_withFieldPrefix_withGuava() {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("import java.util.List;\n");
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("List<String> getNamesOfFields();\n");
		b.append("}");

		settingsBuilder.hashCodeAndEquals(true);
		settingsBuilder.hashCodePrecomputation(true);
		settingsBuilder.fieldPrefix("_PREFIX_");
		settingsBuilder.guava(true);
		final ImmutableSettings settings = settingsBuilder.build();
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settings).getImplCode();
		assertTrue(generatedCode.contains("private static int buildHashCode(final String name, final List<String> namesOfFields) {"));
		assertTrue(generatedCode.contains("return Objects.hashCode(name, namesOfFields);"));
	}

	@Test
	public void renderingOf_withInnerCompilationUnit() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("interface InnerInterface {\n");
		b.append("String getName();\n");
		b.append("}\n");
		b.append("InnerInterface getInnerInterface();\n");
		b.append("}");
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build()).getImplCode();
		assertTrue(generatedCode.contains("private final InnerInterface innerInterface;"));
		assertTrue(generatedCode.contains("public InnerInterface getInnerInterface()"));
	}

	@Test
	public void renderingOf_withReservedWord() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("package net.sf.qualitycheck.test;\n");
		b.append("interface TestObject {\n");
		b.append("Import getImport();\n");
		b.append("}");
		final String generatedCode = ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build()).getImplCode();
		assertTrue(generatedCode.contains("private final Import import1;"));
		assertTrue(generatedCode.contains("ImmutableTestObject(final Import import1)"));
		assertTrue(generatedCode.contains("public Import getImport() {"));
	}

	@Test
	public void testCarInterface() throws IOException {
		final ImmutableSettings.Builder settings = new ImmutableSettings.Builder();

		// global settings
		settings.fieldPrefix("");
		settings.jsr305Annotations(true);
		settings.guava(false);
		settings.qualityCheck(true);

		// immutable settings
		settings.copyMethods(true);
		settings.hashCodePrecomputation(false);
		settings.hashCodeAndEquals(true);
		settings.serializable(false);

		// builder settings
		settings.builderCopyConstructor(true);
		settings.builderFlatMutators(false);
		settings.builderFluentMutators(true);
		settings.builderName("Builder");
		settings.builderImplementsInterface(true);

		final String file = "Car.java";
		assertEquals(readReferenceImmutable(file).replace("\r", ""), readInterfaceAndGenerate(file, settings.build()).replace("\r", ""));
	}

	@Test
	public void testCarInterface_TestGeneration() throws IOException {
		final ImmutableSettings.Builder settings = new ImmutableSettings.Builder();

		// global settings
		settings.fieldPrefix("");
		settings.jsr305Annotations(true);
		settings.guava(false);
		settings.qualityCheck(true);

		// immutable settings
		settings.copyMethods(true);
		settings.hashCodePrecomputation(false);
		settings.hashCodeAndEquals(true);
		settings.serializable(false);

		// builder settings
		settings.builderCopyConstructor(true);
		settings.builderFlatMutators(true);
		settings.builderFluentMutators(true);
		settings.builderName("Builder");
		settings.builderImplementsInterface(true);

		final String file = "Car.java";
		System.out.println(readInterfaceAndGenerateTest(file, settings.build()).replace("\r", ""));
		assertEquals(readReferenceImmutableTest(file).replace("\r", ""),
				readInterfaceAndGenerateTest(file, settings.build()).replace("\r", ""));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void throwsException_hasPossibleMutatingMethods_withGetterArguments() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("interface TestObject {\n");
		b.append("String getName(int index);\n");
		b.append("}");
		ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void throwsException_hasPossibleMutatingMethods_withSetter() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("interface TestObject {\n");
		b.append("void setName(String name);\n");
		b.append("}");
		ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void throwsException_moreThenOneCompilationUnit() throws IOException {
		final StringBuilder b = new StringBuilder();
		b.append("interface TestObject {\n");
		b.append("String getName();\n");
		b.append("}\n");
		b.append("interface TestObject2 {\n");
		b.append("String getName();\n");
		b.append("}");
		ImmutableObjectGenerator.generate(b.toString(), settingsBuilder.build());
	}

}
