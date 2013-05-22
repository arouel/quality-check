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

import java.util.ArrayList;
import java.util.List;

import net.sf.qualitytest.CoverageForPrivateConstructor;
import net.sf.qualitytest.StaticCheck;

import org.junit.Assert;
import org.junit.Test;

public class CollectionBlueprintTest {

	@Test
	public void coverPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(CollectionBlueprint.class);
	}

	@Test
	public void testAddMany() {
		final List<ImmutableObject> list = new ArrayList<ImmutableObject>();
		CollectionBlueprint.addMany(list, ImmutableObject.class, 9);
		Assert.assertEquals(9, list.size());
		for (final ImmutableObject immutable : list) {
			Assert.assertNotNull(immutable.getDate());
		}
	}

	@Test
	public void testMakeSureClassIsFinalAndNotAccessible() {
		StaticCheck.classIsFinal(CollectionBlueprint.class);
		StaticCheck.noPublicDefaultConstructor(CollectionBlueprint.class);
	}

}
