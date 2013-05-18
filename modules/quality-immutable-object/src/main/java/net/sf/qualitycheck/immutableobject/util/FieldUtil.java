/*******************************************************************************
 * Copyright 2013 André Rouél
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private FieldUtil() {
		// This class is not intended to create objects from it.
	}

}
