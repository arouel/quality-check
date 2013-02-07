package net.sf.qualitycheck.immutableobject;

import java.util.Date;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.concurrent.Immutable;

import net.sf.qualitycheck.immutableobject.domain.Annotation;

@Immutable
public interface AnyInterface<T extends AnyInterface<T>> {

	<O extends Annotation> O getAnnotation(Class<O> annotationType);

	T getChildren();

	List<String> getMessages();

	String getName();

	@Nonnull
	Date getTime();

	boolean hasName();

	boolean isActive();

	void setIsUncool(int madInt);

}
