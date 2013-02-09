package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.List;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class FieldTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Field.class, areImmutable(), provided(ImmutableList.class).isAlsoImmutable());
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final Type type = Type.of(String.class);
		final Visibility visibility = Visibility.PUBLIC;
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of(Annotation.of(Nullable.class));
		final String value = "";
		final AccessorPrefix accessorPrefix = AccessorPrefix.GET;
		new Field(name, type, visibility, finalModifier, staticModifier, annotations, value, accessorPrefix).isNullable();
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final Type type = Type.of(String.class);
		final Visibility visibility = Visibility.PUBLIC;
		final Final finalModifier = Final.UNDEFINED;
		final Static staticModifier = Static.UNDEFINED;
		final List<Annotation> annotations = ImmutableList.of(Annotation.of(Nullable.class));
		final String value = "";
		final AccessorPrefix accessorPrefix = AccessorPrefix.GET;
		new Field(name, type, visibility, finalModifier, staticModifier, annotations, value, accessorPrefix).isNullable();
	}

	@Test
	public void isNullable() {
		final String name = "field";
		final Type type = Type.of(String.class);
		final Visibility visibility = Visibility.PUBLIC;
		final Final finalMod = Final.UNDEFINED;
		final Static staticMod = Static.UNDEFINED;
		final String value = "";
		final AccessorPrefix prefix = AccessorPrefix.GET;

		final List<Annotation> annotationsEmpty = ImmutableList.of();
		final List<Annotation> annotationsWithNullable = ImmutableList.of(Annotation.of(Nullable.class));
		final List<Annotation> annotationsWithNonnegative = ImmutableList.of(Annotation.of(Nonnegative.class));
		final List<Annotation> annotationsWithNonnull = ImmutableList.of(Annotation.of(Nonnull.class));

		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsEmpty, value, prefix).isNullable());
		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNullable, value, prefix).isNullable());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnegative, value, prefix).isNullable());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnull, value, prefix).isNullable());

		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsEmpty, value, prefix).isNonnegative());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNullable, value, prefix).isNonnegative());
		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnegative, value, prefix).isNonnegative());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnull, value, prefix).isNonnegative());

		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsEmpty, value, prefix).isNonnull());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNullable, value, prefix).isNonnull());
		assertFalse(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnegative, value, prefix).isNonnull());
		assertTrue(new Field(name, type, visibility, finalMod, staticMod, annotationsWithNonnull, value, prefix).isNonnull());
	}

}
