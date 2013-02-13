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
