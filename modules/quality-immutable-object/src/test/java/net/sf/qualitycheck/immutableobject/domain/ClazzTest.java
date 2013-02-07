package net.sf.qualitycheck.immutableobject.domain;

import static org.mutabilitydetector.unittesting.AllowedReason.provided;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;

import org.junit.Test;

import com.google.common.collect.ImmutableList;

public class ClazzTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Clazz.class, areImmutable(), provided(ImmutableList.class, Annotations.class, Imports.class).isAlsoImmutable());
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void construct_abtractAndFinal() {
		final String name = "AbtractAndFinalTestClazz";
		final Package pkg = new Package(getClass().getPackage().getName());
		final List<Field> field = new ImmutableList.Builder<Field>().build();
		final List<Constructor> constructors = new ImmutableList.Builder<Constructor>().build();
		final List<Method> methods = new ImmutableList.Builder<Method>().build();
		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.ABSTRACT;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of();
		final List<Annotation> annotations = new ImmutableList.Builder<Annotation>().add(Annotation.of(Immutable.class)).build();
		ImmutableList.of();
		new Clazz(name, pkg, field, constructors, methods, visibility, finalModifier, abstractModifier, interfaces, Imports.of(),
				annotations);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void construct_name_isEmpty() {
		final String name = "";
		final Package pkg = Package.UNDEFINED;
		final List<Field> field = ImmutableList.of();
		final List<Constructor> constructors = ImmutableList.of();
		final List<Method> methods = ImmutableList.of();
		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.UNDEFINED;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		new Clazz(name, pkg, field, constructors, methods, visibility, finalModifier, abstractModifier, interfaces, Imports.of(),
				annotations);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void construct_name_isNull() {
		final String name = null;
		final Package pkg = Package.UNDEFINED;
		final List<Field> field = ImmutableList.of();
		final List<Constructor> constructors = ImmutableList.of();
		final List<Method> methods = ImmutableList.of();
		final Final finalModifier = Final.FINAL;
		final Abstract abstractModifier = Abstract.UNDEFINED;
		final Visibility visibility = Visibility.PUBLIC;
		final List<Interface> interfaces = ImmutableList.of();
		final List<Annotation> annotations = ImmutableList.of();
		new Clazz(name, pkg, field, constructors, methods, visibility, finalModifier, abstractModifier, interfaces, Imports.of(),
				annotations);
	}
}
