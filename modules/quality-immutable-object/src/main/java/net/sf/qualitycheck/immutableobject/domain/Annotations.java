package net.sf.qualitycheck.immutableobject.domain;

import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Sets;

@Immutable
public final class Annotations {

	@Nonnull
	public static Annotations of(@Nonnull final Iterable<Annotation> annotations) {
		Check.notNull(annotations, "annotations");
		return new Annotations(annotations);
	}

	@Nonnull
	private static Set<Annotation> removeUnqualified(@Nonnull final List<Annotation> annotations, @Nonnull final Imports imports) {
		final Set<Annotation> result = Sets.newLinkedHashSet();
		for (final Annotation annotation : annotations) {
			if (annotation.getType().getPackage() == Package.UNDEFINED) {
				final Import imp = imports.find(annotation.getType().getTypeName());
				if (imp != null) {
					result.add(new Annotation(imp.getType()));
				}
			} else {
				result.add(annotation);
			}
		}
		return result;
	}

	@Nonnull
	private final List<Annotation> _annotations;

	private Annotations(@Nonnull final Iterable<Annotation> annotations) {
		Check.notNull(annotations, "annotations");
		_annotations = ImmutableList.copyOf(annotations);
	}

	@Nonnull
	public List<Annotation> getAnnotations() {
		return _annotations;
	}

	@Nonnull
	public Annotations removeUnqualified(@Nonnull final Imports imports) {
		Check.notNull(imports, "imports");
		return Annotations.of(removeUnqualified(getAnnotations(), imports));
	}

}
