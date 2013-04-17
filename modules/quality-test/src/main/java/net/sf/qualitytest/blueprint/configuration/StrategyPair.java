package net.sf.qualitytest.blueprint.configuration;

import net.sf.qualitytest.blueprint.CreationStrategy;
import net.sf.qualitytest.blueprint.MatchingStrategy;

public class StrategyPair {

	private final MatchingStrategy matching;
	private final CreationStrategy<?> creation;

	public StrategyPair(final MatchingStrategy matching, final CreationStrategy<?> creation) {
		this.matching = matching;
		this.creation = creation;
	}

	public MatchingStrategy getKey() {
		return matching;
	}

	public CreationStrategy<?> getValue() {
		return creation;
	}

}
