package net.sf.qualitytest;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;

import org.junit.Test;

public class CheckIntegrationTest {

	@Test
	public void publicMethodsMustBeAnnotatedWithThrows() {
		StaticCheck.publicMethodsAnnotated(Check.class, Throws.class);
	}

}
