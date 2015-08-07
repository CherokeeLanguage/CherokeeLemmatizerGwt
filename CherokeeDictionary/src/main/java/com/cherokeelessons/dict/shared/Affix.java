package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import com.google.gwt.core.shared.GWT;

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
	
	private final Set<Affix> follows = new HashSet<>();
	
	public Set<Affix> getFollows() {
		return new HashSet<Affix>(follows);
	}
	
	public static List<Affix> getValidChains() {
		List<Affix> list = new ArrayList<Affix>();
		list.add(Affix.values()[0]);
		ListIterator<Affix> li = list.listIterator();
		return list;
	}
}