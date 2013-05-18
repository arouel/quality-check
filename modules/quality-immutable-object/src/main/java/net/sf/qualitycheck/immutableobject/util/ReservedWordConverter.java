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
package net.sf.qualitycheck.immutableobject.util;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.ReservedWord;

public final class ReservedWordConverter {

	public static String convertIfNecessary(final String name) {
		Check.notNull(name, "name");
		return ReservedWord.isReserved(name) ? name + 1 : name;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private ReservedWordConverter() {
		// This class is not intended to create objects from it.
	}

}
