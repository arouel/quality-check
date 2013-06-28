package net.sf.qualitytest.blueprint;

import net.sf.qualitytest.blueprint.strategy.cycle.ExceptionCycleHandlingStrategy;
import net.sf.qualitytest.blueprint.strategy.cycle.NullCycleHandlingStrategy;
import net.sf.qualitytest.exception.BlueprintCycleException;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_selfReference {

	public class Address {
		private String street;
		private Customer customer;

		public Customer getCustomer() {
			return customer;
		}

		public String getStreet() {
			return street;
		}

		public void setCustomer(final Customer customer) {
			this.customer = customer;
		}

		public void setStreet(final String street) {
			this.street = street;
		}

	}

	public class Customer {
		private String name;
		private Address address;

		public Address getAddress() {
			return address;
		}

		public String getName() {
			return name;
		}

		public void setAddress(final Address address) {
			this.address = address;
		}

		public void setName(final String name) {
			this.name = name;
		}

	}

	@Test(expected = BlueprintCycleException.class)
	public void testNoSelfReference() {
		Blueprint.def().construct(Customer.class);
	}

	@Test
	public void testSelfReference() {
		final Customer c = Blueprint.def().with(new NullCycleHandlingStrategy<Customer>(Customer.class)).construct(Customer.class);
		Assert.assertNull(c.getAddress().getCustomer());
	}

	@Test
	public void testSelfReferenceWithIgnoredStrategy() {
		final Customer c = Blueprint.def().with(new NullCycleHandlingStrategy<Customer>(Customer.class))
				.with(new NullCycleHandlingStrategy<String>(String.class)).construct(Customer.class);
		Assert.assertNull(c.getAddress().getCustomer());
	}

	@Test(expected = BlueprintCycleException.class)
	public void testStrategyOrder() {
		Blueprint.def().with(new NullCycleHandlingStrategy<Customer>(Customer.class)).with(new ExceptionCycleHandlingStrategy())
				.construct(Customer.class);

	}
}
