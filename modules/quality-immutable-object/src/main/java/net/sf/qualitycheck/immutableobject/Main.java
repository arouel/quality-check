/*******************************************************************************
 * Copyright 2013 André Rouél and Dominik Seichter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package net.sf.qualitycheck.immutableobject;

import java.io.File;
import java.io.IOException;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.generator.ImmutableObjectGenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	static ImmutableSettings defaultSettings() {
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
		settings.builderImplementsInterface(false);

		return settings.build();
	}

	public static void main(final String[] args) throws IOException {
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
			final String generated = ImmutableObjectGenerator.generate(code, defaultSettings()).getImplCode();
			LOG.info("\n" + generated);
		}
		LOG.info("--");
		LOG.info("We hope that you saved some time.");
		LOG.info("Your participation in this project is much appreciated!");
	}

}
