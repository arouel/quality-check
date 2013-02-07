package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;
import org.mutabilitydetector.repackaged.com.google.common.collect.ImmutableList;

public class ConstructorTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final String content = "";
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		new Constructor(name, content, attributes, visibility, annotations);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final String content = "";
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		new Constructor(name, content, attributes, visibility, annotations);
	}

}
