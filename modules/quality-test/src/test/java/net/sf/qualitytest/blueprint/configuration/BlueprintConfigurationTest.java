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
import net.sf.qualitytest.blueprint.BlueprintTest;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintConfigurationTest {

	public static final class User {
		private int age;
		private String name;
		private String email;
		private long id;

		public int getAge() {
			return age;
		}

		public String getEmail() {
			return email;
		}

		public long getId() {
			return id;
		}

		public String getName() {
			return name;
		}

		public void setAge(final int age) {
			this.age = age;
		}

		public void setEmail(final String email) {
			this.email = email;
		}

		public void setId(final long id) {
			this.id = id;
		}

		public void setName(final String name) {
			this.name = name;
		}

	}

	@Test
	public void testBlueprintWith() {
		final User user = Blueprint.random()

		.with("email", "mail@example.com")

		.with("age", 18)

		.construct(User.class);

		Assert.assertEquals(18, user.getAge());
		Assert.assertEquals("mail@example.com", user.getEmail());
		Assert.assertTrue(BlueprintTest.UUID_PATTERN.matcher(user.getName()).matches());
	}

}
