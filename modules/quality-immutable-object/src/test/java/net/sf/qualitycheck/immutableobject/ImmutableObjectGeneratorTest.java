package net.sf.qualitycheck.immutableobject;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.io.CharStreams;

public class ImmutableObjectGeneratorTest {

	private static final Logger LOG = LoggerFactory.getLogger(ImmutableObjectGeneratorTest.class);

	@Test
	@Ignore("This is only a convenience test to get a quick overview what differ.")
	public void compareReadingSourceCodeVersusRuntime() throws IOException {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("Car.java");
		final String code = CharStreams.toString(new InputStreamReader(stream));
		final Class<?> type = Car.class;
		assertEquals(ImmutableObjectGenerator.generateByInterface(type), ImmutableObjectGenerator.generateByCode(code));
	}

	@Test
	public void createByReadingSourceCode() throws IOException {
		final InputStream stream = this.getClass().getClassLoader().getResourceAsStream("Car.java");
		final String code = CharStreams.toString(new InputStreamReader(stream));
		LOG.info(ImmutableObjectGenerator.generateByCode(code));
	}

	@Test
	public void createDuringRuntime() {
		final Class<?> type = Car.class;
		LOG.info(ImmutableObjectGenerator.generateByInterface(type));
	}

}
