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
package net.sf.qualitytest.blueprint.configuration;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalEmptyArgumentException;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.Blueprint;
import net.sf.qualitytest.blueprint.BlueprintConfiguration;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CreationStrategy;
import net.sf.qualitytest.blueprint.CycleHandlingStrategy;
import net.sf.qualitytest.blueprint.MatchingStrategy;
import net.sf.qualitytest.blueprint.invocationhandler.ProxyInvocationHandler;
import net.sf.qualitytest.blueprint.strategy.creation.BlueprintCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.creation.SingleValueCreationStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.CaseInsensitiveMethodNameMatchingStrategy;
import net.sf.qualitytest.blueprint.strategy.matching.TypeMatchingStrategy;
import net.sf.qualitytest.exception.BlueprintCycleException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

/**
 * Configure how blueprinting is done. A BlueprintConfiguration defines how the values for certain attributes are
 * generated.
 * 
 * This class is immutable all modifier methods have to return a new instance of this class.
 * 
 * @author Dominik Seichter
 */
class ImmutableBlueprintConfiguration implements BlueprintConfiguration {

	/**
	 * Mapping of class members
	 */
	private final List<StrategyPair> mapping;
	private final List<CycleHandlingStrategy<?>> cycleHandling;
	private final List<InvocationHandlerPair> invocationHandlerMapping;
	private final boolean withPublicAttributes;

	/**
	 * Create an empty {@code BlueprintConfiguration}
	 */
	public ImmutableBlueprintConfiguration() {
		mapping = ImmutableList.of();
		cycleHandling = ImmutableList.of();
		invocationHandlerMapping = ImmutableList.of();
		withPublicAttributes = false;
	}

	protected ImmutableBlueprintConfiguration(@Nonnull final List<StrategyPair> attributeMapping,
			@Nonnull final List<CycleHandlingStrategy<?>> attributeCycleHandling,
			@Nonnull final List<InvocationHandlerPair> attributeInvocationHandlerMapping, final boolean withPublicAttributes) {
		Check.notNull(attributeMapping, "attributeMapping");
		Check.notNull(attributeCycleHandling, "attributeCycleHandling");
		Check.notNull(attributeInvocationHandlerMapping, "attributeInvocationHandlerMapping");

		mapping = ImmutableList.copyOf(attributeMapping);
		cycleHandling = ImmutableList.copyOf(attributeCycleHandling);
		invocationHandlerMapping = ImmutableList.copyOf(attributeInvocationHandlerMapping);
		this.withPublicAttributes = withPublicAttributes;
	}

	@Nullable
	@Override
	@Throws(IllegalNullArgumentException.class)
	public <T> T construct(@Nonnull final Class<T> clazz) {
		return Blueprint.construct(clazz, this, new BlueprintSession());
	}

