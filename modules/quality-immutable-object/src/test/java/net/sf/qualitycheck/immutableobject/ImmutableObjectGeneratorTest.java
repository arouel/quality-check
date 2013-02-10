package net.sf.qualitycheck.immutableobject;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;

import org.junit.Test;

import com.google.common.io.CharStreams;

public class ImmutableObjectGeneratorTest {

	@Test
	public void createByReadingSourceCode() throws IOException {
		readInterfaceAndCompareToReference("Car.java");
	}

	private String readInterface(final String name) throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		return CharStreams.toString(new InputStreamReader(stream));
	}

	private void readInterfaceAndCompareToReference(final String name) throws IOException {
		assertEquals(readReferenceImmutable(name), ImmutableObjectGenerator.generateByCode(readInterface(name)));
	}

	private String readReferenceImmutable(final String name) throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream("Immutable" + name);
		return CharStreams.toString(new InputStreamReader(stream));
	}

}
