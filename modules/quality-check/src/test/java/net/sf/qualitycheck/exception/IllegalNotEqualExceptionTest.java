package net.sf.qualitycheck.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNotEqualExceptionTest {

	@Test
	public void construct_withArgs_successful() {
		new IllegalNotEqualException("a^2 + b^2 = c^2");
	}

	@Test
	public void message_without_template() {
		final IllegalNotEqualException e = new IllegalNotEqualException();
		Assert.assertEquals("Argument must be equal to a defined value.", e.getMessage());
	}

	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalNotEqualException("a^2 + b^2 = c^2", (Throwable) null);
	}

	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalNotEqualException e = new IllegalNotEqualException("a != b", (Throwable) null);
		Assert.assertEquals("a != b", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNotEqualException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNotEqualException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNotEqualException();
	}

	
}
