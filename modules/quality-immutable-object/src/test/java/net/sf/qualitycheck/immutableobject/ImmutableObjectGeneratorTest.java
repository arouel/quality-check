package net.sf.qualitycheck.immutableobject;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;

public class ImmutableObjectGeneratorTest {

	private static final Logger LOG = LoggerFactory.getLogger(ImmutableObjectGeneratorTest.class);

	@Test
	public void createBy() throws IOException {
		LOG.info(readInterfaceAndGenerate("Settings.java"));
	}

	@Test
	public void createByReadingSourceCode() throws IOException {
		readInterfaceAndCompareToReference("Car.java");
	}

	private void readInterfaceAndCompareToReference(final String name) throws IOException {
		assertEquals(readReferenceImmutable(name), readInterfaceAndGenerate(name));
	}

	private String readInterfaceAndGenerate(final String name) throws IOException {
		final ImmutableSettings.Builder settings = new ImmutableSettings.Builder();

		// global settings
		settings.jsr305Annotations(true);
		settings.guava(false);
		settings.qualityCheck(true);

		// immutable settings
		settings.serializable(false);

		// builder settings
		settings.builderCopyConstructor(true);
		settings.builderFlatMutators(false);
		settings.builderFluentMutators(true);
		settings.builderName("Builder");
		settings.builderImplementsInterface(true);

		final InputStream stream = getClass().getClassLoader().getResourceAsStream(name);
		return ImmutableObjectGenerator.generate(CharStreams.toString(new InputStreamReader(stream)), settings.build());
	}

	private String readReferenceImmutable(final String name) throws IOException {
		final InputStream stream = getClass().getClassLoader().getResourceAsStream("Immutable" + name);
		return CharStreams.toString(new InputStreamReader(stream));
	}

}
