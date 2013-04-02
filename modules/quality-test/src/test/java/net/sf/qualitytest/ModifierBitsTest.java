package net.sf.qualitytest;

import org.junit.Test;

public class ModifierBitsTest {

	@Test
	public void testClassIsFinal() {
		StaticCheck.classIsFinal(ModifierBits.class);
		StaticCheck.noPublicDefaultConstructor(ModifierBits.class);
	}

	@Test
	public void testGiveMeCoverageForMyPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(ModifierBits.class);
	}

}
