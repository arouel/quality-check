/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
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
		final Object id = 12376L;
		final Long result = Check.instanceOf(Long.class, id);
		Assert.assertEquals(id, result);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void instanceOf_withArgName_obj_isNull() {
		Check.instanceOf(Collection.class, null, "argName");
	}

	@Test
	public void instanceOf_withArgName_subtype_isValid() {
		final List<Integer> list = Arrays.asList(new Integer[] { 1, 2, 3, 4, null });
		final Collection<Integer> result = Check.instanceOf(Collection.class, list, "list");
		Assert.assertEquals(list, result);
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
