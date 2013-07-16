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

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_interfaceMethod {

	private static interface SomeEntityBean {

		long getId();

		String getName();
	}

	@Test
	public void testBlueprintInterfaceMethod() {
		final SomeEntityBean bean = Blueprint.def().with("getId", 42L).with("getName", "Hello World!").construct(SomeEntityBean.class);
		Assert.assertEquals(42L, bean.getId());
		Assert.assertEquals("Hello World!", bean.getName());
	}

}
