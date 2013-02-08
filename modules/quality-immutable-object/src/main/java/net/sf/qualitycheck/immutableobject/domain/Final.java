package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;

/**
 * Represents the modifier {@code final} which can be available at attributes, classes or methods
 */
public enum Final implements Modifier {

	/**
	 * This value can be used to express that no declaration is available.
	 */
	UNDEFINED(""),

	/**
	 * This value express that an attribute, class or method is final.
	 */
	FINAL("final");

	private final String name;

	Final(@Nonnull final String name) {
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
