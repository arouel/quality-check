package net.sf.qualitycheck;

import java.lang.annotation.Annotation;

import javax.annotation.Generated;
import javax.annotation.Resource;

import net.sf.qualitycheck.exception.IllegalMissingAnnotationException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_hasAnnotation {

	@Resource
	private static class FakeAnnotatedClass {
	}

	@Generated(value = { "2001-07-04T12:08:56.235-0700" })
	private static class FakeSourceAnnotatedClass {
	}

	@Test(expected = IllegalMissingAnnotationException.class)
	public void hasAnnotation_annotation_fail() {
		Check.hasAnnotation(CheckTest.class, ArgumentsChecked.class);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void hasAnnotation_annotation_isNull() {
		Check.hasAnnotation(CheckTest.class, null);
	}

	@Test
	public void hasAnnotation_annotation_ok() {
		final Annotation annotation = Check.hasAnnotation(FakeAnnotatedClass.class, Resource.class);
		Assert.assertTrue(annotation instanceof Resource);
	}

	@Test(expected = IllegalMissingAnnotationException.class)
	public void hasAnnotation_annotationRetentionSource_fail() {
		Check.hasAnnotation(FakeSourceAnnotatedClass.class, Generated.class);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void hasAnnotation_class_isNull() {
		Check.hasAnnotation(null, ArgumentsChecked.class);
	}

}
