package net.sf.qualitytest.blueprint;

import java.lang.reflect.Method;
import java.util.Map;

import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;

public interface BlueprintConfiguration {

	/**
	 * Find a creation strategy that matches on the given method.
	 * 
	 * @param method
	 *            A setter method
	 * 
	 * @return a {@code ValueCreationStrategy} or {@code null}
	 */
	ValueCreationStrategy<?> findCreationStrategyForMethod(final Method method);

	ValueCreationStrategy<?> findCreationStrategyForType(final Class<?> clazz);

	Map<ValueMatchingStrategy, ValueCreationStrategy<?>> getAttributeMappings();

	/**
	 * Blueprint a Java-Object using this configuration.
	 * 
	 * @see Blueprint
	 * 
	 * @param <T>
	 * @param clazz
	 *            a class
	 * @return a blue printed instance of {@code T}
	 */
	@Throws(IllegalNullArgumentException.class)
	<T> T object(final Class<T> clazz);

	/**
	 * Replace every attribute with the typee {@code type} with a given value.
	 * 
	 * @param type
	 *            a Java type.
	 * @param value
	 *            value which should be assigned to the attribute
	 * 
	 * @return the changed blueprint configuration.
	 */
	<T> BlueprintConfiguration with(final Class<T> type, final T value);

	/**
	 * Replace every attribute with the name {@code name} with a given value.
	 * 
	 * @param name
	 *            case insensitive name of an attribute.
	 * @param value
	 *            value which should be assigned to the attribute
	 * 
	 * @return the changed blueprint configuration.
	 */
	<T> BlueprintConfiguration with(final String name, final T value);

	/**
	 * Replace every attribute which matches a given strategy with a given value.
	 * 
	 * @param matcher
	 *            Matching strategy to define if the replaced should be applied.
	 * @param creator
	 *            Creation strategy which actually creates a new value.
	 * 
	 * @return the changed blueprint configuration.
	 */
	BlueprintConfiguration with(final ValueMatchingStrategy matcher, final ValueCreationStrategy<?> creator);

}
