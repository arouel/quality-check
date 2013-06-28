package net.sf.qualitytest.blueprint.strategy.creation;

import net.sf.qualitytest.blueprint.Blueprint;
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

		Assert.assertEquals(1, date.getDay());
		Assert.assertEquals(2, date.getMonth());
		Assert.assertEquals(3, date.getYear());
	}

	@Test
	public void testIncrement_int() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(0));
		Assert.assertEquals(1, (int) s.createValue(int.class));
		Assert.assertEquals(2, (int) s.createValue(int.class));
		Assert.assertEquals(3, (int) s.createValue(int.class));
	}

	@Test
	public void testIncrement_integer() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(0));
		Assert.assertEquals(1, (int) s.createValue(Integer.class));
		Assert.assertEquals(2, (int) s.createValue(Integer.class));
		Assert.assertEquals(3, (int) s.createValue(Integer.class));
	}

	@Test
	public void testIncrement_long_offset() {
		final IncrementValueCreationStrategy<Long> s = new IncrementValueCreationStrategy<Long>(Long.valueOf(0), 2L);
		Assert.assertEquals(2L, (long) s.createValue(Long.class));
		Assert.assertEquals(4L, (long) s.createValue(Long.class));
		Assert.assertEquals(6L, (long) s.createValue(Long.class));
	}

	@Test
	public void testIncrement_negative() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(-100));
		Assert.assertEquals(-99, (int) s.createValue(Integer.class));
		Assert.assertEquals(-98, (int) s.createValue(Integer.class));
	}

	@Test
	public void testIncrement_negative_offset() {
		final IncrementValueCreationStrategy<Integer> s = new IncrementValueCreationStrategy<Integer>(Integer.valueOf(0),
				Integer.valueOf(-1));
		Assert.assertEquals(-1, (int) s.createValue(Integer.class));
		Assert.assertEquals(-2, (int) s.createValue(Integer.class));
	}

	@Test
	public void testIncrement_overflow_byte() {
		final IncrementValueCreationStrategy<Byte> s = new IncrementValueCreationStrategy<Byte>(Byte.valueOf((byte) 0));
		for (int i = 1; i <= Byte.MAX_VALUE; i++) {
			Assert.assertEquals(Byte.valueOf((byte) i), s.createValue(byte.class));
		}

		Assert.assertEquals(Byte.valueOf((byte) 1), s.createValue(Byte.class));
	}
}
