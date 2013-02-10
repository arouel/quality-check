package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;

import net.sf.qualitycheck.immutableobject.domain.Type.CollectionVariant;

import org.junit.Test;

public class CollectionVariantTest {

	@Test
	public void collectionVariant_checkAll() {
		assertEquals(CollectionVariant.COLLECTION, CollectionVariant.evaluate(Type.of(Collection.class)));
		assertEquals(CollectionVariant.ITERABLE, CollectionVariant.evaluate(Type.of(Iterable.class)));
		assertEquals(CollectionVariant.LIST, CollectionVariant.evaluate(Type.of(List.class)));
		assertEquals(CollectionVariant.MAP, CollectionVariant.evaluate(Type.of(Map.class)));
		assertEquals(CollectionVariant.SET, CollectionVariant.evaluate(Type.of(Set.class)));
		assertEquals(CollectionVariant.SORTEDSET, CollectionVariant.evaluate(Type.of(SortedSet.class)));
		assertEquals(CollectionVariant.SORTEDSET, CollectionVariant.evaluate(Type.of(SortedSet.class)));
		assertEquals(7, CollectionVariant.values().length);
	}

}
