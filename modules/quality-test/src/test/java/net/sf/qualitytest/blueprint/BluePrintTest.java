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

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import net.sf.qualitytest.CoverageForPrivateConstructor;
import net.sf.qualitytest.StaticCheck;
import net.sf.qualitytest.blueprint.configuration.DefaultBluePrintConfiguration;
import net.sf.qualitytest.blueprint.configuration.RandomBluePrintConfiguration;
import net.sf.qualitytest.exception.BluePrintException;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class BluePrintTest {

	public static final class Immutable {

		private final int value;
		private final String name;
		private final Date date;
		private final List<String> list;

		public Immutable(final int value, final String name, final Date date, final List<String> list) {
			this.value = value;
			this.name = name;
			this.date = date;
			this.list = list;
		}

		public Date getDate() {
			return date;
		}

		public List<String> getList() {
			return list;
		}

		public String getName() {
			return name;
		}

		public int getValue() {
			return value;
		}

		@Override
		public String toString() {
			return "Immutable [date=" + date + ", list=" + list + ", name=" + name + ", value=" + value + "]";
		}

	}

	public static abstract class MyAbstract {
		public abstract String getValue();
	}

	public static interface MyInterface {
		int getInt();

		String getString();

	}

	public static final class TestBean {
		public enum SomeEnum {
			A, B, C
		}

		private TestInnerBean innerBean;
		private String str;
		private Long number;
		private Integer integer;
		private Character character;
		private Short shortObjNumber;
		private Byte byteObjNumber;
		private Float floatObjNumber;
		private Double doubleObjNumber;
		private Boolean booleanObj;

		private Immutable immutable;

		private int[] basicArray;

		private String[] objectArray;

		private int intNumber;
		private long longNumber;

		private short shortNumber;

		private byte byteNumber;

		private char charValue;

		private boolean boolValue;

		private float floatValue;

		private double doubleValue;

		private SomeEnum enumValue;

		public int[] getBasicArray() {
			return basicArray;
		}

		public Boolean getBooleanObj() {
			return booleanObj;
		}

		public byte getByteNumber() {
			return byteNumber;
		}

		public Byte getByteObjNumber() {
			return byteObjNumber;
		}

		public Character getCharacter() {
			return character;
		};

		public char getCharValue() {
			return charValue;
		}

		public Double getDoubleObjNumber() {
			return doubleObjNumber;
		}

		public double getDoubleValue() {
			return doubleValue;
		}

		public SomeEnum getEnumValue() {
			return enumValue;
		}

		public Float getFloatObjNumber() {
			return floatObjNumber;
		}

		public float getFloatValue() {
			return floatValue;
		}

		public Immutable getImmutable() {
			return immutable;
		}

		public TestInnerBean getInnerBean() {
			return innerBean;
		}

		public Integer getInteger() {
			return integer;
		}

		public int getIntNumber() {
			return intNumber;
		}

		public long getLongNumber() {
			return longNumber;
		}

		public Long getNumber() {
			return number;
		}

		public String[] getObjectArray() {
			return objectArray;
		}

		public short getShortNumber() {
			return shortNumber;
		}

		public Short getShortObjNumber() {
			return shortObjNumber;
		}

		public String getStr() {
			return str;
		}

		public boolean isBoolValue() {
			return boolValue;
		}

		public void setBasicArray(final int[] basicArray) {
			this.basicArray = basicArray;
		}

		public void setBooleanObj(final Boolean booleanObj) {
			this.booleanObj = booleanObj;
		}

		public void setBoolValue(final boolean boolValue) {
			this.boolValue = boolValue;
		}

		public void setByteNumber(final byte byteNumber) {
			this.byteNumber = byteNumber;
		}

		public void setByteObjNumber(final Byte byteObjNumber) {
			this.byteObjNumber = byteObjNumber;
		}

		public void setCharacter(final Character character) {
			this.character = character;
		}

		public void setCharValue(final char charValue) {
			this.charValue = charValue;
		}

		public void setDoubleObjNumber(final Double doubleObjNumber) {
			this.doubleObjNumber = doubleObjNumber;
		}

		public void setDoubleValue(final double doubleValue) {
			this.doubleValue = doubleValue;
		}

		public void setEnumValue(final SomeEnum enumValue) {
			this.enumValue = enumValue;
		}

		public void setFloatObjNumber(final Float floatObjNumber) {
			this.floatObjNumber = floatObjNumber;
		}

		public void setFloatValue(final float floatValue) {
			this.floatValue = floatValue;
		}

		public void setImmutable(final Immutable immutable) {
			this.immutable = immutable;
		}

		public void setInnerBean(final TestInnerBean innerBean) {
			this.innerBean = innerBean;
		}

		public void setInteger(final Integer integer) {
			this.integer = integer;
		}

		public void setIntNumber(final int intNumber) {
			this.intNumber = intNumber;
		}

		public void setLongNumber(final long longNumber) {
			this.longNumber = longNumber;
		}

		public void setNumber(final Long number) {
			this.number = number;
		}

		public void setObjectArray(final String[] objectArray) {
			this.objectArray = objectArray;
		}

		public void setShortNumber(final short shortNumber) {
			this.shortNumber = shortNumber;
		}

		public void setShortObjNumber(final Short shortObjNumber) {
			this.shortObjNumber = shortObjNumber;
		}

		public void setStr(final String str) {
			this.str = str;
		}

	}

	public static final class TestInnerBean {
		private String name;

		public String getName() {
			return name;
		}

		public void setName(final String name) {
			this.name = name;
		}

	}

	public static final Pattern UUID_PATTERN = Pattern.compile("[a-z0-9]{8}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{4}-[a-z0-9]{12}");

	@Test
	public void coverPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(BluePrint.class);
	}

	@Test(expected = BluePrintException.class)
	public void testAbstract() {
		BluePrint.object(MyAbstract.class);
	}

	@Test
	public void testArray() {
		final int[] array = (int[]) BluePrint.array(int[].class);
		Assert.assertTrue(array.length > 0);
		for (final int i : array) {
			Assert.assertEquals(0, i);
		}
	}

	@Test
	public void testBluePrintBean() {
		final TestBean bean = BluePrint.object(TestBean.class, new DefaultBluePrintConfiguration());
		Assert.assertNotNull(bean);
		Assert.assertNotNull(bean.getStr());
		Assert.assertNotNull(bean.getNumber());
		Assert.assertNotNull(bean.getInteger());
		Assert.assertNotNull(bean.getCharacter());
		Assert.assertNotNull(bean.getShortObjNumber());
		Assert.assertNotNull(bean.getByteObjNumber());
		Assert.assertNotNull(bean.getFloatObjNumber());
		Assert.assertNotNull(bean.getDoubleObjNumber());
		Assert.assertNotNull(bean.getBooleanObj());
		Assert.assertNotNull(bean.getBasicArray());
		Assert.assertNotNull(bean.getObjectArray());
		Assert.assertNotNull(bean.getIntNumber());
		Assert.assertNotNull(bean.getLongNumber());
		Assert.assertNotNull(bean.getShortNumber());
		Assert.assertNotNull(bean.getByteNumber());
		Assert.assertNotNull(bean.getCharValue());
		Assert.assertNotNull(bean.isBoolValue());
		Assert.assertNotNull(bean.getFloatValue());
		Assert.assertNotNull(bean.getDoubleValue());
		Assert.assertNotNull(bean.getEnumValue());
		Assert.assertNotEquals(0, bean.getObjectArray().length);
		Assert.assertNotEquals(0, bean.getBasicArray().length);
		Assert.assertNotNull(bean.getInnerBean());
		Assert.assertNotNull(bean.getInnerBean().getName());
		Assert.assertEquals("", bean.getInnerBean().getName());
		Assert.assertNotNull(bean.getImmutable());
		Assert.assertNotNull(bean.getImmutable().getName());
		Assert.assertNotNull(bean.getImmutable().getDate());
		Assert.assertNotNull(bean.getImmutable().getList());
	}

	public void testBluePrintObject_String() {
		final Object o = BluePrint.object(String.class);
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof String);
	}

	@Test
	public void testClassIsFinal() {
		StaticCheck.classIsFinal(BluePrint.class);
	}

	@Test
	public void testEnumeration() {
		Assert.assertEquals(TestBean.SomeEnum.A, BluePrint.enumeration(TestBean.SomeEnum.class));
	}

	@Test
	public void testGiveMeCoverageForMyPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(BluePrint.class);
	}

	@Test
	public void testImmutable() {
		final Immutable immutable = BluePrint.object(Immutable.class);
		Assert.assertNotNull(immutable);
		Assert.assertNotNull(immutable.getName());
		Assert.assertEquals("", immutable.getName());
		Assert.assertEquals(0, immutable.getValue());
		Assert.assertNotNull(immutable.getDate());
		Assert.assertNotNull(immutable.getList());
	}

	@Ignore
	@Test
	public void testInterfaceAndLastStrategyWins() {
		// TODO: Make sure last strategy wins
		final MyInterface iface = BluePrint.random().with(int.class, 12).with(int.class, 0).object(MyInterface.class);
		Assert.assertEquals(0, iface.getInt());
		Assert.assertTrue(UUID_PATTERN.matcher(iface.getString()).matches());
	}

	@Test
	public void testMakeSureClassIsFinalAndNotAccessible() {
		StaticCheck.classIsFinal(BluePrint.class);
		StaticCheck.noPublicDefaultConstructor(BluePrint.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMap() {
		final Map<String, Immutable> map = (Map<String, Immutable>) BluePrint.object(Map.class);
		Assert.assertNotNull(map);
		map.put("any", BluePrint.object(Immutable.class, new RandomBluePrintConfiguration()));

		final Immutable immutable = map.get("any");
		Assert.assertNotNull(immutable);
		Assert.assertNotNull(immutable.getDate());
		Assert.assertTrue(UUID_PATTERN.matcher(immutable.getName()).matches());
	}

	@Test
	public void testRandom() {
		final BluePrintConfiguration config = new RandomBluePrintConfiguration();
		final int COUNT = 5;
		final Immutable[] immutables = new Immutable[COUNT];
		for (int i = 0; i < COUNT; i++) {
			immutables[i] = BluePrint.object(Immutable.class, config);

			if (i > 0) {
				Assert.assertNotEquals(immutables[i].getName(), immutables[i - 1].getName());
				Assert.assertNotEquals(immutables[i].getValue(), immutables[i - 1].getValue());
			}

			System.out.println(immutables[i]);
		}
	}

	@Test
	public void testStringArray() {
		final String[] array = (String[]) BluePrint.array(String[].class, new RandomBluePrintConfiguration());
		Assert.assertTrue(array.length > 0);
		for (final String i : array) {
			Assert.assertTrue(UUID_PATTERN.matcher(i).matches());
		}
	}

	@Test
	public void testStringIsUuid() {
		Assert.assertTrue(UUID_PATTERN.matcher(BluePrint.string()).matches());
	}

	@Test
	public void testStringNotEmpty() {
		Assert.assertNotNull(BluePrint.string());
		Assert.assertNotEquals(0, BluePrint.string());
	}

	@Test
	public void testWithConfig() {
		final BluePrintConfiguration config = new DefaultBluePrintConfiguration().with("number", 42l);
		final TestBean bean = BluePrint.object(TestBean.class, config);
		Assert.assertEquals(bean.getNumber(), Long.valueOf(42l));
	}

}
