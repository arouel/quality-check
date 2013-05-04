package net.sf.qualitycheck.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNotGreaterOrEqualThanExceptionTest {

	@Test
	public void construct_withArgs_successful() {
		new IllegalNotGreaterOrEqualThanException("a^2 + b^2 = c^2");
	}

	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalNotGreaterOrEqualThanException("a^2 + b^2 = c^2", (Throwable) null);
	}

	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalNotGreaterOrEqualThanException e = new IllegalNotGreaterOrEqualThanException("a != b", (Throwable) null);
		Assert.assertEquals("a != b", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNotGreaterOrEqualThanException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNotGreaterOrEqualThanException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNotGreaterOrEqualThanException();
	}

	@Test
	public void message_without_template() {
		final IllegalNotGreaterOrEqualThanException e = new IllegalNotGreaterOrEqualThanException();
		Assert.assertEquals("Argument must be greater or equal than a defined value.", e.getMessage());
	}

}
