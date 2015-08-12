package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public enum GeneralAffix {
	SoAnd(), But(SoAnd), YesYes(But), YesNo(But), Just(YesYes, YesNo), Very(
			Just), Truly(Very), Towards(Truly), InOnAt(Towards), Place(Towards);
	
	private GeneralAffix(GeneralAffix... affixs) {
		if (affixs == null) {
			return;
		}
		for (GeneralAffix x : affixs) {
			x.follows.add(this);
		}
	}

	private final Set<GeneralAffix> follows = new HashSet<>();

	public Set<GeneralAffix> getFollows() {
		return new HashSet<>(follows);
	}

	public static List<List<GeneralAffix>> getValidSequences() {
		List<List<GeneralAffix>> lists = new ArrayList<>();
		lists.add(new ArrayList<GeneralAffix>());
		lists.get(0).add(GeneralAffix.values()[0]);
		boolean again;
		do {
			again = false;
			for (int ix = 0; ix < lists.size(); ix++) {
				List<GeneralAffix> list = lists.get(ix);
				Iterator<GeneralAffix> iaffix = list.get(list.size() - 1).follows
						.iterator();
				if (iaffix.hasNext()) {
					again = true;
					list.add(iaffix.next());
				}
				while (iaffix.hasNext()) {
					List<GeneralAffix> copy = new ArrayList<GeneralAffix>(list.subList(0,
							list.size() - 1));
					copy.add(iaffix.next());
					lists.add(copy);
				}
			}
		} while (again);
		return lists;
	}
}
