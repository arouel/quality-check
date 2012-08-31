package net.sf.qualitycheck;

import java.lang.reflect.Method;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReadAnnotationTest {

	private static final Logger LOG = LoggerFactory.getLogger(ReadAnnotationTest.class);

	@Test
	public void read() throws Exception {
		for (final Method m : Class.forName("net.sf.qualitycheck.Check").getMethods()) {
			if (m.isAnnotationPresent(ArgumentsChecked.class)) {
				LOG.info("Analyzing: " + m.toGenericString());
				final Class<? extends Throwable>[] throwsExceptions = m.getAnnotation(ArgumentsChecked.class).value();
				for (final Class<? extends Throwable> throwsException : throwsExceptions) {
					LOG.info("\tthrows " + throwsException.getName());
				}
			}
		}
	}

}
