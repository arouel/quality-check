package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mutabilitydetector.unittesting.MutabilityAssert.assertInstancesOf;
import static org.mutabilitydetector.unittesting.MutabilityMatchers.areImmutable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import org.junit.Test;

public class TypeTest {

	@Test
	public void checkClassIsImmutable() {
		assertInstancesOf(Type.class, areImmutable());
	}

	@Test
	public void collectionVariant_checkAll() {
		assertTrue(Type.of(Collection.class).isCollectionVariant());
		assertTrue(Type.of(Iterable.class).isCollectionVariant());
		assertTrue(Type.of(List.class).isCollectionVariant());
		assertTrue(Type.of(Map.class).isCollectionVariant());
		assertTrue(Type.of(Set.class).isCollectionVariant());
		assertTrue(Type.of(SortedSet.class).isCollectionVariant());
		assertTrue(Type.of(SortedSet.class).isCollectionVariant());
		assertEquals(7, CollectionVariant.values().length);
	}

	@Test
	public void construct_innerInterface() {
		final Type type = new Type("com.github.before.Immutable$InnerInterface");
		assertEquals("com.github.before.Immutable.InnerInterface", type.toString());
	}

	@Test
	public void construct_typeGeneric() {
		final Type type = new Type("T");
		assertEquals(Package.UNDEFINED, type.getPackage());
		assertEquals("T", type.getName());
		assertEquals(GenericDeclaration.UNDEFINED, type.getGenericDeclaration());
	}

	@Test
	public void construct_typeWithoutPackage() {
		new Type("Immutable");
	}

	@Test
	public void construct_typeWithoutPackageAndGeneric() {
		final Type type = new Type("List<String>");
		assertEquals("List", type.getName());
		assertEquals("String", type.getGenericDeclaration().getDeclaration());
		assertEquals("List<String>", type.toString());
	}

	@Test
	public void construct_typeWithPackage() {
		new Type("com.github.before.Immutable");
	}

	@Test
	public void toString_primitive() {
		final Type type = new Type("int");
		assertEquals("int", type.toString());
	}

}
