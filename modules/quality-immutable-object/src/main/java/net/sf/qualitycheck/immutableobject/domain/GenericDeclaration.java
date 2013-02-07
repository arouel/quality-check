package net.sf.qualitycheck.immutableobject.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

public final class GenericDeclaration {

	/**
	 * Represents a undefined generic declaration
	 */
	public static final GenericDeclaration UNDEFINED = new GenericDeclaration();

	/**
	 * ^((\w[\?\d\w])*)<(\w+|(\w+\sextends\s(.*)))>
	 */
	private static final Pattern PATTERN = Pattern.compile("((\\w[\\d\\w])*)(<(\\w+.*)>)?");

	public static GenericDeclaration of(@Nonnull final String declaration) {
		return new GenericDeclaration(Check.notEmpty(declaration, "declaration"));
	}

	public static GenericDeclaration parseFrom(@Nonnull final String typeDeclaration) {
		Check.notNull(typeDeclaration, "typeDeclaration");
		final Matcher m = PATTERN.matcher(typeDeclaration);
		return m.matches() && m.group(4) != null ? GenericDeclaration.of(m.group(4)) : GenericDeclaration.UNDEFINED;
	}

	@Nonnull
	private final String _declaration;

	/**
	 * Creates an undefined representation of {@code GenericDeclaration} and is intended only for internal usage.
	 */
	private GenericDeclaration() {
		_declaration = "";
	}

	private GenericDeclaration(@Nonnull final String declaration) {
		_declaration = Check.notEmpty(declaration, "declaration");
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
		final GenericDeclaration other = (GenericDeclaration) obj;
		if (!_declaration.equals(other._declaration)) {
			return false;
		}
		return true;
	}

	public String getDeclaration() {
		return _declaration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _declaration.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return _declaration;
	}

}
