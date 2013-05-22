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
package net.sf.qualitycheck.immutableobject.domain;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

@Immutable
public final class ReturnType {

	public static final ReturnType BOOLEAN = ReturnType.of("boolean");

	public static final ReturnType INT = ReturnType.of("int");

	public static final ReturnType VOID = new ReturnType();

	public static ReturnType of(@Nonnull final Class<?> clazz) {
		return new ReturnType(Type.of(Check.notNull(clazz, "clazz")));
	}

	public static ReturnType of(@Nonnull final String type) {
		return new ReturnType(new Type(Check.notEmpty(type, "type")));
	}

	public static ReturnType of(@Nonnull final Type type) {
		return new ReturnType(Check.notNull(type, "type"));
	}

	@Nonnull
	private final Type type;

	private ReturnType() {
		type = new Type("void");
	}

	private ReturnType(@Nonnull final Type type) {
		this.type = Check.notNull(type, "type");
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
		final ReturnType other = (ReturnType) obj;
		if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}

	public Type getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + type.hashCode();
		return result;
	}

	@Override
	public String toString() {
		return type.toString();
	}

}
