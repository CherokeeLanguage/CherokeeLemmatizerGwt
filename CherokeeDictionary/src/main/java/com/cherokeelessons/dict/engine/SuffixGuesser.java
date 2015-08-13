package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.List;

import com.cherokeelessons.dict.engine.Affixes.AboutTo;
import com.cherokeelessons.dict.engine.Affixes.Accidental;
import com.cherokeelessons.dict.engine.Affixes.AffixResult;
import com.cherokeelessons.dict.engine.Affixes.Again;
import com.cherokeelessons.dict.engine.Affixes.AptTo;
import com.cherokeelessons.dict.engine.Affixes.Around;
import com.cherokeelessons.dict.engine.Affixes.But;
import com.cherokeelessons.dict.engine.Affixes.Causative;
import com.cherokeelessons.dict.engine.Affixes.ComeForDoing;
import com.cherokeelessons.dict.engine.Affixes.Completely;
import com.cherokeelessons.dict.engine.Affixes.InOnAt;
import com.cherokeelessons.dict.engine.Affixes.IntendTo;
import com.cherokeelessons.dict.engine.Affixes.Just;
import com.cherokeelessons.dict.engine.Affixes.Place;
import com.cherokeelessons.dict.engine.Affixes.Repeatedly;
import com.cherokeelessons.dict.engine.Affixes.SoAnd;
import com.cherokeelessons.dict.engine.Affixes.ToFor;
import com.cherokeelessons.dict.engine.Affixes.ToForᏏ;
import com.cherokeelessons.dict.engine.Affixes.Towards;
import com.cherokeelessons.dict.engine.Affixes.Truly;
import com.cherokeelessons.dict.engine.Affixes.Very;
import com.cherokeelessons.dict.engine.Affixes.WentForDoing;
import com.cherokeelessons.dict.engine.Affixes.YesNo;
import com.cherokeelessons.dict.engine.Affixes.YesYes;
import com.google.gwt.core.shared.GWT;
import commons.lang3.StringUtils;

public enum SuffixGuesser {

	INSTANCE;

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	private SuffixGuesser() {
	}

	public static class Without extends Affixes {
		@Override
		public AffixResult match(String syllabary) {
			if (syllabary.startsWith("Ᏹ")) {
				syllabary = syllabary.substring(1);
			}
			AffixResult match = super.match(syllabary);
			if (match.isMatch
					&& !"ᎾᎿᏁᏂᏃᏄᏅ".contains(StringUtils.left(syllabary, 1))) {
				GWT.log("BAD WITHOUT? " + syllabary);
			}
			return match;
		}

		public Without() {
			completiveStem = true;
			addSet("Ꭵ", "Ꮎ");
		}
	}

	public static class Tool extends Affixes {
		public Tool() {
			completiveStem = false;
			addSet("", "ᏍᏙᏗ");
		}
	}

	/**
	 * Recursively scans for endings in any order... The 1840's stuff doesn't
	 * seem to follow the Disseration's description of affix usage...
	 * 
	 * @param syllabary
	 * @return
	 */
	public List<AffixResult> getMatches(String syllabary) {
		List<AffixResult> list = new ArrayList<Affixes.AffixResult>();
		Without withoutEnding = new Without();
		AffixResult without = withoutEnding.match(syllabary);
		if (without.isMatch) {
			without.desc = "WithOut";
			without.suffix = "Ꮒ/" + without.suffix;
			list.add(without);
			// should only be one stem for a "without" match!
			without.stem.set(0, removeᏂprefix(without.stem.get(0)));
			GWT.log("WITHOUT: " + without.stem.get(0));
			list.addAll(getMatches(without.stem.get(0)));
			return list;
		}
		for (VerbStemAffix affix : VerbStemAffix.values()) {
			Affixes s = SuffixGuesser.getSuffixMatcher(affix);
			AffixResult matchResult = s.match(syllabary);
			if (matchResult.isMatch) {
				matchResult.desc = affix.name();
				list.add(matchResult);
				GWT.log("SuffixGuesser: " + matchResult.stem + "+"
						+ matchResult.suffix + ":" + matchResult.desc);
				for (String stem : matchResult.stem) {
					list.addAll(getMatches(stem));
				}
			}
		}
		for (GeneralAffix affix : GeneralAffix.values()) {
			Affixes s = SuffixGuesser.getSuffixMatcher(affix);
			AffixResult matchResult = s.match(syllabary);
			if (matchResult.isMatch) {
				matchResult.desc = affix.name();
				list.add(matchResult);
				GWT.log("SuffixGuesser: " + matchResult.stem + "+"
						+ matchResult.suffix + ":" + matchResult.desc);
				for (String stem : matchResult.stem) {
					list.addAll(getMatches(stem));
				}
			}
		}
		if (list.size() > 0) {
			GWT.log("SuffixGuesser Results: " + list.toString());
		}
		return list;
	}

	private static Affixes getSuffixMatcher(GeneralAffix affix) {
		switch (affix) {
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
		case Truly:
			return new Truly();
		case Very:
			return new Very();
		case Towards:
			return new Towards();
		case InOnAt:
			return new InOnAt();
		}
		throw new RuntimeException(
				"SPECIFIED SUFFIX MATCHER IS NOT IMPLEMENTED.");
	}

	private String removeᏂprefix(String word) {
		String pre = StringUtils.left(word, 1);
		word = word.substring(1);
		if (pre.equals("Ꮎ") || pre.equals("Ꮏ")) {
			pre = "Ꭰ";
		}
		if (pre.equals("Ꮑ")) {
			pre = "Ꭱ";
		}
		if (pre.equals("Ꮒ")) {
			pre = "";
		}
		if (pre.equals("Ꮓ")) {
			pre = "Ꭳ";
		}
		if (pre.equals("Ꮔ")) {
			pre = "Ꭴ";
		}
		if (pre.equals("Ꮕ")) {
			pre = "Ꭵ";
		}
		return pre + word;
	}

	public static Affixes getSuffixMatcher(VerbStemAffix affix) {
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
			return new Causative();
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
		case AptTo:
			return new AptTo();
		case ToForᏏ:
			return new ToForᏏ();
		}
		throw new RuntimeException(
				"SPECIFIED SUFFIX MATCHER IS NOT IMPLEMENTED.");
	}
}
