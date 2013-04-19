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

import java.util.Map;
import java.util.regex.Pattern;

import net.sf.qualitytest.CoverageForPrivateConstructor;
import net.sf.qualitytest.StaticCheck;
import net.sf.qualitytest.blueprint.configuration.DefaultBlueprintConfiguration;
import net.sf.qualitytest.blueprint.configuration.RandomBlueprintConfiguration;
import net.sf.qualitytest.exception.BlueprintException;

import org.junit.Assert;
import org.junit.Test;

public class BlueprintTest {

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

		private ImmutableObject immutable;

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

		public ImmutableObject getImmutableObject() {
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

		public void setImmutableObject(final ImmutableObject immutable) {
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
		CoverageForPrivateConstructor.giveMeCoverage(Blueprint.class);
	}

	@Test(expected = BlueprintException.class)
	public void testAbstract() {
		Blueprint.construct(MyAbstract.class);
	}

	@Test
	public void testArray() {
		final int[] array = (int[]) Blueprint.construct(int[].class);
		Assert.assertTrue(array.length > 0);
		for (final int i : array) {
			Assert.assertEquals(0, i);
		}
	}

	@Test
	public void testBlueprintBean() {
		final TestBean bean = Blueprint.construct(TestBean.class, new DefaultBlueprintConfiguration(), new BlueprintSession());
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
		Assert.assertNotNull(bean.getImmutableObject());
		Assert.assertNotNull(bean.getImmutableObject().getName());
		Assert.assertNotNull(bean.getImmutableObject().getDate());
		Assert.assertNotNull(bean.getImmutableObject().getList());
	}

	public void testBlueprintObject_String() {
		final Object o = Blueprint.construct(String.class);
		Assert.assertNotNull(o);
		Assert.assertTrue(o instanceof String);
	}

	@Test
	public void testClassIsFinal() {
		StaticCheck.classIsFinal(Blueprint.class);
	}

	@Test
	public void testEnumeration() {
		Assert.assertEquals(TestBean.SomeEnum.A, Blueprint.construct(TestBean.SomeEnum.class));
	}

	@Test
	public void testGiveMeCoverageForMyPrivateConstructor() {
		CoverageForPrivateConstructor.giveMeCoverage(Blueprint.class);
	}

	@Test
	public void testImmutableObject() {
		final ImmutableObject immutable = Blueprint.construct(ImmutableObject.class);
		Assert.assertNotNull(immutable);
		Assert.assertNotNull(immutable.getName());
		Assert.assertEquals("", immutable.getName());
		Assert.assertEquals(0, immutable.getValue());
		Assert.assertNotNull(immutable.getDate());
		Assert.assertNotNull(immutable.getList());
	}

	@Test
	public void testInterfaceAndLastStrategyWins() {
		final MyInterface iface = Blueprint.random().with(int.class, 12).with(int.class, -1).construct(MyInterface.class);
		Assert.assertEquals(-1, iface.getInt());
		Assert.assertTrue(UUID_PATTERN.matcher(iface.getString()).matches());
	}

	@Test
	public void testMakeSureClassIsFinalAndNotAccessible() {
		StaticCheck.classIsFinal(Blueprint.class);
		StaticCheck.noPublicDefaultConstructor(Blueprint.class);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMap() {
		final Map<String, ImmutableObject> map = (Map<String, ImmutableObject>) Blueprint.construct(Map.class);
		Assert.assertNotNull(map);
		map.put("any", Blueprint.construct(ImmutableObject.class, new RandomBlueprintConfiguration(), new BlueprintSession()));

		final ImmutableObject immutable = map.get("any");
		Assert.assertNotNull(immutable);
		Assert.assertNotNull(immutable.getDate());
		Assert.assertTrue(UUID_PATTERN.matcher(immutable.getName()).matches());
	}

	@Test
	public void testRandom() {
		final BlueprintConfiguration config = new RandomBlueprintConfiguration();
		final int COUNT = 5;
		final ImmutableObject[] immutables = new ImmutableObject[COUNT];
		for (int i = 0; i < COUNT; i++) {
			immutables[i] = Blueprint.construct(ImmutableObject.class, config, new BlueprintSession());

			if (i > 0) {
				Assert.assertNotEquals(immutables[i].getName(), immutables[i - 1].getName());
				Assert.assertNotEquals(immutables[i].getValue(), immutables[i - 1].getValue());
			}

			System.out.println(immutables[i]);
		}
	}

	@Test
	public void testStringArray() {
		final String[] array = (String[]) Blueprint.construct(String[].class, new RandomBlueprintConfiguration(), new BlueprintSession());
		Assert.assertTrue(array.length > 0);
		for (final String i : array) {
			Assert.assertTrue(UUID_PATTERN.matcher(i).matches());
		}
	}

	@Test
	public void testWithConfig() {
		final BlueprintConfiguration config = new DefaultBlueprintConfiguration().with("number", 42l);
		final TestBean bean = Blueprint.construct(TestBean.class, config, new BlueprintSession());
		Assert.assertEquals(bean.getNumber(), Long.valueOf(42l));
	}

}
