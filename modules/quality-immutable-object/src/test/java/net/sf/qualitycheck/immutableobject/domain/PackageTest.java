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
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class PackageTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		new Package("");
	}

	@Test
	public void construct_name_isNotEmpty() {
		new Package("com.github.before");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		new Package(null);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_nameOnlyWithDot_isEmpty() {
		new Package(".");
	}

	@Test
	public void equals_identical() {
		final Package a = new Package("test.pkg");
		final Package b = new Package("test.pkg");
		assertEquals(a, b);
		assertTrue(a.hashCode() == b.hashCode());
	}

	@Test
	public void equals_null() {
		final Package a = new Package("test.pkg");
		assertFalse(a.equals(null));
	}

	@Test
	public void equals_otherClass() {
		final Package a = new Package("test.pkg");
		assertFalse(a.equals(""));
	}

	@Test
	public void equals_same() {
		final Package a = new Package("test.pkg");
		assertEquals(a, a);
		assertSame(a, a);
		assertTrue(a.hashCode() == a.hashCode());
	}

	@Test
	public void isUndefined() {
		assertTrue(Package.UNDEFINED.isUndefined());
		assertFalse(Package.JAVA_LANG.isUndefined());
		assertFalse(new Package("net.sf.qualitycheck").isUndefined());
	}

}
