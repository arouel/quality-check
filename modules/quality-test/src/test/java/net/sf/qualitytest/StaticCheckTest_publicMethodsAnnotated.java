package net.sf.qualitytest;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodException;

import org.junit.Test;

public class StaticCheckTest_publicMethodsAnnotated {

	@Test(expected = IllegalNullArgumentException.class)
	public void publicMethodsAnnotated_annotation_isNull() {
		StaticCheck.publicMethodsAnnotated(Retention.class, null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void publicMethodsAnnotated_clazz_isNull() {
		StaticCheck.publicMethodsAnnotated(null, Retention.class);
	}

	@Test(expected = IllegalMissingAnnotationOnMethodException.class)
	public void publicMethodsAnnotated_searchAnnotationNotPresent() {
		StaticCheck.publicMethodsAnnotated(Retention.class, Retention.class);
	}

	@Test
	public void publicMethodsAnnotated_successfullyChecked() {
		StaticCheck.publicMethodsAnnotated(Documented.class, Retention.class);
		StaticCheck.publicMethodsAnnotated(Documented.class, Target.class);
		StaticCheck.publicMethodsAnnotated(Override.class, Retention.class);
		StaticCheck.publicMethodsAnnotated(Override.class, Target.class);
	}

}
