package net.sf.qualitycheck.immutableobject.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

/**
 * Represents a list of import statements of a class
 */
@Immutable
public final class Imports {

	private static final Predicate<Import> IGNORE = new Predicate<Import>() {
		@Override
		public boolean apply(final Import imp) {
			return !Package.UNDEFINED.equals(imp.getType().getPackage());
		}
	};

	private static final Ordering<Import> ORDER = new Ordering<Import>() {
		@Override
		public int compare(final Import left, final Import right) {
			return left.getType().toString().compareTo(right.getType().toString());
		}
	};

	public static Imports allOf(@Nonnull final Clazz clazz) {
		Check.notNull(clazz, "clazz");
		final List<Import> imports = Lists.newArrayList();
		imports.addAll(ofAnnotations(clazz.getAnnotations()).asList());
		imports.addAll(ofConstructors(clazz.getConstructors()).asList());
		imports.addAll(ofFields(clazz.getFields()).asList());
		imports.addAll(ofInterfaces(clazz.getInterfaces()).asList());
		imports.addAll(ofMethods(clazz.getMethods()).asList());
		return new Imports(imports);
	}

	public static Imports allOf(@Nonnull final Field field) {
		Check.notNull(field, "field");
		final List<Import> imports = Lists.newArrayList();
		imports.add(Import.of(field));
		imports.addAll(ofAnnotations(field.getAnnotations()).asList());
		return new Imports(imports);
	}

	public static Imports allOf(@Nonnull final Method method) {
		Check.notNull(method, "method");
		final List<Import> imports = Lists.newArrayList();
		imports.add(Import.of(method.getReturnType().getType()));
		imports.addAll(ofAnnotations(method.getAnnotations()).asList());
		return new Imports(imports);
	}

	public static Imports copyOf(final Iterable<Import> imports) {
		Check.notNull(imports, "imports");
		return new Imports(imports);
	}

	public static Imports of(final Import... imports) {
		Check.notNull(imports, "imports");
		return new Imports(Lists.newArrayList(imports));
	}

	public static Imports of(final Iterable<Import> imports) {
		Check.notNull(imports, "imports");
		return new Imports(Lists.newArrayList(imports));
	}

	private static Imports ofAnnotations(@Nonnull final Iterable<Annotation> annotations) {
		Check.notNull(annotations, "annotations");
		final List<Import> imports = Lists.newArrayList();
		for (final Annotation annotation : annotations) {
			imports.add(Import.of(annotation));
		}
		return new Imports(imports);
	}

	private static Imports ofAttributes(@Nonnull final Iterable<Attribute> attributes) {
		Check.notNull(attributes, "attributes");
		final List<Import> imports = Lists.newArrayList();
		for (final Attribute attribute : attributes) {
			imports.add(Import.of(attribute));
		}
		return new Imports(imports);
	}

	private static Imports ofConstructors(@Nonnull final Iterable<Constructor> constructors) {
		Check.notNull(constructors, "attributes");
		final List<Import> imports = Lists.newArrayList();
		for (final Constructor constructor : constructors) {
			imports.addAll(ofAnnotations(constructor.getAnnotations()).asList());
			imports.addAll(ofAttributes(constructor.getAttributes()).asList());
		}
		return new Imports(imports);
	}

	private static Imports ofFields(@Nonnull final Iterable<Field> fields) {
		Check.notNull(fields, "fields");
		final List<Import> imports = Lists.newArrayList();
		for (final Field field : fields) {
			imports.add(Import.of(field));
		}
		return new Imports(imports);
	}

	private static Imports ofInterfaces(@Nonnull final Iterable<Interface> interfaces) {
		Check.notNull(interfaces, "interfaces");
		final List<Import> imports = Lists.newArrayList();
		for (final Interface i : interfaces) {
			imports.add(Import.of(i));
		}
		return new Imports(imports);
	}

	private static Imports ofMethods(@Nonnull final Iterable<Method> methods) {
		Check.notNull(methods, "methods");
		final List<Import> imports = Lists.newArrayList();
		for (final Method method : methods) {
			imports.addAll(allOf(method).asList());
		}
		return new Imports(imports);
	}

	@Nonnull
	private final List<Import> _imports;

	private Imports(final Iterable<Import> imports) {
		Check.notNull(imports, "imports");
		_imports = ImmutableList.copyOf(imports);
	}

	/**
	 * Gets the current set of {@code Import}s as {@link List}.
	 * 
	 * @return immutable list of {@code Import}s
	 */
	@Nonnull
	public List<Import> asList() {
		return _imports;
	}

	/**
	 * Adds the passed set of {@code Import}s to the current set and returns them as new instance of {@code Imports}.
	 * 
	 * @param imports
	 *            set of {@code Import}s to add
	 * @return new instance of {@code Imports}
	 */
	@Nonnull
	public Imports copyAndAdd(final Collection<Import> imports) {
		Check.notNull(imports, "imports");
		final List<Import> internal = Lists.newArrayList(_imports);
		internal.addAll(imports);
		return new Imports(internal);
	}

	/**
	 * Adds the passed {@code Imports} to the current set and returns them as new instance of {@code Imports}.
	 * 
	 * @param imports
	 *            {@code Imports} to add
	 * @return new instance of {@code Imports}
	 */
	@Nonnull
	public Imports copyAndAdd(final Imports imports) {
		Check.notNull(imports, "imports");
		return copyAndAdd(imports.asList());
	}

	/**
	 * Filters primitive types, types without a package declaration and duplicates and returns them as new instance of
	 * {@code Imports}.
	 * 
	 * @return new instance of {@code Imports}
	 */
	@Nonnull
	public Imports filter() {
		return new Imports(Sets.newHashSet(Collections2.filter(_imports, IGNORE)));
	}

	/**
	 * Searches the set of imports to find a matching import by type name.
	 * 
	 * @param typeName
	 *            name of type (qualified or simple name allowed)
	 * @return found import or {@code null}
	 */
	@Nullable
	public Import find(@Nonnull final String typeName) {
		Check.notEmpty(typeName, "typeName");
		Import ret = null;
		final Type type = new Type(typeName);
		for (final Import imp : _imports) {
			if (imp.getType().getTypeName().equals(type.getTypeName())) {
				ret = imp;
				break;
			}
		}
		return ret;
	}

	/**
	 * Sorts the current set of {@code Import}s by name and returns them as new instance of {@code Imports}.
	 * 
	 * @return new instance of {@code Imports}
	 */
	@Nonnull
	public Imports sortByName() {
		final List<Import> imports = Lists.newArrayList(_imports);
		Collections.sort(imports, ORDER.nullsLast());
		return new Imports(imports);
	}

}
