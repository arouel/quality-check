package net.sf.qualitycheck.immutableobject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;
import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator.Result;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;

public class ImmutableObjectGeneratorMain {

	private static final Logger LOG = LoggerFactory.getLogger(ImmutableObjectGeneratorMain.class);

	public static void main(final String[] args) throws IOException {
		final ImmutableSettings.Builder settings = new ImmutableSettings.Builder();

		// global settings
		settings.fieldPrefix("");
		settings.jsr305Annotations(true);
		settings.guava(true);
		settings.qualityCheck(true);

		// immutable settings
		settings.copyMethods(false);
		settings.hashCodePrecomputation(false);
		settings.hashCodeAndEquals(true);
		settings.replacement(true);
		settings.serializable(false);
		settings.toString(true);

		// builder settings
		settings.builderCopyConstructor(true);
		settings.builderFlatMutators(false);
		settings.builderFluentMutators(true);
		settings.builderName("");
		settings.builderImplementsInterface(false);

		final String name = "Constructor.java";
		final InputStream stream = ImmutableObjectGeneratorMain.class.getClassLoader().getResourceAsStream(name);
		final Result result = ImmutableObjectGenerator.generate(CharStreams.toString(new InputStreamReader(stream)), settings.build());
		LOG.info("\n" + result.getImplCode());
		LOG.info("\n" + result.getTestCode());
	}

}
