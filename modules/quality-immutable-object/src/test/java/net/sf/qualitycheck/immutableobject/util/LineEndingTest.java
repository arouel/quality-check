package net.sf.qualitycheck.immutableobject.util;

import static org.junit.Assert.assertEquals;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter.LineEnding;

import org.junit.Test;

public class LineEndingTest {

	@Test(expected = IllegalNullArgumentException.class)
	public void find_code_isNull() {
		LineEnding.find(LineEnding.KEEP, null);
	}

	@Test
	public void find_in_CR_mode() {
		assertEquals("\r", LineEnding.find(LineEnding.CR, "test\ntest\ntest\n"));
		assertEquals("\r", LineEnding.find(LineEnding.CR, "test\r\ntest\r\ntest\r\n"));
		assertEquals("\r", LineEnding.find(LineEnding.CR, "test\rtest\rtest\r"));

		// mixed, preserve CR
		assertEquals(LineEnding.CR.asString(), LineEnding.find(LineEnding.CR, "test\ntest\r\ntest\r"));
		assertEquals(LineEnding.CR.asString(), LineEnding.find(LineEnding.CR, "test\r\ntest\ntest\r"));
		assertEquals(LineEnding.CR.asString(), LineEnding.find(LineEnding.CR, "test\rtest\r\ntest\n"));
	}

	@Test
	public void find_in_CRLF_mode() {
		assertEquals("\r\n", LineEnding.find(LineEnding.CRLF, "test\ntest\ntest\n"));
		assertEquals("\r\n", LineEnding.find(LineEnding.CRLF, "test\r\ntest\r\ntest\r\n"));
		assertEquals("\r\n", LineEnding.find(LineEnding.CRLF, "test\rtest\rtest\r"));

		// mixed, preserve CRLF
		assertEquals(LineEnding.CRLF.asString(), LineEnding.find(LineEnding.CRLF, "test\ntest\r\ntest\r"));
		assertEquals(LineEnding.CRLF.asString(), LineEnding.find(LineEnding.CRLF, "test\r\ntest\ntest\r"));
		assertEquals(LineEnding.CRLF.asString(), LineEnding.find(LineEnding.CRLF, "test\rtest\r\ntest\n"));
	}

	@Test
	public void find_in_KEEP_mode() {
		assertEquals("\n", LineEnding.find(LineEnding.KEEP, "test\ntest\ntest\n"));
		assertEquals("\r\n", LineEnding.find(LineEnding.KEEP, "test\r\ntest\r\ntest\r\n"));
		assertEquals("\r", LineEnding.find(LineEnding.KEEP, "test\rtest\rtest\r"));

		// mixed, take default EOL-style
		assertEquals(LineEnding.DEFAULT_LINE_ENDING.asString(), LineEnding.find(LineEnding.KEEP, "test\ntest\r\ntest\r"));
		assertEquals(LineEnding.DEFAULT_LINE_ENDING.asString(), LineEnding.find(LineEnding.KEEP, "test\r\ntest\ntest\r"));
		assertEquals(LineEnding.DEFAULT_LINE_ENDING.asString(), LineEnding.find(LineEnding.KEEP, "test\rtest\r\ntest\n"));
		assertEquals(LineEnding.DEFAULT_LINE_ENDING.asString(),
				LineEnding.find(LineEnding.KEEP, "test\rtest\r\ntest\ntest\rtest\r\ntest\ntest\rtest\r\ntest\n"));

		// mixed, take most frequent occurrence
		assertEquals(LineEnding.LF.asString(), LineEnding.find(LineEnding.KEEP, "test\ntest\ntest\r\ntest\r"));
		assertEquals(LineEnding.CRLF.asString(), LineEnding.find(LineEnding.KEEP, "test\r\ntest\r\ntest\ntest\r"));
		assertEquals(LineEnding.CR.asString(), LineEnding.find(LineEnding.KEEP, "test\rtest\rtest\r\ntest\n"));
	}

	@Test
	public void find_in_LF_mode() {
		assertEquals("\n", LineEnding.find(LineEnding.LF, "test\ntest\ntest\n"));
		assertEquals("\n", LineEnding.find(LineEnding.LF, "test\r\ntest\r\ntest\r\n"));
		assertEquals("\n", LineEnding.find(LineEnding.LF, "test\rtest\rtest\r"));

		// mixed, preserve LF
		assertEquals(LineEnding.LF.asString(), LineEnding.find(LineEnding.LF, "test\ntest\r\ntest\r"));
		assertEquals(LineEnding.LF.asString(), LineEnding.find(LineEnding.LF, "test\r\ntest\ntest\r"));
		assertEquals(LineEnding.LF.asString(), LineEnding.find(LineEnding.LF, "test\rtest\r\ntest\n"));
	}

	@Test
	public void find_inCodeBy_KEEP() {
		assertEquals("\n", LineEnding.KEEP.find("test\ntest\ntest\n"));
		assertEquals("\r\n", LineEnding.KEEP.find("test\r\ntest\r\ntest\r\n"));
		assertEquals("\r", LineEnding.KEEP.find("test\rtest\rtest\r"));

		assertEquals("\r", LineEnding.CR.find("test\ntest\ntest\n"));
		assertEquals("\r", LineEnding.CR.find("test\r\ntest\r\ntest\r\n"));
		assertEquals("\r", LineEnding.CR.find("test\rtest\rtest\r"));

		assertEquals("\r\n", LineEnding.CRLF.find("test\ntest\ntest\n"));
		assertEquals("\r\n", LineEnding.CRLF.find("test\r\ntest\r\ntest\r\n"));
		assertEquals("\r\n", LineEnding.CRLF.find("test\rtest\rtest\r"));

		assertEquals("\n", LineEnding.LF.find("test\ntest\ntest\n"));
		assertEquals("\n", LineEnding.LF.find("test\r\ntest\r\ntest\r\n"));
		assertEquals("\n", LineEnding.LF.find("test\rtest\rtest\r"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void find_lineEnding_isNull() {
		LineEnding.find(null, "");
	}

}
