package net.sf.qualitycheck.immutableobject.generator;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Abstract;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Clazz;
import net.sf.qualitycheck.immutableobject.domain.Constructor;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.GenericDeclaration;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.domain.Interface;
import net.sf.qualitycheck.immutableobject.domain.InterfaceAnalysis;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.domain.Package;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;
import net.sf.qualitycheck.immutableobject.util.FieldUtil;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Immutable
public final class ImmutableObjectGenerator {

	public static final String CLAZZ_PREFIX = "Immutable";

	@Nonnull
	private static List<Field> findFields(@Nonnull final List<Method> methods) {
		final List<Field> fields = Lists.newArrayList();
		for (final Method method : methods) {
			final List<Annotation> annotations = method.getAnnotations();
			fields.add(new Field(FieldUtil.determineFieldName(method.getName()), method.getReturnType().getType(), Visibility.PRIVATE,
					Final.FINAL, Static.UNDEFINED, annotations, Field.WITHOUT_VALUE, FieldUtil.determineAccessorPrefix(method.getName())));
		}
		return fields;
	}

	/**
	 * The specified interface must not contain methods, that changes the state of this object itself.
	 * 
	 * @param code
	 *            source code of an interface which describes how to generate the <i>immutable</i>
	 * @param settings
	 *            settings to generate code
	 * @return generated source code as string
	 */
	public static String generate(@Nonnull final String code, @Nonnull final ImmutableSettings settings) {
		Check.notNull(code, "code");
		final ImmutableSettings.Builder settingsBuilder = new ImmutableSettings.Builder(Check.notNull(settings, "settings"));

		final InterfaceAnalysis analysis = InterfaceAnalyzer.analyze(code);
		final Clazz clazz = scaffoldClazz(analysis, settings);

		// immutable settings
		settingsBuilder.fields(clazz.getFields());
		settingsBuilder.immutableName(clazz.getName());
		settingsBuilder.imports(clazz.getImports());
		settingsBuilder.interfaceDeclaration(clazz.getInterfaces().get(0));
		settingsBuilder.packageDeclaration(clazz.getPackage());

		return SourceCodeFormatter.format(ImmutableObjectRenderer.toString(clazz, settingsBuilder.build()));
	}

	private static boolean isSerializable(@Nonnull final List<Interface> interfaces) {
		boolean ret = false;
		for (final Interface extend : interfaces) {
			if ("Serializable".equals(extend.getType().getName())) {
				ret = true;
			}
		}
		return ret;
	}

	@Nonnull
	private static Clazz scaffoldClazz(@Nonnull final InterfaceAnalysis analysis, @Nonnull final ImmutableSettings settings) {
		final String name = CLAZZ_PREFIX + analysis.getInterfaceName();
		final Package pkg = analysis.getPackage();
		final List<Field> fields = new ArrayList<Field>();
		if (settings.isSerializable() || isSerializable(analysis.getExtends())) {
			fields.add(SerialVersionGenerator.generate());
		}
		fields.addAll(findFields(analysis.getMethods()));

		final List<Constructor> constructors = ImmutableList.of();
		final Interface toImplement = new Interface(new Type(pkg, analysis.getInterfaceName(), GenericDeclaration.UNDEFINED));
		final List<Interface> interfaces = ImmutableList.of(toImplement);
		return new Clazz(name, pkg, fields, constructors, analysis.getMethods(), Visibility.PUBLIC, Final.FINAL, Abstract.UNDEFINED,
				interfaces, analysis.getImports(), analysis.getAnnotations());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private ImmutableObjectGenerator() {
		// This class is not intended to create objects from it.
	}

}
