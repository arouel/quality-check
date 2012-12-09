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

public class StaticCheckTest_noNonStaticFinal {

	public static class ClassWithNoStaticVariable {
		private int a;
		private String c;
		
		public ClassWithNoStaticVariable(int a, String c) {
			this.a = a;
			this.c = c;
		}
		
		public int getA() {
			return a;
		}
		
		public String getC() {
			return c;
		}
	}
	
	public static class ClassWithStaticVariable extends ClassWithNoStaticVariable {
		public static int STATIC = 4; 

		public ClassWithStaticVariable(int a, String c) {
			super(a, c);
		}
	}
	
	public static class ClassWithStaticFinalVariable extends ClassWithNoStaticVariable {
		public static final int STATIC = 4; 

		public ClassWithStaticFinalVariable(int a, String c) {
			super(a, c);
		}
	}
	
	public static class ClassWithInheritedStaticVariable extends ClassWithNoStaticVariable {
		public double pi = 3.14;

		public ClassWithInheritedStaticVariable(int a, String c) {
			super(a, c);
		}
	}
	
	@Test
	public void testClassWithNoStaticVariable() {
		StaticCheck.noNonStaticFinal(ClassWithNoStaticVariable.class);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testClassWithStaticVariable() {
		StaticCheck.noNonStaticFinal(ClassWithStaticVariable.class);
	}
	
	@Test
	public void testClassWithStaticFinalVariable() {
		StaticCheck.noNonStaticFinal(ClassWithStaticFinalVariable.class);
	}
	
	@Test
	public void testClassWithInheritedStaticVariable() {
		StaticCheck.noNonStaticFinal(ClassWithInheritedStaticVariable.class);
	}
}
