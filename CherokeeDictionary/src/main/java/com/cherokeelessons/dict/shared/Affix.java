package com.cherokeelessons.dict.shared;

import java.util.HashSet;
import java.util.Set;

public enum Affix {
	/**
	 * These entries are order dependent and are listed from word END to ROOT
	 * ending start order!
	 */
	SoAnd(), But(SoAnd), Place(But), YesYes(Place), YesNo(Place), Just(YesNo, YesYes), AboutTo(
			Just), IntendTo(Just), WentTo(AboutTo, IntendTo), CameFor(AboutTo,
			IntendTo), Around(CameFor, WentTo), ToFor(Around), Completely(ToFor), ByAccident(
			Completely), Causative(Completely), OverAndOver(Causative,
			ByAccident), Again(OverAndOver);
	private Affix(Affix... affixs) {
		if (affixs == null) {
			return;
		}
		for (Affix x : affixs) {
			x.follows.add(this);
		}
	}

	public Set<Affix> follows = new HashSet<>();
}