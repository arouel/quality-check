package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

/**
 * Represents an import statement of a class
 */
@Immutable
public final class Import {

	/**
	 * Prefix when written in class file
	 */
	private static final String PREFIX = "import";

	private static Type filterGenericDeclaration(final Type type) {
		return type.getGenericDeclaration() == GenericDeclaration.UNDEFINED ? type : new Type(type.getPackage(), type.getName(),
				GenericDeclaration.UNDEFINED);
	}

	public static Import of(@Nonnull final Annotation annotation) {
		Check.notNull(annotation, "annotation");
		return of(annotation.getType());
	}

	public static Import of(@Nonnull final Attribute attribute) {
		Check.notNull(attribute, "attribute");
		return of(attribute.getType());
	}

	public static Import of(@Nonnull final Class<?> type) {
		Check.notNull(type, "type");
		return of(Type.of(type));
	}

	public static Import of(@Nonnull final Field field) {
		Check.notNull(field, "field");
		return of(field.getType());
	}

	public static Import of(@Nonnull final Interface interfaceType) {
		Check.notNull(interfaceType, "interfaceType");
		return of(interfaceType.getType());
	}

	public static Import of(@Nonnull final String type) {
		return Import.of(new Type(Check.notNull(type, "type")));
	}

	public static Import of(@Nonnull final Type type) {
		return new Import(Check.notNull(type, "type"));
	}

	@Nonnull
	private final Type type;

	private Import(@Nonnull final Type type) {
		this.type = filterGenericDeclaration(Check.notNull(type, "type"));
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
		final Import other = (Import) obj;
		if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	public Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return PREFIX + Characters.SPACE + type.toString() + Characters.SEMICOLON;
	}

}
