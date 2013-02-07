package net.sf.qualitycheck.immutableobject;

import java.io.File;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(final String[] args) throws Exception {
		Check.notNull(args, "args");
		LOG.info("This tool generates an immutable class from an interface.");
		LOG.info("--");
		LOG.info("--");
		if (args.length == 0) {
			LOG.info("Input a path to an interface, please.");
		}
		LOG.info("--");
		for (final String path : args) {
			final File file = new File(path);
			final String code = Files.toString(file, Charsets.UTF_8);
			final String generated = ImmutableObjectGenerator.generateByCode(code);
			LOG.info("\n" + generated);
		}
		LOG.info("--");
		LOG.info("We hope that you saved some time.");
		LOG.info("Your participation in this project is much appreciated!");
	}
}
