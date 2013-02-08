package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;

/**
 * Represents the visibility of an class, interface, field, method or annotation
 */
public enum Visibility implements Modifier {

	/**
	 * This value can be used to express that no declaration is available.
	 */
	UNDEFINED(""),

	PRIVATE("private"),

	PROTECTED("protected"),

	PUBLIC("public");

	private final String name;

	Visibility(@Nonnull final String name) {
		this.name = name;
	}

	@Override
	@Nonnull
	public String getName() {
		return name;
	}

	public boolean isUndefined() {
		return UNDEFINED.equals(this);
	}

}
