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

@Immutable
public final class Attribute {

	@Nonnull
	private final List<Annotation> annotations;

	@Nonnull
	private final Final finalModifier;

	@Nonnull
	private final String name;

	@Nonnull
	private final Type type;

	public Attribute(@Nonnull final String name, @Nonnull final Type type, @Nonnull final Final finalModifier,
			@Nonnull final List<Annotation> annotations) {
		this.name = Check.notNull(name, "name");
		this.type = Check.notNull(type, "type");
		this.finalModifier = Check.notNull(finalModifier, "finalModifier");
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
		final Attribute other = (Attribute) obj;
		return Objects.equal(annotations, other.annotations) && Objects.equal(finalModifier, other.finalModifier)
				&& Objects.equal(name, other.name) && Objects.equal(type, other.type);
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Nonnull
	public Final getFinal() {
		return finalModifier;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(annotations, finalModifier, name, type);
	}

	@Override
	public String toString() {
		return "Attribute [annotations=" + annotations + ", final1=" + finalModifier + ", name=" + name + ", type=" + type + "]";
	}

}
