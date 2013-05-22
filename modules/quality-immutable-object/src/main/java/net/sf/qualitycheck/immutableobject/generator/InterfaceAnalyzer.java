/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.qualitycheck.immutableobject.generator;

import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.TypeDeclaration;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.ImmutableInterfaceAnalysis;
import net.sf.qualitycheck.immutableobject.domain.Imports;
import net.sf.qualitycheck.immutableobject.domain.Interface;
import net.sf.qualitycheck.immutableobject.domain.InterfaceAnalysis;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.domain.Package;

@ThreadSafe
final class InterfaceAnalyzer {

	/**
	 * Analyzes the source code of an interface. The specified interface must not contain methods, that changes the
	 * state of the corresponding object itself.
	 * 
	 * @param code
	 *            source code of an interface which describes how to generate the <i>immutable</i>
	 * @return analysis result
	 */
	@Nonnull
	public static InterfaceAnalysis analyze(@Nonnull final String code) {
		Check.notNull(code, "code");

		final CompilationUnit unit = Check.notNull(SourceCodeReader.parse(code), "compilationUnit");
		final List<TypeDeclaration> types = Check.notEmpty(unit.getTypes(), "typeDeclarations");
		Check.stateIsTrue(types.size() == 1, "only one interface declaration per analysis is supported");

		final ClassOrInterfaceDeclaration type = (ClassOrInterfaceDeclaration) types.get(0);

		final Imports imports = SourceCodeReader.findImports(unit.getImports());
		final Package pkg = unit.getPackage() != null ? new Package(unit.getPackage().getName().toString()) : Package.UNDEFINED;
		final List<Annotation> annotations = SourceCodeReader.findAnnotations(type.getAnnotations(), imports);
		final List<Method> methods = SourceCodeReader.findMethods(type.getMembers(), imports);
		Check.stateIsTrue(!hasPossibleMutatingMethods(methods), "The passed interface '%s' seems to have mutating methods", type.getName());
		final List<Interface> extendsInterfaces = SourceCodeReader.findExtends(type);
		final String interfaceName = type.getName();
		return new ImmutableInterfaceAnalysis(annotations, extendsInterfaces, imports.asList(), interfaceName, methods, pkg);
	}

	/**
	 * Checks if the given type has possible mutating methods (e.g. setter methods or methods with parameters).
	 * 
	 * @param type
	 *            interface
	 * @return {@code true} if possible mutating methods were found otherwise {@code false}
	 */
	private static boolean hasPossibleMutatingMethods(@Nonnull final List<Method> methods) {
		boolean result = false;
		for (final Method method : methods) {
			if (!method.getAttributes().isEmpty()) {
				result = true;
				break;
			}
		}
		return result;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private InterfaceAnalyzer() {
		// This class is not intended to create objects from it.
	}

}
