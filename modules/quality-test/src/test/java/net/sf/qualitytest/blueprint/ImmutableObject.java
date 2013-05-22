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
