/*******************************************************************************
 * Copyright 2013 Dominik Seichter
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
package net.sf.qualitytest.blueprint;

import java.util.Collection;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

/**
 * This class contains utilities for blueprinting collections.
 * 
 * @author Dominik Seichter
 */
public final class CollectionBlueprint {

	/**
	 * A small utility to fill a collection automatically with blueprinted objects.
	 * 
	 * The default configuration is used.
	 * 
	 * @see Blueprint.def
	 * 
	 * @param <T>
	 *            type of the objects to blueprint and add
	 * @param collection
	 *            a collection where objects are supposed to be added
	 * @param clazz
	 *            the class of the type which is to be added
	 * @param numberOfItems
	 *            the number of items that should be added.
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> void addMany(final Collection<T> collection, final Class<T> clazz, final int numberOfItems) {
		CollectionBlueprint.addMany(collection, clazz, numberOfItems, Blueprint.def());
	}

	/**
	 * A small utility to fill a collection automatically with blueprinted objects.
	 * 
	 * @param <T>
	 *            type of the objects to blueprint and add
	 * @param collection
	 *            a collection where objects are supposed to be added
	 * @param clazz
	 *            the class of the type which is to be added
	 * @param numberOfItems
	 *            the number of items that should be added.
	 * @param config
	 *            The configuration to use
	 */
	@Throws(IllegalNullArgumentException.class)
	public static <T> void addMany(final Collection<T> collection, final Class<T> clazz, final int numberOfItems,
			final BlueprintConfiguration config) {
		Check.notNull(collection, "collection");
		Check.notNull(clazz, "clazz");
		Check.notNull(config, "config");

		int cnt = numberOfItems;
		while (cnt > 0) {
			collection.add(Blueprint.object(clazz, config));
			cnt--;
		}
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private CollectionBlueprint() {
		// This class is not intended to create objects from it.
	}

}
