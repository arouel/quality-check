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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

@Immutable
public final class Clazz {

	@Nonnull
	private final Abstract abstractModifier;

	@Nonnull
	private final List<Annotation> annotations;

	@Nonnull
	private final List<Constructor> constructors;

	@Nonnull
	private final List<Field> fields;

	@Nonnull
	private final Final finalModifier;

	@Nonnull
	private final List<Import> imports;

	@Nonnull
	private final List<Interface> interfaces;

	@Nonnull
	private final List<Method> methods;

	@Nonnull
	private final String name;

	@Nonnull
	private final Package package1;

	@Nonnull
	private final Visibility visibility;

	public Clazz(@Nonnull final String name, @Nonnull final Package package1, @Nonnull final List<Field> fields,
			@Nonnull final List<Constructor> constructors, @Nonnull final List<Method> methods, @Nonnull final Visibility visibility,
			@Nonnull final Final final1, @Nonnull final Abstract abstract1, @Nonnull final List<Interface> interfaces,
			@Nonnull final List<Import> imports, @Nonnull final List<Annotation> annotations) {
		this.name = Check.notEmpty(name, "name");
		this.package1 = Check.notNull(package1, "package1");
		this.fields = ImmutableList.copyOf(Check.notNull(fields, "fields"));
		this.constructors = ImmutableList.copyOf(Check.notNull(constructors, "constructors"));
		this.methods = ImmutableList.copyOf(Check.notNull(methods, "methods"));
		this.visibility = Check.notNull(visibility, "visibility");
		finalModifier = Check.notNull(final1, "final1");
		abstractModifier = Check.notNull(abstract1, "abstract1");
		this.interfaces = ImmutableList.copyOf(Check.notNull(interfaces, "interfaces"));
		this.imports = ImmutableList.copyOf(Check.notNull(imports, "imports"));

		// TODO find a nice solution
		Check.notNull(annotations, "annotations");
		this.annotations = ImmutableList.copyOf(Annotations.of(annotations).removeUnqualified(imports).getAnnotations());

		Check.stateIsTrue(!abstractAndFinal(), "A class can be either abstract or final, not both.");
	}

	private boolean abstractAndFinal() {
		return abstractModifier == Abstract.ABSTRACT && finalModifier == Final.FINAL ? true : false;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Clazz other = (Clazz) obj;
		return Objects.equal(name, other.name) && Objects.equal(package1, other.package1) && Objects.equal(fields, other.fields)
				&& Objects.equal(constructors, other.constructors) && Objects.equal(methods, other.methods)
				&& Objects.equal(visibility, other.visibility) && Objects.equal(finalModifier, other.finalModifier)
				&& Objects.equal(abstractModifier, other.abstractModifier) && Objects.equal(interfaces, other.interfaces)
				&& Objects.equal(imports, other.imports) && Objects.equal(annotations, other.annotations);
	}

	@Nonnull
	public Abstract getAbstract() {
		return abstractModifier;
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Nonnull
	public List<Constructor> getConstructors() {
		return constructors;
	}

	@Nonnull
	public List<Field> getFields() {
		return fields;
	}

	@Nonnull
	public Final getFinal() {
		return finalModifier;
	}

	@Nonnull
	public List<Import> getImports() {
		return Imports.copyOf(imports).copyAndAdd(Imports.allOf(this)).filter().sortByName().asList();
	}

	@Nonnull
	public List<Interface> getInterfaces() {
		return interfaces;
	}

	@Nonnull
	public List<Method> getMethods() {
		return methods;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public Package getPackage() {
		return package1;
	}

	@Nonnull
	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, package1, fields, constructors, methods, visibility, finalModifier, abstractModifier, interfaces,
				imports, annotations);
	}

	@Override
	public String toString() {
		return "Clazz [name=" + name + ", package1=" + package1 + ", fields=" + fields + ", constructors=" + constructors + ", methods="
				+ methods + ", visibility=" + visibility + ", final1=" + finalModifier + ", abstract1=" + abstractModifier
				+ ", interfaces=" + interfaces + ", imports=" + imports + ", annotations=" + annotations + "]";
	}

}
