package net.sf.qualitytest.blueprint.strategy.cycle;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitycheck.Throws;
import net.sf.qualitycheck.exception.IllegalNullArgumentException;
import net.sf.qualitytest.blueprint.BlueprintSession;
import net.sf.qualitytest.blueprint.CycleHandlingStrategy;
import net.sf.qualitytest.exception.BlueprintCycleException;

/**
 * The default handling strategy for cycles in the blueprinting graph, which throws a {@link BlueprintCycleException}.
 * 
 * @author dominik.seichter
 * 
 */
public class ExceptionCycleHandlingStrategy implements CycleHandlingStrategy<Object> {

	@Override
	@ArgumentsChecked
	@Throws(IllegalNullArgumentException.class)
	@Nullable
	public Object handleCycle(@Nonnull final BlueprintSession session, @Nonnull final Class<?> clazz) {
		throw new BlueprintCycleException(Check.notNull(session, "session"), Check.notNull(clazz, "clazz"));
	}

	@Override
	public boolean isActiveForType(@Nonnull final Class<?> clazz) {
		return true;
	}

}
