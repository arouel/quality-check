package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

@Immutable
public final class ReturnType {

	public static final ReturnType BOOLEAN = ReturnType.of("boolean");

	public static final ReturnType INT = ReturnType.of("int");

	public static final ReturnType VOID = new ReturnType();

	public static ReturnType of(@Nonnull final Class<?> clazz) {
		return new ReturnType(Type.of(Check.notNull(clazz, "clazz")));
	}

	public static ReturnType of(@Nonnull final String type) {
		return new ReturnType(new Type(Check.notEmpty(type, "type")));
	}

	public static ReturnType of(@Nonnull final Type type) {
		return new ReturnType(Check.notNull(type, "type"));
	}

	@Nonnull
	private final Type _type;

	private ReturnType() {
		_type = new Type("void");
	}

	private ReturnType(@Nonnull final Type type) {
		_type = Check.notNull(type, "type");
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
		final ReturnType other = (ReturnType) obj;
		if (!_type.equals(other._type)) {
			return false;
		}
		return true;
	}

	public Type getType() {
		return _type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _type.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return _type.toString();
	}

}
