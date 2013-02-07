package net.sf.qualitycheck.immutableobject.util;

import javax.annotation.Nonnull;


import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.Constants;

public final class FieldUtil {

	/**
	 * Determines the field name based on a method name.
	 * 
	 * @param methodName
	 *            a method name
	 * @return the resulting field name
	 */
	public static String determineFieldName(@Nonnull final String methodName) {
		Check.notEmpty(methodName, "methodName");
		String filtered = methodName.replace(Constants.METHOD_GET_PREFIX, "");
		if (filtered.equals(methodName)) {
			filtered = methodName.replace(Constants.METHOD_IS_PREFIX, "");
		} else if (filtered.equals(methodName)) {
			filtered = methodName.replace(Constants.METHOD_HAS_PREFIX, "");
		}
		Check.stateIsTrue(!filtered.isEmpty(), "method name requires characters after prefix (%s, %s, %s)", Constants.METHOD_GET_PREFIX,
				Constants.METHOD_HAS_PREFIX, Constants.METHOD_IS_PREFIX);
		return filtered.substring(0, 1).toLowerCase() + filtered.substring(1);
	}

	private FieldUtil() {
	}

}
