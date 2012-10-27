package net.sf.qualitycheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.qualitycheck.exception.IllegalNullElementsException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_noNullElements {

	@Test
	public void noNullElements_checkManipulation() {
		final Object[] array = new Object[] {};
		Assert.assertArrayEquals(array, Check.noNullElements(array.clone(), "obj"));
	}

	@Test
	public void noNullElements_emptyArray_ok() {
		Check.noNullElements(new Object[] {});
	}

	@Test
	public void noNullElements_emptyArrayWithName_ok() {
		final Object[] array = new Object[] {};
		Assert.assertSame(array, Check.noNullElements(array, "obj"));
	}

	@Test
	public void noNullElements_emptyIterable_isValid() {
		final List<String> iterable = new ArrayList<String>();
		Assert.assertSame(iterable, Check.noNullElements(iterable));
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullAtEndArray_fail() {
		IllegalNullElementsException actual = null;
		try {
			Check.noNullElements(new Integer[] { 1, 2, 3, 4, null });
		} catch (final IllegalNullElementsException e) {
			actual = e;
			throw e;
		} finally {
			final String expected = "The passed parameter must not contain elements that are null.";
			if (actual != null) {
				Assert.assertEquals(expected, actual.getMessage());
			}
		}
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullAtEndArrayWithName_fail() {
		IllegalNullElementsException actual = null;
		try {
			Check.noNullElements(new Integer[] { 1, 2, 3, 4, null }, "obj");
		} catch (final IllegalNullElementsException e) {
			actual = e;
			throw e;
		} finally {
			final String expected = "The passed parameter 'obj' must not contain elements that are null.";
			if (actual != null) {
				Assert.assertEquals(expected, actual.getMessage());
			}
		}
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullAtIterableEnd_fail() {
		IllegalNullElementsException actual = null;
		try {
			final List<Integer> iterable = Arrays.asList(new Integer[] { 1, 2, 3, 4, null });
			Check.noNullElements(iterable);
		} catch (final IllegalNullElementsException e) {
			actual = e;
			throw e;
		} finally {
			final String expected = "The passed parameter must not contain elements that are null.";
			if (actual != null) {
				Assert.assertEquals(expected, actual.getMessage());
			}
		}
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullAtIterableEnd_withArgName_fail() {
		IllegalNullElementsException actual = null;
		try {
			final List<Integer> iterable = Arrays.asList(new Integer[] { 1, 2, 3, 4, null });
			Check.noNullElements(iterable, "myIterable");
		} catch (final IllegalNullElementsException e) {
			actual = e;
			throw e;
		} finally {
			final String expected = "The passed parameter 'myIterable' must not contain elements that are null.";
			if (actual != null) {
				Assert.assertEquals(expected, actual.getMessage());
			}
		}
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullOnlyArray_fail() {
		Check.noNullElements(new String[] { null });
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullOnlyArrayWithName_fail() {
		Check.noNullElements(new String[] { null }, "obj");
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullOnlyIterable_fail() {
		final List<Integer> iterable = Arrays.asList(new Integer[] { null, null, null });
		Assert.assertSame(iterable, Check.noNullElements(iterable));
	}

	@Test(expected = IllegalNullElementsException.class)
	public void noNullElements_nullOnlyIterable_withArgName_fail() {
		final List<Integer> iterable = Arrays.asList(new Integer[] { null, null, null });
		Assert.assertSame(iterable, Check.noNullElements(iterable, "myIterable"));
	}

	@Test
	public void noNullElements_stringArray_ok() {
		Check.noNullElements(new String[] { "Hello", "World" });
	}

	@Test
	public void noNullElements_stringArrayWithName_ok() {
		Check.noNullElements(new String[] { "Hello", "World" }, "obj");
	}

}
