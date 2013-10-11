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
package net.sf.qualitytest.blueprint.configuration;

import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.invocationhandler.CachedBlueprintInvocationHandler;
import net.sf.qualitytest.blueprint.invocationhandler.RefreshingBlueprintInvocationHandler;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;

import org.junit.Assert;
import org.junit.Test;

public class ImmutableBlueprintConfigurationTest {

	public interface SimpleInterface {
		String getString();
	}

	public interface SimpleInterface2 {
		int getNumber();
	}

	@Test
	public void testDifferentInvocationHandlers() {
		final BlueprintConfiguration config = Blueprint.random().with(SimpleInterface.class, new RefreshingBlueprintInvocationHandler())
				.with(SimpleInterface2.class, new CachedBlueprintInvocationHandler());
		final SimpleInterface si = config.construct(SimpleInterface.class);
		Assert.assertNotNull(si.getString());

		final SimpleInterface2 si2 = config.construct(SimpleInterface2.class);
		final int a = si2.getNumber();
		final int b = si2.getNumber();

		Assert.assertEquals(a, b);
	}

	@Test
	public void testInvocationHandlerNotFound() {
		final BlueprintConfiguration config = new ImmutableBlueprintConfiguration();
		final SimpleInterface si = config.construct(SimpleInterface.class);
		Assert.assertNotNull(si.getString());
	}

	@Test
	public void testWithPublicAttributesDefault() {
		final ImmutableBlueprintConfiguration config = new ImmutableBlueprintConfiguration();
		Assert.assertEquals(false, config.isWithPublicAttributes());
	}

	@Test
	public void testWithPublicAttributesTrue() {
		final BlueprintConfiguration config = new ImmutableBlueprintConfiguration().withPublicAttributes(true);
		Assert.assertEquals(true, config.isWithPublicAttributes());
	}

	@Test
	public void testWithPublicAttributesTrueFalse() {
		final BlueprintConfiguration config = new ImmutableBlueprintConfiguration().withPublicAttributes(true).withPublicAttributes(false);
		Assert.assertEquals(false, config.isWithPublicAttributes());
	}

	@Test
	public void testWithTypeAndCreationStrategy() {
		final BlueprintConfiguration config = new ImmutableBlueprintConfiguration().with(String.class,
				new SingleValueCreationStrategy<String>("Hello"));
		Assert.assertEquals("Hello", config.construct(String.class));
	}
}
