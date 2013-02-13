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
		private String _builderName;

		@Nonnull
		private String _fieldPrefix;

		@Nonnull
		private List<Field> _fields;

		@Nonnull
		private String _immutableName;

		@Nonnull
		private List<Import> _imports;

		@Nonnull
		private Interface _interfaceDeclaration;

		@Nonnull
		private Package _packageDeclaration;

		private boolean _builderCopyConstructor;

		private boolean _builderFlatMutators;

		private boolean _builderFluentMutators;

		private boolean _builderImplementsInterface;

		private boolean _guava;

		private boolean _jsr305Annotations;

		private boolean _qualityCheck;

		private boolean _serializable;

		public Builder() {
			// default constructor
		}

		public Builder(@Nonnull final Settings settings) {
			_builderName = settings.getBuilderName();
			_fieldPrefix = Check.notNull(settings.getFieldPrefix(), "settings.getFieldPrefix()");
			_fields = Lists.newArrayList(Check.notNull(settings.getFields(), "settings.getFields()"));
			_immutableName = Check.notNull(settings.getImmutableName(), "settings.getImmutableName()");
			_imports = Lists.newArrayList(Check.notNull(settings.getImports(), "settings.getImports()"));
			_interfaceDeclaration = Check.notNull(settings.getInterfaceDeclaration(), "settings.getInterfaceDeclaration()");
			_packageDeclaration = Check.notNull(settings.getPackageDeclaration(), "settings.getPackageDeclaration()");
			_builderCopyConstructor = settings.hasBuilderCopyConstructor();
			_builderFlatMutators = settings.hasBuilderFlatMutators();
			_builderFluentMutators = settings.hasBuilderFluentMutators();
			_builderImplementsInterface = settings.hasBuilderImplementsInterface();
			_guava = settings.hasGuava();
			_jsr305Annotations = settings.hasJsr305Annotations();
			_qualityCheck = settings.hasQualityCheck();
			_serializable = settings.isSerializable();
		}

		@Nonnull
		public ImmutableSettings build() {
			return new ImmutableSettings(_builderName, _fieldPrefix, _fields, _immutableName, _imports, _interfaceDeclaration,
					_packageDeclaration, _builderCopyConstructor, _builderFlatMutators, _builderFluentMutators,
					_builderImplementsInterface, _guava, _jsr305Annotations, _qualityCheck, _serializable);
		}

		@Nonnull
		public Builder builderCopyConstructor(final boolean builderCopyConstructor) {
			_builderCopyConstructor = builderCopyConstructor;
			return this;
		}

		@Nonnull
		public Builder builderFlatMutators(final boolean builderFlatMutators) {
			_builderFlatMutators = builderFlatMutators;
			return this;
		}

		@Nonnull
		public Builder builderFluentMutators(final boolean builderFluentMutators) {
			_builderFluentMutators = builderFluentMutators;
			return this;
		}

		@Nonnull
		public Builder builderImplementsInterface(final boolean builderImplementsInterface) {
			_builderImplementsInterface = builderImplementsInterface;
			return this;
		}

		@Nonnull
		public Builder builderName(@Nullable final String builderName) {
			_builderName = builderName;
			return this;
		}

		@Nonnull
		public Builder fieldPrefix(@Nonnull final String fieldPrefix) {
			_fieldPrefix = Check.notNull(fieldPrefix, "fieldPrefix");
			return this;
		}

		@Nonnull
		public Builder fields(@Nonnull final List<Field> fields) {
			_fields = Lists.newArrayList(Check.notNull(fields, "fields"));
			return this;
		}

		@Nonnull
		public Builder guava(final boolean guava) {
			_guava = guava;
			return this;
		}

		@Nonnull
		public Builder immutableName(@Nonnull final String immutableName) {
			_immutableName = Check.notNull(immutableName, "immutableName");
			return this;
		}

		@Nonnull
		public Builder imports(@Nonnull final List<Import> imports) {
			_imports = Lists.newArrayList(Check.notNull(imports, "imports"));
			return this;
		}

		@Nonnull
		public Builder interfaceDeclaration(@Nonnull final Interface interfaceDeclaration) {
			_interfaceDeclaration = Check.notNull(interfaceDeclaration, "interfaceDeclaration");
			return this;
		}

		@Nonnull
		public Builder jsr305Annotations(final boolean jsr305Annotations) {
			_jsr305Annotations = jsr305Annotations;
			return this;
		}

		@Nonnull
		public Builder packageDeclaration(@Nonnull final Package packageDeclaration) {
			_packageDeclaration = Check.notNull(packageDeclaration, "packageDeclaration");
			return this;
		}

		@Nonnull
		public Builder qualityCheck(final boolean qualityCheck) {
			_qualityCheck = qualityCheck;
			return this;
		}

		@Nonnull
		public Builder serializable(final boolean serializable) {
			_serializable = serializable;
			return this;
		}

	}

	@Nonnull
	public static ImmutableSettings copyOf(@Nonnull final Settings settings) {
		Check.notNull(settings, "settings");
		return new ImmutableSettings(settings.getBuilderName(), settings.getFieldPrefix(), settings.getFields(),
				settings.getImmutableName(), settings.getImports(), settings.getInterfaceDeclaration(), settings.getPackageDeclaration(),
				settings.hasBuilderCopyConstructor(), settings.hasBuilderFlatMutators(), settings.hasBuilderFluentMutators(),
				settings.hasBuilderImplementsInterface(), settings.hasGuava(), settings.hasJsr305Annotations(), settings.hasQualityCheck(),
				settings.isSerializable());
	}

	@Nullable
	private final String _builderName;

	@Nonnull
	private final String _fieldPrefix;

	@Nonnull
	private final List<Field> _fields;

	@Nonnull
	private final String _immutableName;

	@Nonnull
	private final List<Import> _imports;

	@Nonnull
	private final Interface _interfaceDeclaration;

	@Nonnull
	private final Package _packageDeclaration;

	private final boolean _builderCopyConstructor;

	private final boolean _builderFlatMutators;

	private final boolean _builderFluentMutators;

	private final boolean _builderImplementsInterface;

	private final boolean _guava;

	private final boolean _jsr305Annotations;

	private final boolean _qualityCheck;

	private final boolean _serializable;

	public ImmutableSettings(@Nullable final String builderName, @Nonnull final String fieldPrefix, @Nonnull final List<Field> fields,
			@Nonnull final String immutableName, @Nonnull final List<Import> imports, @Nonnull final Interface interfaceDeclaration,
			@Nonnull final Package packageDeclaration, final boolean builderCopyConstructor, final boolean builderFlatMutators,
			final boolean builderFluentMutators, final boolean builderImplementsInterface, final boolean guava,
			final boolean jsr305Annotations, final boolean qualityCheck, final boolean serializable) {
		_builderName = builderName;
		_fieldPrefix = Check.notNull(fieldPrefix, "fieldPrefix");
		_fields = ImmutableList.copyOf(Check.notNull(fields, "fields"));
		_immutableName = Check.notNull(immutableName, "immutableName");
		_imports = ImmutableList.copyOf(Check.notNull(imports, "imports"));
		_interfaceDeclaration = Check.notNull(interfaceDeclaration, "interfaceDeclaration");
		_packageDeclaration = Check.notNull(packageDeclaration, "packageDeclaration");
		_builderCopyConstructor = builderCopyConstructor;
		_builderFlatMutators = builderFlatMutators;
		_builderFluentMutators = builderFluentMutators;
		_builderImplementsInterface = builderImplementsInterface;
		_guava = guava;
		_jsr305Annotations = jsr305Annotations;
		_qualityCheck = qualityCheck;
		_serializable = serializable;
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
		return Objects.equal(_builderName, other._builderName) && Objects.equal(_fieldPrefix, other._fieldPrefix)
				&& Objects.equal(_fields, other._fields) && Objects.equal(_immutableName, other._immutableName)
				&& Objects.equal(_imports, other._imports) && Objects.equal(_interfaceDeclaration, other._interfaceDeclaration)
				&& Objects.equal(_packageDeclaration, other._packageDeclaration)
				&& Objects.equal(_builderCopyConstructor, other._builderCopyConstructor)
				&& Objects.equal(_builderFlatMutators, other._builderFlatMutators)
				&& Objects.equal(_builderFluentMutators, other._builderFluentMutators)
				&& Objects.equal(_builderImplementsInterface, other._builderImplementsInterface) && Objects.equal(_guava, other._guava)
				&& Objects.equal(_jsr305Annotations, other._jsr305Annotations) && Objects.equal(_qualityCheck, other._qualityCheck)
				&& Objects.equal(_serializable, other._serializable);
	}

	@Override
	@Nullable
	public String getBuilderName() {
		return _builderName;
	}

	@Override
	@Nonnull
	public String getFieldPrefix() {
		return _fieldPrefix;
	}

	@Override
	@Nonnull
	public List<Field> getFields() {
		return _fields;
	}

	@Override
	@Nonnull
	public String getImmutableName() {
		return _immutableName;
	}

	@Override
	@Nonnull
	public List<Import> getImports() {
		return _imports;
	}

	@Override
	@Nonnull
	public Interface getInterfaceDeclaration() {
		return _interfaceDeclaration;
	}

	@Override
	@Nonnull
	public Package getPackageDeclaration() {
		return _packageDeclaration;
	}

	@Override
	public boolean hasBuilderCopyConstructor() {
		return _builderCopyConstructor;
	}

	@Override
	public boolean hasBuilderFlatMutators() {
		return _builderFlatMutators;
	}

	@Override
	public boolean hasBuilderFluentMutators() {
		return _builderFluentMutators;
	}

	@Override
	public boolean hasBuilderImplementsInterface() {
		return _builderImplementsInterface;
	}

	@Override
	public boolean hasGuava() {
		return _guava;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(_builderName, _fieldPrefix, _fields, _immutableName, _imports, _interfaceDeclaration, _packageDeclaration,
				_builderCopyConstructor, _builderFlatMutators, _builderFluentMutators, _builderImplementsInterface, _guava,
				_jsr305Annotations, _qualityCheck, _serializable);
	}

	@Override
	public boolean hasJsr305Annotations() {
		return _jsr305Annotations;
	}

	@Override
	public boolean hasQualityCheck() {
		return _qualityCheck;
	}

	@Override
	public boolean isSerializable() {
		return _serializable;
	}

}
