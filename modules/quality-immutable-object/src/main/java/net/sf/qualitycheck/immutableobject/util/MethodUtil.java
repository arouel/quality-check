package net.sf.qualitycheck.immutableobject.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;

@ThreadSafe
public final class MethodUtil {

	private static final String METHOD_SET_PREFIX = "set";

	private static final Pattern PATTERN = Pattern.compile("([A-Za-z][A-Za-z0-9]*)");

	/**
	 * Determines the accessor method name based on a field name.
	 * 
	 * @param fieldName
	 *            a field name
	 * @return the resulting method name
	 */
	public static String determineAccessorName(@Nonnull final AccessorPrefix prefix, @Nonnull final String fieldName) {
		Check.notNull(prefix, "prefix");
		Check.notEmpty(fieldName, "fieldName");
		final Matcher m = PATTERN.matcher(fieldName);
		Check.stateIsTrue(m.find(), "passed field name '%s' is not applicable", fieldName);
		final String name = m.group();
		return prefix.getPrefix() + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	/**
	 * Determines the mutator method name based on a field name.
	 * 
	 * @param fieldName
	 *            a field name
	 * @return the resulting method name
	 */
	public static String determineMutatorName(@Nonnull final String fieldName) {
		Check.notEmpty(fieldName, "fieldName");
		final Matcher m = PATTERN.matcher(fieldName);
		Check.stateIsTrue(m.find(), "passed field name '%s' is not applicable", fieldName);
		final String name = m.group();
		return METHOD_SET_PREFIX + name.substring(0, 1).toUpperCase() + name.substring(1);
	}

	private MethodUtil() {
	}

}
