package net.sf.qualitytest.blueprint.strategy.creation;

import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.strategy.matching.TypeMatchingStrategy;

import org.junit.Assert;
import org.junit.Test;

public class IncrementValueCreationStrategyTest {

	public static class SimpleDate {
		private int day;
		private int month;
		private int year;

		public int getDay() {
			return day;
		}

		public int getMonth() {
			return month;
		}

		public int getYear() {
			return year;
		}

		public void setDay(final int day) {
			this.day = day;
		}

		public void setMonth(final int month) {
			this.month = month;
		}

		public void setYear(final int year) {
			this.year = year;
		}

	}

	@Test
	public void testBlueprint() {
		final SimpleDate date = Blueprint.def()
				.with(new TypeMatchingStrategy(int.class), new IncrementValueCreationStrategy<Number>(Integer.valueOf(0)))
				.construct(SimpleDate.class);

		// Test that day, month and year got the values 1, 2, 3 assigned (added = 6)
		Assert.assertEquals(6, date.getDay() + date.getMonth() + date.getYear());
		Assert.assertNotEquals(date.getYear(), date.getMonth());
		Assert.assertNotEquals(date.getDay(), date.getMonth());
	}

	@Test
	public void testIncrement_int() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(0));
		Assert.assertEquals(1, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(2, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(3, (int) s.createValue(int.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testIncrement_integer() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(0));
		Assert.assertEquals(1, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(2, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(3, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testIncrement_long_offset() {
		final IncrementValueCreationStrategy<Long> s = new IncrementValueCreationStrategy<Long>(Long.valueOf(0), 2L);
		Assert.assertEquals(2L, (long) s.createValue(Long.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(4L, (long) s.createValue(Long.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(6L, (long) s.createValue(Long.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testIncrement_negative() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(-100));
		Assert.assertEquals(-99, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(-98, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testIncrement_negative_offset() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(0),
				Integer.valueOf(-1));
		Assert.assertEquals(-1, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
		Assert.assertEquals(-2, (int) s.createValue(Integer.class, Blueprint.def(), new BlueprintSession()));
	}

	@Test
	public void testIncrement_overflow_byte() {
		final IncrementValueCreationStrategy<Byte> s = new IncrementValueCreationStrategy<Byte>(Byte.valueOf((byte) 0));
		for (int i = 1; i <= Byte.MAX_VALUE; i++) {
			Assert.assertEquals(Byte.valueOf((byte) i), s.createValue(byte.class, Blueprint.def(), new BlueprintSession()));
		}

		Assert.assertEquals(Byte.valueOf((byte) 1), s.createValue(Byte.class, Blueprint.def(), new BlueprintSession()));
	}
}
