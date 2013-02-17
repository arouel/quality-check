package net.sf.qualitycheck.immutableobject.generator;

import java.util.Locale;

import javax.annotation.concurrent.ThreadSafe;

import org.stringtemplate.v4.AttributeRenderer;

@ThreadSafe
final class BasicFormatRenderer implements AttributeRenderer {

	@Override
	public String toString(final Object o, final String formatName, final Locale locale) {
		// o will be instanceof String
		if (formatName == null) {
			return o.toString();
		}
		if (formatName.equals("toUpper")) {
			return o.toString().toUpperCase();
		} else if (formatName.equals("toLower")) {
			return o.toString().toLowerCase();
		} else {
			throw new IllegalArgumentException("Unsupported format name");
		}
	}

}
