package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.util.MethodUtil;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

@Immutable
public final class Field {

	/**
	 * Represents an undefined default value of a field
	 */
	public static final String WITHOUT_VALUE = "";

	@Nonnull
	private transient final String accessorMethodName;

	@Nonnull
	private final AccessorPrefix accessorPrefix;

	@Nonnull
	private final List<Annotation> annotations;

	@Nonnull
	private final Final finalModifier;

	@Nonnull
	private transient final String mutatorMethodName;

	@Nonnull
	private final String name;

	@Nonnull
	private final Static staticModifier;

	@Nonnull
	private final Type type;

	@Nonnull
	private final String value;

	@Nonnull
	private final Visibility visibility;

	public Field(@Nonnull final String name, @Nonnull final Type type, @Nonnull final Visibility visibility,
			@Nonnull final Final finalModifier, @Nonnull final Static staticModifier, @Nonnull final List<Annotation> annotations,
			@Nonnull final String value, @Nonnull final AccessorPrefix accessorPrefix) {
		this.name = Check.notNull(name, "name");
		this.type = Check.notNull(type, "type");
		this.visibility = Check.notNull(visibility, "visibility");
		this.finalModifier = Check.notNull(finalModifier, "finalModifier");
		this.staticModifier = Check.notNull(staticModifier, "staticModifier");
		this.annotations = ImmutableList.copyOf(Check.notNull(annotations, "annotations"));
		this.value = Check.notNull(value, "value");
		this.accessorPrefix = Check.notNull(accessorPrefix, "accessorPrefix");

		accessorMethodName = MethodUtil.determineAccessorName(accessorPrefix, name);
		mutatorMethodName = MethodUtil.determineMutatorName(name);
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
		final Field other = (Field) obj;
		return Objects.equal(accessorPrefix, other.accessorPrefix) && Objects.equal(annotations, other.annotations)
				&& Objects.equal(finalModifier, other.finalModifier) && Objects.equal(name, other.name)
				&& Objects.equal(staticModifier, other.staticModifier) && Objects.equal(type, other.type)
				&& Objects.equal(value, other.value) && Objects.equal(visibility, other.visibility);
	}

	public String getAccessorMethodName() {
		return accessorMethodName;
	}

	@Nonnull
	public AccessorPrefix getAccessorPrefix() {
		return accessorPrefix;
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Nonnull
	public Final getFinal() {
		return finalModifier;
	}

	public String getMutatorMethodName() {
		return mutatorMethodName;
	}

	@Nonnull
	public String getName() {
		return name;
	}

	@Nonnull
	public Static getStatic() {
		return staticModifier;
	}

	@Nonnull
	public Type getType() {
		return type;
	}

	@Nonnull
	public String getValue() {
		return value;
	}

	@Nonnull
	public Visibility getVisibility() {
		return visibility;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(accessorPrefix, annotations, finalModifier, name, staticModifier, type, value, visibility);
	}

	public boolean isNonnegative() {
		return annotations.contains(Annotation.NONNEGATIVE);
	}

	public boolean isNonnull() {
		return annotations.contains(Annotation.NONNULL);
	}

	public boolean isNullable() {
		return annotations.contains(Annotation.NULLABLE) || !(isNonnull() || isNonnegative());
	}

	@Override
	public String toString() {
		return "Field [accessorPrefix=" + accessorPrefix + ", annotations=" + annotations + ", final1=" + finalModifier + ", name=" + name
				+ ", static1=" + staticModifier + ", type=" + type + ", value=" + value + ", visibility=" + visibility + "]";
	}

}
