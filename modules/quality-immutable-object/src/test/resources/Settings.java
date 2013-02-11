package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public interface Settings {

	/**
	 * Name of the corresponding builder class for an immutable object to be generated.
	 * 
	 * @return name of builder class or {@code null} to suppress generating of it
	 */
	@Nullable
	String getBuilderName();

	/**
	 * The fields of the immutable object class (and corresponding builder class) to be generated.
	 * 
	 * @return a list of fields to be generated
	 */
	@Nonnull
	List<Field> getFields();

	/**
	 * Name of the immutable object class to be generated.
	 * 
	 * @return name of immutable object class
	 */
	@Nonnull
	String getImmutableName();

	/**
	 * The imports of the compilation unit to be generated.
	 * 
	 * @return a list of imports to be generated
	 */
	@Nonnull
	List<Import> getImports();

	/**
	 * The interface as starting point to generate an immutable object class.
	 * 
	 * @return scaffold of an immutable object class
	 */
	@Nonnull
	Interface getInterfaceDeclaration();

	/**
	 * The package declaration of the immutable object class to be generated.
	 * 
	 * @return package declaration of compilation units to be generated
	 */
	@Nonnull
	Package getPackageDeclaration();

	/**
	 * Generate a copy constructor in builder class
	 * 
	 * @return {@code true} to generate a corresponding builder class for an immutable object class otherwise
	 *         {@code false}
	 */
	boolean hasBuilderCopyConstructor();

	/**
	 * Generate flat mutator methods in corresponding builder class (e.g. <code>builderName</code> instead of
	 * <code>setBuilderName</code>).
	 * 
	 * @return {@code true} to generate fluent methods otherwise {@code false}
	 */
	boolean hasBuilderFlatMutators();

	/**
	 * Generate fluent mutator methods in corresponding builder class. The methods return the builder itself (
	 * <code>this</code>) instead of <code>void</code> (self-referential).
	 * 
	 * @return {@code true} to generate fluent methods otherwise {@code false}
	 */
	boolean hasBuilderFluentMutators();

	/**
	 * Implements the related interface into corresponding builder too.
	 * 
	 * @return {@code true} to generate accessor methods into builder otherwise {@code false}
	 */
	boolean hasBuilderSameInterface();

	/**
	 * Use Guava (Google Core Libraries for Java 1.6+) when generating <code>equals()</code> and <code>hashCode()</code>
	 * methods and to copy or to make <i>immutable</i> {@code Iterable}s and {@code Map}s.
	 * 
	 * @return {@code true} to use Guava classes otherwise {@code false}
	 */
	boolean hasGuava();

	/**
	 * Add JSR 305 Annotations for Software Defect Detection in Java.
	 * 
	 * @return {@code true} to generate such annotations otherwise {@code false}
	 */
	boolean hasJsr305Annotations();

	/**
	 * Generates <code>serialVersionUID</code> constant (with default value <code>1L</code>) into immutable object class
	 * and adds implementation hint to {@code Serializable} interface.
	 * 
	 * @return {@code true} to generate <code>serialVersionUID</code> otherwise {@code false}
	 */
	boolean hasSerialVersion();

}