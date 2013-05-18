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
import static org.junit.Assert.assertNull;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Date;
import java.util.List;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

public class ImportsTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void allOf_clazz_isNull() {
		Imports.allOf((Clazz) null);
	}

	@Test
	public void allOf_clazz_successful() {
		final String clazzName = "TestClass";
		final Annotation nonnull = Annotation.NONNULL;
		final List<Annotation> annotations = Lists.newArrayList(nonnull);
		final Type type = Type.of(String.class);
		final Field field = new Field("field", type, Visibility.PRIVATE, Final.FINAL, Static.UNDEFINED, annotations, "", AccessorPrefix.GET);
		final List<Field> fields = Lists.newArrayList(field);
		final Attribute attribute = new Attribute("attribute", Type.of(List.class), Final.FINAL, annotations);
		final List<Attribute> attributes = Lists.newArrayList(attribute);
		final Constructor constructor = new Constructor(clazzName, attributes, Visibility.PUBLIC, annotations);
		final List<Constructor> constructors = Lists.newArrayList(constructor);
		final List<Method> methods = Lists.newArrayList();
		final Final finalModifier = Final.FINAL;
		final List<Interface> interfaces = Lists.newArrayList();
		final Visibility visibility = Visibility.PUBLIC;
		final Package packageDeclaration = new Package("com.github.before.qio");
		final Import clazzImport = Import.of(List.class);
		final List<Import> clazzImports = Lists.newArrayList(clazzImport);
		final Clazz clazz = new Clazz(clazzName, packageDeclaration, fields, constructors, methods, visibility, finalModifier,
				Abstract.UNDEFINED, interfaces, clazzImports, annotations);

		final Imports imports = Imports.allOf(clazz);

		final List<Import> expected = Lists.newArrayList();
		expected.add(Import.of(nonnull));
		expected.add(Import.of(List.class));
		assertEquals(expected, imports.filter().asList());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void allOf_field_isNull() {
		Imports.allOf((Field) null);
	}

	@Test
	public void allOf_field_successful() {
		final Annotation nonnull = Annotation.NONNULL;
		final List<Annotation> annotations = Lists.newArrayList(nonnull);
		final Type type = Type.of(String.class);
		final Field field = new Field("field", type, Visibility.PRIVATE, Final.FINAL, Static.UNDEFINED, annotations, "", AccessorPrefix.GET);

		final Imports imports = Imports.allOf(field);

		final List<Import> expected = Lists.newArrayList();
		expected.add(Import.of(type));
		expected.add(Import.of(nonnull));
		assertEquals(expected, imports.asList());
	}

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Imports.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

	@Test
	public void filter_java_lang() {
		assertEquals(0, Imports.of(Import.of(String.class)).filter().asList().size());
		assertEquals(0, Imports.of(Import.of(new Type("String"))).filter().asList().size());
		assertEquals(0, Imports.of(Import.of(new Type("java.lang.String"))).filter().asList().size());
	}

	@Test
	public void find() {
		final List<Import> set = ImmutableList.of(Import.of(String.class), Import.of(Date.class), Import.of(Integer.class));
		final Imports imports = Imports.copyOf(set);
		assertEquals(Import.of(String.class), imports.find("String"));
		assertEquals(Import.of(String.class), imports.find("java.lang.String"));
		assertEquals(Import.of(Date.class), imports.find("Date"));
		assertEquals(Import.of(Date.class), imports.find("java.util.Date"));
		assertEquals(Import.of(Integer.class), imports.find("Integer"));
		assertEquals(Import.of(Integer.class), imports.find("java.lang.Integer"));
	}

	@Test
	public void find_StringClass_withoutExplicitImport() {
		final List<Import> set = ImmutableList.of(Import.of(Date.class), Import.of(Integer.class));
		final Imports imports = Imports.copyOf(set);
		assertEquals(Import.of(String.class), imports.find("String"));
		assertEquals(Import.of(String.class), imports.find("java.lang.String"));
		assertEquals(Import.of(Date.class), imports.find("Date"));
		assertEquals(Import.of(Date.class), imports.find("java.util.Date"));
		assertEquals(Import.of(Integer.class), imports.find("Integer"));
		assertEquals(Import.of(Integer.class), imports.find("java.lang.Integer"));
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void find_typeName_isEmpty() {
		Imports.of(Import.of(String.class)).find("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void find_typeName_isNull() {
		Imports.of(Import.of(String.class)).find(null);
	}

	@Test
	public void find_typeName_notFound() {
		assertNull(Imports.of(Import.of(String.class)).find("Unknown"));
	}

}
