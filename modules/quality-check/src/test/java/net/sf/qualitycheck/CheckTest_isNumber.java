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

import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumberRangeException;

import org.junit.Assert;
import org.junit.Test;

public class CheckTest_isNumber {

	private final class FakeNumber extends Number  {

		private static final long serialVersionUID = -828838716306473102L;

		@Override
		public double doubleValue() {
			return 0;
		}

		@Override
		public float floatValue() {
			return 0;
		}

		@Override
		public int intValue() {
			return 0;
		}

		@Override
		public long longValue() {
			return 0;
		}
		
	};

	@Test(expected=IllegalNumberArgumentException.class)
	public void testUnknownClass_Fail() {
		Check.isNumber("A", FakeNumber.class);
	}
	
	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_decimalNumber_fail() {
		Check.isNumber("1.23");
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_fail() {
		Check.isNumber("Hallo Welt!");
	}

	@Test
	public void isNumber_negativeNumber_okay() {
		Assert.assertEquals(-123, Check.isNumber("-123"));
	}

	@Test
	public void isNumber_okay() {
		Assert.assertEquals(123, Check.isNumber("123"));
	}

	@Test
	public void isNumber_startsWithZero_okay() {
		Assert.assertEquals(123, Check.isNumber("0123"));
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_withArgument_decimalNumber_fail() {
		Check.isNumber("1.23", "numeric");
	}

	@Test(expected = IllegalNumberArgumentException.class)
	public void isNumber_withArgument_fail() {
		Check.isNumber("Hallo Welt", "numeric");
	}

	@Test
	public void isNumber_withArgument_negativeNumber_okay() {
		Assert.assertEquals(-123, Check.isNumber("-123", "numeric"));
	}

	@Test
	public void isNumber_withArgument_okay() {
		Assert.assertEquals(123, Check.isNumber("123", "numeric"));
	}

	@Test
	public void isNumber_withArgument_startsWithZero_okay() {
		Assert.assertEquals(123, Check.isNumber("0123", "numeric"));
	}

	@Test(expected = IllegalNumberRangeException.class)
	public void isNumeric_longNumericString_fail() {
		Check.isNumber("1230000000000000000000000000");
	}

	@Test
	public void isNumber_Byte_Ok() {
		byte b = Check.isNumber("12", Byte.class).byteValue();
		Assert.assertEquals((byte)12, b);
	}
	
	@Test
	public void isNumber_Double_Ok() {
		double d = Check.isNumber("12.1", Double.class).doubleValue();
		Assert.assertEquals(12.1d, d, 0.0);
	}
	
	@Test
	public void isNumber_Float_Ok() {
		float f = Check.isNumber("12.1", Float.class).floatValue();
		Assert.assertEquals(12.1f, f, 0.0);
	}
	
	@Test
	public void isNumber_Short_Ok() {
		short s = Check.isNumber("121", Short.class).shortValue();
		Assert.assertEquals((short)121, s);
	}
	
	@Test
	public void isNumber_Integer_Ok() {
		int i = Check.isNumber("42", Integer.class).intValue();
		Assert.assertEquals(42, i);
	}
	
	@Test
	public void isNumber_Long_Ok() {
		long l = Check.isNumber("-121", Long.class).longValue();
		Assert.assertEquals(-121L, l);
	}
	
	@Test
	public void isNumber_BigInteger_Ok() {
		final BigInteger bi = Check.isNumber("121000099999999999999999", BigInteger.class);
		Assert.assertEquals(new BigInteger("121000099999999999999999"), bi);
	}
	
	@Test
	public void isNumber_BigDecimal_Ok() {
		final BigDecimal bd = Check.isNumber("121000099999999999999999.90", BigDecimal.class);
		Assert.assertEquals(new BigDecimal("121000099999999999999999.90"), bd);
	}
	
	@Test(expected=IllegalNumberArgumentException.class)
	public void isNumber_BigDecimal_Fail() {
		Check.isNumber("Halllo121000099999999999999999.90", "fail", BigDecimal.class);
	}
}
