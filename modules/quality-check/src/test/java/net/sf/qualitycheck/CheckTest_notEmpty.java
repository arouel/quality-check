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

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_notEmpty {

	@Test
	public void notEmpty_checkReferenceIsSame_withString() {
		final String text = "beer tastes good";
		Assert.assertSame(text, Check.notEmpty(text));
		Assert.assertSame(text, Check.notEmpty(text, "text"));
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyArray_isInvalid() {
		final String[] array = new String[] {};
		Check.notEmpty(array);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyArray_withArgName_isInvalid() {
		final String[] array = new String[] {};
		Check.notEmpty(array, "array");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyCollection_isInvalid() {
		final Set<String> collection = new HashSet<String>();
		Check.notEmpty(collection);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyCollection_withArgName_isInvalid() {
		final Set<String> collection = new HashSet<String>();
		Check.notEmpty(collection, "collection");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyMap_isInvalid() {
		final Map<String, String> map = new HashMap<String, String>();
		Check.notEmpty(map);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyMap_withArgName_isInvalid() {
		final Map<String, String> map = new HashMap<String, String>();
		Check.notEmpty(map, "map");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyString_isInvalid() {
		final String text = "";
		Check.notEmpty(text);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_emptyString_withArgName_isInvalid() {
		final String text = "";
		Check.notEmpty(text, "text");
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_expression_isInvalid() {
		Check.notEmpty(true);
	}

	@Test
	public void notEmpty_expression_isValid() {
		Check.notEmpty(false);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_expression_withArgName_isInvalid() {
		Check.notEmpty(true, "myVar");
	}

	@Test
	public void notEmpty_expression_withArgName_isValid() {
		Check.notEmpty(false, "myVar");
	}

	@Test
	public void notEmpty_filledArray_isValid() {
		final String[] array = new String[] { "nom nom, what a tasty vanilla ice cream" };
		final String[] nonEmptyArray = Check.notEmpty(array);
		Assert.assertSame(array, nonEmptyArray);
	}

	@Test
	public void notEmpty_filledArray_withArgName_isValid() {
		final String[] array = new String[] { "Mr. Spock" };
		final String[] nonEmptyMap = Check.notEmpty(array, "array");
		Assert.assertSame(array, nonEmptyMap);
	}

	@Test
	public void notEmpty_filledCollection_isValid() {
		final Set<String> collection = new HashSet<String>();
		collection.add("hmm, what a tasty ice cream");
		final Set<String> nonEmptySet = Check.notEmpty(collection);
		Assert.assertSame(collection, nonEmptySet);
	}

	@Test
	public void notEmpty_filledCollection_withArgName_isValid() {
		final Set<String> collection = new HashSet<String>();
		collection.add("hmm, what a tasty ice cream");
		final Set<String> nonEmptySet = Check.notEmpty(collection, "collection");
		Assert.assertSame(collection, nonEmptySet);
	}

	@Test
	public void notEmpty_filledMap_isValid() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		final Map<String, String> nonEmptyMap = Check.notEmpty(map);
		Assert.assertSame(map, nonEmptyMap);
	}

	@Test
	public void notEmpty_filledMap_withArgName_isValid() {
		final Map<String, String> map = new HashMap<String, String>();
		map.put("drink", "water");
		final Map<String, String> nonEmptyMap = Check.notEmpty(map, "map");
		Assert.assertSame(map, nonEmptyMap);
	}

	@Test(expected = IllegalEmptyArgumentException.class)
	public void notEmpty_withEmptyReference_isEmpty_withArgName_isInvalid() {
		final String text = "";
		Check.notEmpty(text, text.isEmpty(), "text");
	}

	@Test
	public void notEmpty_withFilledReference_notEmpty_withArgName_isValid() {
		final String text = "strawberries tastes also good";
		Assert.assertSame(text, Check.notEmpty(text, text.isEmpty(), "text"));
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullCollection_withArgName_isInvalid() {
		Check.notEmpty((Collection<?>) null, "text");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullReference_isEmpty_withArgName_isInvalid() {
		Check.notEmpty(null, true, "argName");
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullReference_isInvalid() {
		Check.notEmpty((String) null);
	}

	@Test(expected = IllegalNullArgumentException.class)
	public void notEmpty_withNullString_withArgName_isInvalid() {
		Check.notEmpty((String) null, "text");
	}

	@Test
	public void notEmpty_withText_withArgName_isValid() {
		final String text = "strawberries tastes also good";
		Assert.assertSame(text, Check.notEmpty(text, "text"));
	}

}
