/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.qualitytest;

import net.sf.qualitytest.exception.IllegalNonFinalStaticException;

import org.junit.Assert;
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
	
	public static class ClassWithInheritedStaticVariable extends ClassWithStaticVariable {
		public double pi = 3.14;

		public ClassWithInheritedStaticVariable(int a, String c) {
			super(a, c);
		}
	}


	private class ClassWithSyntethicField {
	}
	
	@Test
	public void testClassWithNoStaticVariable() {
		StaticCheck.noNonFinalStatic(ClassWithNoStaticVariable.class);
	}
	
	@Test
	public void testClassWithNoStaticButSyntethicField() {
		StaticCheck.noNonFinalStatic(ClassWithSyntethicField.class);
	}
	
	@Test(expected=IllegalNonFinalStaticException.class)
	public void testClassWithStaticVariable() {
		StaticCheck.noNonFinalStatic(ClassWithStaticVariable.class);
	}
	
	@Test
	public void testClassWithStaticVariableFieldNameInException() {
		try {
			StaticCheck.noNonFinalStatic(ClassWithStaticVariable.class);
			Assert.assertFalse(true);
		} catch( final IllegalNonFinalStaticException e ) {
			Assert.assertEquals("The given class 'net.sf.qualitytest.StaticCheckTest_noNonStaticFinal$ClassWithStaticVariable' contains a non-final static variable 'STATIC'.", e.getMessage());
		}
	}
	
	@Test
	public void testClassWithStaticFinalVariable() {
		StaticCheck.noNonFinalStatic(ClassWithStaticFinalVariable.class);
	}
	
	@Test
	public void testClassWithInheritedStaticVariable() {
		StaticCheck.noNonFinalStatic(ClassWithInheritedStaticVariable.class);
	}
	
	@Test(expected=IllegalNonFinalStaticException.class)
	public void testClassWithInheritedStaticVariableInHierarchy() {
		StaticCheck.noNonStaticFinalInHierarchy(ClassWithInheritedStaticVariable.class);
	}

	@Test
	public void testClassWithoutInheritedStaticVariableInHierarchy() {
		StaticCheck.noNonStaticFinalInHierarchy(ClassWithNoStaticVariable.class);
	}

}
