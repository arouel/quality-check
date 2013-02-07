package net.sf.qualitycheck.immutableobject.generator;

import net.sf.qualitycheck.immutableobject.domain.Imports;

/**
 * Defines a source code generator
 */
public interface CodeGenerator<T> {

	Imports dependsOn();

	String generate();

}
