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

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

public class CollectionVariantTest {

	@Test
	public void collectionVariant_checkAll() {
		assertEquals(CollectionVariant.COLLECTION, CollectionVariant.evaluate(Type.of(Collection.class)));
		assertEquals(CollectionVariant.ITERABLE, CollectionVariant.evaluate(Type.of(Iterable.class)));
		assertEquals(CollectionVariant.LIST, CollectionVariant.evaluate(Type.of(List.class)));
		assertEquals(CollectionVariant.MAP, CollectionVariant.evaluate(Type.of(Map.class)));
		assertEquals(CollectionVariant.SET, CollectionVariant.evaluate(Type.of(Set.class)));
		assertEquals(CollectionVariant.SORTEDSET, CollectionVariant.evaluate(Type.of(SortedSet.class)));
		assertEquals(CollectionVariant.SORTEDSET, CollectionVariant.evaluate(Type.of(SortedSet.class)));
		assertEquals(7, CollectionVariant.values().length);
	}

}
