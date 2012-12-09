/*******************************************************************************
 * Copyright 2012 André Rouél
 * Copyright 2012 Dominik Seichter
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

import org.junit.Test;

public class StaticCheckTest_isImmutable {
	
	public static final class ImmutableClass {
		private final int a;
		
		public ImmutableClass(int a) {
			this.a = a;
		}
		
		public int getA() {
			return a;
		}
	}

	public static final class ImmutableClassWithString {
		private final String a;
		
		public ImmutableClassWithString(final String a) {
			this.a = a;
		}
		
		public String getA() {
			return a;
		}
	}

	public static final class MutableClass {
		private int a;
		
		public MutableClass(int a) {
			this.a = a;
		}
		
		public int getA() {
			return a;
		}
		
		public void setA(int a) {
			this.a = a;
		}
	}
	
	@Test
	public void testDetectImmutableClass() {
		StaticCheck.isImmutable(ImmutableClass.class);
	}
	
	@Test
	public void testDetectImmutableWithStringClass() {
		StaticCheck.isImmutable(ImmutableClassWithString.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDetectNonFinalClass() {
		StaticCheck.isImmutable(MutableClass.class);
	}
	
}
