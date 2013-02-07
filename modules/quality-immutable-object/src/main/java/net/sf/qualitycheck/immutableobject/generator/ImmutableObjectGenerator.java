package net.sf.qualitycheck.immutableobject.generator;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.TypeDeclaration;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Abstract;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Attribute;
import net.sf.qualitycheck.immutableobject.domain.Clazz;
import net.sf.qualitycheck.immutableobject.domain.Constructor;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.GenericDeclaration;
import net.sf.qualitycheck.immutableobject.domain.Imports;
import net.sf.qualitycheck.immutableobject.domain.Interface;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.domain.Package;
import net.sf.qualitycheck.immutableobject.domain.ReturnType;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;
import net.sf.qualitycheck.immutableobject.parser.InterfaceParser;
import net.sf.qualitycheck.immutableobject.util.FieldUtil;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Immutable
public final class ImmutableObjectGenerator {

	public static final String CLAZZ_PREFIX = "Immutable";

	private static final List<Attribute> WITHOUT_ATTRIBUTES = ImmutableList.of();

	@Nonnull
	private static List<Annotation> findAnnotations(@Nonnull final java.lang.annotation.Annotation[] annotations) {
		Check.notNull(annotations, "annotations");
		final ImmutableList.Builder<Annotation> builder = new ImmutableList.Builder<Annotation>();
		for (final java.lang.annotation.Annotation annotation : annotations) {
			builder.add(Annotation.of(annotation));
		}
		return builder.build();
	}

	@Nonnull
	private static List<Field> findFields(@Nonnull final List<Method> methods) {
		Check.notNull(methods, "methods");
		final List<Field> fields = Lists.newArrayList();
		for (final Method method : methods) {
			final List<Annotation> annotations = method.getAnnotations();
			fields.add(new Field(FieldUtil.determineFieldName(method.getName()), method.getReturnType().getType(), Visibility.PRIVATE,
					Final.FINAL, Static.UNDEFINED, annotations, Field.WITHOUT_VALUE));
		}
		return fields;
	}

	@Nonnull
	private static List<Method> findMethods(@Nonnull final Class<?> type) {
		final Set<java.lang.reflect.Method> methods = InterfaceParser.findReadableMethods(type);
		final List<Method> members = Lists.newArrayList();
		for (final java.lang.reflect.Method method : methods) {
			final List<Annotation> annotations = findAnnotations(method.getAnnotations());
			final ReturnType returnType = ReturnType.of(method.getReturnType());
			final Method m = new Method(method.getName(), returnType, Method.NOT_IMPLEMENTED, WITHOUT_ATTRIBUTES, Visibility.PUBLIC,
					Final.UNDEFINED, Static.UNDEFINED, annotations);
			members.add(m);
		}
		return members;
	}

	@Nonnull
	private static List<Method> generateAccessorMethods(@Nonnull final List<Method> methods) {
		final List<Method> members = Lists.newArrayList();
		for (final Method method : methods) {
			members.add(AccessorMethodGenerator.generate(method));
		}
		return members;
	}

	/**
	 * The specified interface must not contain methods, that changes the state of this object itself.
	 * 
	 * @param code
	 *            source code of an interface which describes how to generate the <i>immutable</i>
	 * @return generated source code as string
	 */
	public static String generateByCode(@Nonnull final String code) {
		Clazz clazz = null;
		try {
			final CompilationUnit unit = JavaParser.parse(new ByteArrayInputStream(code.getBytes(Charsets.UTF_8.displayName())));
			final List<TypeDeclaration> types = unit.getTypes();
			final TypeDeclaration type = types.get(0);
			Imports imports = SourceCodeReader.findImports(unit.getImports()).copyAndAdd(EqualsMethodGenerator.DEPENDS_ON)
					.copyAndAdd(HashCodeMethodGenerator.DEPENDS_ON);
			final String name = CLAZZ_PREFIX + type.getName();
			final Package pkg = new Package(unit.getPackage().getName().toString());
			final List<Annotation> annotations = SourceCodeReader.findAnnotations(type.getAnnotations(), imports);
			annotations.add(Annotation.IMMUTABLE);
			final List<Method> methods = generateAccessorMethods(SourceCodeReader.findMethods(type.getMembers(), imports));
			final List<Field> fields = new ArrayList<Field>();
			fields.add(SerialVersionGenerator.generate());
			fields.addAll(findFields(methods));

			methods.add(EqualsMethodGenerator.generate(name, fields));
			methods.add(HashCodeMethodGenerator.generate(fields));

			final ConstructorGenerator constructorGenerator = new ConstructorGenerator(name, fields);
			final Constructor constructor = constructorGenerator.createConstructor();
			final List<Constructor> constructors = ImmutableList.of(constructor);
			imports = imports.copyAndAdd(constructorGenerator.dependsOn());
			final List<Interface> interfaces = ImmutableList.of(new Interface(new Type(pkg, type.getName(), GenericDeclaration.UNDEFINED)));
			clazz = new Clazz(name, pkg, fields, constructors, methods, Visibility.PUBLIC, Final.FINAL, Abstract.UNDEFINED, interfaces,
					imports, annotations);
		} catch (final Exception e) {
			throw new RuntimeException("Failed to parse interface: " + e.getLocalizedMessage(), e);
		}

		return SourceCodeFormatter.format(clazz.toString());
	}

	/**
	 * The specified interface must not contain methods, that changes the state of this object itself.
	 * 
	 * @param type
	 *            an interface which describes how to generate the <i>immutable</i>
	 * @return generated source code as string
	 */
	public static String generateByInterface(@Nonnull final Class<?> type) {
		Check.notNull(type, "type");
		Check.stateIsTrue(type.isInterface(), "requires an interface");
		Check.stateIsTrue(!hasPossibleMutatingMethods(type), "The passed interface '%s' seems to have mutating methods", type);

		final String name = CLAZZ_PREFIX + type.getSimpleName();
		final Package pkg = new Package(type.getPackage().getName());

		final List<Method> methods = generateAccessorMethods(findMethods(type));
		final List<Field> fields = new ArrayList<Field>();
		fields.add(SerialVersionGenerator.generate());
		fields.addAll(findFields(methods));
		final ConstructorGenerator constructorGenerator = new ConstructorGenerator(name, fields);
		final Constructor constructor = constructorGenerator.createConstructor();
		final List<Constructor> constructors = ImmutableList.of(constructor);

		methods.add(EqualsMethodGenerator.generate(name, fields));
		methods.add(HashCodeMethodGenerator.generate(fields));

		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.UNDEFINED;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of(Interface.of(type));
		final Imports imports = EqualsMethodGenerator.DEPENDS_ON.copyAndAdd(HashCodeMethodGenerator.DEPENDS_ON).copyAndAdd(
				constructorGenerator.dependsOn());
		final List<Annotation> annotations = ImmutableList.of(Annotation.IMMUTABLE);
		final Clazz clazz = new Clazz(name, pkg, fields, constructors, methods, visibility, finalModifier, abstractModifier, interfaces,
				imports, annotations);

		return SourceCodeFormatter.format(clazz.toString());
	}

	/**
	 * Checks if the given type has possible mutating methods (e.g. setter methods or methods with parameters).
	 * 
	 * @param type
	 *            interface
	 * @return {@code true} if possible mutating methods were found otherwise {@code false}
	 */
	private static boolean hasPossibleMutatingMethods(@Nonnull final Class<?> type) {
		return !InterfaceParser.findPossibleMutatingMethods(type).isEmpty();
	}

}
