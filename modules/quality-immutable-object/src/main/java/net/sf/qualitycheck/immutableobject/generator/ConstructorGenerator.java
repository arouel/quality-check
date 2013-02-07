package net.sf.qualitycheck.immutableobject.generator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.concurrent.NotThreadSafe;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Attribute;
import net.sf.qualitycheck.immutableobject.domain.Constructor;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.Import;
import net.sf.qualitycheck.immutableobject.domain.Imports;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedMap;
import com.google.common.collect.ImmutableSortedSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

@NotThreadSafe
public final class ConstructorGenerator implements CodeGenerator<Constructor> {

	private enum CollectionType {

		COLLECTION(Type.of(Collection.class), Type.of(ImmutableCollection.class)),

		LIST(Type.of(List.class), Type.of(ImmutableList.class)),

		MAP(Type.of(Map.class), Type.of(ImmutableMap.class)),

		SET(Type.of(Set.class), Type.of(ImmutableSet.class)),

		SORTEDMAP(Type.of(SortedMap.class), Type.of(ImmutableSortedMap.class)),

		SORTEDSET(Type.of(SortedSet.class), Type.of(ImmutableSortedSet.class));

		@Nullable
		public static Type convert(@Nonnull final Type type) {
			Check.notNull(type, "type");
			Type ret = null;
			for (final CollectionType t : values()) {
				if (equal(t._originalType, type)) {
					ret = t._alternativeType;
					break;
				}
			}
			return ret;
		}

		private static boolean equal(final Type a, final Type b) {
			return Objects.equal(a.getPackage(), b.getPackage()) && Objects.equal(a.getTypeName(), b.getTypeName());

		}

		private final Type _alternativeType;
		private final Type _originalType;

		CollectionType(@Nonnull final Type originalType, @Nonnull final Type alternativeType) {
			Check.notNull(originalType, "originalType");
			Check.notNull(alternativeType, "alternativeType");
			_originalType = originalType;
			_alternativeType = alternativeType;
		}

	}

	@Nonnull
	private final String _clazzName;

	@Nonnull
	private final List<Field> _fields;

	private static final Import CHECK_IMPORT = Import.of(Check.class);

	private final Set<Import> _imports = Sets.newHashSet();

	public ConstructorGenerator(@Nonnull final String clazzName, @Nonnull final List<Field> fields) {
		_clazzName = Check.notNull(clazzName, "clazzName");
		_fields = Check.notNull(fields, "fields");
	}

	public Constructor createConstructor() {
		final List<Attribute> attributes = Lists.newArrayListWithCapacity(_fields.size());
		for (final Field field : _fields) {
			if (field.getStatic() != Static.STATIC) {
				attributes.add(new Attribute(field.getName(), field.getType(), Final.FINAL, field.getAnnotations()));
			}
		}
		final Visibility visibility = Visibility.PUBLIC;
		final List<Annotation> annotations = ImmutableList.of();
		return new Constructor(_clazzName, generateContent(attributes), attributes, visibility, annotations);
	}

	@Override
	public Imports dependsOn() {
		return Imports.of(_imports);
	}

	@Override
	public String generate() {
		return "";
	}

	private String generateContent(@Nonnull final List<Attribute> attributes) {
		final StringBuilder b = new StringBuilder();
		for (final Attribute attribute : attributes) {
			if (attribute.getAnnotations().contains(Annotation.NONNEGATIVE)) {
				_imports.add(CHECK_IMPORT);
				b.append("Check.stateIsTrue(");
				b.append(attribute.getName());
				b.append(" >= 0, \"Argument '");
				b.append(attribute.getName());
				b.append("' must not be negative.\");\n");
			}
			b.append("this.");
			b.append(attribute.getName());
			b.append(" = ");
			final Type alternative = CollectionType.convert(attribute.getType());
			if (alternative != null) {
				_imports.add(Import.of(alternative));
				b.append(alternative.getTypeName());
				b.append(".copyOf(");
			}
			if (attribute.getAnnotations().contains(Annotation.NONNULL)) {
				_imports.add(CHECK_IMPORT);
				b.append("Check.notNull(");
				b.append(attribute.getName());
				b.append(", \"");
				b.append(attribute.getName());
				b.append("\")");
			} else {
				b.append(attribute.getName());
			}
			if (alternative != null) {
				b.append(")");
			}
			b.append(";");
		}
		return b.toString();
	}

}
