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
