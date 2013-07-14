package net.sf.qualitycheck.immutableobject;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalNegativeArgumentException;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import net.sf.qualitycheck.immutableobject.Car;

public final class ImmutableCarTest {

	private static final class Blueprint {

		private List<String> codes = null; // TODO you must setup a default value for all test cases

		private int wheels = 0; // TODO you must setup a default value for all test cases

		private String name = "test"; // TODO you must setup a default value for all test cases

		public Blueprint() {
			// default constructor
		}

		public Blueprint codes(final List<String> codes) {
			this.codes = codes;
			return this;
		}

		public Blueprint wheels(final int wheels) {
			this.wheels = wheels;
			return this;
		}

		public Blueprint name(final String name) {
			this.name = name;
			return this;
		}

		@Nonnull
		public ImmutableCar build() {
			return new ImmutableCar(codes, wheels, name);
		}

	}

	@Test(expected = IllegalNullArgumentException.class)
	public void copyOf_argIsNull() {
		ImmutableCar.copyOf(null);
	}

	@Test
	public void copyOf_successful() {
		assertNotNull(ImmutableCar.copyOf(new Blueprint().build()));
	}

	@Test
	public void equals_different_CODES() {
		// TODO test equality and hash-code if field 'codes' is different
		ImmutableCar a = new Blueprint().codes(null).build();
		ImmutableCar b = new Blueprint().codes(null).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_WHEELS() {
		// TODO test equality and hash-code if field 'wheels' is different
		ImmutableCar a = new Blueprint().wheels(0).build();
		ImmutableCar b = new Blueprint().wheels(0).build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_different_NAME() {
		// TODO test equality and hash-code if field 'name' is different
		ImmutableCar a = new Blueprint().name("test").build();
		ImmutableCar b = new Blueprint().name("test").build();
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		ImmutableCar a = new Blueprint().build();
		ImmutableCar b = new Blueprint().build();
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		ImmutableCar a = new Blueprint().build();
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		ImmutableCar a = new Blueprint().build();
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		ImmutableCar a = new Blueprint().build();
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_CODES() {
		new Blueprint().codes(null).build();
	}

	@Test(expected = IllegalNegativeArgumentException.class)
	public void precondition_WHEELS() {
		new Blueprint().wheels(-1).build();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void precondition_NAME() {
		new Blueprint().name(null).build();
	}

	@Test
	public void blueprintEqualsBuilder() {
		final ImmutableCar blueprint = new Blueprint().build();
		final ImmutableCar.Builder builder = new ImmutableCar.Builder();

		builder.codes(null);

		builder.wheels(0);

		builder.name("test");

		final ImmutableCar obj = builder.build();
		assertEquals(blueprint, obj);

	}

}
