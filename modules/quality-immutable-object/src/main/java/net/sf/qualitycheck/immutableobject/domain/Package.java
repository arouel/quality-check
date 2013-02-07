package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

/**
 * Represents a package
 */
@Immutable
public final class Package implements Characters {

	/**
	 * Represents a undefined package
	 */
	public static final Package UNDEFINED = new Package();

	/**
	 * Prefix when written in class file
	 */
	private static final String PREFIX = "package";

	/**
	 * Filters a dot at the end of the passed package name if present.
	 * 
	 * @param packageName
	 *            a package name
	 * @return a filtered package name
	 */
	private static String removeLastDot(final String packageName) {
		return packageName.charAt(packageName.length() - 1) == DOT ? packageName.substring(0, packageName.length() - 1) : packageName;
	}

	/**
	 * Name of the package
	 */
	private final String _name;

	/**
	 * Creates an undefined representation of {@code Package} and is intended only for internal usage.
	 */
	private Package() {
		_name = "";
	}

	/**
	 * Creates a new instance of {@code Package}.
	 * 
	 * @param packageName
	 *            package name
	 */
	public Package(final String packageName) {
		Check.notEmpty(packageName, "packageName");
		_name = Check.notEmpty(removeLastDot(packageName.trim()), "packageName");
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
		if (!_name.equals(other._name)) {
			return false;
		}
		return true;
	}

	/**
	 * Gets the name of this package.
	 * 
	 * @return package name
	 */
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _name.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return PREFIX + SPACE + _name;
	}

}
