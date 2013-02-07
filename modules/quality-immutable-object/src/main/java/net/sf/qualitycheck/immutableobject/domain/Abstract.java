package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;

/**
 * Represents the modifier {@code final} which can be available at attributes, classes or methods
 */
public enum Abstract implements Modifier {

	/**
	 * This value can be used to express that no declaration is available.
	 */
	UNDEFINED(""),

	/**
	 * This value express that a class or method is abstract.
	 */
	ABSTRACT("abstract");

	private final String name;

	Abstract(@Nonnull final String name) {
		this.name = name;
	}

	@Override
	@Nonnull
	public String getName() {
		return name;
	}

}
