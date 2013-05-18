/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.qualitycheck.immutableobject.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;

public final class GenericDeclaration {

	/**
	 * Represents a undefined generic declaration
	 */
	public static final GenericDeclaration UNDEFINED = new GenericDeclaration();

	/**
	 * ^((\w[\?\d\w])*)<(\w+|(\w+\sextends\s(.*)))>
	 */
	private static final Pattern PATTERN = Pattern.compile("((\\w[\\d\\w])*)(<(\\w+.*)>)?");

	public static GenericDeclaration of(@Nonnull final String declaration) {
		return new GenericDeclaration(Check.notEmpty(declaration, "declaration"));
	}

	public static GenericDeclaration parseFrom(@Nonnull final String typeDeclaration) {
		Check.notNull(typeDeclaration, "typeDeclaration");
		final Matcher m = PATTERN.matcher(typeDeclaration);
		return m.matches() && m.group(4) != null ? GenericDeclaration.of(m.group(4)) : GenericDeclaration.UNDEFINED;
	}

	@Nonnull
	private final String declaration;

	/**
	 * Creates an undefined representation of {@code GenericDeclaration} and is intended only for internal usage.
	 */
	private GenericDeclaration() {
		declaration = "";
	}

	private GenericDeclaration(@Nonnull final String declaration) {
		this.declaration = Check.notEmpty(declaration, "declaration");
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
		final GenericDeclaration other = (GenericDeclaration) obj;
		if (!declaration.equals(other.declaration)) {
			return false;
		}
		return true;
	}

	public String getDeclaration() {
		return declaration;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + declaration.hashCode();
		return result;
	}

	public boolean isUndefined() {
		return UNDEFINED.equals(this);
	}

	@Override
	public String toString() {
		return declaration;
	}

}
