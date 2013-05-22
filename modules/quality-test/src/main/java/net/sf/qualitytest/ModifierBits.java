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
package net.sf.qualitytest;

/**
 * Utility class to work with modifiers in the Java Reflection API.
 * 
 * @author Dominik Seichter
 * 
 */
public final class ModifierBits {

	/**
	 * Tests if a certain modifier is set into a bitmask.
	 * 
	 * @param modifiers
	 *            bitmask of modifiers
	 * @param modifier
	 *            bit of the modifier to be queried
	 * @return true if the bit is set
	 */
	public static boolean isModifierBitSet(final int modifiers, final int modifier) {
		return (modifiers & modifier) == modifier;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private ModifierBits() {
		// This class is not intended to create objects from it.
	}

}
