package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalNotNullArgumentException;

import org.junit.Test;

public class CheckTest_isNull {

	@Test(expected = IllegalNotNullArgumentException.class)
	public void notNull_withReference_isInvalid() {
		Check.isNull(Long.valueOf(42));
	}

	@Test(expected = IllegalNotNullArgumentException.class)
	public void notNull_withReference_withName_isInvalid() {
		Check.isNull("quality-check", "foo");
	}

	@Test
	public void notNull_withReference_withName_isValid() {
		Check.isNull(null, "foo");
	}

	@Test
	public void null_withReference_isValid() {
		Check.isNull(null);
	}

}
