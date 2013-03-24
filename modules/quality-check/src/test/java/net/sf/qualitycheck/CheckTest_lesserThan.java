package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNotLesserThanException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_lesserThan {

	@Test
	public void testLesserThan_Comparable() {
		final String check = "check";
		final String expected = "quality";

		Assert.assertEquals("check", Check.lesserThan(expected, check));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testLesserThanFailure_Comparable() {
		final Long check = Long.valueOf(322l);
		final Long expected = Long.valueOf(42l);
		Check.lesserThan(expected, check);
	}

	@Test
	public void testLesserThanMessage_Comparable() {
		final String check = "check";
		final String expected = "quality";

		Assert.assertEquals("check", Check.lesserThan(expected, check, "Must be lesser than 'quality'."));
	}

	@Test(expected = IllegalNotLesserThanException.class)
	public void testLesserThanMessageFailure_Comparable() {
		final Long check = Long.valueOf(100l);
		final Long expected = Long.valueOf(42l);

		Check.lesserThan(expected, check, "Must be greater than '42'.");
	}
}
