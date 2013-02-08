package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

@Immutable
public final class Attribute implements Characters {

	private final List<Annotation> _annotations;

	private final Final _final;

	private final String _name;

	private final Type _type;

	public Attribute(@Nonnull final String name, @Nonnull final Type type, @Nonnull final Final finalModifier,
			@Nonnull final List<Annotation> annotations) {
		_name = Check.notNull(name, "name");
		_type = Check.notNull(type, "type");
		_final = Check.notNull(finalModifier, "finalModifier");
		_annotations = Check.notNull(annotations, "annotations");
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
		if (!_annotations.equals(other._annotations)) {
			return false;
		}
		if (_final != other._final) {
			return false;
		}
		if (!_name.equals(other._name)) {
			return false;
		}
		if (!_type.equals(other._type)) {
			return false;
		}
		return true;
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return _annotations;
	}

	@Nonnull
	public Final getFinal() {
		return _final;
	}

	@Nonnull
	public String getName() {
		return _name;
	}

	@Nonnull
	public Type getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _annotations.hashCode();
		result = prime * result + _final.hashCode();
		result = prime * result + _name.hashCode();
		result = prime * result + _type.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		if (!_annotations.isEmpty()) {
			for (final Annotation annotation : _annotations) {
				b.append(AT_SIGN);
				b.append(annotation.getType().getName());
				b.append(SPACE);
			}
		}
		if (_final != Final.UNDEFINED) {
			b.append(_final.getName());
			b.append(SPACE);
		}
		b.append(_type.getName());
		if (_type.getGenericDeclaration() != GenericDeclaration.UNDEFINED) {
			b.append(BRACKET_OPEN);
			b.append(_type.getGenericDeclaration());
			b.append(BRACKET_CLOSE);
		}
		b.append(SPACE);
		b.append(_name);
		return b.toString();
	}

}
