package net.sf.qualitycheck.immutableobject.generator;

import java.util.Locale;
import java.util.Set;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.immutableobject.domain.CollectionVariant;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Type;

import org.stringtemplate.v4.AttributeRenderer;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;

final class FieldRenderer implements AttributeRenderer {

	public enum Option {
		CHECK, COPY, GUAVA, IMMUTABLE;
		public static Set<Option> evaluate(final Iterable<String> options) {
			final Builder<Option> builder = ImmutableSet.builder();
			for (final String option : options) {
				final Option value = valueOf(option.toUpperCase());
				if (value != null) {
					builder.add(value);
				}
			}
			return builder.build();
		}
	}

	private static final String CHECK_NONNULL = "Check.notNull(%s, \"%s\")";

	private static final String CHECK_NONNEGATIVE = "Check.notNegative(%s, \"%s\")";

	@Nonnull
	static String convertCollection(@Nonnull final Field field, @Nonnull final Set<Option> opts, @Nonnull final String currentResult) {
		String result = currentResult;
		final CollectionVariant variant = CollectionVariant.evaluate(field.getType());
		if (variant != null) {
			if (opts.contains(Option.IMMUTABLE) && opts.contains(Option.GUAVA)) {
				result = String.format(variant.getGuavaImmutable(), result);
			} else if (opts.contains(Option.IMMUTABLE)) {
				final String generic = determineGeneric(field.getType());
				result = String.format(variant.getDefaultImmutable(), generic, result);
			} else if (opts.contains(Option.COPY) && opts.contains(Option.GUAVA)) {
				result = String.format(variant.getGuavaCopy(), result);
			} else if (opts.contains(Option.COPY)) {
				final String generic = determineGeneric(field.getType());
				result = String.format(variant.getDefaultCopy(), generic, result);
			}
		}
		return result;
	}

	@Nonnull
	public static final String determineGeneric(@Nonnull final Type type) {
		return type.getGenericDeclaration().isUndefined() ? "" : "<" + type.getGenericDeclaration().getDeclaration() + ">";
	}

	@Nonnull
	static String insertCheck(@Nonnull final Field field) {
		String result = field.getName();
		if (field.isNonnegative()) {
			result = String.format(CHECK_NONNEGATIVE, field.getName(), field.getName());
		} else if (field.isNonnull()) {
			result = String.format(CHECK_NONNULL, field.getName(), field.getName());
		}
		return result;
	}

	@Nonnull
	@Override
	public String toString(final Object o, final String formatOptions, final Locale locale) {
		// o will be instanceof CollectionVariant
		final Field field = (Field) o;
		String result = field.getName();
		if (formatOptions != null) {
			final Set<Option> opts = Option.evaluate(Splitter.on(",").trimResults().omitEmptyStrings().split(formatOptions));
			if (opts.contains(Option.CHECK)) {
				result = insertCheck(field);
			}
			result = convertCollection(field, opts, result);
		}
		return result;
	}

}
