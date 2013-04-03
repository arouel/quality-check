package net.sf.qualitytest.blueprint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

final class ImmutableObject {

	private final int value;
	private final String name;
	private final Date date;
	private final List<String> list;

	public ImmutableObject(final int value, final String name, final Date date, final List<String> list) {
		this.value = value;
		this.name = name;
		this.date = (Date) date.clone();
		this.list = new ArrayList<String>(list);
	}

	public Date getDate() {
		return (Date) date.clone();
	}

	public List<String> getList() {
		return new ArrayList<String>(list);
	}

	public String getName() {
		return name;
	}

	public int getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "ImmutableObject [date=" + date + ", list=" + list + ", name=" + name + ", value=" + value + "]";
	}

}
