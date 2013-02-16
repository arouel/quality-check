package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;

@Immutable
public final class ImmutableInterfaceAnalysis implements InterfaceAnalysis {

	@Nonnull
	public static ImmutableInterfaceAnalysis copyOf(@Nonnull final InterfaceAnalysis interfaceanalysis) {
		Check.notNull(interfaceanalysis, "interfaceanalysis");
		return new ImmutableInterfaceAnalysis(interfaceanalysis.getAnnotations(), interfaceanalysis.getExtends(),
				interfaceanalysis.getImports(), interfaceanalysis.getInterfaceName(), interfaceanalysis.getMethods(),
				interfaceanalysis.getPackage());
	}

	@Nonnull
	private final List<Annotation> annotations;

	@Nonnull
	private final List<Interface> extends1;

	@Nonnull
	private final List<Import> imports;

	@Nonnull
	private final String interfaceName;

	@Nonnull
	private final List<Method> methods;

	@Nonnull
	private final Package package1;

	public ImmutableInterfaceAnalysis(@Nonnull final List<Annotation> annotations, @Nonnull final List<Interface> extends1,
			@Nonnull final List<Import> imports, @Nonnull final String interfaceName, @Nonnull final List<Method> methods,
			@Nonnull final Package package1) {
		this.annotations = ImmutableList.copyOf(Check.notNull(annotations, "annotations"));
		this.extends1 = ImmutableList.copyOf(Check.notNull(extends1, "extends1"));
		this.imports = ImmutableList.copyOf(Check.notNull(imports, "imports"));
		this.interfaceName = Check.notNull(interfaceName, "interfaceName");
		this.methods = ImmutableList.copyOf(Check.notNull(methods, "methods"));
		this.package1 = Check.notNull(package1, "package1");
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
		final ImmutableInterfaceAnalysis other = (ImmutableInterfaceAnalysis) obj;
		return Objects.equal(annotations, other.annotations) && Objects.equal(extends1, other.extends1)
				&& Objects.equal(imports, other.imports) && Objects.equal(interfaceName, other.interfaceName)
				&& Objects.equal(methods, other.methods) && Objects.equal(package1, other.package1);
	}

	@Override
	@Nonnull
	public List<Annotation> getAnnotations() {
		return annotations;
	}

	@Override
	@Nonnull
	public List<Interface> getExtends() {
		return extends1;
	}

	@Override
	@Nonnull
	public List<Import> getImports() {
		return imports;
	}

	@Override
	@Nonnull
	public String getInterfaceName() {
		return interfaceName;
	}

	@Override
	@Nonnull
	public List<Method> getMethods() {
		return methods;
	}

	@Override
	@Nonnull
	public Package getPackage() {
		return package1;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(annotations, extends1, imports, interfaceName, methods, package1);
	}

}
