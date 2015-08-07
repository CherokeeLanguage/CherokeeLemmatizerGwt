package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cherokeelessons.dict.shared.Suffixes.AboutTo;
import com.cherokeelessons.dict.shared.Suffixes.Accidental;
import com.cherokeelessons.dict.shared.Suffixes.Again;
import com.cherokeelessons.dict.shared.Suffixes.Around;
import com.cherokeelessons.dict.shared.Suffixes.But;
import com.cherokeelessons.dict.shared.Suffixes.CausativePast;
import com.cherokeelessons.dict.shared.Suffixes.ComeForDoing;
import com.cherokeelessons.dict.shared.Suffixes.Completely;
import com.cherokeelessons.dict.shared.Suffixes.IntendTo;
import com.cherokeelessons.dict.shared.Suffixes.Just;
import com.cherokeelessons.dict.shared.Suffixes.MatchResult;
import com.cherokeelessons.dict.shared.Suffixes.Place;
import com.cherokeelessons.dict.shared.Suffixes.Repeatedly;
import com.cherokeelessons.dict.shared.Suffixes.SoAnd;
import com.cherokeelessons.dict.shared.Suffixes.ToFor;
import com.cherokeelessons.dict.shared.Suffixes.WentForDoing;
import com.cherokeelessons.dict.shared.Suffixes.YesNo;
import com.cherokeelessons.dict.shared.Suffixes.YesYes;
import com.google.gwt.core.shared.GWT;

public enum SuffixGuesser {

	INSTANCE;

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	private SuffixGuesser() {
	}

	private List<MatchResult> getMatchesStartingFrom(Affix affix,
			String syllabary) {
		GWT.log("\tTRYING: "+affix.name());
		List<MatchResult> list = new ArrayList<Suffixes.MatchResult>();
		Suffixes s = SuffixGuesser.getSuffixMatcher(affix);
		MatchResult matchResult = s.match(syllabary);
		if (matchResult.isMatch) {
			GWT.log("MATCHED: " + syllabary + " " + affix.name());
			matchResult.desc = affix.name();
			list.add(matchResult);
			//TODO: THIS PART IS NOT WORKING RIGHT
			for (Affix nextAffix: affix.follows) {
				list.addAll(getMatchesStartingFrom(nextAffix, matchResult.stem));
			}
		}
		return list;
	}

	public List<MatchResult> getMatches(String syllabary) {
		List<List<MatchResult>> lists = new ArrayList<>();
		for (Affix affix : Affix.values()) {
			lists.add(getMatchesStartingFrom(affix, syllabary));
		}
		Iterator<List<MatchResult>> xx = lists.iterator();
		while (xx.hasNext()) {
			if (xx.next().size() == 0) {
				xx.remove();
			}
		}
		if (lists.size() == 0) {
			GWT.log("NO MATCHES.");
			return new ArrayList<MatchResult>();
		}
		/*
		 * pick the one with the most matches
		 */
		Iterator<List<MatchResult>> il = lists.iterator();
		List<MatchResult> list = il.next();
		if (!il.hasNext()) {
			GWT.log("ONLY ONE MATCH.");
			return list;
		}
		GWT.log("COMPARING MATCH COUNT SIZES.");
		while (il.hasNext()) {
			List<MatchResult> tmp = il.next();
			// int listsize = list.size();
			// int tmpsize = tmp.size();
			// if (tmpsize>listsize){
			// list=tmp;
			// continue;
			// }
			// if (tmpsize==listsize) {
			if (tmp.get(0).suffix.length() > list.get(0).suffix.length()) {
				list = tmp;
				continue;
			}
			// }
		}
		return list;
	}

	public static Suffixes getSuffixMatcher(Affix affix) {
		switch (affix) {
		case AboutTo:
			return new AboutTo();
		case Again:
			return new Again();
		case Around:
			return new Around();
		case ByAccident:
			return new Accidental();
		case CameFor:
			return new ComeForDoing();
		case Causative:
			// TODO: Causative, not past tense is special
			return new CausativePast();
		case Completely:
			return new Completely();
		case OverAndOver:
			return new Repeatedly();
		case ToFor:
			return new ToFor();
		case WentTo:
			return new WentForDoing();
		case IntendTo:
			return new IntendTo();
		case Just:
			return new Just();
		case SoAnd:
			return new SoAnd();
		case Place:
			return new Place();
		case YesNo:
			return new YesNo();
		case YesYes:
			return new YesYes();
		case But:
			return new But();
		}
		throw new RuntimeException("SPECIFIED SUFFIX MATCHER IS NOT IMPLEMENTED.");
	}
}
