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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

/**
 * Represents a method in a class
 */
@Immutable
public final class Method {

	@Nonnull
	private final String name;

	@Nonnull
	private final ReturnType returnType;

	@Nonnull
	private final List<Attribute> attributes;

	@Nonnull
	private final Visibility visibility;

	@Nonnull
	private final Final final1;

	@Nonnull
	private final Static static1;

	@Nonnull
	private final List<Annotation> annotations;

	public Method(@Nonnull final String name, @Nonnull final ReturnType returnType, @Nonnull final List<Attribute> attributes,
			@Nonnull final Visibility visibility, @Nonnull final Final final1, @Nonnull final Static static1,
			@Nonnull final List<Annotation> annotations) {
		this.name = Check.notEmpty(name, "name");
		this.returnType = Check.notNull(returnType, "returnType");
		this.attributes = ImmutableList.copyOf(Check.notNull(attributes, "attributes"));
		this.visibility = Check.notNull(visibility, "visibility");
		this.final1 = Check.notNull(final1, "final1");
		this.static1 = Check.notNull(static1, "static1");
		this.annotations = ImmutableList.copyOf(Check.notNull(annotations, "annotations"));
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
		final Method other = (Method) obj;
		return Objects.equal(name, other.name) && Objects.equal(returnType, other.returnType)
				&& Objects.equal(attributes, other.attributes) && Objects.equal(visibility, other.visibility)
				&& Objects.equal(final1, other.final1) && Objects.equal(static1, other.static1)
				&& Objects.equal(annotations, other.annotations);
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Nonnull
	public List<Attribute> getAttributes() {
		return attributes;
	}

	@Nonnull
	public Final getFinal() {
		return final1;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public ReturnType getReturnType() {
		return returnType;
	}

	@Nonnull
	public Static getStatic() {
		return static1;
	}

	@Nonnull
	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, returnType, attributes, visibility, final1, static1, annotations);
	}

	@Override
	public String toString() {
		return "Method [name=" + name + ", returnType=" + returnType + ", attributes=" + attributes + ", visibility=" + visibility
				+ ", final1=" + final1 + ", static1=" + static1 + ", annotations=" + annotations + "]";
	}

}
