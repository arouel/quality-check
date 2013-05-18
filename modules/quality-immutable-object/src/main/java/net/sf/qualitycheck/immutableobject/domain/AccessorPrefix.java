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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

@Immutable
public final class AccessorPrefix {

	private static final String METHOD_GET_PREFIX = "get";
	private static final String METHOD_HAS_PREFIX = "has";
	private static final String METHOD_IS_PREFIX = "is";

	public static final AccessorPrefix NOT_NEEDED = new AccessorPrefix();
	public static final AccessorPrefix GET = new AccessorPrefix(METHOD_GET_PREFIX);
	public static final AccessorPrefix HAS = new AccessorPrefix(METHOD_HAS_PREFIX);
	public static final AccessorPrefix IS = new AccessorPrefix(METHOD_IS_PREFIX);

	@Nonnull
	private final String prefix;

	private AccessorPrefix() {
		prefix = "";
	}

	public AccessorPrefix(@Nonnull final String prefix) {
		Check.notNull(prefix, "prefix");
		this.prefix = Check.notEmpty(prefix.trim(), "prefix");
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
		final AccessorPrefix other = (AccessorPrefix) obj;
		if (!prefix.equals(other.prefix)) {
			return false;
		}
		return true;
	}

	public String getPrefix() {
		return prefix;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + prefix.hashCode();
		return result;
	}

}
