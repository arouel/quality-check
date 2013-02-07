package net.sf.qualitycheck.immutableobject.util;

import java.util.Collection;

import javax.annotation.Nullable;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Static;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;

@Immutable
public final class NonStaticFilter {

	private static final Predicate<Field> NON_STATIC = new Predicate<Field>() {
		@Override
		public boolean apply(@Nullable final Field field) {
			return field != null && Static.STATIC != field.getStatic();
		}
	};

	public static Collection<Field> filter(final Collection<Field> fields) {
		return Collections2.filter(fields, NON_STATIC);
	}

}
