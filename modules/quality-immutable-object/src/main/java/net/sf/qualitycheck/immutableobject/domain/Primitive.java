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

import net.sf.qualitycheck.Check;

public enum Primitive {

	BOOLEAN(ReservedWord.BOOLEAN.getWord()),

	BYTE(ReservedWord.BYTE.getWord()),

	LONG(ReservedWord.LONG.getWord()),

	INT(ReservedWord.INT.getWord()),

	DOUBLE(ReservedWord.DOUBLE.getWord()),

	FLOAT(ReservedWord.FLOAT.getWord()),

	CHAR(ReservedWord.CHAR.getWord()),

	SHORT(ReservedWord.SHORT.getWord());

	public static boolean isPrimitive(final String name) {
		Check.notNull(name, "name");
		boolean ret = false;
		for (final Primitive p : values()) {
			if (p.name.equals(name)) {
				ret = true;
				break;
			}
		}
		return ret;
	}

	private final String name;

	private Primitive(final String name) {
		this.name = Check.notNull(name);
	}

	public String getName() {
		return name;
	}

}
