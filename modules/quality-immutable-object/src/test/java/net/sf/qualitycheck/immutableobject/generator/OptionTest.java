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
package net.sf.qualitycheck.immutableobject.generator;

import static org.junit.Assert.assertEquals;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.immutableobject.generator.FieldRenderer.Option;

import org.junit.Test;

public class OptionTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Option.class, areImmutable());
	}

	@Test
	public void evaluate() {
		assertEquals(Option.ATTRIBUTE, Option.evaluate("aTtribute"));
		assertEquals(Option.COPY, Option.evaluate("copy"));
		assertEquals(Option.COPY_FROM_INTERFACE, Option.evaluate("COPY_FROM_INTERFACE"));
		assertEquals(Option.IMMUTABLE, Option.evaluate("ImmutaBle"));
		assertEquals(Option.UNDEFINED, Option.evaluate(""));
		assertEquals(Option.UNDEFINED, Option.evaluate(" "));
		assertEquals(Option.UNDEFINED, Option.evaluate("unknown"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void evaluate_option_isNull() {
		Option.evaluate(null);
	}

}
