package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;

public interface InterfaceAnalysis {

	@Nonnull
	List<Annotation> getAnnotations();

	@Nonnull
	List<Interface> getExtends();

	@Nonnull
	List<Import> getImports();

	@Nonnull
	String getInterfaceName();

	@Nonnull
	List<Method> getMethods();

	@Nonnull
	Package getPackage();

}
