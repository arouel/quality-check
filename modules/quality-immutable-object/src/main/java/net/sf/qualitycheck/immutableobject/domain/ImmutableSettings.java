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

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.NotThreadSafe;

import net.sf.qualitycheck.Check;

import com.google.common.base.Objects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

@Immutable
public final class ImmutableSettings implements Settings {

	@NotThreadSafe
	public static final class Builder {

		private boolean builderCopyConstructor;

		private boolean builderFlatMutators;

		private boolean builderFluentMutators;

		private boolean builderImplementsInterface;

		@Nonnull
		private String builderName = "Builder";

		private boolean copyMethods;

		@Nonnull
		private String fieldPrefix = "";

		@Nonnull
		private List<Field> fields = Lists.newArrayList();

		private boolean guava;

		private boolean hashCodeAndEquals;

		private boolean hashCodePrecomputation;

		@Nonnull
		private String immutableName = "ImmutableUnknownObject";

		@Nonnull
		private List<Import> imports = Lists.newArrayList();

		@Nonnull
		private List<Interface> interfaces = Lists.newArrayList();

		private boolean jsr305Annotations;

		@Nonnull
		private Interface mainInterface = Interface.UNDEFINED;

		@Nonnull
		private Package packageDeclaration = Package.UNDEFINED;

		private boolean qualityCheck;

		private boolean replacement;

		private boolean serializable;

		private boolean toString;

		public Builder() {
			// default constructor
		}

		public Builder(@Nonnull final Settings settings) {
			Check.notNull(settings, "settings");
			builderName = Check.notNull(settings.getBuilderName(), "settings.getBuilderName()");
			fieldPrefix = Check.notNull(settings.getFieldPrefix(), "settings.getFieldPrefix()");
			fields = Lists.newArrayList(Check.notNull(settings.getFields(), "settings.getFields()"));
			immutableName = Check.notNull(settings.getImmutableName(), "settings.getImmutableName()");
			imports = Lists.newArrayList(Check.notNull(settings.getImports(), "settings.getImports()"));
			interfaces = Lists.newArrayList(Check.notNull(settings.getInterfaces(), "settings.getInterfaces()"));
			mainInterface = Check.notNull(settings.getMainInterface(), "settings.getMainInterface()");
			packageDeclaration = Check.notNull(settings.getPackageDeclaration(), "settings.getPackageDeclaration()");
			builderCopyConstructor = settings.hasBuilderCopyConstructor();
			builderFlatMutators = settings.hasBuilderFlatMutators();
			builderFluentMutators = settings.hasBuilderFluentMutators();
			builderImplementsInterface = settings.hasBuilderImplementsInterface();
			copyMethods = settings.hasCopyMethods();
			guava = settings.hasGuava();
			hashCodeAndEquals = settings.hasHashCodeAndEquals();
			hashCodePrecomputation = settings.hasHashCodePrecomputation();
			jsr305Annotations = settings.hasJsr305Annotations();
			qualityCheck = settings.hasQualityCheck();
			toString = settings.hasToString();
			replacement = settings.isReplacement();
			serializable = settings.isSerializable();
		}

		@Nonnull
		public ImmutableSettings build() {
			return new ImmutableSettings(builderName, fieldPrefix, fields, immutableName, imports, interfaces, mainInterface,
					packageDeclaration, builderCopyConstructor, builderFlatMutators, builderFluentMutators, builderImplementsInterface,
					copyMethods, guava, hashCodeAndEquals, hashCodePrecomputation, jsr305Annotations, qualityCheck, toString, replacement,
					serializable);
		}

		@Nonnull
		public Builder builderCopyConstructor(final boolean builderCopyConstructor) {
			this.builderCopyConstructor = builderCopyConstructor;
			return this;
		}

		@Nonnull
		public Builder builderFlatMutators(final boolean builderFlatMutators) {
			this.builderFlatMutators = builderFlatMutators;
			return this;
		}

		@Nonnull
		public Builder builderFluentMutators(final boolean builderFluentMutators) {
			this.builderFluentMutators = builderFluentMutators;
			return this;
		}

		@Nonnull
		public Builder builderImplementsInterface(final boolean builderImplementsInterface) {
			this.builderImplementsInterface = builderImplementsInterface;
			return this;
		}

		@Nonnull
		public Builder builderName(@Nonnull final String builderName) {
			this.builderName = Check.notNull(builderName, "builderName");
			return this;
		}

		@Nonnull
		public Builder copyMethods(final boolean copyMethods) {
			this.copyMethods = copyMethods;
			return this;
		}

		@Nonnull
		public Builder fieldPrefix(@Nonnull final String fieldPrefix) {
			this.fieldPrefix = Check.notNull(fieldPrefix, "fieldPrefix");
			return this;
		}

		@Nonnull
		public Builder fields(@Nonnull final List<Field> fields) {
			this.fields = Lists.newArrayList(Check.notNull(fields, "fields"));
			return this;
		}

