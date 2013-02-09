package net.sf.qualitycheck.immutableobject.parser;

import static org.reflections.ReflectionUtils.withParametersCount;
import static org.reflections.ReflectionUtils.withPrefix;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import net.sf.qualitycheck.immutableobject.domain.AccessorPrefix;

import org.reflections.ReflectionUtils;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.ImmutableSet;

public final class InterfaceParser {

	private static final Predicate<Method> FILTER_GET = Predicates.and(withPrefix(AccessorPrefix.GET.getPrefix()), withParametersCount(0));
	private static final Predicate<Method> FILTER_HAS = Predicates.and(withPrefix(AccessorPrefix.HAS.getPrefix()), withParametersCount(0));
	private static final Predicate<Method> FILTER_IS = Predicates.and(withPrefix(AccessorPrefix.IS.getPrefix()), withParametersCount(0));
	private static final Predicate<Method> FILTER_POSSIBLE_MUTATING = new Predicate<Method>() {
		@Override
		public boolean apply(@Nullable final Method input) {
			return input != null && input.getParameterTypes().length > 0;
		}
	};

	public static Set<Method> findPossibleMutatingMethods(final Class<?> type) {
		return ReflectionUtils.getAllMethods(type, FILTER_POSSIBLE_MUTATING);
	}

	public static Set<Method> findReadableMethods(final Class<?> type) {
		final Set<Method> methods = new HashSet<Method>();
		methods.addAll(ReflectionUtils.getAllMethods(type, FILTER_GET));
		methods.addAll(ReflectionUtils.getAllMethods(type, FILTER_HAS));
		methods.addAll(ReflectionUtils.getAllMethods(type, FILTER_IS));
		return ImmutableSet.copyOf(methods);
	}

	private InterfaceParser() {
	}

}
