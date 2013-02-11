package net.sf.qualitycheck.immutableobject.generator;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Attribute;
import net.sf.qualitycheck.immutableobject.domain.Clazz;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.domain.Interface;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import com.google.common.collect.Lists;

final class ImmutableObjectRenderer {

	@Nullable
	private static Attribute determineCopyConstructorAttribute(@Nonnull final Clazz clazz) {
		Attribute attr = null;
		if (clazz.getInterfaces().size() == 1) {
			final Interface i = clazz.getInterfaces().get(0);
			attr = new Attribute(i.getType().getName().toLowerCase(), i.getType(), Final.FINAL, Lists.newArrayList(Annotation.NONNULL));
		}
		return attr;
	}

	public static String toString(@Nonnull final Clazz clazz) {
		Check.notNull(clazz, "clazz");
		final STGroup group = new STGroupFile("templates/default.stg");
		group.registerRenderer(Field.class, new FieldRenderer());
		group.registerRenderer(String.class, new BasicFormatRenderer());
		final ST template = group.getInstanceOf("immutableCompilationUnit");
		final ImmutableSettings.Builder settings = new ImmutableSettings.Builder();

		// global settings
		settings.jsr305Annotations(true);
		settings.guava(false);

		// immutable settings
		settings.fields(clazz.getFields());
		settings.immutableName(clazz.getName());
		settings.imports(clazz.getImports());
		settings.interfaceDeclaration(clazz.getInterfaces().get(0));
		settings.packageDeclaration(clazz.getPackage());

		// builder settings
		settings.builderCopyConstructor(true);
		settings.builderFlatMutators(true);
		settings.builderFluentMutators(true);
		settings.builderName("Builder");
		settings.builderSameInterface(false);

		template.add("builderName", "Builder");
		template.add("builderNotThreadSafeAnnotation", Annotation.NOT_THREAD_SAFE);
		template.add("clazz", clazz);
		template.add("copyConstructorAttribute", determineCopyConstructorAttribute(clazz));
		template.add("immutableAnnotation", Annotation.IMMUTABLE);
		template.add("immutableName", clazz.getName());
		template.add("settings", settings.build());
		return SourceCodeFormatter.format(template.render());
	}

}
