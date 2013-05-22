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
public final class Constructor {

	@Nonnull
	private final List<Annotation> annotations;

	@Nonnull
	private final List<Attribute> attributes;

	@Nonnull
	private final String name;

	@Nonnull
	private final Visibility visibility;

	public Constructor(@Nonnull final String name, @Nonnull final List<Attribute> attributes, @Nonnull final Visibility visibility,
			@Nonnull final List<Annotation> annotations) {
		this.name = Check.notEmpty(name, "name");
		this.attributes = ImmutableList.copyOf(Check.notNull(attributes, "attributes"));
		this.visibility = Check.notNull(visibility, "visibility");
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
		final Constructor other = (Constructor) obj;
		return Objects.equal(name, other.name) && Objects.equal(attributes, other.attributes)
				&& Objects.equal(visibility, other.visibility) && Objects.equal(annotations, other.annotations);
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
	public String getName() {
		return name;
	}

	@Nonnull
	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, attributes, visibility, annotations);
	}

	@Override
	public String toString() {
		return "Constructor [name=" + name + ", attributes=" + attributes + ", visibility=" + visibility + ", annotations=" + annotations
				+ "]";
	}

}
