package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public enum Affix {
	/**
	 * These entries are order dependent and are listed from word END to ROOT
	 * ending start order!
	 */
	SoAnd(), But(SoAnd), YesYes(But), YesNo(But), Just(YesYes, YesNo), Very(
			Just), Truly(Very), Towards(Truly), InOnAt(Towards), Place(Towards),
			AboutTo(Place, InOnAt), IntendTo(Place), WentTo(AboutTo, IntendTo),
			CameFor(AboutTo, IntendTo), Around(CameFor, WentTo), ToForᏏ(Around), ToFor(
			Around), Completely(ToFor, ToForᏏ), AptTo(Completely), ByAccident(
			AptTo), Causative(AptTo), OverAndOver(Causative, ByAccident), Again(
			OverAndOver);
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

	public static List<List<Affix>> getValidSequences() {
		List<List<Affix>> lists = new ArrayList<>();
		lists.add(new ArrayList<Affix>());
		lists.get(0).add(Affix.values()[0]);
		boolean again;
		do {
			again = false;
			for (int ix = 0; ix < lists.size(); ix++) {
				List<Affix> list = lists.get(ix);
				Iterator<Affix> iaffix = list.get(list.size() - 1).follows
						.iterator();
				if (iaffix.hasNext()) {
					again = true;
					list.add(iaffix.next());
				}
				while (iaffix.hasNext()) {
					List<Affix> copy = new ArrayList<Affix>(list.subList(0,
							list.size() - 1));
					copy.add(iaffix.next());
					lists.add(copy);
				}
			}
		} while (again);
		return lists;
	}
}