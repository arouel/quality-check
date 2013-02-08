package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

@Immutable
public final class Clazz implements Characters {

	/**
	 * Prefix when written in class file
	 */
	private static final String PREFIX = "class";

	@Nonnull
	private final List<Annotation> _annotations;

	@Nonnull
	private final Abstract _abstract;

	@Nonnull
	private final List<Interface> _interfaces;

	@Nonnull
	private final List<Constructor> _constructors;

	@Nonnull
	private final List<Field> _fields;

	@Nonnull
	private final Final _final;

	@Nonnull
	private final List<Method> _methods;

	@Nonnull
	private final String _name;

	@Nonnull
	private final Package _package;

	@Nonnull
	private final List<Import> _imports;

	@Nonnull
	private final Visibility _visibility;

	public Clazz(final String name, final Package pkg, final List<Field> fields, final List<Constructor> constructors,
			final List<Method> methods, final Visibility visibility, final Final finalModifier, final Abstract abstractModifier,
			final List<Interface> interfaces, final List<Import> imports, @Nonnull final List<Annotation> annotations) {
		_name = Check.notEmpty(name, "name");
		_package = Check.notNull(pkg, "pkg");
		_abstract = Check.notNull(abstractModifier, "abstractModifier");
		_fields = ImmutableList.copyOf(Check.notNull(fields, "fields"));
		_constructors = ImmutableList.copyOf(Check.notNull(constructors, "constructors"));
		_methods = ImmutableList.copyOf(Check.notNull(methods, "methods"));
		_visibility = Check.notNull(visibility, "visibility");
		_final = Check.notNull(finalModifier, "finalModifier");
		_interfaces = ImmutableList.copyOf(Check.notNull(interfaces, "interfaces"));
		_imports = ImmutableList.copyOf(Check.notNull(imports, "imports"));

		// TODO find a nice solution
		Check.notNull(annotations, "annotations");
		_annotations = ImmutableList.copyOf(Annotations.of(annotations).removeUnqualified(_imports).getAnnotations());

		Check.stateIsTrue(!abstractAndFinal(), "A class can be either abstract or final, not both.");
	}

	private boolean abstractAndFinal() {
		return _abstract == Abstract.ABSTRACT && _final == Final.FINAL ? true : false;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Clazz other = (Clazz) obj;
		if (_abstract != other._abstract) {
			return false;
		}
		if (!_annotations.equals(other._annotations)) {
			return false;
		}
		if (!_constructors.equals(other._constructors)) {
			return false;
		}
		if (!_fields.equals(other._fields)) {
			return false;
		}
		if (_final != other._final) {
			return false;
		}
		if (!_interfaces.equals(other._interfaces)) {
			return false;
		}
		if (!_methods.equals(other._methods)) {
			return false;
		}
		if (!_name.equals(other._name)) {
			return false;
		}
		if (!_package.equals(other._package)) {
			return false;
		}
		if (_visibility != other._visibility) {
			return false;
		}
		return true;
	}

	public Abstract getAbstract() {
		return _abstract;
	}

	public List<Annotation> getAnnotations() {
		return _annotations;
	}

	public List<Constructor> getConstructors() {
		return _constructors;
	}

	public List<Field> getFields() {
		return _fields;
	}

	public Final getFinal() {
		return _final;
	}

	public List<Import> getImports() {
		return Imports.copyOf(_imports).copyAndAdd(Imports.allOf(this)).filter().sortByName().asList();
	}

	public List<Interface> getInterfaces() {
		return _interfaces;
	}

	public List<Method> getMethods() {
		return _methods;
	}

	public String getName() {
		return _name;
	}

	public Package getPackage() {
		return _package;
	}

	public Visibility getVisibility() {
		return _visibility;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _abstract.hashCode();
		result = prime * result + _annotations.hashCode();
		result = prime * result + _constructors.hashCode();
		result = prime * result + _fields.hashCode();
		result = prime * result + _final.hashCode();
		result = prime * result + _interfaces.hashCode();
		result = prime * result + _methods.hashCode();
		result = prime * result + _name.hashCode();
		result = prime * result + _package.hashCode();
		result = prime * result + _visibility.hashCode();
		return result;
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		if (!Package.UNDEFINED.equals(_package)) {
			b.append(_package.toString());
			b.append(SEMICOLON);
			b.append(NEWLINE);
		}
		b.append(NEWLINE);
		final List<Import> imports = getImports();
		for (final Import imp : imports) {
			b.append(imp.toString());
			b.append(NEWLINE);
		}
		b.append(NEWLINE);
		if (!_annotations.isEmpty()) {
			for (final Annotation annotation : _annotations) {
				b.append(AT_SIGN);
				b.append(annotation.getType().getName());
				b.append(NEWLINE);
			}
		}
		if (_visibility != Visibility.UNDEFINED) {
			b.append(_visibility.getName());
			b.append(SPACE);
		}
		if (_final != Final.UNDEFINED) {
			b.append(_final.getName());
			b.append(SPACE);
		}
		b.append(PREFIX);
		b.append(SPACE);
		b.append(_name);
		b.append(SPACE);
		if (!_interfaces.isEmpty()) {
			b.append("implements");
			b.append(SPACE);
			b.append(Joiner.on(COMMA_SPACE).join(_interfaces));
			b.append(SPACE);
		}
		b.append(BRACE_OPEN);
		b.append(NEWLINE);
		b.append(NEWLINE);
		if (!_fields.isEmpty()) {
			for (final Field field : _fields) {
				b.append(field.toString());
				b.append(NEWLINE);
				b.append(NEWLINE);
			}
		}
		if (!_constructors.isEmpty()) {
			for (final Constructor constructor : _constructors) {
				b.append(constructor.toString());
				b.append(NEWLINE);
				b.append(NEWLINE);
			}
		}
		if (!_methods.isEmpty()) {
			for (final Method method : _methods) {
				b.append(method.toString());
				b.append(NEWLINE);
				b.append(NEWLINE);
			}
		}
		b.append(BRACE_CLOSE);
		b.append(NEWLINE);
		return b.toString();
	}

}
