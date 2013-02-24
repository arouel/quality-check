package net.sf.qualitycheck.immutableobject.util;

import static org.junit.Assert.assertEquals;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class ReservedWordConverterTest {

	@Test
	public void convertIfNecessary_name_isEmpty() {
		assertEquals("", ReservedWordConverter.convertIfNecessary(""));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void convertIfNecessary_name_isNull() {
		ReservedWordConverter.convertIfNecessary(null);
	}

	@Test
	public void convertIfNecessary_withoutReservedWord() {
		assertEquals("name", ReservedWordConverter.convertIfNecessary("name"));
		assertEquals("var", ReservedWordConverter.convertIfNecessary("var"));
		assertEquals("Boolean", ReservedWordConverter.convertIfNecessary("Boolean"));
		assertEquals("Long", ReservedWordConverter.convertIfNecessary("Long"));
	}

	@Test
	public void convertIfNecessary_withReservedWord() {
		assertEquals("class1", ReservedWordConverter.convertIfNecessary("class"));
		assertEquals("import1", ReservedWordConverter.convertIfNecessary("import"));
		assertEquals("package1", ReservedWordConverter.convertIfNecessary("package"));
		assertEquals("enum1", ReservedWordConverter.convertIfNecessary("enum"));
	}

}
