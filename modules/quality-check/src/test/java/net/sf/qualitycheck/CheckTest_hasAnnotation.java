/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
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
