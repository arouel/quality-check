package net.sf.qualitycheck.immutableobject.util;

import static org.junit.Assert.assertEquals;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;

import org.junit.Test;

public class MethodUtilTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void determineAccessorName_isEmpty() {
		MethodUtil.determineAccessorName(AccessorPrefix.GET, "");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void determineAccessorName_isNull() {
		MethodUtil.determineAccessorName(AccessorPrefix.GET, null);
	}

	@Test
	public void determineAccessorName_successful() {
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "_field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-??field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-08978field"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "_field_"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__field__"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-field-__"));
		assertEquals("getField", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-??field??-__"));
		assertEquals("getField123", MethodUtil.determineAccessorName(AccessorPrefix.GET, "__-08978field123-___"));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineAccessorName_wrongFieldName() {
		MethodUtil.determineAccessorName(AccessorPrefix.GET, " ");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void determineMutatorName_isEmpty() {
		MethodUtil.determineMutatorName("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void determineMutatorName_isNull() {
		MethodUtil.determineMutatorName(null);
	}

	@Test
	public void determineMutatorName_successful() {
		assertEquals("setField", MethodUtil.determineMutatorName("field"));
		assertEquals("setField", MethodUtil.determineMutatorName("_field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-??field"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-08978field"));
		assertEquals("setField", MethodUtil.determineMutatorName("_field_"));
		assertEquals("setField", MethodUtil.determineMutatorName("__field__"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-field-__"));
		assertEquals("setField", MethodUtil.determineMutatorName("__-??field??-__"));
		assertEquals("setField123", MethodUtil.determineMutatorName("__-08978field123-___"));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineMutatorName_wrongFieldName() {
		MethodUtil.determineMutatorName(" ");
	}

}
