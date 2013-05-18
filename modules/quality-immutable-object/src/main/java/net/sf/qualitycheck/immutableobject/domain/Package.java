package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

/**
 * Represents a package
 */
@Immutable
public final class Package {

	/**
	 * Represents a undefined package
	 */
	public static final Package UNDEFINED = new Package();

	/**
	 * Represents the package <code>java.lang</code>
	 */
	public static final Package JAVA_LANG = new Package("java.lang");

	/**
	 * Prefix when written in class file
	 */
	private static final String PREFIX = "package";

	/**
	 * Filters a dot at the end of the passed package name if present.
	 * 
	 * @param pkgName
	 *            a package name
	 * @return a filtered package name
	 */
	private static String removeLastDot(final String pkgName) {
		return pkgName.charAt(pkgName.length() - 1) == Characters.DOT ? pkgName.substring(0, pkgName.length() - 1) : pkgName;
	}

	/**
	 * Name of the package
	 */
	@Nonnull
	private final String name;

	/**
	 * Creates an undefined representation of {@code Package} and is intended only for internal usage.
	 */
	private Package() {
		name = "";
	}

	/**
	 * Creates a new instance of {@code Package}.
	 * 
	 * @param packageName
	 *            package name
	 */
	public Package(@Nonnull final String packageName) {
		Check.notEmpty(packageName, "packageName");
		name = Check.notEmpty(removeLastDot(packageName.trim()), "packageName");
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
		final Package other = (Package) obj;
		if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the name of this package.
	 * 
	 * @return package name
	 */
	@Nonnull
	public String getName() {
		return name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + name.hashCode();
		return result;
	}

	public boolean isUndefined() {
		return UNDEFINED.equals(this);
	}

	@Override
	public String toString() {
		return PREFIX + Characters.SPACE + name;
	}

}
