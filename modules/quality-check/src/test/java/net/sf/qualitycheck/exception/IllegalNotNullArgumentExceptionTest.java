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
package net.sf.qualitycheck.exception;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

public class IllegalNotNullArgumentExceptionTest {

	@Test
	public void construct_withArgName_successful() {
		new IllegalNotNullArgumentException("argName", Collections.emptySet());
	}

	@Test
	public void construct_withEmptyArgName_successful() {
		new IllegalNotNullArgumentException("", Collections.emptySet());
	}

	@Test
	public void construct_withEmptyArgNameAndNullCause() {
		new IllegalNotNullArgumentException("", Collections.emptySet(), null);
	}

	@Test
	public void construct_withFilledArgNameAndFilledCause() {
		new IllegalNotNullArgumentException("argName", Collections.emptySet(), new NumberFormatException());
	}

	@Test
	public void construct_withFilledArgNameAndNullCause() {
		final IllegalNotNullArgumentException e = new IllegalNotNullArgumentException("argName", Collections.emptySet(), null);
		Assert.assertEquals("Argument 'argName' must be null.", e.getMessage());
	}

	@Test
	public void construct_withFilledCause() {
		new IllegalNotNullArgumentException(Collections.emptySet(), new NumberFormatException());
	}

	@Test
	public void construct_withNullArgName() {
		new IllegalNotNullArgumentException((String) null, Collections.emptySet());
	}

	@Test
	public void construct_withNullArgNameAndNullCause() {
		new IllegalNotNullArgumentException((String) null, Collections.emptySet(), null);
	}

	@Test
	public void construct_withNullCause() {
		new IllegalNotNullArgumentException(Collections.emptySet(), (Throwable) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_withNullValue() {
		new IllegalNotNullArgumentException(null);
	}

	@Test
	public void construct_withoutArgs_successful() {
		new IllegalNotNullArgumentException(Collections.emptySet());
	}

	@Test
	public void testGetIllegalArgument() {
		final ArrayList<Integer> lst = new ArrayList<Integer>();
		lst.add(Integer.valueOf(42));

		final IllegalArgumentHolder<Object> iah = new IllegalNotNullArgumentException(lst);
		Assert.assertEquals(lst, iah.getIllegalArgument());
	}
}
