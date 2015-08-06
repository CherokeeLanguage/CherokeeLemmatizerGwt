package com.cherokeelessons.dict.shared;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.shared.GWT;

public enum Affix {
	// these entries are order dependent!
	AboutTo(), WentTo(AboutTo), CameFor(AboutTo), Around(CameFor, WentTo), ToFor(
			Around), Completely(ToFor), ByAccident(Completely), Causative(
			Completely), OverAndOver(Causative, ByAccident), Again(
			OverAndOver);
	private Affix(Affix... affixs) {
		if (affixs == null) {
			return;
		}
		isFollowedBy.addAll(Arrays.asList(affixs));
		for (Affix affix : affixs) {
			isFollowedBy.addAll(affix.isFollowedBy);
		}
//		GWT.log("AFFIX: " + this.name() + " -> " + isFollowedBy.toString());
	}

	public Set<Affix> isFollowedBy = new HashSet<>();
}