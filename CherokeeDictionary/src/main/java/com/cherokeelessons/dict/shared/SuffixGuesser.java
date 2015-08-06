package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.cherokeelessons.dict.shared.Suffixes.AboutTo;
import com.cherokeelessons.dict.shared.Suffixes.Accidental;
import com.cherokeelessons.dict.shared.Suffixes.Again;
import com.cherokeelessons.dict.shared.Suffixes.Around;
import com.cherokeelessons.dict.shared.Suffixes.ComeForDoing;
import com.cherokeelessons.dict.shared.Suffixes.Completely;
import com.cherokeelessons.dict.shared.Suffixes.ForTo;
import com.cherokeelessons.dict.shared.Suffixes.Repeatedly;
import com.cherokeelessons.dict.shared.Suffixes.WentForDoing;
import com.google.gwt.core.shared.GWT;
import commons.lang.StringUtils;

public class SuffixGuesser {

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	public static class FullSuffixEntry {
		public FullSuffixEntry(String desc, String suffix) {
			this.appendable = suffix.endsWith("*");
			this.desc = desc;
			this.suffix = suffix.replace("*", "");
		}

		public String suffix;
		public String desc;
		public boolean appendable;
	}

	public SuffixGuesser() {
		GWT.log("SuffixGuesser: INIT START: " + String.valueOf(new Date()));
		GWT.log("COUNT: " + getSuffixes().size());
		GWT.log("SuffixGuesser: INIT DONE: " + String.valueOf(new Date()));
	}

	public List<FullSuffixEntry> getSuffixes() {
		List<FullSuffixEntry> result = new ArrayList<>();

		for (Affix a: Affix.values()) {
			GWT.log("PROCESSED ROOT: ["+a.name()+"] "+generateRecursive(null, a).size());
		}

		return result;
	}

	public List<String> generateRecursive(String prepend, Affix affix) {
		List<String> list = new ArrayList<String>();
		switch (affix) {
		case AboutTo:
			list.addAll(combine(affix.isFollowedBy, new AboutTo(prepend)));
			break;
		case Again:
			list.addAll(combine(affix.isFollowedBy, new Again(prepend)));
			break;
		case Around:
			list.addAll(combine(affix.isFollowedBy, new Around(prepend)));
			break;
		case ByAccident:
			list.addAll(combine(affix.isFollowedBy, new Accidental(prepend)));
			break;
		case CameFor:
			list.addAll(combine(affix.isFollowedBy, new ComeForDoing(prepend)));
			break;
		case Causative:
			// i = new Causative(prepend).patterns.iterator();
			// while (i.hasNext()) {
			// String suffix = i.next();
			// if (suffix.endsWith("*")){
			// suffix=suffix.replace("*", "");
			// for (Affix followedBy: affix.beFollowedBy){
			// list.addAll(generatedRecursive(suffix, followedBy));
			// }
			// list.add(suffix);
			// }
			// }
			break;
		case Completely:
			list.addAll(combine(affix.isFollowedBy, new Completely(prepend)));
			break;
		case OverAndOver:
			list.addAll(combine(affix.isFollowedBy, new Repeatedly(prepend)));
			break;
		case ToFor:
			list.addAll(combine(affix.isFollowedBy, new ForTo(prepend)));
			break;
		case WentTo:
			list.addAll(combine(affix.isFollowedBy, new WentForDoing(prepend)));
			break;
		default:
			break;

		}
		return list;
	}

	private List<String> combine(Set<Affix> affixs, Suffixes suffixes) {
		List<String> list = new ArrayList<String>();
		Iterator<String> i = suffixes.getPatterns().iterator();
		while (i.hasNext()) {
			String suffix = i.next();
			if (suffix.endsWith("*")) {
				suffix = suffix.replace("*", "");
				for (Affix followedBy : affixs) {
					list.addAll(generateRecursive(suffix, followedBy));
				}
			}
			list.add(suffix);
		}
		return list;
	}
}
