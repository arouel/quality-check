package net.sf.qualitytest.blueprint;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.SafeInvoke.ExceptionRunnable;

public abstract class BlueprintExceptionRunnable<T> implements ExceptionRunnable<T> {

	@ArgumentsChecked
	public BlueprintExceptionRunnable(@Nonnull final BlueprintSession session, @Nonnull final String lastAction) {
		Check.notNull(session, "session");
		session.setLastAction(Check.notNull(lastAction, "lastAction"));
	}

	@Override
	public T run() throws Exception {
		return runInternal();
	}

	protected abstract T runInternal() throws Exception;

}
