package net.sf.qualitycheck.immutableobject.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class GenericDeclarationTest {

	@Test
	public void test() {
		assertEquals(GenericDeclaration.of("String"), GenericDeclaration.parseFrom("List<String>"));
		assertEquals(GenericDeclaration.of("T extends List<String>"), GenericDeclaration.parseFrom("List<T extends List<String>>"));
		// assertEquals(Sets.newHashSet("List", "String"), Generic.parse("<T extends Annotation> T"));
	}

}
