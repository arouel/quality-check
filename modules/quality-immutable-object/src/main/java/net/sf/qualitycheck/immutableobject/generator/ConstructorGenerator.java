package net.sf.qualitycheck.immutableobject.generator;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Attribute;
import net.sf.qualitycheck.immutableobject.domain.Constructor;
import net.sf.qualitycheck.immutableobject.domain.Import;
import net.sf.qualitycheck.immutableobject.domain.Imports;

@Immutable
public final class ConstructorGenerator {

	public static final Imports DEPENDS_ON = Imports.of(Import.of(Check.class));

	public static Constructor generate(@Nonnull final Constructor constructor) {
		Check.notNull(constructor, "constructor");
		final StringBuilder b = new StringBuilder();
		for (final Attribute attribute : constructor.getAttributes()) {
			if (attribute.getAnnotations().contains(Annotation.NONNEGATIVE)) {
				b.append("Check.stateIsTrue(");
				b.append(attribute.getName());
				b.append(" >= 0, \"Argument '");
				b.append(attribute.getName());
				b.append("' must not be negative.\");\n");
			}
			b.append("this.");
			b.append(attribute.getName());
			b.append(" = ");
			if (attribute.getAnnotations().contains(Annotation.NONNULL)) {
				b.append("Check.notNull(");
				b.append(attribute.getName());
				b.append(", \"");
				b.append(attribute.getName());
				b.append("\")");
			} else {
				b.append(attribute.getName());
			}
			b.append(";");
		}
		return new Constructor(constructor.getName(), b.toString(), constructor.getAttributes(), constructor.getVisibility(),
				constructor.getAnnotations());
	}
}
