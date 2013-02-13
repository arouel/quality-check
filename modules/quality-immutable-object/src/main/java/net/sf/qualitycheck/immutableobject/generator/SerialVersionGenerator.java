package net.sf.qualitycheck.immutableobject.generator;

import java.util.List;

import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;
import net.sf.qualitycheck.immutableobject.domain.Annotation;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.Final;
import net.sf.qualitycheck.immutableobject.domain.Static;
import net.sf.qualitycheck.immutableobject.domain.Type;
import net.sf.qualitycheck.immutableobject.domain.Visibility;

import com.google.common.collect.ImmutableList;

@Immutable
final class SerialVersionGenerator {

	private static final String FIELD_NAME = "serialVersionUID";

	private static final List<Annotation> WITHOUT_ANNOTATIONS = ImmutableList.of();

	private static final Field SERIAL_VERSION_UID = new Field(FIELD_NAME, new Type("long"), Visibility.PRIVATE, Final.FINAL, Static.STATIC,
			WITHOUT_ANNOTATIONS, "1L", AccessorPrefix.NOT_NEEDED);

	public static Field generate() {
		return SERIAL_VERSION_UID;
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private SerialVersionGenerator() {
		// This class is not intended to create objects from it.
	}

}
