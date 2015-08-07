package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.List;

import com.cherokeelessons.dict.shared.Suffixes.AboutTo;
import com.cherokeelessons.dict.shared.Suffixes.Accidental;
import com.cherokeelessons.dict.shared.Suffixes.Again;
import com.cherokeelessons.dict.shared.Suffixes.Around;
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
	
	public List<MatchResult> getMatches(String syllabary) {
		List<MatchResult> list = new ArrayList<Suffixes.MatchResult>(); 
		for (Affix affix: Affix.values()) {
			GWT.log("Trying: "+affix.name()+" for '"+syllabary+"'");
			Suffixes s = SuffixGuesser.getSuffixMatcher(affix);
			MatchResult matchResult = s.match(syllabary);
			if (matchResult.isMatch){
				matchResult.desc=affix.name();
				list.add(matchResult);
				syllabary=matchResult.stem;
			}
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
		}
		return null;
	}
}
