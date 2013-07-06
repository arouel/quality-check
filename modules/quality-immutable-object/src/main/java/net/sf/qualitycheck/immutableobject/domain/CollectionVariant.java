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
package net.sf.qualitycheck.immutableobject.domain;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;

public enum CollectionVariant {

	COLLECTION(Collection.class, "new ArrayList%s(%s)", "Lists.newArrayList(%s)", "Collections.unmodifiableList(new ArrayList%s(%s))",
			"ImmutableCollection.copyOf(%s)"),

	ITERABLE(Iterable.class, "new ArrayList%s(%s)", "Lists.newArrayList(%s)", "Collections.unmodifiableList(new ArrayList%s(%s))",
			"ImmutableCollection.copyOf(%s)"),

	LIST(List.class, "new ArrayList%s(%s)", "Lists.newArrayList(%s)", "Collections.unmodifiableList(new ArrayList%s(%s))",
			"ImmutableList.copyOf(%s)"),

	MAP(Map.class, "new HashMap%s(%s)", "Maps.newHashMap(%s)", "Collections.unmodifiableMap(new HashMap%s(%s))", "ImmutableMap.copyOf(%s)"),

	SET(Set.class, "new HashSet%s(%s)", "Sets.newHashSet(%s)", "Collections.unmodifiableSet(new HashSet%s(%s))", "ImmutableSet.copyOf(%s)"),

	SORTEDMAP(SortedMap.class, "new TreeMap%s(%s)", "Maps.newTreeMap(%s)", "Collections.unmodifiableSortedMap(new TreeMap%s(%s))",
			"ImmutableSortedMap.copyOf(%s)"),

	SORTEDSET(SortedSet.class, "new TreeSet%s(%s)", "Sets.newTreeSet(%)", "Collections.unmodifiableSortedSet(new TreeSet%s(%s))",
			"ImmutableSortedSet.copyOf(%s)");

	@Nullable
	public static CollectionVariant evaluate(@Nonnull final Package pkg, @Nonnull final String typeName) {
		Check.notNull(pkg, "pkg");
		Check.notNull(typeName, "typeName");
		CollectionVariant variant = null;
		for (final CollectionVariant v : values()) {
			if (v.type.getPackage().getName().equals(pkg.getName()) && v.type.getSimpleName().equals(typeName)) {
				variant = v;
				break;
			}
		}
		return variant;
	}

	@Nullable
	public static CollectionVariant evaluate(@Nonnull final Type type) {
		Check.notNull(type, "type");
		return evaluate(type.getPackage(), type.getName());
	}

	private final String defaultCopy;
	private final String defaultImmutable;
	private final String guavaCopy;
	private final String guavaImmutable;
	private final Class<?> type;

	CollectionVariant(@Nonnull final Class<?> type, @Nonnull final String defaultCopy, @Nonnull final String guavaCopy,
			@Nonnull final String defaultImmutable, @Nonnull final String guavaImmutable) {
		this.type = Check.notNull(type, "type");
		this.defaultCopy = Check.notNull(defaultCopy, "defaultCopy");
		this.guavaCopy = Check.notNull(guavaCopy, "guavaCopy");
		this.defaultImmutable = Check.notNull(defaultImmutable, "defaultImmutable");
		this.guavaImmutable = Check.notNull(guavaImmutable, "guavaImmutable");
	}

	public String getDefaultCopy() {
		return defaultCopy;
	}

	public String getDefaultImmutable() {
		return defaultImmutable;
	}

	public String getGuavaCopy() {
		return guavaCopy;
	}

	public String getGuavaImmutable() {
		return guavaImmutable;
	}

}
