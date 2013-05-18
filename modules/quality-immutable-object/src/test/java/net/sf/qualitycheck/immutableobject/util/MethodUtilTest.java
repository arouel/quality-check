/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.qualitycheck.immutableobject.util;

import static org.junit.Assert.assertEquals;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;

import org.junit.Test;

public class MethodUtilTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void determineAccessorName_isEmpty() {
		MethodUtil.determineAccessorName(AccessorPrefix.GET, "");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void determineAccessorName_isNull() {
		MethodUtil.determineAccessorName(AccessorPrefix.GET, null);
	}

	@Test
	public void determineAccessorName_successful() {
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "_field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-??field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-08978field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "_field_"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__field__"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-field-__"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-??field??-__"));
		assertEquals("getField123", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-08978field123-___"));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineAccessorName_wrongFieldName() {
		MethodUtil.determineAccessorName(AccessorPrefix.GET, " ");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void determineMutatorName_isEmpty() {
		MethodUtil.determineMutatorName("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void determineMutatorName_isNull() {
		MethodUtil.determineMutatorName(null);
	}

	@Test
	public void determineMutatorName_successful() {
		assertEquals("setField", MethodUtil.determineMutatorName("field"));
		assertEquals("setField", MethodUtil.determineMutatorName("_field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-??field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-08978field"));
		assertEquals("setField", MethodUtil.determineMutatorName("_field_"));
		assertEquals("setField", MethodUtil.determineMutatorName("__field__"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-field-__"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-??field??-__"));
		assertEquals("setField123", MethodUtil.determineMutatorName("__-08978field123-___"));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineMutatorName_wrongFieldName() {
		MethodUtil.determineMutatorName(" ");
	}

}
