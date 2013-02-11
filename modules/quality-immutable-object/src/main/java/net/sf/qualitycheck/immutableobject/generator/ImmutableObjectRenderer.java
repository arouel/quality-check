package net.sf.qualitycheck.immutableobject.generator;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Clazz;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

final class ImmutableObjectRenderer {

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
		settings.qualityCheck(true);

		// immutable settings
		settings.fields(clazz.getFields());
		settings.immutableName(clazz.getName());
		settings.imports(clazz.getImports());
		settings.interfaceDeclaration(clazz.getInterfaces().get(0));
		settings.packageDeclaration(clazz.getPackage());

		// builder settings
		settings.builderCopyConstructor(true);
		settings.builderFlatMutators(false);
		settings.builderFluentMutators(true);
		settings.builderName("Builder");
		settings.builderImplementsInterface(false);

		template.add("settings", settings.build());

		return SourceCodeFormatter.format(template.render());
	}

}
