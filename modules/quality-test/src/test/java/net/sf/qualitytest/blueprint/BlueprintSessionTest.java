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

import net.sf.qualitytest.blueprint.configuration.DefaultBlueprintConfiguration;
import net.sf.qualitytest.exception.BlueprintCycleException;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintSessionTest {

	public static class CyclicObject {
		private CyclicObject next;
		private String value;

		public CyclicObject getNext() {
			return next;
		}

		public String getValue() {
			return value;
		}

		public void setNext(final CyclicObject next) {
			this.next = next;
		}

		public void setValue(final String value) {
			this.value = value;
		}

	}

	@Test
	public void testCountAndClasses() {
		final BlueprintSession session = new BlueprintSession();
		final String s = Blueprint.construct(String.class, new DefaultBlueprintConfiguration(), session);
		Assert.assertEquals("", s);
		Assert.assertEquals(1, session.getBlueprintCount());
		Assert.assertEquals(1, session.getBlueprintClasses().size());
		Assert.assertTrue(session.getBlueprintClasses().contains(String.class));
	}

	@Test(expected = BlueprintCycleException.class)
	public void testCycle() {
		final CyclicObject o = Blueprint.construct(CyclicObject.class);
		Assert.assertEquals("", o.getValue());
		Assert.assertNotNull(o.getNext());
	}

	@Test
	public void testCycleMessage() {
		try {
			Blueprint.construct(CyclicObject.class);
			Assert.assertFalse(true);
		} catch (final BlueprintCycleException e) {
			Assert.assertTrue(e
					.getMessage()
					.startsWith(
							"Error during blueprinting class 'net.sf.qualitytest.blueprint.BlueprintSessionTest$CyclicObject': net.sf.qualitytest.blueprint.BlueprintSessionTest$CyclicObject->net.sf.qualitytest.blueprint.BlueprintSessionTest$CyclicObject {Invoking method setValue with arguments"));
		}
	}
}
