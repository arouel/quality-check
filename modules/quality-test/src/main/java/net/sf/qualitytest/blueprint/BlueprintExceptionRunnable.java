package net.sf.qualitytest.blueprint;

import javax.annotation.Nonnull;

import net.sf.qualitycheck.ArgumentsChecked;
import net.sf.qualitycheck.Check;
import net.sf.qualitytest.blueprint.SafeInvoke.ExceptionRunnable;
import net.sf.qualitytest.exception.BlueprintException;

public abstract class BlueprintExceptionRunnable<T> implements ExceptionRunnable<T> {

	private final BlueprintSession session;

	@ArgumentsChecked
	public BlueprintExceptionRunnable(@Nonnull final BlueprintSession session, @Nonnull final String lastAction) {
		this.session = Check.notNull(session, "session");
		session.setLastAction(Check.notNull(lastAction, "lastAction"));
	}

	@Override
	public T run() throws Exception {
		try {
			return runInternal();
		} catch (final Exception e) {
			final BlueprintException b = new BlueprintException(e);
			b.setSession(session);
			throw b;
		}
	}

	protected abstract T runInternal() throws Exception;

}
