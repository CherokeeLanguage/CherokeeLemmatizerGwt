package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public enum VerbStemAffix {
	/**
	 * These entries are order dependent and are listed from word END to ROOT
	 * ending start order!
	 */
			AboutTo(), IntendTo(), WentTo(AboutTo, IntendTo),
			CameFor(AboutTo, IntendTo), Around(CameFor, WentTo), ToForᏏ(Around), ToFor(
			Around), Completely(ToFor, ToForᏏ), AptTo(Completely), ByAccident(
			AptTo), Causative(AptTo), OverAndOver(Causative, ByAccident), Again(
			OverAndOver);
			
	private VerbStemAffix(VerbStemAffix... affixs) {
		if (affixs == null) {
			return;
		}
		for (VerbStemAffix x : affixs) {
			x.follows.add(this);
		}
	}

	private final Set<VerbStemAffix> follows = new HashSet<>();

	public Set<VerbStemAffix> getFollows() {
		return new HashSet<VerbStemAffix>(follows);
	}

	public static List<List<VerbStemAffix>> getValidSequences() {
		List<List<VerbStemAffix>> lists = new ArrayList<>();
		lists.add(new ArrayList<VerbStemAffix>());
		lists.get(0).add(VerbStemAffix.values()[0]);
		boolean again;
		do {
			again = false;
			for (int ix = 0; ix < lists.size(); ix++) {
				List<VerbStemAffix> list = lists.get(ix);
				Iterator<VerbStemAffix> iaffix = list.get(list.size() - 1).follows
						.iterator();
				if (iaffix.hasNext()) {
					again = true;
					list.add(iaffix.next());
				}
				while (iaffix.hasNext()) {
					List<VerbStemAffix> copy = new ArrayList<VerbStemAffix>(list.subList(0,
							list.size() - 1));
					copy.add(iaffix.next());
					lists.add(copy);
				}
			}
		} while (again);
		return lists;
	}
}