package net.sf.qualitycheck.immutableobject.generator;

import static org.junit.Assert.assertEquals;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Test;

public class BasicFormatRendererTest {

	@Test
	public void toString_formatName_isNull() {
		assertEquals("text", new BasicFormatRenderer().toString("text", null, null));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void toString_o_isNull() {
		new BasicFormatRenderer().toString(null, null, null);
	}

	@Test
	public void toString_toLower() {
		assertEquals("text", new BasicFormatRenderer().toString("TEXT", "toLower", null));
		assertEquals("t", new BasicFormatRenderer().toString("T", "toLower", null));
		assertEquals("", new BasicFormatRenderer().toString("", "toLower", null));
		assertEquals(" ", new BasicFormatRenderer().toString(" ", "toLower", null));
	}

	@Test
	public void toString_toLowerCamelCase() {
		assertEquals("textText", new BasicFormatRenderer().toString("TextText", "toLowerCamelCase", null));
		assertEquals("t", new BasicFormatRenderer().toString("T", "toLowerCamelCase", null));
		assertEquals("", new BasicFormatRenderer().toString("", "toLowerCamelCase", null));
		assertEquals(" ", new BasicFormatRenderer().toString(" ", "toLowerCamelCase", null));
	}

	@Test
	public void toString_toUpper() {
		assertEquals("TEXT", new BasicFormatRenderer().toString("text", "toUpper", null));
		assertEquals("T", new BasicFormatRenderer().toString("t", "toUpper", null));
		assertEquals("", new BasicFormatRenderer().toString("", "toUpper", null));
	}

	@Test
	public void toString_toUpperCamelCase() {
		assertEquals("TextText", new BasicFormatRenderer().toString("textText", "toUpperCamelCase", null));
		assertEquals("T", new BasicFormatRenderer().toString("t", "toUpperCamelCase", null));
		assertEquals("", new BasicFormatRenderer().toString("", "toUpperCamelCase", null));
	}

}
