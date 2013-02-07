package net.sf.qualitycheck.immutableobject.generator;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.immutableobject.domain.Method;
import net.sf.qualitycheck.immutableobject.util.FieldUtil;

@Immutable
public final class AccessorMethodGenerator {

	public static Method generate(@Nonnull final Method method) {
		Check.notNull(method, "method");
		final String content = "return " + FieldUtil.determineFieldName(method.getName()) + ";";
		return new Method(method.getName(), method.getReturnType(), content, method.getAttributes(), method.getVisibility(),
				method.getFinal(), method.getStatic(), method.getAnnotations());
	}

}
