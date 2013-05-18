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

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SourceCodeFormatter {

	public enum LineEnding {

		KEEP(""),

		CR("\r"),

		CRLF("\r\n"),

		LF("\n");

		/**
		 * Default line ending
		 */
		public static final LineEnding DEFAULT_LINE_ENDING = LineEnding.LF;

		/**
		 * Determines the most occurring line-ending characters in the file text or null if no line-ending occurs the
		 * most.
		 * 
		 * @param code
		 *            source code
		 * @return most occurring line ending or {@code LineEnding#KEEP}
		 */
		@Nonnull
		private static LineEnding determine(final String code) {
			Check.notNull(code, "code");
			int lfCount = 0;
			int crCount = 0;
			int crlfCount = 0;
			for (int i = 0; i < code.length(); i++) {
				final char c = code.charAt(i);
				if (c == '\r') {
					if (i + 1 < code.length() && code.charAt(i + 1) == '\n') {
						crlfCount++;
						i++;
					} else {
						crCount++;
					}
				} else if (c == '\n') {
					lfCount++;
				}
			}
			if (lfCount > crCount && lfCount > crlfCount) {
				return LineEnding.LF;
			} else if (crlfCount > lfCount && crlfCount > crCount) {
				return LineEnding.CRLF;
			} else if (crCount > lfCount && crCount > crlfCount) {
				return LineEnding.CR;
			}
			return LineEnding.KEEP;
		}

		/**
		 * Returns the line ending parameter as characters when the value is known (LF, CRLF, CR) or it will trying to
		 * detect it from the source code (KEEP), otherwise the default line ending will be returned.
		 * 
		 * @param lineEnding
		 *            line ending
		 * @param code
		 *            source code
		 * @return line ending parameter as characters
		 */
		@Nonnull
		public static String find(@Nonnull final LineEnding lineEnding, @Nonnull final String code) {
			Check.notNull(lineEnding, "lineEnding");
			Check.notNull(code, "code");
			String ret = DEFAULT_LINE_ENDING.asString();
			if (LineEnding.KEEP == lineEnding) {
				final LineEnding determined = determine(code);
				ret = LineEnding.KEEP == determined ? DEFAULT_LINE_ENDING.asString() : determined.asString();
			} else if (LineEnding.LF == lineEnding) {
				ret = lineEnding.asString();
			} else if (LineEnding.CRLF == lineEnding) {
				ret = lineEnding.asString();
			} else if (LineEnding.CR == lineEnding) {
				ret = lineEnding.asString();
			}
			return ret;
		}

		private final String chars;

		private LineEnding(final String chars) {
			this.chars = chars;
		}

		public String asString() {
			return chars;
		}

		public String find(final String code) {
			return find(this, code);
		}

	}

	/**
	 * Default options to be passed when no custom properties are given
	 */
	public static final Properties DEFAULT_PROPERTIES = findDefaultProperties();

	private static final String DEFAULT_PROPERTIES_PATH = "org.eclipse.jdt.core.prefs";

	private static final Logger LOG = LoggerFactory.getLogger(SourceCodeFormatter.class);

	/**
	 * Gets the default options to be passed when no custom properties are given.
	 * 
	 * @return properties with formatter options
	 */
	@Nonnull
	private static Properties findDefaultProperties() {
		final InputStream in = SourceCodeFormatter.class.getClassLoader().getResourceAsStream(DEFAULT_PROPERTIES_PATH);
		final Properties p = new Properties();
		try {
			p.load(in);
		} catch (final IOException e) {
			throw new RuntimeException(String.format("Can not load resource %s", DEFAULT_PROPERTIES_PATH));
		}
		return p;
	}

	/**
	 * Pretty prints the given source code.
	 * 
	 * @param code
	 *            source code to format
	 * @return formatted source code
	 */
	public static String format(final String code) {
		return format(code, DEFAULT_PROPERTIES, LineEnding.DEFAULT_LINE_ENDING);
	}

	/**
	 * Pretty prints the given source code.
	 * 
	 * @param code
	 *            source code to format
	 * @param options
	 *            formatter options
	 * @param lineEnding
	 *            desired line ending
	 * @return formatted source code
	 */
	public static String format(final String code, final Properties options, final LineEnding lineEnding) {
		Check.notEmpty(code, "code");
		Check.notEmpty(options, "options");
		Check.notNull(lineEnding, "lineEnding");

		final CodeFormatter formatter = ToolFactory.createCodeFormatter(options);
		final String lineSeparator = LineEnding.find(lineEnding, code);
		TextEdit te = null;
		try {
			te = formatter.format(CodeFormatter.K_COMPILATION_UNIT, code, 0, code.length(), 0, lineSeparator);
		} catch (final Exception formatFailed) {
			LOG.warn("Formatting failed", formatFailed);
		}

		String formattedCode = code;
		if (te == null) {
			LOG.info("Code cannot be formatted. Possible cause is unmatched source/target/compliance version.");
		} else {

			final IDocument doc = new Document(code);
			try {
				te.apply(doc);
			} catch (final Exception e) {
				LOG.warn(e.getLocalizedMessage(), e);
			}
			formattedCode = doc.get();
		}
		return formattedCode;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private SourceCodeFormatter() {
		// This class is not intended to create objects from it.
	}

}
