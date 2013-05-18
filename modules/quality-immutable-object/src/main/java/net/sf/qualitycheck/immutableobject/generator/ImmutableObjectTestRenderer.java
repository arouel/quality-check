/*******************************************************************************
 * Copyright 2013 André Rouél
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

import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Clazz;
import net.sf.qualitycheck.immutableobject.domain.Field;
import net.sf.qualitycheck.immutableobject.domain.ImmutableSettings;
import net.sf.qualitycheck.immutableobject.util.SourceCodeFormatter;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

@ThreadSafe
final class ImmutableObjectTestRenderer {

	public static String toString(@Nonnull final Clazz clazz, @Nonnull final ImmutableSettings settings) {
		Check.notNull(clazz, "clazz");
		Check.notNull(settings, "settings");

		final STGroup group = new STGroupFile("templates/test.stg");
		group.registerRenderer(Field.class, new FieldRenderer(settings));
		group.registerRenderer(String.class, new BasicFormatRenderer());
		final ST template = group.getInstanceOf("immutableTestCompilationUnit");
		template.add("settings", settings);

		return SourceCodeFormatter.format(template.render());
	}

	/**
	 * <strong>Attention:</strong> This class is not intended to create objects from it.
	 */
	private ImmutableObjectTestRenderer() {
		// This class is not intended to create objects from it.
	}

}
