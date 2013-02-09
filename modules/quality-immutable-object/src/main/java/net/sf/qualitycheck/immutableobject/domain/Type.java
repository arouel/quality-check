package net.sf.qualitycheck.immutableobject.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.util.Primitive;

/**
 * Represents a type which can be a class, interface or annotation
 */
@Immutable
public final class Type {

	/**
	 * Pattern to parse a full qualified name of a type
	 * <p>
	 * ^(((\d|\w)+\.)*)((\d|\w)+)(\$((\d|\w)+))?(<(\w.*)>)?$
	 */
	private static final Pattern PATTERN = Pattern.compile("^(((\\d|\\w)+\\.)*)((\\d|\\w)+)(\\$((\\d|\\w)+))?(<(\\w.*)>)?$");

	/**
	 * Creates a new instance of {@code GenericDeclaration} when the given declaration is not empty other wise it
	 * returns {@link GenericDeclaration#UNDEFINED}.
	 * 
	 * @param declaration
	 *            declaration of a generic
	 * @return instance of {@code GenericDeclaration} and never null
	 */
	@Nonnull
	private static GenericDeclaration createGenericDeclaration(@Nonnull final String declaration) {
		Check.notNull(declaration, "declaration");
		return declaration.isEmpty() ? GenericDeclaration.UNDEFINED : GenericDeclaration.of(declaration);
	}

	/**
	 * Creates a new instance of {@code Package} when the given name is not empty other wise it returns
	 * {@link Package#UNDEFINED}.
	 * 
	 * @param packageName
	 *            package name
	 * @return instance of {@code Package} and never null
	 */
	@Nonnull
	private static Package createPackage(@Nonnull final String packageName) {
		Check.notNull(packageName, "packageName");
		return packageName.isEmpty() ? Package.UNDEFINED : new Package(packageName);
	}

	@Nonnull
	public static Type of(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz, "clazz");
		return new Type(clazz.getName());
	}

	@Nonnull
	private final GenericDeclaration _genericDeclaration;

	@Nonnull
	private final Package _package;

	@Nonnull
	private final String _name;

	public Type(@Nonnull final Package packageName, @Nonnull final String typeName, @Nonnull final GenericDeclaration genericDeclaration) {
		_package = Check.notNull(packageName, "packageName");
		_name = Check.notEmpty(typeName, "typeName");
		_genericDeclaration = Check.notNull(genericDeclaration, "genericDeclaration");
	}

	public Type(@Nonnull final String qualifiedName) {
		Check.notNull(qualifiedName, "qualifiedName");
		final Matcher m = PATTERN.matcher(Check.notEmpty(qualifiedName.trim(), "qualifiedName"));
		Check.stateIsTrue(m.matches(), "qualified name must match against: %s", PATTERN.pattern());
		if (m.group(7) != null) {
			// should be an inner interface
			_package = createPackage(m.group(1) + m.group(4));
			_name = m.group(7);
		} else {
			_package = createPackage(m.group(1));
			_name = Check.notEmpty(m.group(4), "typeName");
		}
		if (m.group(10) != null) {
			_genericDeclaration = createGenericDeclaration(m.group(10));
		} else {
			_genericDeclaration = GenericDeclaration.UNDEFINED;
		}
	}

	public Type(@Nonnull final String packageName, @Nonnull final String typeName, @Nonnull final String genericDeclaration) {
		this(createPackage(Check.notNull(packageName, "packageName")), Check.notEmpty(typeName, "typeName"), createGenericDeclaration(Check
				.notNull(genericDeclaration, "genericDeclaration")));
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
		final Type other = (Type) obj;
		if (!_genericDeclaration.equals(other._genericDeclaration)) {
			return false;
		}
		if (!_package.equals(other._package)) {
			return false;
		}
		if (!_name.equals(other._name)) {
			return false;
		}
		return true;
	}

	@Nonnull
	public GenericDeclaration getGenericDeclaration() {
		return _genericDeclaration;
	}

	@Nonnull
	public String getName() {
		return _name;
	}

	@Nonnull
	public Package getPackage() {
		return _package;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + _genericDeclaration.hashCode();
		result = prime * result + _package.hashCode();
		result = prime * result + _name.hashCode();
		return result;
	}

	public boolean isPrimitive() {
		return Primitive.isPrimitive(_name);
	}

	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		if (!_package.getName().isEmpty()) {
			b.append(_package.getName());
			b.append(Characters.DOT);
		}
		b.append(_name);
		if (!_genericDeclaration.getDeclaration().isEmpty()) {
			b.append('<');
			b.append(_genericDeclaration.getDeclaration());
			b.append('>');
		}
		return b.toString();
	}

}
