package net.sf.qualitycheck;

import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.math.BigInteger;

import net.sf.qualitycheck.exception.IllegalNumberArgumentException;
import net.sf.qualitycheck.exception.IllegalNumberRangeException;

import org.junit.Assert;
import org.junit.Test;

public class NumberInRangeTest {

	private final class FakeNumber extends Number  {

		private static final long serialVersionUID = 6755851124660688600L;

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
	public void testInRange_Integer_Fail() {
		NumberInRange.isInRange(new FakeNumber(), BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(Integer.MAX_VALUE));
	}
	
	@Test(expected=IllegalNumberArgumentException.class) 
	public void testInRange_Decimal_Fail() {
		NumberInRange.isInRange(new FakeNumber(), new BigDecimal(Integer.MIN_VALUE), new BigDecimal(Integer.MAX_VALUE));
	}
	
	@Test
	public void testCheckByte_Ok() {
		byte b = NumberInRange.checkByte(Byte.valueOf((byte) 4));
		Assert.assertEquals((byte)4, b);
	}
	
	@Test(expected=IllegalNumberRangeException.class)
	public void testCheckByte_Fail() {
		NumberInRange.checkByte(Long.valueOf(128l));
	}
	
	@Test
	public void testCheckShort_Ok() {
		short s = NumberInRange.checkShort(Short.valueOf((short) 4));
		Assert.assertEquals((short)4, s);
	}
	
	@Test(expected=IllegalNumberRangeException.class)
	public void testCheckShort_Fail() {
		NumberInRange.checkShort(Long.valueOf(-2147483650l));
	}
	
	@Test
	public void testCheckInt_Ok() {
		int i = NumberInRange.checkInteger(Integer.valueOf((int) 4));
		Assert.assertEquals(4, i);
	}
	
	@Test(expected=IllegalNumberRangeException.class)
	public void testCheckInt_Fail() {
		NumberInRange.checkInteger(Long.valueOf(2147483650l));
	}
	
	@Test
	public void testCheckLong_Ok() {
		long l = NumberInRange.checkLong(Long.valueOf((int) 4));
		Assert.assertEquals(4l, l);
	}
	
	@Test(expected=IllegalNumberRangeException.class)
	public void testCheckLong_Fail() {
		NumberInRange.checkLong(new BigInteger("9999999999999999999999999"));
	}
	
	@Test
	public void testCheckFloat_Ok() {
		float l = NumberInRange.checkFloat(Float.valueOf(4.1f));
		Assert.assertEquals(4.1f, l, 0.0);
	}
	
	@Test(expected=IllegalNumberRangeException.class)
	public void testCheckFloat_Fail() {
		NumberInRange.checkFloat(Double.MAX_VALUE);
	}
	
	@Test
	public void testCheckDouble_Ok() {
		double l = NumberInRange.checkDouble(Double.valueOf(4.1d));
		Assert.assertEquals(4.1d, l, 0.0);
	}
	
	@Test(expected=IllegalNumberRangeException.class)
	public void testCheckDoublet_Fail() {
		NumberInRange.checkDouble(new BigDecimal("1E309"));
	}
	
	@Test
	public void testIsInRange_Byte_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Byte.valueOf((byte)4), new BigDecimal(Byte.MIN_VALUE), new BigDecimal(Byte.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Byte_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Byte.valueOf((byte)4), BigInteger.valueOf(Byte.MIN_VALUE), BigInteger.valueOf(Byte.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Short_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Short.valueOf((short)4), new BigDecimal(Short.MIN_VALUE), new BigDecimal(Short.MAX_VALUE)));
		
	}
	
	@Test
	public void testIsInRange_Short_Decimal_Fail() {
		Assert.assertEquals(false, 
				NumberInRange.isInRange(Short.valueOf((short)-400), new BigDecimal(Byte.MIN_VALUE), new BigDecimal(Byte.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Short_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Short.valueOf((short)4), BigInteger.valueOf(Short.MIN_VALUE), BigInteger.valueOf(Short.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Integer_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Integer.valueOf(4), new BigDecimal(Integer.MIN_VALUE), new BigDecimal(Integer.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Integer_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Integer.valueOf(4), BigInteger.valueOf(Integer.MIN_VALUE), BigInteger.valueOf(Integer.MAX_VALUE)));
		
	}
	
	@Test
	public void testIsInRange_Long_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Long.valueOf(4l), new BigDecimal(Long.MIN_VALUE), new BigDecimal(Long.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Long_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Long.valueOf(4l), BigInteger.valueOf(Long.MIN_VALUE), BigInteger.valueOf(Long.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Float_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Float.valueOf(4f), new BigDecimal(Float.MIN_VALUE), new BigDecimal(Float.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Float_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Float.valueOf(4f), BigInteger.valueOf((long)Float.MIN_VALUE), BigInteger.valueOf((long)Float.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Double_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Double.valueOf(4d), new BigDecimal(Double.MIN_VALUE), new BigDecimal(Double.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_Double_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(Double.valueOf(4d), BigInteger.valueOf((long)Double.MIN_VALUE), BigInteger.valueOf((long)Double.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_BigDecimal_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(new BigDecimal("4"), new BigDecimal(Double.MIN_VALUE), new BigDecimal(Double.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_BigDecimal_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(new BigDecimal("4"), BigInteger.valueOf((long)Double.MIN_VALUE), BigInteger.valueOf((long)Double.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_BigInteger_Decimal() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(new BigInteger("4"), new BigDecimal(Double.MIN_VALUE), new BigDecimal(Double.MAX_VALUE)));
		
	}

	@Test
	public void testIsInRange_BigInteger_Integer() {
		Assert.assertEquals(true, 
				NumberInRange.isInRange(new BigInteger("4"), BigInteger.valueOf((long)Double.MIN_VALUE), BigInteger.valueOf((long)Double.MAX_VALUE)));
		
	}
	

	@Test
	public void giveMeCoverageForMyPrivateConstructor() throws Exception {
		// reduces only some noise in coverage report
		final Constructor<NumberInRange> constructor = NumberInRange.class.getDeclaredConstructor();
		constructor.setAccessible(true);
		constructor.newInstance();
	}

}
