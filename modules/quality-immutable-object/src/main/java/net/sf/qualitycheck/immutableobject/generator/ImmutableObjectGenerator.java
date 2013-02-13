package net.sf.qualitycheck.immutableobject.generator;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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
import net.sf.qualitycheck.immutableobject.domain.Imports;
import net.sf.qualitycheck.immutableobject.domain.Interface;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.domain.Package;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;
import net.sf.qualitycheck.immutableobject.util.FieldUtil;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Immutable
public final class ImmutableObjectGenerator {

	public static final String CLAZZ_PREFIX = "Immutable";

	@Nonnull
	private static List<Field> findFields(@Nonnull final List<Method> methods) {
		Check.notNull(methods, "methods");
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

		final CompilationUnit unit = Check.notNull(parse(code), "compilationUnit");
		final List<TypeDeclaration> types = Check.notEmpty(unit.getTypes(), "typeDeclarations");
		Check.stateIsTrue(types.size() == 1, "more than one interface declaration per compilation unit is not supported");

		final ClassOrInterfaceDeclaration type = (ClassOrInterfaceDeclaration) types.get(0);
		final Imports imports = SourceCodeReader.findImports(unit.getImports());
		final String name = CLAZZ_PREFIX + type.getName();
		final Package pkg = new Package(unit.getPackage().getName().toString());
		final List<Annotation> annotations = SourceCodeReader.findAnnotations(type.getAnnotations(), imports);
		final List<Method> methods = generateAccessorMethods(SourceCodeReader.findMethods(type.getMembers(), imports));
		final List<Field> fields = new ArrayList<Field>();
		if (settings.isSerializable() || isSerializable(type)) {
			fields.add(SerialVersionGenerator.generate());
		}
		fields.addAll(findFields(methods));

		final List<Constructor> constructors = ImmutableList.of();
		final List<Interface> interfaces = ImmutableList.of(new Interface(new Type(pkg, type.getName(), GenericDeclaration.UNDEFINED)));
		final Clazz clazz = new Clazz(name, pkg, fields, constructors, methods, Visibility.PUBLIC, Final.FINAL, Abstract.UNDEFINED,
				interfaces, imports.asList(), annotations);

		// immutable settings
		settingsBuilder.fields(clazz.getFields());
		settingsBuilder.immutableName(clazz.getName());
		settingsBuilder.imports(clazz.getImports());
		settingsBuilder.interfaceDeclaration(clazz.getInterfaces().get(0));
		settingsBuilder.packageDeclaration(clazz.getPackage());

		return SourceCodeFormatter.format(ImmutableObjectRenderer.toString(clazz, settingsBuilder.build()));
	}

	@Nonnull
	private static List<Method> generateAccessorMethods(@Nonnull final List<Method> methods) {
		final List<Method> members = Lists.newArrayList();
		for (final Method method : methods) {
			members.add(AccessorMethodGenerator.generate(method));
		}
		return members;
	}

	private static boolean isSerializable(@Nonnull final ClassOrInterfaceDeclaration type) {
		boolean ret = false;
		if (type.getExtends() != null) {
			for (final ClassOrInterfaceType extend : type.getExtends()) {
				if ("Serializable".equals(extend.getName())) {
					ret = true;
				}
			}
		}
		return ret;
	}

	@Nullable
	static CompilationUnit parse(@Nonnull final String code) {
		Check.notNull(code, "code");
		CompilationUnit unit = null;
		try {
			unit = JavaParser.parse(new ByteArrayInputStream(code.getBytes(Charsets.UTF_8.displayName())));
		} catch (final UnsupportedEncodingException e) {
			throw new RuntimeException("Character encoding is not supported: switch to UTF-8");
		} catch (final ParseException e) {
			throw new RuntimeException("Failed to parse interface: " + e.getLocalizedMessage(), e);
		}
		return unit;
	}

}
