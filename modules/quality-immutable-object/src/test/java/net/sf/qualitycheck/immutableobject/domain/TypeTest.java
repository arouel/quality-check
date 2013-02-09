package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sf.qualitycheck.immutableobject.domain.Type.CollectionVariant;

import org.junit.Test;

public class TypeTest {

	@Test
	public void collectionVariant_checkAll() {
		assertEquals(CollectionVariant.COLLECTION, Type.of(Collection.class).getCollectionVariant());
		assertEquals(CollectionVariant.ITERABLE, Type.of(Iterable.class).getCollectionVariant());
		assertEquals(CollectionVariant.LIST, Type.of(List.class).getCollectionVariant());
		assertEquals(CollectionVariant.MAP, Type.of(Map.class).getCollectionVariant());
		assertEquals(CollectionVariant.SET, Type.of(Set.class).getCollectionVariant());
		assertEquals(CollectionVariant.SORTEDSET, Type.of(SortedSet.class).getCollectionVariant());
		assertEquals(CollectionVariant.SORTEDSET, Type.of(SortedSet.class).getCollectionVariant());
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
