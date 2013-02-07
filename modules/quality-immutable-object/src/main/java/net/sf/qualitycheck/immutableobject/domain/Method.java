package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

/**
 * Represents a method in a class
 */
@Immutable
public final class Method implements Characters {

	/**
	 * Represents the default content of an unimplemented method
	 */
	public static final String NOT_IMPLEMENTED = "throw new NotImplementedException();";

	@Nonnull
	private final List<Annotation> _annotations;

	@Nonnull
	private final List<Attribute> _attributes;

	@Nonnull
	private final String _content;

	@Nonnull
	private final Final _final;

	@Nonnull
	private final String _name;

	@Nonnull
	private final ReturnType _returnType;

	@Nonnull
	private final Static _static;

	@Nonnull
	private final Visibility _visibility;

	public Method(@Nonnull final String name, @Nonnull final ReturnType returnType, final String content,
			@Nonnull final List<Attribute> attributes, @Nonnull final Visibility visibility, @Nonnull final Final finalModifier,
			@Nonnull final Static staticModifier, @Nonnull final List<Annotation> annotations) {
		_name = Check.notEmpty(name, "name");
		_returnType = Check.notNull(returnType, "returnType");
		_content = Check.notEmpty(content, "content");
		_attributes = ImmutableList.copyOf(Check.notNull(attributes, "attributes"));
		_visibility = Check.notNull(visibility, "visibility");
		_final = Check.notNull(finalModifier, "finalModifier");
		_static = Check.notNull(staticModifier, "staticModifier");
		_annotations = ImmutableList.copyOf(Check.notNull(annotations, "annotations"));
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
		if (!_annotations.equals(other._annotations)) {
			return false;
		}
		if (!_attributes.equals(other._attributes)) {
			return false;
		}
		if (!_content.equals(other._content)) {
			return false;
		}
		if (_final != other._final) {
			return false;
		}
		if (!_name.equals(other._name)) {
			return false;
		}
		if (!_returnType.equals(other._returnType)) {
			return false;
		}
		if (_static != other._static) {
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

	public List<Attribute> getAttributes() {
		return _attributes;
	}

	public String getContent() {
		return _content;
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
	public ReturnType getReturnType() {
		return _returnType;
	}

	public Static getStatic() {
		return _static;
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
		result = prime * result + _final.hashCode();
		result = prime * result + _name.hashCode();
		result = prime * result + _returnType.hashCode();
		result = prime * result + _static.hashCode();
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
		if (_static != Static.UNDEFINED) {
			b.append(_static.getName());
			b.append(SPACE);
		}
		if (_final != Final.UNDEFINED) {
			b.append(_final.getName());
			b.append(SPACE);
		}
		b.append(_returnType.getType().getTypeName());
		if (_returnType.getType().getGenericDeclaration() != GenericDeclaration.UNDEFINED) {
			b.append(BRACKET_OPEN);
			b.append(_returnType.getType().getGenericDeclaration());
			b.append(BRACKET_CLOSE);
		}
		b.append(SPACE);
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
