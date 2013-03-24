package net.sf.qualitycheck.exception;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNotLesserThanExceptionTest {

	@Test
	public void construct_withArgs_successful() {
		new IllegalNotLesserThanException("a^2 + b^2 = c^2");
	}

	@Test
	public void construct_withArgsAndNullCause() {
		new IllegalNotLesserThanException("a^2 + b^2 = c^2", (Throwable) null);
	}

	@Test
	public void construct_withFilledArgsAndNullCause() {
		final IllegalNotLesserThanException e = new IllegalNotLesserThanException("a != b", (Throwable) null);
		Assert.assertEquals("a != b", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNotLesserThanException(new NumberFormatException());
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNotLesserThanException((Throwable) null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNotLesserThanException();
	}

	@Test
	public void message_without_template() {
		final IllegalNotLesserThanException e = new IllegalNotLesserThanException();
		Assert.assertEquals("Argument must be lesser than a defined value.", e.getMessage());
	}

}
