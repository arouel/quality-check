/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
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
package net.sf.qualitycheck.immutableobject.generator;

import java.util.Locale;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.stringtemplate.v4.AttributeRenderer;

@ThreadSafe
final class BasicFormatRenderer implements AttributeRenderer {

	static String toLowerCamelCase(@Nonnull final String text) {
		final String t = Check.notEmpty(text.trim(), "text");
		return t.substring(0, 1).toLowerCase() + t.substring(1, t.length());
	}

	static String toUpperCamelCase(@Nonnull final String text) {
		final String t = Check.notEmpty(text.trim(), "text");
		return t.substring(0, 1).toUpperCase() + t.substring(1, t.length());
	}

	@Override
	public String toString(final Object o, final String formatName, final Locale locale) {
		Check.notNull(o, "o");
		final String text = Check.instanceOf(String.class, o);
		String result = text;
		if (!text.trim().isEmpty() && formatName != null) {
			if (formatName.equals("toUpper")) {
				result = text.toUpperCase();
			} else if (formatName.equals("toLower")) {
				result = text.toLowerCase();
			} else if (formatName.equals("toUpperCamelCase")) {
				result = toUpperCamelCase(text);
			} else if (formatName.equals("toLowerCamelCase")) {
				result = toLowerCamelCase(text);
			} else {
				throw new IllegalStateOfArgumentException("Unsupported format name: " + formatName);
			}
		}
		return result;
	}
}
