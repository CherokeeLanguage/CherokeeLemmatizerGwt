package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.cherokeelessons.dict.engine.Suffixes.AboutTo;
import com.cherokeelessons.dict.engine.Suffixes.Accidental;
import com.cherokeelessons.dict.engine.Suffixes.Again;
import com.cherokeelessons.dict.engine.Suffixes.AptTo;
import com.cherokeelessons.dict.engine.Suffixes.Around;
import com.cherokeelessons.dict.engine.Suffixes.But;
import com.cherokeelessons.dict.engine.Suffixes.CausativePast;
import com.cherokeelessons.dict.engine.Suffixes.ComeForDoing;
import com.cherokeelessons.dict.engine.Suffixes.Completely;
import com.cherokeelessons.dict.engine.Suffixes.InOnAt;
import com.cherokeelessons.dict.engine.Suffixes.IntendTo;
import com.cherokeelessons.dict.engine.Suffixes.Just;
import com.cherokeelessons.dict.engine.Suffixes.MatchResult;
import com.cherokeelessons.dict.engine.Suffixes.Place;
import com.cherokeelessons.dict.engine.Suffixes.Repeatedly;
import com.cherokeelessons.dict.engine.Suffixes.SoAnd;
import com.cherokeelessons.dict.engine.Suffixes.ToFor;
import com.cherokeelessons.dict.engine.Suffixes.ToForᏏ;
import com.cherokeelessons.dict.engine.Suffixes.Towards;
import com.cherokeelessons.dict.engine.Suffixes.Truly;
import com.cherokeelessons.dict.engine.Suffixes.Very;
import com.cherokeelessons.dict.engine.Suffixes.WentForDoing;
import com.cherokeelessons.dict.engine.Suffixes.YesNo;
import com.cherokeelessons.dict.engine.Suffixes.YesYes;
import com.cherokeelessons.dict.shared.Affix;
import com.google.gwt.core.shared.GWT;
import commons.lang3.StringUtils;

public enum SuffixGuesser {

	INSTANCE;

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	private SuffixGuesser() {
	}

	public static class Without extends Suffixes {
		@Override
		public MatchResult match(String syllabary) {
			if (syllabary.startsWith("Ᏹ")){
				syllabary=syllabary.substring(1);
			}
			MatchResult match = super.match(syllabary);
			if (match.isMatch && !"ᎾᎿᏁᏂᏃᏄᏅ".contains(StringUtils.left(syllabary,1))) {
				GWT.log("BAD WITHOUT? "+syllabary);
			}
			return match;
		}
		public Without() {
			vowelFixStem = true;
			addSet("Ꭵ", "Ꮎ");
		}
	}

	public List<MatchResult> getMatches(String syllabary) {
		
		// TODO: Figure WHERE this should go in the infix list
		Suffixes apt = SuffixGuesser.getSuffixMatcher(Affix.AptTo);
		Iterator<List<Affix>> iseq = Affix.getValidSequences().iterator();
		List<List<MatchResult>> lists = new ArrayList<>();

		Without withoutEnding = new Without();
		MatchResult without = withoutEnding.match(syllabary);
		if (without.isMatch) {
			List<MatchResult> list = new ArrayList<Suffixes.MatchResult>();
			without.desc = "WithOut";
			without.suffix="Ꮒ/"+without.suffix;
			list.add(without);
			without.stem = removeᏂprefix(without.stem);
			List<MatchResult> tmplist = getMatches(without.stem);
			if (tmplist.size() > 0) {
				list.addAll(tmplist);
			}
			return list;
		}
		
		seq: while (iseq.hasNext()) {
			List<MatchResult> list = new ArrayList<Suffixes.MatchResult>();
			String active = syllabary;
			for (Affix affix : iseq.next()) {
				Suffixes s = SuffixGuesser.getSuffixMatcher(affix);
				MatchResult matchResult = s.match(active);
				if (matchResult.isMatch) {
					matchResult.desc = affix.name();
					list.add(matchResult);
					active = matchResult.stem;
				}
			}
			without = withoutEnding.match(active);
			if (without.isMatch) {
				without.desc = "WithOut";
				list.add(without);
				without.stem = removeᏂprefix(without.stem);
				active = without.stem;
				List<MatchResult> tmplist = getMatches(without.stem);
				if (tmplist.size() > 0) {
					list.addAll(tmplist);
				}
				lists.add(list);
				continue seq;
			}
			MatchResult matchResult = apt.match(active);
			if (matchResult.isMatch) {
				matchResult.desc = "***" + Affix.AptTo.name();
				list.add(matchResult);
				active = matchResult.stem;
			}
			if (list.size() != 0) {
				lists.add(list);
			}
		}

		Iterator<List<MatchResult>> xx = lists.iterator();
		while (xx.hasNext()) {
			if (xx.next().size() == 0) {
				xx.remove();
			}
		}
		if (lists.size() == 0) {
			return new ArrayList<MatchResult>();
		}
		/*
		 * pick the one with the most matches
		 */
		GWT.log("MULTIPLE MATCHES FOUND: " + lists.size());
		Iterator<List<MatchResult>> il = lists.iterator();
		List<MatchResult> list = il.next();
		if (!il.hasNext()) {
			return list;
		}
		while (il.hasNext()) {
			List<MatchResult> tmp = il.next();
			GWT.log("MATCH: " + tmp.toString());
			if (tmp.get(0).suffix.length() < list.get(0).suffix.length()) {
				continue;
			}
			if (tmp.size() < list.size()) {
				continue;
			}
			list = tmp;
		}
		return list;
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
		case AptTo:
			return new AptTo();
		case ToForᏏ:
			return new ToForᏏ();
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
}
