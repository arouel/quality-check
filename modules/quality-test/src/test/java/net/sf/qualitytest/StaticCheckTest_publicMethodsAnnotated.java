package net.sf.qualitytest;

import net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodException;

import org.junit.Test;

public class StaticCheckTest_publicMethodsAnnotated {

	@Test
	public void testAllMethodsAreAnnotated() {
		StaticCheck.publicMethodsAnnotated(StaticCheckTest_publicMethodsAnnotated.class, Test.class);
	}
	
	@Test(expected=IllegalMissingAnnotationOnMethodException.class)
	public void testAllMethodsAreAnnotated_error() {
		StaticCheck.publicMethodsAnnotated(StaticCheck.class, Test.class);
	}
}
