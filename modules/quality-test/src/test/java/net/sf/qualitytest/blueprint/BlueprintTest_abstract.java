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

import java.lang.reflect.Method;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.strategy.matching.AbstractTypeMatchingStrategy;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_abstract {

	public static class AbstractCreationStrategy implements CreationStrategy<Object> {

		@SuppressWarnings("unchecked")
		public static <A> A createDefaultImplementation(final Class<A> abstractClass, final BlueprintConfiguration config,
				final BlueprintSession session) {
			final Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(abstractClass);
			enhancer.setCallback(new MethodInterceptor() {

				@Override
				public Object intercept(final Object proxy, final Method method, final Object[] args, final MethodProxy methodProxy)
						throws Throwable {
					return Blueprint.construct(method.getReturnType(), config, session);
				}
			});

			return (A) enhancer.create();
		}

		@Override
		public Object createValue(final Class<?> expectedClazz, final BlueprintConfiguration config, final BlueprintSession session) {
			Check.notNull(expectedClazz, "expectedClazz");
			Check.notNull(config, "config");
			Check.notNull(session, "session");

			return createDefaultImplementation(expectedClazz, config, session);
		}

	}

	public static abstract class MyAbstract {
		private String a;

		public String getA() {
			return a;
		}

		public abstract String getString();

		public void setA(final String a) {
			this.a = a;
		}
	}

	@Test
	public void testBlueprintAbstract() {
		final MyAbstract obj = Blueprint.random().with(new AbstractTypeMatchingStrategy(), new AbstractCreationStrategy())
				.construct(MyAbstract.class);
		Assert.assertTrue(BlueprintTest.UUID_PATTERN.matcher(obj.getString()).matches());
		Assert.assertTrue(BlueprintTest.UUID_PATTERN.matcher(obj.getA()).matches());

	}
}
