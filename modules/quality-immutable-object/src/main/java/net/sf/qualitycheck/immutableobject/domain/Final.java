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

/**
 * Represents the modifier {@code final} which can be available at attributes, classes or methods
 */
public enum Final implements Modifier {

	/**
	 * This value can be used to express that no declaration is available.
	 */
	UNDEFINED(""),

	/**
	 * This value express that an attribute, class or method is final.
	 */
	FINAL("final");

	private final String name;

	Final(@Nonnull final String name) {
		this.name = name;
	}

	@Override
	@Nonnull
	public String getName() {
		return name;
	}

	public boolean isUndefined() {
		return UNDEFINED.equals(this);
	}

}
