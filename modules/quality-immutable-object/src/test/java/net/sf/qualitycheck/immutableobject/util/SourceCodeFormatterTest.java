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
package net.sf.qualitycheck.immutableobject.util;

import static org.junit.Assert.assertEquals;

import java.util.Properties;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter.LineEnding;

import org.junit.Test;

public class SourceCodeFormatterTest {

	@Test(expected = IllegalEmptyArgumentException.class)
	public void format_code_isEmpty() {
		SourceCodeFormatter.format("", SourceCodeFormatter.DEFAULT_PROPERTIES, LineEnding.LF);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void format_code_isNull() {
		SourceCodeFormatter.format(null, SourceCodeFormatter.DEFAULT_PROPERTIES, LineEnding.LF);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void format_isEmpty() {
		SourceCodeFormatter.format("");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void format_isNull() {
		SourceCodeFormatter.format(null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void format_lineEnding_isNull() {
		SourceCodeFormatter.format("interface TestObject {}", SourceCodeFormatter.DEFAULT_PROPERTIES, null);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void format_properties_isEmpty() {
		SourceCodeFormatter.format("interface TestObject {}", new Properties(), LineEnding.LF);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void format_properties_isNull() {
		SourceCodeFormatter.format("interface TestObject {}", null, LineEnding.LF);
	}

	@Test
	public void format_successful() {
		final Properties properties = new Properties();
		properties.setProperty("org.eclipse.jdt.core.compiler.compliance", "1.4");
		final String code = SourceCodeFormatter.format("interface TestObject{String getName () ;}", properties, LineEnding.LF);
		final StringBuilder expected = new StringBuilder();
		expected.append("interface TestObject {\n");
		expected.append("\tString getName();\n");
		expected.append("}");
		assertEquals(expected.toString(), code);
	}

	@Test
	public void format_unmatchedCompliance() {
		final Properties properties = new Properties();
		properties.setProperty("org.eclipse.jdt.core.compiler.compliance", "1.4");
		final String inputCode = "interface TestObject{List<String> getNames () ;}";
		final String outputCode = SourceCodeFormatter.format("interface TestObject{List<String> getNames () ;}", properties, LineEnding.LF);
		assertEquals(inputCode, outputCode);
	}

}
