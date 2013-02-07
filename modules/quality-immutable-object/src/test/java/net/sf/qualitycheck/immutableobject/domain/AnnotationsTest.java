package net.sf.qualitycheck.immutableobject.domain;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class AnnotationsTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Annotations.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

}
