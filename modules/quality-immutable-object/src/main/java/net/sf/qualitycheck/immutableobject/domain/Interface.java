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
public final class Interface {

	public static final Interface UNDEFINED = new Interface();

	public static Interface of(@Nonnull final Class<?> interfaceType) {
		Check.notNull(interfaceType, "interfaceType");
		Check.stateIsTrue(interfaceType.isInterface(), "requires an interface");
		return new Interface(Type.of(interfaceType));
	}

	public static Interface of(@Nonnull final String interfaceType) {
		Check.notEmpty(interfaceType, "interfaceType");
		return new Interface(new Type(interfaceType));
	}

	@Nonnull
	private final Type type;

	public Interface() {
		type = new Type("void");
	}

	public Interface(final Type interfaceType) {
		type = Check.notNull(interfaceType, "interfaceType");
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
		final Interface other = (Interface) obj;
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
