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
package net.sf.qualitytest.blueprint;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_collection {

	public static class WithCollection {
		private Collection<?> elements;

		public Collection<?> getElements() {
			return elements;
		}

		public void setElements(final Collection<?> elements) {
			this.elements = elements;
		}

	}

	@Test
	public void testCollection() {
		Assert.assertNotNull(Blueprint.def().construct(Collection.class));
	}

	@Test
	public void testCollectionInClass() {
		final WithCollection wc = Blueprint.def().construct(WithCollection.class);
		Assert.assertTrue(wc.getElements().isEmpty());
	}

	@Test
	public void testCollectionInClassWithRandom() {
		final WithCollection wc = Blueprint.random().construct(WithCollection.class);
		Assert.assertTrue(wc.getElements().isEmpty());
	}
}