		@Nonnull
		public Builder guava(final boolean guava) {
			this.guava = guava;
			return this;
		}

		@Nonnull
		public Builder hashCodeAndEquals(final boolean hashCodeAndEquals) {
			this.hashCodeAndEquals = hashCodeAndEquals;
			return this;
		}

		@Nonnull
		public Builder hashCodePrecomputation(final boolean hashCodePrecomputation) {
			this.hashCodePrecomputation = hashCodePrecomputation;
			return this;
		}

		@Nonnull
		public Builder immutableName(@Nonnull final String immutableName) {
			this.immutableName = Check.notNull(immutableName, "immutableName");
			return this;
		}

		@Nonnull
		public Builder imports(@Nonnull final List<Import> imports) {
			this.imports = Lists.newArrayList(Check.notNull(imports, "imports"));
			return this;
		}

		@Nonnull
		public Builder interfaces(@Nonnull final List<Interface> interfaces) {
			this.interfaces = Lists.newArrayList(Check.notNull(interfaces, "interfaces"));
			return this;
		}

		@Nonnull
		public Builder jsr305Annotations(final boolean jsr305Annotations) {
			this.jsr305Annotations = jsr305Annotations;
			return this;
		}

		@Nonnull
		public Builder mainInterface(@Nonnull final Interface mainInterface) {
			this.mainInterface = Check.notNull(mainInterface, "mainInterface");
			return this;
		}

		@Nonnull
		public Builder packageDeclaration(@Nonnull final Package packageDeclaration) {
			this.packageDeclaration = Check.notNull(packageDeclaration, "packageDeclaration");
			return this;
		}

		@Nonnull
		public Builder qualityCheck(final boolean qualityCheck) {
			this.qualityCheck = qualityCheck;
			return this;
		}

		@Nonnull
		public Builder replacement(final boolean replacement) {
			this.replacement = replacement;
			return this;
		}

		@Nonnull
		public Builder serializable(final boolean serializable) {
			this.serializable = serializable;
			return this;
		}

		@Nonnull
		public Builder toString(final boolean toString) {
			this.toString = toString;
			return this;
		}

	}

	@Nonnull
	public static ImmutableSettings copyOf(@Nonnull final Settings settings) {
		Check.notNull(settings, "settings");
		return new ImmutableSettings(settings.getBuilderName(), settings.getFieldPrefix(), settings.getFields(),
				settings.getImmutableName(), settings.getImports(), settings.getInterfaces(), settings.getMainInterface(),
				settings.getPackageDeclaration(), settings.hasBuilderCopyConstructor(), settings.hasBuilderFlatMutators(),
				settings.hasBuilderFluentMutators(), settings.hasBuilderImplementsInterface(), settings.hasCopyMethods(),
				settings.hasGuava(), settings.hasHashCodeAndEquals(), settings.hasHashCodePrecomputation(),
				settings.hasJsr305Annotations(), settings.hasQualityCheck(), settings.hasToString(), settings.isReplacement(),
				settings.isSerializable());
	}

	@Nonnull
	public static ImmutableSettings copyOnlyIfNecessary(@Nonnull final Settings settings) {
		Check.notNull(settings, "settings");
		return settings instanceof ImmutableSettings ? (ImmutableSettings) settings : copyOf(settings);
	}

	private final boolean builderCopyConstructor;

	private final boolean builderFlatMutators;

	private final boolean builderFluentMutators;

	private final boolean builderImplementsInterface;

	@Nonnull
	private final String builderName;

	private final boolean copyMethods;

	@Nonnull
	private final String fieldPrefix;

	@Nonnull
	private final List<Field> fields;

	private final boolean guava;

	private final boolean hashCodeAndEquals;

	private final boolean hashCodePrecomputation;

	@Nonnull
	private final String immutableName;

	@Nonnull
	private final List<Import> imports;

	@Nonnull
	private final List<Interface> interfaces;

	private final boolean jsr305Annotations;

	@Nonnull
	private final Interface mainInterface;

	@Nonnull
	private final Package packageDeclaration;

	private final boolean qualityCheck;

	private final boolean replacement;

	private final boolean serializable;

	private final boolean toString;

