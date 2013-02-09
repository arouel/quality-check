package net.sf.qualitycheck.immutableobject.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;

public final class FieldUtil {

	private static final Pattern PATTERN = Pattern.compile("^(get|has|is)([A-Z]\\w+)");

	/**
	 * Determines the prefix of an accessor method based on an accessor method name.
	 * 
	 * @param methodName
	 *            an accessor method name
	 * @return the resulting prefix
	 */
	public static AccessorPrefix determineAccessorPrefix(@Nonnull final String methodName) {
		Check.notEmpty(methodName, "methodName");
		final Matcher m = PATTERN.matcher(methodName);
		Check.stateIsTrue(m.find(), "passed method name '%s' is not applicable", methodName);
		return new AccessorPrefix(m.group(1));
	}

	/**
	 * Determines the field name based on an accessor method name.
	 * 
	 * @param methodName
	 *            an accessor method name
	 * @return the resulting field name
	 */
	public static String determineFieldName(@Nonnull final String methodName) {
		Check.notEmpty(methodName, "methodName");
		final Matcher m = PATTERN.matcher(methodName);
		Check.stateIsTrue(m.find(), "passed method name '%s' is not applicable", methodName);
		return m.group(2).substring(0, 1).toLowerCase() + m.group(2).substring(1);
	}

	private FieldUtil() {
	}

}
