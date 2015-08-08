package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cherokeelessons.dict.shared.Suffixes.AboutTo;
import com.cherokeelessons.dict.shared.Suffixes.Accidental;
import com.cherokeelessons.dict.shared.Suffixes.Again;
import com.cherokeelessons.dict.shared.Suffixes.AptTo;
import com.cherokeelessons.dict.shared.Suffixes.Around;
import com.cherokeelessons.dict.shared.Suffixes.But;
import com.cherokeelessons.dict.shared.Suffixes.CausativePast;
import com.cherokeelessons.dict.shared.Suffixes.ComeForDoing;
import com.cherokeelessons.dict.shared.Suffixes.Completely;
import com.cherokeelessons.dict.shared.Suffixes.InOnAt;
import com.cherokeelessons.dict.shared.Suffixes.IntendTo;
import com.cherokeelessons.dict.shared.Suffixes.Just;
import com.cherokeelessons.dict.shared.Suffixes.MatchResult;
import com.cherokeelessons.dict.shared.Suffixes.Place;
import com.cherokeelessons.dict.shared.Suffixes.Repeatedly;
import com.cherokeelessons.dict.shared.Suffixes.SoAnd;
import com.cherokeelessons.dict.shared.Suffixes.ToFor;
import com.cherokeelessons.dict.shared.Suffixes.ToForᏏ;
import com.cherokeelessons.dict.shared.Suffixes.Towards;
import com.cherokeelessons.dict.shared.Suffixes.Truly;
import com.cherokeelessons.dict.shared.Suffixes.Very;
import com.cherokeelessons.dict.shared.Suffixes.WentForDoing;
import com.cherokeelessons.dict.shared.Suffixes.YesNo;
import com.cherokeelessons.dict.shared.Suffixes.YesYes;
import com.cherokeelessons.dict.shared.Syllabary.Vowel;
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
		public Without() {
			vowelFixStem = true;
			addSet("Ꭵ", "Ꮎ");
		}
	}

	public List<MatchResult> getMatches(String syllabary) {
		GWT.log("PROCESSING: " + syllabary);
		// TODO: Figure WHERE this should go in the infix list
		Suffixes apt = SuffixGuesser.getSuffixMatcher(Affix.AptTo);

		Without withoutEnding = new Without();

		Iterator<List<Affix>> iseq = Affix.getValidSequences().iterator();
		List<List<MatchResult>> lists = new ArrayList<>();
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
			MatchResult without = withoutEnding.match(active);
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
