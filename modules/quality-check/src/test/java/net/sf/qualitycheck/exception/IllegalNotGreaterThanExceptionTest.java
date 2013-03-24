package net.sf.qualitycheck.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNotGreaterThanExceptionTest {

	@Test
	public void construct_withArgs_successful() {
		new IllegalNotGreaterThanException("a^2 + b^2 = c^2");
	}

	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalNotGreaterThanException("a^2 + b^2 = c^2", (Throwable) null);
	}

	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalNotGreaterThanException e = new IllegalNotGreaterThanException("a != b", (Throwable) null);
		Assert.assertEquals("a != b", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNotGreaterThanException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNotGreaterThanException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNotGreaterThanException();
	}

	@Test
	public void message_without_template() {
		final IllegalNotGreaterThanException e = new IllegalNotGreaterThanException();
		Assert.assertEquals("Argument must be greater than a defined value.", e.getMessage());
	}

}
