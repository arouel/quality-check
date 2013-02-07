package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

public class AnnotationTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Annotation.class, areImmutable());
	}

	@Test
	public void construct_isAnnotation() {
		Annotation.of(Immutable.class);
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_isNotAnnotation() {
		Annotation.of(String.class);
	}

	@Test
	public void equals_differentType() {
		final Annotation immutable = Annotation.of(Immutable.class);
		final Annotation nonnull = Annotation.of(Nonnull.class);
		assertTrue(!immutable.equals(nonnull));
	}

	@Test
	public void equals_identical() {
		final Annotation immutable1 = Annotation.of(Immutable.class);
		final Annotation immutable2 = Annotation.of(Immutable.class);
		assertEquals(immutable1, immutable2);
		assertEquals(Annotation.IMMUTABLE, immutable2);
	}

	@Test
	public void equals_same() {
		final Annotation immutable = Annotation.of(Immutable.class);
		assertEquals(immutable, immutable);
		assertSame(immutable, immutable);
	}

}
