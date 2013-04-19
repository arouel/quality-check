/*******************************************************************************
 * Copyright 2013 Dominik Seichter
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
package net.sf.qualitytest.blueprint;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest_withPublicAttributes {

	public static class StupidImmutableWithPublicAttribut {
		private final String str;
		public Date date;

		public StupidImmutableWithPublicAttribut(final String str) {
			this.str = str;
		}

		public String getStr() {
			return str;
		}
	}

	public static class WithPublicAttributes {

		public int a;
		public String str;
		public URL url;

		private Long l;

		public Long getL() {
			return l;
		}

		public void setL(final Long l) {
			this.l = l;
		}

	}

	@Test
	public void testBlueprintImmutableWithAttributes() {
		final StupidImmutableWithPublicAttribut stupid = Blueprint.def().withPublicAttributes(true)
				.construct(StupidImmutableWithPublicAttribut.class);
		Assert.assertEquals("", stupid.getStr());
		Assert.assertNotEquals(0l, stupid.date.getTime());
	}

	@Test
	public void testBlueprintImmutableWithoutAttributes() {
		final StupidImmutableWithPublicAttribut stupid = Blueprint.def().withPublicAttributes(false)
				.construct(StupidImmutableWithPublicAttribut.class);
		Assert.assertEquals("", stupid.getStr());
		Assert.assertNull(stupid.date);
	}

	@Test
	public void testBlueprintWithAttributes() throws MalformedURLException {
		final URL url = new URL("http://example.com/a");
		final WithPublicAttributes wpa = Blueprint.def().withPublicAttributes(true).with(URL.class, url).construct(WithPublicAttributes.class);
		Assert.assertEquals(0, wpa.a);
		Assert.assertEquals("", wpa.str);
		Assert.assertEquals("/a", wpa.url.getPath());
		Assert.assertEquals(Long.valueOf(0), wpa.getL());
	}

	@Test
	public void testBlueprintWithoutAttributes() {
		final WithPublicAttributes wpa = Blueprint.construct(WithPublicAttributes.class);
		Assert.assertEquals(0, wpa.a);
		Assert.assertNull(wpa.str);
		Assert.assertNull(wpa.url);
		Assert.assertEquals(Long.valueOf(0), wpa.getL());
	}
}
