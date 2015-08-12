package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.List;

import com.cherokeelessons.dict.engine.VerbAffixes.AboutTo;
import com.cherokeelessons.dict.engine.VerbAffixes.Accidental;
import com.cherokeelessons.dict.engine.VerbAffixes.AffixResult;
import com.cherokeelessons.dict.engine.VerbAffixes.Again;
import com.cherokeelessons.dict.engine.VerbAffixes.AptTo;
import com.cherokeelessons.dict.engine.VerbAffixes.Around;
import com.cherokeelessons.dict.engine.VerbAffixes.CausativePast;
import com.cherokeelessons.dict.engine.VerbAffixes.ComeForDoing;
import com.cherokeelessons.dict.engine.VerbAffixes.Completely;
import com.cherokeelessons.dict.engine.VerbAffixes.IntendTo;
import com.cherokeelessons.dict.engine.VerbAffixes.Repeatedly;
import com.cherokeelessons.dict.engine.VerbAffixes.ToFor;
import com.cherokeelessons.dict.engine.VerbAffixes.ToForᏏ;
import com.cherokeelessons.dict.engine.VerbAffixes.WentForDoing;
import com.google.gwt.core.shared.GWT;
import commons.lang3.StringUtils;

public enum SuffixGuesser {

	INSTANCE;

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	private SuffixGuesser() {
	}

	public static class Without extends VerbAffixes {
		@Override
		public AffixResult match(String syllabary) {
			if (syllabary.startsWith("Ᏹ")){
				syllabary=syllabary.substring(1);
			}
			AffixResult match = super.match(syllabary);
			if (match.isMatch && !"ᎾᎿᏁᏂᏃᏄᏅ".contains(StringUtils.left(syllabary,1))) {
				GWT.log("BAD WITHOUT? "+syllabary);
			}
			return match;
		}
		public Without() {
			completiveStem = true;
			addSet("Ꭵ", "Ꮎ");
		}
	}
	
	public static class Tool extends VerbAffixes {
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
		List<AffixResult> list = new ArrayList<VerbAffixes.AffixResult>();
		Without withoutEnding = new Without();
		AffixResult without = withoutEnding.match(syllabary);
		if (without.isMatch) {
			without.desc = "WithOut";
			without.suffix="Ꮒ/"+without.suffix;
			list.add(without);
			//should only be one stem for a "without" match!
			without.stem.set(0, removeᏂprefix(without.stem.get(0)));
			GWT.log("WITHOUT: "+without.stem.get(0));
			list.addAll(getMatches(without.stem.get(0)));
			return list;
		}
		for (VerbStemAffix affix: VerbStemAffix.values()){
			VerbAffixes s = SuffixGuesser.getSuffixMatcher(affix);
			AffixResult matchResult = s.match(syllabary);
			if (matchResult.isMatch) {
				matchResult.desc = affix.name();
				list.add(matchResult);
				GWT.log("SuffixGuesser: "+matchResult.stem+"+"+matchResult.suffix+":"+matchResult.desc);
				for (String stem: matchResult.stem) {
					list.addAll(getMatches(stem));
				}
			}
		}
		if (list.size()>0) {
			GWT.log("SuffixGuesser Results: "+list.toString());
		}
		return list;
	}

//	public List<AffixResult> getMatches_ordered(String syllabary) {
//		
//		Suffixes apt = SuffixGuesser.getSuffixMatcher(VerbStemAffix.AptTo);
//		Iterator<List<VerbStemAffix>> iseq = VerbStemAffix.getValidSequences().iterator();
//		List<List<AffixResult>> lists = new ArrayList<>();
//
//		Without withoutEnding = new Without();
//		AffixResult without = withoutEnding.match(syllabary);
//		if (without.isMatch) {
//			List<AffixResult> list = new ArrayList<Suffixes.AffixResult>();
//			without.desc = "WithOut";
//			without.suffix="Ꮒ/"+without.suffix;
//			list.add(without);
//			without.stem.add(removeᏂprefix(without.stem.get(0)));
//			List<AffixResult> tmplist = getMatches(without.stem.get(0));
//			if (tmplist.size() > 0) {
//				list.addAll(tmplist);
//			}
//			return list;
//		}
//		
//		seq: while (iseq.hasNext()) {
//			List<AffixResult> list = new ArrayList<Suffixes.AffixResult>();
//			String active = syllabary;
//			for (VerbStemAffix affix : iseq.next()) {
//				Suffixes s = SuffixGuesser.getSuffixMatcher(affix);
//				AffixResult matchResult = s.match(active);
//				if (matchResult.isMatch) {
//					matchResult.desc = affix.name();
//					list.add(matchResult);
//					active = matchResult.stem;
//				}
//			}
//			without = withoutEnding.match(active);
//			if (without.isMatch) {
//				without.desc = "WithOut";
//				list.add(without);
//				without.stem = removeᏂprefix(without.stem);
//				active = without.stem;
//				List<AffixResult> tmplist = getMatches(without.stem);
//				if (tmplist.size() > 0) {
//					list.addAll(tmplist);
//				}
//				lists.add(list);
//				continue seq;
//			}
//			AffixResult matchResult = apt.match(active);
//			if (matchResult.isMatch) {
//				matchResult.desc = "***" + VerbStemAffix.AptTo.name();
//				list.add(matchResult);
//				active = matchResult.stem;
//			}
//			if (list.size() != 0) {
//				lists.add(list);
//			}
//		}
//
//		Iterator<List<AffixResult>> xx = lists.iterator();
//		while (xx.hasNext()) {
//			if (xx.next().size() == 0) {
//				xx.remove();
//			}
//		}
//		if (lists.size() == 0) {
//			return new ArrayList<AffixResult>();
//		}
//		/*
//		 * pick the one with the most matches
//		 */
//		GWT.log("MULTIPLE MATCHES FOUND: " + lists.size());
//		Iterator<List<AffixResult>> il = lists.iterator();
//		List<AffixResult> list = il.next();
//		if (!il.hasNext()) {
//			return list;
//		}
//		while (il.hasNext()) {
//			List<AffixResult> tmp = il.next();
//			GWT.log("MATCH: " + tmp.toString());
//			if (tmp.get(0).suffix.length() < list.get(0).suffix.length()) {
//				continue;
//			}
//			if (tmp.size() < list.size()) {
//				continue;
//			}
//			list = tmp;
//		}
//		return list;
//	}

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

	public static VerbAffixes getSuffixMatcher(VerbStemAffix affix) {
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
//		case Just:
//			return new Just();
//		case SoAnd:
//			return new SoAnd();
//		case Place:
//			return new Place();
//		case YesNo:
//			return new YesNo();
//		case YesYes:
//			return new YesYes();
//		case But:
//			return new But();
		case AptTo:
			return new AptTo();
		case ToForᏏ:
			return new ToForᏏ();
//		case Truly:
//			return new Truly();
//		case Very:
//			return new Very();
//		case Towards:
//			return new Towards();
//		case InOnAt:
//			return new InOnAt();
		}
		throw new RuntimeException(
				"SPECIFIED SUFFIX MATCHER IS NOT IMPLEMENTED.");
	}
}
