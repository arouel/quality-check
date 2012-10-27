package net.sf.qualitycheck;

import net.sf.qualitycheck.exception.IllegalPositionIndexException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_positionIndex {

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionAllZero() {
		Check.positionIndex(0, 0);
	}

	@Test
	public void checkPositionIndex_ok_highest() {
		final int ret = Check.positionIndex(2, 3);
		Assert.assertEquals(2, ret);
	}

	@Test
	public void checkPositionIndex_ok_lowest() {
		final int ret = Check.positionIndex(0, 3);
		Assert.assertEquals(0, ret);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionIndexEqualsSize() {
		Check.positionIndex(3, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionIndexNegative() {
		Check.positionIndex(-1, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionIndexToBig() {
		Check.positionIndex(4, 3);
	}

	@Test(expected = IllegalPositionIndexException.class)
	public void checkPositionSizeNegative() {
		Check.positionIndex(0, -1);
	}

}
