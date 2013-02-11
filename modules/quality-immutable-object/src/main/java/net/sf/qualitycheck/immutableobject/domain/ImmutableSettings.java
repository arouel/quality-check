package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
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

		@Nullable
		private String builderName;

		@Nonnull
		private List<Field> fields;

		@Nonnull
		private String immutableName;

		@Nonnull
		private List<Import> imports;

		@Nonnull
		private Interface interfaceDeclaration;

		@Nonnull
		private Package packageDeclaration;

		private boolean builderCopyConstructor;

		private boolean builderFlatMutators;

		private boolean builderFluentMutators;

		private boolean builderSameInterface;

		private boolean guava;

		private boolean jsr305Annotations;

		private boolean serialVersion;

		public Builder() {
			// default constructor
		}

		public Builder(@Nonnull final Settings settings) {
			builderName = settings.getBuilderName();
			fields = settings.getFields();
			immutableName = settings.getImmutableName();
			imports = settings.getImports();
			interfaceDeclaration = settings.getInterfaceDeclaration();
			packageDeclaration = settings.getPackageDeclaration();
			builderCopyConstructor = settings.hasBuilderCopyConstructor();
			builderFlatMutators = settings.hasBuilderFlatMutators();
			builderFluentMutators = settings.hasBuilderFluentMutators();
			builderSameInterface = settings.hasBuilderSameInterface();
			guava = settings.hasGuava();
			jsr305Annotations = settings.hasJsr305Annotations();
			serialVersion = settings.hasSerialVersion();
		}

		@Nonnull
		public ImmutableSettings build() {
			return new ImmutableSettings(builderName, fields, immutableName, imports, interfaceDeclaration, packageDeclaration,
					builderCopyConstructor, builderFlatMutators, builderFluentMutators, builderSameInterface, guava, jsr305Annotations,
					serialVersion);
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
		public Builder builderName(@Nullable final String builderName) {
			this.builderName = builderName;
			return this;
		}

		@Nonnull
		public Builder builderSameInterface(final boolean builderSameInterface) {
			this.builderSameInterface = builderSameInterface;
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
		public Builder interfaceDeclaration(@Nonnull final Interface interfaceDeclaration) {
			this.interfaceDeclaration = Check.notNull(interfaceDeclaration, "interfaceDeclaration");
			return this;
		}

		@Nonnull
		public Builder jsr305Annotations(final boolean jsr305Annotations) {
			this.jsr305Annotations = jsr305Annotations;
			return this;
		}

		@Nonnull
		public Builder packageDeclaration(@Nonnull final Package packageDeclaration) {
			this.packageDeclaration = Check.notNull(packageDeclaration, "packageDeclaration");
			return this;
		}

		@Nonnull
		public Builder serialVersion(final boolean serialVersion) {
			this.serialVersion = serialVersion;
			return this;
		}

	}

	@Nonnull
	public static ImmutableSettings copyOf(@Nonnull final Settings settings) {
		Check.notNull(settings, "settings");
		return new ImmutableSettings(settings.getBuilderName(), settings.getFields(), settings.getImmutableName(), settings.getImports(),
				settings.getInterfaceDeclaration(), settings.getPackageDeclaration(), settings.hasBuilderCopyConstructor(),
				settings.hasBuilderFlatMutators(), settings.hasBuilderFluentMutators(), settings.hasBuilderSameInterface(),
				settings.hasGuava(), settings.hasJsr305Annotations(), settings.hasSerialVersion());
	}

	@Nullable
	private final String builderName;

	@Nonnull
	private final List<Field> fields;

	@Nonnull
	private final String immutableName;

	@Nonnull
	private final List<Import> imports;

	@Nonnull
	private final Interface interfaceDeclaration;

	@Nonnull
	private final Package packageDeclaration;

	private final boolean builderCopyConstructor;

	private final boolean builderFlatMutators;

	private final boolean builderFluentMutators;

	private final boolean builderSameInterface;

	private final boolean guava;

	private final boolean jsr305Annotations;

	private final boolean serialVersion;

	public ImmutableSettings(@Nullable final String builderName, @Nonnull final List<Field> fields, @Nonnull final String immutableName,
			@Nonnull final List<Import> imports, @Nonnull final Interface interfaceDeclaration, @Nonnull final Package packageDeclaration,
			final boolean builderCopyConstructor, final boolean builderFlatMutators, final boolean builderFluentMutators,
			final boolean builderSameInterface, final boolean guava, final boolean jsr305Annotations, final boolean serialVersion) {
		this.builderName = builderName;
		this.fields = ImmutableList.copyOf(Check.notNull(fields, "fields"));
		this.immutableName = Check.notNull(immutableName, "immutableName");
		this.imports = ImmutableList.copyOf(Check.notNull(imports, "imports"));
		this.interfaceDeclaration = Check.notNull(interfaceDeclaration, "interfaceDeclaration");
		this.packageDeclaration = Check.notNull(packageDeclaration, "packageDeclaration");
		this.builderCopyConstructor = builderCopyConstructor;
		this.builderFlatMutators = builderFlatMutators;
		this.builderFluentMutators = builderFluentMutators;
		this.builderSameInterface = builderSameInterface;
		this.guava = guava;
		this.jsr305Annotations = jsr305Annotations;
		this.serialVersion = serialVersion;
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
		return Objects.equal(builderName, other.builderName) && Objects.equal(fields, other.fields)
				&& Objects.equal(immutableName, other.immutableName) && Objects.equal(imports, other.imports)
				&& Objects.equal(interfaceDeclaration, other.interfaceDeclaration)
				&& Objects.equal(packageDeclaration, other.packageDeclaration)
				&& Objects.equal(builderCopyConstructor, other.builderCopyConstructor)
				&& Objects.equal(builderFlatMutators, other.builderFlatMutators)
				&& Objects.equal(builderFluentMutators, other.builderFluentMutators)
				&& Objects.equal(builderSameInterface, other.builderSameInterface) && Objects.equal(guava, other.guava)
				&& Objects.equal(jsr305Annotations, other.jsr305Annotations) && Objects.equal(serialVersion, other.serialVersion);
	}

	@Override
	@Nullable
	public String getBuilderName() {
		return builderName;
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
	public Interface getInterfaceDeclaration() {
		return interfaceDeclaration;
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
	public boolean hasBuilderSameInterface() {
		return builderSameInterface;
	}

	@Override
	public boolean hasGuava() {
		return guava;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(builderName, fields, immutableName, imports, interfaceDeclaration, packageDeclaration,
				builderCopyConstructor, builderFlatMutators, builderFluentMutators, builderSameInterface, guava, jsr305Annotations,
				serialVersion);
	}

	@Override
	public boolean hasJsr305Annotations() {
		return jsr305Annotations;
	}

	@Override
	public boolean hasSerialVersion() {
		return serialVersion;
	}

}
