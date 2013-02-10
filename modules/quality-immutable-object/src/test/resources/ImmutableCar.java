package net.sf.qualitycheck.immutableobject;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import java.io.Serializable;
import java.lang.Override;
import java.util.List;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.Car;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

@Immutable
public final class ImmutableCar implements Car {

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
			this.codes = car.getCodes();
			this.wheels = car.getWheels();
			this.name = car.getName();
		}

		@Nonnull
		public List<String> getCodes() {
			return codes;
		}

		@Nonnegative
		public int getWheels() {
			return wheels;
		}

		@Nonnull
		public String getName() {
			return name;
		}

		@Nonnull
		public Builder setCodes(@Nonnull final List<String> codes) {
			this.codes = new ArrayList<String>(Check.notNull(codes, "codes"));
			return this;
		}

		@Nonnull
		public Builder setWheels(@Nonnegative final int wheels) {
			this.wheels = Check.notNegative(wheels, "wheels");
			return this;
		}

		@Nonnull
		public Builder setName(@Nonnull final String name) {
			this.name = Check.notNull(name, "name");
			return this;
		}

		@Nonnull
		public ImmutableCar build() {
			return new ImmutableCar(codes, wheels, name);
		}

	}

	private static final long serialVersionUID = 1L;

	@Nonnull
	public static ImmutableCar copyOf(@Nonnull final Car car) {
		Check.notNull(car, "car");
		return new ImmutableCar(car.getCodes(), car.getWheels(), car.getName());
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ImmutableCar other = (ImmutableCar) obj;
		if (!codes.equals(other.codes))
			return false;
		if (wheels != other.wheels)
			return false;
		if (!name.equals(other.name))
			return false;
		return true;
	}

	@Nonnull
	public List<String> getCodes() {
		return codes;
	}

	@Nonnegative
	public int getWheels() {
		return wheels;
	}

	@Nonnull
	public String getName() {
		return name;
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
