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

	SORTEDMAP(SortedMap.class, "new LinkedHashMap%s(%s)", "Maps.newLinkedHashMap(%s)",
			"Collections.unmodifiableSortedMap(new LinkedHashMap%s(%s))", "ImmutableSortedMap.copyOf(%s)"),

	SORTEDSET(SortedSet.class, "new LinkedHashSet%s(%s)", "Sets.newLinkedHashSet(%)",
			"Collections.unmodifiableSortedSet(new LinkedHashSet%s(%s))", "ImmutableSortedSet.copyOf(%s)");

	@Nullable
	public static CollectionVariant evaluate(@Nonnull final Package pkg, @Nonnull final String typeName) {
		Check.notNull(pkg, "pkg");
		Check.notNull(typeName, "typeName");
		CollectionVariant variant = null;
		for (final CollectionVariant v : values()) {
			if (v._type.getPackage().getName().equals(pkg.getName()) && v._type.getSimpleName().equals(typeName)) {
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

	private final String _defaultCopy;
	private final String _defaultImmutable;
	private final String _guavaCopy;
	private final String _guavaImmutable;
	private final Class<?> _type;

	CollectionVariant(@Nonnull final Class<?> type, @Nonnull final String defaultCopy, @Nonnull final String guavaCopy,
			@Nonnull final String defaultImmutable, @Nonnull final String guavaImmutable) {
		_type = Check.notNull(type, "type");
		_defaultCopy = Check.notNull(defaultCopy, "defaultCopy");
		_guavaCopy = Check.notNull(guavaCopy, "guavaCopy");
		_defaultImmutable = Check.notNull(defaultImmutable, "defaultImmutable");
		_guavaImmutable = Check.notNull(guavaImmutable, "guavaImmutable");
	}

	public String getDefaultCopy() {
		return _defaultCopy;
	}

	public String getDefaultImmutable() {
		return _defaultImmutable;
	}

	public String getGuavaCopy() {
		return _guavaCopy;
	}

	public String getGuavaImmutable() {
		return _guavaImmutable;
	}

}
