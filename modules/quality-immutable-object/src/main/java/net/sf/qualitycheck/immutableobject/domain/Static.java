package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;

/**
 * Represents if a field, method or inner-class is static
 */
public enum Static implements Modifier {

	/**
	 * This value can be used to express that no declaration is available.
	 */
	UNDEFINED(""),

	STATIC("static");

	private final String name;

	Static(@Nonnull final String name) {
		this.name = name;
	}

	@Override
	@Nonnull
	public String getName() {
		return name;
	}

}
