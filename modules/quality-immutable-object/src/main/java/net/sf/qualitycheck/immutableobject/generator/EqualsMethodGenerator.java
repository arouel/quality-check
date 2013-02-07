package net.sf.qualitycheck.immutableobject.generator;

import java.io.StringWriter;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Attribute;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.Import;
import net.sf.qualitycheck.immutableobject.domain.Imports;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.domain.ReturnType;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;
import net.sf.qualitycheck.immutableobject.util.ClasspathVelocityEngine;
import net.sf.qualitycheck.immutableobject.util.NonStaticFilter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

@Immutable
public final class EqualsMethodGenerator {

	private static final String ARGUMENT_NAME = "obj";

	public static final Imports DEPENDS_ON = Imports.of(Import.of(Objects.class));

	private static final String METHOD_NAME = "equals";

	private static final String TEMPLATE_NAME = "equals-method.vm";

	private static final List<Annotation> WITHOUT_ANNOTATIONS = ImmutableList.of();

	public static Method generate(@Nonnull final String name, @Nonnull final List<Field> fields) {
		Check.notNull(fields, "fields");

		final VelocityEngine engine = ClasspathVelocityEngine.VELOCITY_ENGINE;
		final Template template = engine.getTemplate(TEMPLATE_NAME);
		final VelocityContext context = new VelocityContext();
		context.put("name", name);
		context.put("fields", NonStaticFilter.filter(fields));

		final StringWriter content = new StringWriter();
		template.merge(context, content);

		final Visibility visibility = Visibility.PUBLIC;
		final List<Annotation> annotations = ImmutableList.of(Annotation.of(Override.class));
		final Attribute attribute = new Attribute(ARGUMENT_NAME, Type.of(Object.class), Final.FINAL, WITHOUT_ANNOTATIONS);
		final List<Attribute> attributes = ImmutableList.of(attribute);
		return new Method(METHOD_NAME, ReturnType.BOOLEAN, content.toString(), attributes, visibility, Final.UNDEFINED, Static.UNDEFINED,
				annotations);
	}

}