	public ImmutableSettings(@Nonnull final String builderName, @Nonnull final String fieldPrefix, @Nonnull final List<Field> fields,
			@Nonnull final String immutableName, @Nonnull final List<Import> imports, @Nonnull final List<Interface> interfaces,
			@Nonnull final Interface mainInterface, @Nonnull final Package packageDeclaration, final boolean builderCopyConstructor,
			final boolean builderFlatMutators, final boolean builderFluentMutators, final boolean builderImplementsInterface,
			final boolean copyMethods, final boolean guava, final boolean hashCodeAndEquals, final boolean hashCodePrecomputation,
			final boolean jsr305Annotations, final boolean qualityCheck, final boolean toString, final boolean replacement,
			final boolean serializable) {
		this.builderName = Check.notNull(builderName, "builderName");
		this.fieldPrefix = Check.notNull(fieldPrefix, "fieldPrefix");
		this.fields = ImmutableList.copyOf(Check.notNull(fields, "fields"));
		this.immutableName = Check.notNull(immutableName, "immutableName");
		this.imports = ImmutableList.copyOf(Check.notNull(imports, "imports"));
		this.interfaces = ImmutableList.copyOf(Check.notNull(interfaces, "interfaces"));
		this.mainInterface = Check.notNull(mainInterface, "mainInterface");
		this.packageDeclaration = Check.notNull(packageDeclaration, "packageDeclaration");
		this.builderCopyConstructor = builderCopyConstructor;
		this.builderFlatMutators = builderFlatMutators;
		this.builderFluentMutators = builderFluentMutators;
		this.builderImplementsInterface = builderImplementsInterface;
		this.copyMethods = copyMethods;
		this.guava = guava;
		this.hashCodeAndEquals = hashCodeAndEquals;
		this.hashCodePrecomputation = hashCodePrecomputation;
		this.jsr305Annotations = jsr305Annotations;
		this.qualityCheck = qualityCheck;
		this.toString = toString;
		this.replacement = replacement;
		this.serializable = serializable;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ImmutableSettings other = (ImmutableSettings) obj;
		return Objects.equal(builderName, other.builderName) && Objects.equal(fieldPrefix, other.fieldPrefix)
				&& Objects.equal(fields, other.fields) && Objects.equal(immutableName, other.immutableName)
				&& Objects.equal(imports, other.imports) && Objects.equal(interfaces, other.interfaces)
				&& Objects.equal(mainInterface, other.mainInterface) && Objects.equal(packageDeclaration, other.packageDeclaration)
				&& Objects.equal(builderCopyConstructor, other.builderCopyConstructor)
				&& Objects.equal(builderFlatMutators, other.builderFlatMutators)
				&& Objects.equal(builderFluentMutators, other.builderFluentMutators)
				&& Objects.equal(builderImplementsInterface, other.builderImplementsInterface)
				&& Objects.equal(copyMethods, other.copyMethods) && Objects.equal(guava, other.guava)
				&& Objects.equal(hashCodeAndEquals, other.hashCodeAndEquals)
				&& Objects.equal(hashCodePrecomputation, other.hashCodePrecomputation)
				&& Objects.equal(jsr305Annotations, other.jsr305Annotations) && Objects.equal(qualityCheck, other.qualityCheck)
				&& Objects.equal(toString, other.toString) && Objects.equal(replacement, other.replacement)
				&& Objects.equal(serializable, other.serializable);
	}

	@Override
	@Nonnull
	public String getBuilderName() {
		return builderName;
	}

	@Override
	@Nonnull
	public String getFieldPrefix() {
		return fieldPrefix;
	}

	@Override
	@Nonnull
	public List<Field> getFields() {
		return fields;
	}

	@Override
	@Nonnull
	public String getImmutableName() {
		return immutableName;
	}

	@Override
	@Nonnull
	public List<Import> getImports() {
		return imports;
	}

	@Override
	@Nonnull
	public List<Interface> getInterfaces() {
		return interfaces;
	}

	@Override
	@Nonnull
	public Interface getMainInterface() {
		return mainInterface;
	}

	@Override
	@Nonnull
	public Package getPackageDeclaration() {
		return packageDeclaration;
	}

	@Override
	public boolean hasBuilderCopyConstructor() {
		return builderCopyConstructor;
	}

	@Override
	public boolean hasBuilderFlatMutators() {
		return builderFlatMutators;
	}

	@Override
	public boolean hasBuilderFluentMutators() {
		return builderFluentMutators;
	}

	@Override
	public boolean hasBuilderImplementsInterface() {
		return builderImplementsInterface;
	}

	@Override
	public boolean hasCopyMethods() {
		return copyMethods;
	}

	@Override
	public boolean hasGuava() {
		return guava;
	}

	@Override
	public boolean hasHashCodeAndEquals() {
		return hashCodeAndEquals;
	}

	@Override
	public boolean hasHashCodePrecomputation() {
		return hashCodePrecomputation;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(builderName, fieldPrefix, fields, immutableName, imports, interfaces, mainInterface, packageDeclaration,
				builderCopyConstructor, builderFlatMutators, builderFluentMutators, builderImplementsInterface, copyMethods, guava,
				hashCodeAndEquals, hashCodePrecomputation, jsr305Annotations, qualityCheck, toString, replacement, serializable);

	}

	@Override
	public boolean hasJsr305Annotations() {
		return jsr305Annotations;
	}

	@Override
	public boolean hasQualityCheck() {
		return qualityCheck;
	}

	@Override
	public boolean hasToString() {
		return toString;
	}

	@Override
	public boolean isReplacement() {
		return replacement;
	}

	@Override
	public boolean isSerializable() {
		return serializable;
	}

}
