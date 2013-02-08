package net.sf.qualitycheck.immutableobject.generator;

import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.VariableDeclaratorId;
import japa.parser.ast.expr.AnnotationExpr;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Attribute;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.Import;
import net.sf.qualitycheck.immutableobject.domain.Imports;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.domain.ReturnType;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;
import net.sf.qualitycheck.immutableobject.util.Primitive;

import com.google.common.collect.Lists;

public final class SourceCodeReader {

	@Nonnull
	public static Method createMethod(@Nonnull final MethodDeclaration methodDeclaration, @Nonnull final Imports imports) {
		Check.notNull(methodDeclaration, "methodDeclaration");
		Check.notNull(imports, "imports");
		final List<Annotation> annotations = findAnnotations(methodDeclaration.getAnnotations(), imports);
		final List<Attribute> attributes = findAttributes(methodDeclaration.getParameters(), imports);
		final ReturnType returnType = ReturnType.of(findType(methodDeclaration.getType(), imports));
		return new Method(methodDeclaration.getName(), returnType, Method.NOT_IMPLEMENTED, attributes, Visibility.PUBLIC, Final.UNDEFINED,
				Static.UNDEFINED, annotations);
	}

	@Nonnull
	public static List<Annotation> findAnnotations(@Nullable final List<AnnotationExpr> annotationExprs, @Nonnull final Imports imports) {
		Check.notNull(imports, "imports");
		final List<Annotation> annotations = Lists.newArrayList();
		if (annotationExprs != null) {
			for (final AnnotationExpr annotation : annotationExprs) {
				final String name = annotation.getName().toString();
				if (!name.isEmpty()) {
					final Import imp = imports.find(name);
					if (imp != null) {
						annotations.add(new Annotation(imp.getType()));
					}
				}
			}
		}
		return annotations;
	}

	@Nonnull
	public static List<Attribute> findAttributes(@Nullable final List<Parameter> parameters, @Nonnull final Imports imports) {
		Check.notNull(imports, "imports");
		final List<Attribute> attributes = Lists.newArrayList();
		if (parameters != null) {
			for (final Parameter parameter : parameters) {
				final VariableDeclaratorId var = parameter.getId();
				final Type type = findType(parameter.getType(), imports);
				final List<Annotation> annotations = findAnnotations(parameter.getAnnotations(), imports);
				attributes.add(new Attribute(var.getName(), type, Final.UNDEFINED, annotations));
			}
		}
		return attributes;
	}

	@Nonnull
	public static Imports findImports(@Nullable final List<ImportDeclaration> importDeclarations) {
		final List<Import> imports = Lists.newArrayList();
		if (imports != null) {
			for (final ImportDeclaration importDeclaration : importDeclarations) {
				imports.add(Import.of(importDeclaration.getName().toString()));
			}
		}
		return Imports.copyOf(imports);
	}

	@Nonnull
	public static List<Method> findMethods(@Nonnull final List<BodyDeclaration> members, @Nonnull final Imports imports) {
		Check.notNull(members, "members");
		Check.notNull(imports, "imports");
		final List<Method> methods = Lists.newArrayList();
		for (final BodyDeclaration member : members) {
			if (member instanceof MethodDeclaration) {
				methods.add(createMethod((MethodDeclaration) member, imports));
			}
		}
		return methods;
	}

	@Nonnull
	public static Type findType(@Nonnull final japa.parser.ast.type.Type type, @Nonnull final Imports imports) {
		Check.notNull(type, "type");
		Check.notNull(imports, "imports");
		final Type ret;
		if (Primitive.isPrimitive(type.toString())) {
			ret = new Type(type.toString());
		} else {
			final Type t = new Type(type.toString());
			final Import imp = imports.find(t.getName());
			ret = imp != null ? new Type(imp.getType().getPackage(), imp.getType().getName(), t.getGenericDeclaration()) : t;
		}
		return ret;
	}

}
