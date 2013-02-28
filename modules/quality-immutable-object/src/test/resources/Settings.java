package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Nonnull;

public interface Settings {

	/**
	 * Name of the corresponding builder class for an immutable object to be generated.
	 * 
	 * @return name of builder class or {@code null} to suppress generating of it
	 */
	@Nonnull
	String getBuilderName();

	/**
	 * Defines a field prefix as convention in all generated classes.
	 * 
	 * @return prefix for all fields in a class or an empty string
	 */
	@Nonnull
	String getFieldPrefix();

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
	 * A list of interfaces which must be implemented.
	 * <p>
	 * The first interface in list is the starting point to generate an immutable object class. All accessor methods in
	 * this interface will be taken to scaffold the class. All following interfaces will not be used as a object
	 * definition. It is intended to serve interfaces like {@link java.io.Serializable} or {@link java.lang.Comparable}.
	 * 
	 * @return list of interfaces to scaffold an immutable object class
	 */
	@Nonnull
	List<Interface> getInterfaces();

	/**
	 * The interface as starting point to generate an immutable object class.
	 * 
	 * @return scaffold of an immutable object class
	 */
	@Nonnull
	Interface getMainInterface();

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
	 * Implements the related interface also into corresponding builder class.
	 * 
	 * @return {@code true} to generate accessor methods also into builder class otherwise {@code false} to suppress
	 *         their rendering
	 */
	boolean hasBuilderImplementsInterface();

	/**
	 * Use <i>Guava</i> (Google Core Libraries for Java 1.6+) when generating <code>equals()</code> and
	 * <code>hashCode()</code> methods and to copy or to make <i>immutable</i> {@code Iterable}s and {@code Map}s.
	 * 
	 * @return {@code true} to use Guava classes otherwise {@code false}
	 */
	boolean hasGuava();

	/**
	 * Generate <code>hashCode()</code> and <code>equals()</code> methods in immutable object class.
	 * 
	 * @return {@code true} to override <code>hashCode()</code> and <code>equals()</code> methods otherwise
	 *         {@code false}
	 */
	boolean hasHashCodeAndEquals();

	/**
	 * Generate code to precompute the {@code hashCode} during construction time.
	 * <p>
	 * Consider to choose this option when and only when you'll get better performance in your application (because you
	 * want to use objects of the class in {@link Set}s and {@link Map}s) and you take care that the class to be
	 * generated is guaranteed to be immutable. This means all fields of this class can never change his state, like
	 * {@link String} does.
	 * 
	 * @return {@code true} to precompute <code>hashCode</code> during instaniation of an object otherwise {@code false}
	 */
	boolean hasHashCodePrecomputation();

	/**
	 * Add JSR 305 Annotations for Software Defect Detection in Java.
	 * 
	 * @return {@code true} to generate such annotations otherwise {@code false}
	 */
	boolean hasJsr305Annotations();

	/**
	 * Use <i>Quality-Check</i> to prevent technical errors in generated class(es), because they only have to work with
	 * valid arguments. <i>Quality-Check</i> will be applied when reading argument values which are annotated with
	 * <code>@Nonnull</code> or <code>@Nonnegative</code>.
	 * 
	 * @return {@code true} to apply technical checks in generated classes otherwise {@code false}
	 */
	boolean hasQualityCheck();

	/**
	 * Generate <code>toString()</code> method in immutable object class.
	 * 
	 * @return {@code true} to override <code>toString()</code> method otherwise {@code false}
	 */
	boolean hasToString();

	/**
	 * Generates <code>serialVersionUID</code> constant (with default value <code>1L</code>) into immutable object class
	 * and adds implementation hint to {@code Serializable} interface.
	 * 
	 * @return {@code true} to generate <code>serialVersionUID</code> otherwise {@code false}
	 */
	boolean isSerializable();

}
