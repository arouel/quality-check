package net.sf.qualitytest;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodException;

import org.junit.Test;

public class StaticCheckTest_publicMethodsAnnotated {

	@Test
	public void testAllMethodsAreAnnotated() {
		StaticCheck.publicMethodsAnnotated(Documented.class, Retention.class);
		StaticCheck.publicMethodsAnnotated(Documented.class, Target.class);
		StaticCheck.publicMethodsAnnotated(Override.class, Retention.class);
		StaticCheck.publicMethodsAnnotated(Override.class, Target.class);
	}

	@Test(expected = IllegalMissingAnnotationOnMethodException.class)
	public void testAllMethodsAreAnnotated_error() {
		StaticCheck.publicMethodsAnnotated(Retention.class, Retention.class);
	}

}
