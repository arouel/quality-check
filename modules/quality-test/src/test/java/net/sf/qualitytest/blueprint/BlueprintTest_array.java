package net.sf.qualitytest.blueprint;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_array {

	@Test
	public void testArray() {
		final int[] exampleArray = { 1, 2, 3 };
		final int[] array = Blueprint.def().with(int[].class, exampleArray).object(int[].class);
		Assert.assertArrayEquals(exampleArray, array);
	}

	@Test
	public void testArrayDefault() {
		final int[] array = Blueprint.def().object(int[].class);
		Assert.assertEquals(7, array.length);
	}
}
