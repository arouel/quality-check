/*******************************************************************************
 * Copyright 2013 Dominik Seichter
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

import java.util.EnumSet;

import net.sf.qualitycheck.exception.IllegalArgumentNotContainedException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_contains {

	private enum Letter {
		A, B, C, D
	};

	private final EnumSet<Letter> set = EnumSet.of(Letter.A, Letter.D);

	@Test
	public void contains_checkReferenceIsSame() {
		Assert.assertSame(Letter.A, Check.contains(set, Letter.A));
		Assert.assertSame(Letter.D, Check.contains(set, Letter.D, "name"));
	}

	@Test(expected = IllegalArgumentNotContainedException.class)
	public void contains_withReference_isInvalid() {
		Check.contains(set, Letter.B);
	}

	@Test
	public void contains_withReference_isValid() {
		Check.contains(set, Letter.A);
	}

	@Test(expected = IllegalArgumentNotContainedException.class)
	public void contains_withReference_withName_isInvalid() {
		Check.contains(set, Letter.C, "foo");
	}

	@Test
	public void contains_withReference_withName_isValid() {
		Check.contains(set, Letter.A, "foo");
	}
}