	@Nullable
	@Override
	@Throws(IllegalNullArgumentException.class)
	public CreationStrategy<?> findCreationStrategyForField(@Nonnull final Field field) {
		Check.notNull(field, "field");

		for (final StrategyPair entry : Lists.reverse(mapping)) {
			if (entry.getKey().matchesByField(field)) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Nullable
	@Override
	@Throws(IllegalNullArgumentException.class)
	public CreationStrategy<?> findCreationStrategyForMethod(@Nonnull final Method method) {
		Check.notNull(method, "method");

		for (final StrategyPair entry : Lists.reverse(mapping)) {
			if (entry.getKey().matchesByMethod(method)) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Nullable
	@Override
	@Throws(IllegalNullArgumentException.class)
	public CreationStrategy<?> findCreationStrategyForType(@Nonnull final Class<?> clazz) {
		Check.notNull(clazz, "clazz");

		for (final StrategyPair entry : Lists.reverse(mapping)) {
			if (entry.getKey().matchesByType(clazz)) {
				return entry.getValue();
			}
		}

		return null;
	}

	@Override
	public ProxyInvocationHandler findInvocationHandlerForClass(final Class<?> iface) {
		Check.notNull(iface, "iface");
		Check.stateIsTrue(iface.isInterface(), "Must be an interface.");

		for (final InvocationHandlerPair pair : Lists.reverse(invocationHandlerMapping)) {
			if (pair.getKey().equals(iface)) {
				return pair.getValue();
			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T handleCycle(@Nonnull final BlueprintSession session, @Nonnull final Class<T> clazz) {
		for (final CycleHandlingStrategy<?> strategy : Lists.reverse(cycleHandling)) {
			if (strategy.isActiveForType(clazz)) {
				return (T) strategy.handleCycle(session, clazz);
			}
		}

		throw new BlueprintCycleException(session, clazz);
	}

	@Override
	public boolean isWithPublicAttributes() {
		return withPublicAttributes;
	}

	@Override
	public <T> BlueprintConfiguration with(@Nonnull final Class<?> iface, @Nonnull final ProxyInvocationHandler invocationHandler) {
		Check.notNull(iface, "iface");
		Check.notNull(invocationHandler, "invocationHandler");
		Check.stateIsTrue(iface.isInterface(), "Must be an interface.");

		final List<InvocationHandlerPair> strategies = new ArrayList<InvocationHandlerPair>();
		strategies.addAll(invocationHandlerMapping);
		strategies.add(new InvocationHandlerPair(iface, invocationHandler));
		return new ImmutableBlueprintConfiguration(mapping, cycleHandling, strategies, withPublicAttributes);
	}

	@Override
	@Nonnull
	public <T> BlueprintConfiguration with(@Nonnull final Class<T> type, @Nullable final CreationStrategy<?> creator) {
		return with(new TypeMatchingStrategy(type), creator);
	}

	@Nonnull
	@Override
	@Throws(IllegalNullArgumentException.class)
	public <T> BlueprintConfiguration with(@Nonnull final Class<T> type, @Nullable final T value) {
		return with(new TypeMatchingStrategy(type), new SingleValueCreationStrategy<T>(value));
	}

	@Throws(IllegalNullArgumentException.class)
	@Override
	@Nonnull
	public <T> BlueprintConfiguration with(@Nonnull final CycleHandlingStrategy<T> cycleHandlingStrategy) {
		Check.notNull(cycleHandlingStrategy, "cycleHandlingStrategy");

		final List<CycleHandlingStrategy<?>> strategies = new ArrayList<CycleHandlingStrategy<?>>();
		strategies.addAll(cycleHandling);
		strategies.add(cycleHandlingStrategy);
		return new ImmutableBlueprintConfiguration(mapping, strategies, invocationHandlerMapping, withPublicAttributes);

	}

	@Nonnull
	@Override
	@Throws(IllegalNullArgumentException.class)
	public <T> BlueprintConfiguration with(@Nonnull final MatchingStrategy matchingStrategy) {
		return with(matchingStrategy, new BlueprintCreationStrategy());
	}

	@Nonnull
	@Override
	@Throws(IllegalNullArgumentException.class)
	public BlueprintConfiguration with(@Nonnull final MatchingStrategy matcher, @Nonnull final CreationStrategy<?> creator) {
		Check.notNull(matcher, "matcher");
		Check.notNull(creator, "creator");

		final List<StrategyPair> mapping = new ArrayList<StrategyPair>();
		mapping.addAll(this.mapping);
		mapping.add(new StrategyPair(matcher, creator));
		return new ImmutableBlueprintConfiguration(mapping, cycleHandling, invocationHandlerMapping, withPublicAttributes);
	}

	@Nonnull
	@Override
	@Throws({ IllegalNullArgumentException.class, IllegalEmptyArgumentException.class })
	public <T> BlueprintConfiguration with(@Nonnull final String name, @Nullable final T value) {
		Check.notEmpty(name, "name");
		return with(new CaseInsensitiveMethodNameMatchingStrategy(name), new SingleValueCreationStrategy<T>(value));
	}

	@Nonnull
	@Override
	public BlueprintConfiguration withPublicAttributes(final boolean withPublicAttributes) {
		return new ImmutableBlueprintConfiguration(mapping, cycleHandling, invocationHandlerMapping, withPublicAttributes);
	}
}
