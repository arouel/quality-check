package net.sf.qualitytest;

import net.sf.qualitycheck.ConditionalCheck;
import net.sf.qualitycheck.Throws;

import org.junit.Test;

public class ConditionalCheckIntegrationTest {

	@Test
	public void publicMethodsMustBeAnnotatedWithThrows() {
		StaticCheck.publicMethodsAnnotated(ConditionalCheck.class, Throws.class);
	}

}
