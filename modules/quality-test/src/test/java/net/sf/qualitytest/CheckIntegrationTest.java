package net.sf.qualitytest;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitytest.exception.IllegalMissingAnnotationOnMethodException;

import org.junit.Test;

public class CheckIntegrationTest {

	private static void publicMethodsAnnotated(@Nonnull final Class<?> clazz, @Nonnull final Class<? extends Annotation> annotation,
			final List<String> ignoreList) {
		final Method[] methods = clazz.getDeclaredMethods();
		for (final Method m : methods) {
			if (!ignoreList.contains(m.getName()) && Modifier.isPublic(m.getModifiers()) && !m.isAnnotationPresent(annotation)) {
				throw new IllegalMissingAnnotationOnMethodException(clazz, annotation, m);
			}
		}
	}

	@Test
	public void publicMethodsMustBeAnnotatedWithThrows() {
		final List<String> ignoreList = new ArrayList<String>();
		ignoreList.add("nothing");

		publicMethodsAnnotated(Check.class, Throws.class, ignoreList);
	}
}
