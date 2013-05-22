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
