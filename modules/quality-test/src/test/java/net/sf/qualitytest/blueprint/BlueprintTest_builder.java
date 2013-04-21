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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import net.sf.qualitycheck.Check;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_builder {
	public interface Car extends Serializable {

		@Nonnull
		List<String> getCodes();

		@Nonnull
		String getName();

		@Nonnegative
		int getWheels();

	}

	@Immutable
	public static final class ImmutableCar implements Car, Serializable {

		@NotThreadSafe
		public static final class Builder implements Car {

			private static final long serialVersionUID = 1L;

			@Nonnull
			private List<String> codes;

			@Nonnegative
			private int wheels;

			@Nonnull
			private String name;

			public Builder() {
				// default constructor
			}

			public Builder(@Nonnull final Car car) {
				Check.notNull(car, "car");
				codes = new ArrayList<String>(Check.notNull(car.getCodes(), "car.getCodes()"));
				wheels = Check.notNegative(car.getWheels(), "car.getWheels()");
				name = Check.notNull(car.getName(), "car.getName()");
			}

			@Nonnull
			public ImmutableCar build() {
				return new ImmutableCar(codes, wheels, name);
			}

			@Override
			@Nonnull
			public List<String> getCodes() {
				return codes;
			}

			@Override
			@Nonnull
			public String getName() {
				return name;
			}

			@Override
			@Nonnegative
			public int getWheels() {
				return wheels;
			}

			@Nonnull
			public Builder setCodes(@Nonnull final List<String> codes) {
				this.codes = new ArrayList<String>(Check.notNull(codes, "codes"));
				return this;
			}

			@Nonnull
			public Builder setName(@Nonnull final String name) {
				this.name = Check.notNull(name, "name");
				return this;
			}

			@Nonnull
			public Builder setWheels(@Nonnegative final int wheels) {
				this.wheels = Check.notNegative(wheels, "wheels");
				return this;
			}

		}

		@NotThreadSafe
		public static final class FlatMutatorBuilder implements Car {

			private static final long serialVersionUID = 1L;

			@Nonnull
			private List<String> codes;

			@Nonnegative
			private int wheels;

			@Nonnull
			private String name;

			public FlatMutatorBuilder() {
				// default constructor
			}

			public FlatMutatorBuilder(@Nonnull final Car car) {
				Check.notNull(car, "car");
				codes = new ArrayList<String>(Check.notNull(car.getCodes(), "car.getCodes()"));
				wheels = Check.notNegative(car.getWheels(), "car.getWheels()");
				name = Check.notNull(car.getName(), "car.getName()");
			}

			@Nonnull
			public ImmutableCar build() {
				return new ImmutableCar(codes, wheels, name);
			}

			@Nonnull
			public FlatMutatorBuilder codes(@Nonnull final List<String> codes) {
				this.codes = new ArrayList<String>(Check.notNull(codes, "codes"));
				return this;
			}

			@Override
			@Nonnull
			public List<String> getCodes() {
				return codes;
			}

			@Override
			@Nonnull
			public String getName() {
				return name;
			}

			@Override
			@Nonnegative
			public int getWheels() {
				return wheels;
			}

			@Nonnull
			public FlatMutatorBuilder name(@Nonnull final String name) {
				this.name = Check.notNull(name, "name");
				return this;
			}

			@Nonnull
			public FlatMutatorBuilder wheels(@Nonnegative final int wheels) {
				this.wheels = Check.notNegative(wheels, "wheels");
				return this;
			}

		}

		private static final long serialVersionUID = 1L;

		@Nonnull
		public static ImmutableCar copyOf(@Nonnull final Car car) {
			Check.notNull(car, "car");
			return new ImmutableCar(car.getCodes(), car.getWheels(), car.getName());
		}

		@Nonnull
		public static ImmutableCar copyOnlyIfNecessary(@Nonnull final Car car) {
			Check.notNull(car, "car");
			return car instanceof ImmutableCar ? (ImmutableCar) car : copyOf(car);
		}

		@Nonnull
		private final List<String> codes;

		@Nonnegative
		private final int wheels;

		@Nonnull
		private final String name;

		public ImmutableCar(@Nonnull final List<String> codes, @Nonnegative final int wheels, @Nonnull final String name) {
			this.codes = Collections.unmodifiableList(new ArrayList<String>(Check.notNull(codes, "codes")));
			this.wheels = Check.notNegative(wheels, "wheels");
			this.name = Check.notNull(name, "name");
		}

		@Override
		public boolean equals(final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (getClass() != obj.getClass()) {
				return false;
			}
			final ImmutableCar other = (ImmutableCar) obj;
			if (!codes.equals(other.codes)) {
				return false;
			}
			if (wheels != other.wheels) {
				return false;
			}
			if (!name.equals(other.name)) {
				return false;
			}
			return true;
		}

		@Override
		@Nonnull
		public List<String> getCodes() {
			return codes;
		}

		@Override
		@Nonnull
		public String getName() {
			return name;
		}

		@Override
		@Nonnegative
		public int getWheels() {
			return wheels;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + codes.hashCode();
			result = prime * result + wheels;
			result = prime * result + name.hashCode();
			return result;
		}
	}

	@Test
	public void testBlueprintFlatMutatorBuilder() {
		final ImmutableCar.Builder builder = Blueprint.def().construct(ImmutableCar.Builder.class);
		final Car car = builder.build();

		Assert.assertEquals(0, car.getWheels());
		Assert.assertEquals("", car.getName());
		Assert.assertTrue(car.getCodes().isEmpty());
	}

	@Test
	public void testBlueprintSetterBuilder() {
		final ImmutableCar.FlatMutatorBuilder builder = Blueprint.def().with(Match.BUILDER_METHODS)
				.construct(ImmutableCar.FlatMutatorBuilder.class);
		final Car car = builder.build();

		Assert.assertEquals(0, car.getWheels());
		Assert.assertEquals("", car.getName());
		Assert.assertTrue(car.getCodes().isEmpty());
	}
}
