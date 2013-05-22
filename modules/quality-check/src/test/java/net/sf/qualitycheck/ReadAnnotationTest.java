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
				final Class<? extends Throwable>[] throwsExceptions = m.getAnnotation(Throws.class).value();
				for (final Class<? extends Throwable> throwsException : throwsExceptions) {
					LOG.info("\tthrows " + throwsException.getName());
				}
			}
		}
	}

}
