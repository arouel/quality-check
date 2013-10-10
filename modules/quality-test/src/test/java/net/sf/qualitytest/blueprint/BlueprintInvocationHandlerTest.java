package net.sf.qualitytest.blueprint;

import net.sf.qualitytest.blueprint.strategy.creation.IncrementValueCreationStrategy;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintInvocationHandlerTest {

	public interface MyInterface {

		int getNumber();
	}

	@Test
	public void testInvocationHandlerReturnsCachedValue() {
		final MyInterface mi = Blueprint.random().construct(MyInterface.class);
		final int a = mi.getNumber();
		final int b = mi.getNumber();

		Assert.assertEquals(a, b);
	}

	@Test
	public void testInvocationHandlerReturnsCachedValueWithStrategy() {
		final MyInterface mi = Blueprint.random().with(int.class, new IncrementValueCreationStrategy<Number>(0))
				.construct(MyInterface.class);
		final int a = mi.getNumber();
		final int b = mi.getNumber();

		Assert.assertEquals(1, a);
		Assert.assertEquals(2, b);
	}
}
