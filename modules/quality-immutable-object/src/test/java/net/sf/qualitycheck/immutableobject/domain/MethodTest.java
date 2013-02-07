package net.sf.qualitycheck.immutableobject.domain;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.List;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class MethodTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Method.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final ReturnType returnType = ReturnType.BOOLEAN;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of();
		new Method(name, returnType, "", attributes, visibility, finalModifier, staticModifier, annotations);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final ReturnType returnType = ReturnType.BOOLEAN;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Attribute> attributes = ImmutableList.of();
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of();
		new Method(name, returnType, "", attributes, visibility, finalModifier, staticModifier, annotations);
	}

}
