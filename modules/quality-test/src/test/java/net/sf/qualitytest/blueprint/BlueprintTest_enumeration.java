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

import net.sf.qualitytest.blueprint.configuration.DefaultBlueprintConfiguration;
import net.sf.qualitytest.blueprint.configuration.RandomBlueprintConfiguration;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_enumeration {

	private enum EmptyEnum {

	};

	private enum SimpleEnum {
		A, B, C, D, E, F, G, H
	};

	@Test
	public void testEnumeration() {
		final SimpleEnum simple = Blueprint.object(SimpleEnum.class);
		Assert.assertEquals(SimpleEnum.A, simple);
	}

	@Test
	public void testEnumerationDefault() {
		final SimpleEnum simple = Blueprint.object(SimpleEnum.class, new DefaultBlueprintConfiguration());
		Assert.assertEquals(SimpleEnum.A, simple);
	}

	@Test
	public void testEnumerationEmpty() {
		final EmptyEnum simple = Blueprint.object(EmptyEnum.class);
		Assert.assertNull(simple);
	}

	@Test
	public void testEnumerationOverride() {
		final SimpleEnum simple = Blueprint.def().with(SimpleEnum.class, SimpleEnum.D).object(SimpleEnum.class);
		Assert.assertEquals(SimpleEnum.D, simple);
	}

	@Test
	public void testEnumerationRandom() {
		final BlueprintSession session = new BlueprintSession();
		final BlueprintConfiguration rand = new RandomBlueprintConfiguration();
		final SimpleEnum simple = Blueprint.object(SimpleEnum.class, rand, session);
		final SimpleEnum simple1 = Blueprint.object(SimpleEnum.class, rand, session);
		final SimpleEnum simple2 = Blueprint.object(SimpleEnum.class, rand, session);
		final boolean a = simple != simple1;
		final boolean b = simple != simple2;
		final boolean c = simple2 != simple1;
		Assert.assertTrue(a || b || c);
	}
}
