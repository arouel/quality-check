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

		.object(User.class);

		Assert.assertEquals(18, user.getAge());
		Assert.assertEquals("mail@example.com", user.getEmail());
		Assert.assertTrue(BlueprintTest.UUID_PATTERN.matcher(user.getName()).matches());
	}

	@Test
	public void testWithIsImmutable() {
		final BlueprintConfiguration config = new DefaultBlueprintConfiguration();
		final int initialSize = config.getAttributeMappings().size();
		final BlueprintConfiguration newConfig = config.with("a", null);
		Assert.assertEquals(initialSize, config.getAttributeMappings().size());
		Assert.assertEquals(initialSize + 1, newConfig.getAttributeMappings().size());
	}
}
