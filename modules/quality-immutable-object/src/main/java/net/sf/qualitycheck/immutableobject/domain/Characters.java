/*******************************************************************************
 * Copyright 2013 André Rouél
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
package net.sf.qualitycheck.immutableobject.domain;

interface Characters {

	/**
	 * An at sign ({@code @})
	 */
	char AT_SIGN = '@';

	/**
	 * An closing brace (<code>}</code>)
	 */
	char BRACE_CLOSE = '}';

	/**
	 * An opening brace (<code>{</code>)
	 */
	char BRACE_OPEN = '{';

	/**
	 * An closing bracket ({@code >})
	 */
	char BRACKET_CLOSE = '>';

	/**
	 * An closing bracket ({@code <})
	 */
	char BRACKET_OPEN = '<';

	/**
	 * A comma ({@code ,})
	 */
	char COMMA = ',';

	/**
	 * A comma and space together (<code>, </code>)
	 */
	String COMMA_SPACE = ", ";

	/**
	 * Representation of a dot character
	 */
	char DOT = '.';

	/**
	 * An equals sign (<code>=</code>)
	 */
	char EQUALS_SIGN = '=';

	/**
	 * A newline, also known as a line break or end-of-line marker
	 */
	char NEWLINE = '\n';

	/**
	 * An closing parentheses <code>)</code>
	 */
	char PARENTHESES_CLOSE = ')';

	/**
	 * An opening parentheses <code>(</code>
	 */
	char PARENTHESES_OPEN = '(';

	/**
	 * A semicolon ( {@code ;})
	 */
	char SEMICOLON = ';';

	/**
	 * A space ( )
	 */
	char SPACE = ' ';

}
