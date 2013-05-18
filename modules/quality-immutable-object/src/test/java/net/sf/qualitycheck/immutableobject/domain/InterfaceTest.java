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
package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.Serializable;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

public class InterfaceTest {

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_withoutInterface() {
		Interface.of(String.class);
	}

	@Test
	public void equals_different() {
		final Interface a = Interface.of(Serializable.class);
		final Interface b = Interface.of(Comparable.class);
		assertFalse(a.equals(b));
		assertFalse(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_identical() {
		final Interface a = Interface.of(Serializable.class);
		final Interface b = Interface.of(Serializable.class);
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Interface a = Interface.of(Serializable.class);
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Interface a = Interface.of(Serializable.class);
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Interface a = Interface.of(Serializable.class);
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void toString_notEmpty() {
		assertEquals(Serializable.class.getName(), Interface.of(Serializable.class).toString());
	}

}
