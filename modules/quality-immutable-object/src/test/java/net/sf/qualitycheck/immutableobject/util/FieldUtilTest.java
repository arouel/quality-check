package net.sf.qualitycheck.immutableobject.util;

import static org.junit.Assert.assertEquals;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.exception.IllegalStateOfArgumentException;
import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;

import org.junit.Test;

public class FieldUtilTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void determineAccessorPrefix_isEmpty() {
		FieldUtil.determineAccessorPrefix("");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineAccessorPrefix_isEmtpyAfterTrim() {
		FieldUtil.determineAccessorPrefix(" ");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void determineAccessorPrefix_isNull() {
		FieldUtil.determineAccessorPrefix(null);
	}

	@Test
	public void determineAccessorPrefix_successful() {
		assertEquals(AccessorPrefix.GET, FieldUtil.determineAccessorPrefix("getField"));
		assertEquals(AccessorPrefix.HAS, FieldUtil.determineAccessorPrefix("hasField"));
		assertEquals(AccessorPrefix.IS, FieldUtil.determineAccessorPrefix("isField"));
		assertEquals(AccessorPrefix.GET, FieldUtil.determineAccessorPrefix("getField123"));
		assertEquals(AccessorPrefix.HAS, FieldUtil.determineAccessorPrefix("hasField123"));
		assertEquals(AccessorPrefix.IS, FieldUtil.determineAccessorPrefix("isField123"));
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineAccessorPrefix_wrongMethodName() {
		FieldUtil.determineAccessorPrefix("getterMethod");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void determineFieldName_isEmpty() {
		FieldUtil.determineFieldName("");
	}

	@Test(expected = IllegalStateOfArgumentException.class)
	public void determineFieldName_isEmptyAfterTrim() {
		FieldUtil.determineFieldName(" ");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void determineFieldName_isNull() {
		FieldUtil.determineFieldName(null);
	}

	@Test
	public void determineFieldName_successful() {
		assertEquals("field", FieldUtil.determineFieldName("getField"));
		assertEquals("field", FieldUtil.determineFieldName("hasField"));
		assertEquals("field", FieldUtil.determineFieldName("isField"));
		assertEquals("field123", FieldUtil.determineFieldName("getField123"));
		assertEquals("field123", FieldUtil.determineFieldName("hasField123"));
		assertEquals("field123", FieldUtil.determineFieldName("isField123"));
	}

}
