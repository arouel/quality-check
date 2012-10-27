package net.sf.qualitycheck;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import net.sf.qualitycheck.exception.IllegalInstanceOfArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_instanceOf {

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_obj_isNull() {
		Check.instanceOf(Integer.class, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_type_isNull() {
		Check.instanceOf(null, "");
	}

	@Test
	public void instanceOf_type_isValid() {
		final Long id = 12376L;
		Check.instanceOf(Long.class, id);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_withArgName_obj_isNull() {
		Check.instanceOf(Collection.class, null, "argName");
	}

	@Test
	public void instanceOf_withArgName_subtype_isValid() {
		final List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 4, null });
		Check.instanceOf(Collection.class, list, "list");
	}

	@Test(expected = IllegalInstanceOfArgumentException.class)
	public void instanceOf_withArgName_type_isInvalid() {
		IllegalInstanceOfArgumentException actual = null;
		try {
			final List<Integer> list = new ArrayList<Integer>();
			Check.instanceOf(Vector.class, list, "list");
		} catch (final IllegalInstanceOfArgumentException e) {
			actual = e;
			throw e;
		} finally {
			final String expected = "The passed argument 'list' is a member of an unexpected type (expected type: java.util.Vector, actual: java.util.ArrayList).";
			if (actual != null) {
				Assert.assertEquals(expected, actual.getMessage());
			}
		}
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_withArgName_type_isNull() {
		Check.instanceOf(null, "", "argName");
	}

	@Test
	public void instanceOf_withArgName_type_isValid() {
		final Long id = 12376L;
		Check.instanceOf(Long.class, id, "id");
	}

}
