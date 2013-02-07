package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Joiner;

/**
 * Represents a constructor of a class
 */
@Immutable
public final class Constructor implements Characters {

	/**
	 * Represents the default content of an unimplemented method
	 */
	public static final String NOT_IMPLEMENTED = Method.NOT_IMPLEMENTED;

	@Nonnull
	private final List<Annotation> _annotations;

	@Nonnull
	private final List<Attribute> _attributes;

	@Nonnull
	private final String _content;

	@Nonnull
	private final String _name;

	@Nonnull
	private final Visibility _visibility;

	public Constructor(@Nonnull final String name, @Nonnull final String content, @Nonnull final List<Attribute> attributes,
			@Nonnull final Visibility visibility, @Nonnull final List<Annotation> annotations) {
		_name = Check.notEmpty(name, "name");
		_content = Check.notNull(content, "content");
		_attributes = Check.notNull(attributes, "attributes");
		_visibility = Check.notNull(visibility, "visibility");
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
		final Constructor other = (Constructor) obj;
		if (!_annotations.equals(other._annotations)) {
			return false;
		}
		if (!_attributes.equals(other._attributes)) {
			return false;
		}
		if (!_content.equals(other._content)) {
			return false;
		}
		if (!_name.equals(other._name)) {
			return false;
		}
		if (_visibility != other._visibility) {
			return false;
		}
		return true;
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return _annotations;
	}

	@Nonnull
	public List<Attribute> getAttributes() {
		return _attributes;
	}

	public String getContent() {
		return _content;
	}

	@Nonnull
	public String getName() {
		return _name;
	}

	@Nonnull
	public Visibility getVisibility() {
		return _visibility;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _annotations.hashCode();
		result = prime * result + _attributes.hashCode();
		result = prime * result + _content.hashCode();
		result = prime * result + _name.hashCode();
		result = prime * result + _visibility.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		if (!_annotations.isEmpty()) {
			for (final Annotation annotation : _annotations) {
				b.append(AT_SIGN);
				b.append(annotation.getType().getTypeName());
				b.append(NEWLINE);
			}
		}
		if (_visibility != Visibility.UNDEFINED) {
			b.append(_visibility.getName());
			b.append(SPACE);
		}
		b.append(_name);
		b.append(PARENTHESES_OPEN);
		if (!_attributes.isEmpty()) {
			b.append(Joiner.on(COMMA_SPACE).join(_attributes));
		}
		b.append(PARENTHESES_CLOSE);
		b.append(SPACE);
		b.append(BRACE_OPEN);
		b.append(NEWLINE);
		b.append(_content);
		b.append(NEWLINE);
		b.append(BRACE_CLOSE);
		return b.toString();
	}

}
