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
import com.cherokeelessons.dict.shared.Suffixes.CausativePast;
import com.cherokeelessons.dict.shared.Suffixes.ComeForDoing;
import com.cherokeelessons.dict.shared.Suffixes.Completely;
import com.cherokeelessons.dict.shared.Suffixes.ForTo;
import com.cherokeelessons.dict.shared.Suffixes.Repeatedly;
import com.cherokeelessons.dict.shared.Suffixes.WentForDoing;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.shared.GWT;

public enum SuffixGuesser {

	INSTANCE;

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	public boolean isReady = false;

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

	private SuffixGuesser() {
//		init();
	}

	private final List<FullSuffixEntry> suffixes = new ArrayList<>();

	private void init() {
		GWT.log("SuffixGuesser: INIT START: " + String.valueOf(new Date()));
		Scheduler.get().scheduleIncremental(new RepeatingCommand() {
			private int i = 0;

			@Override
			public boolean execute() {
				if (i < Affix.values().length) {
					final Affix a = Affix.values()[i++];
					GWT.log("BUILDING SUFFIX ENTRIES FOR: " + a.name()+" ("+i+")");
					final List<String> entries = generateRecursive(null, a);
					GWT.log("\tEntries: " + entries.size());
					for (String entry : entries) {
						FullSuffixEntry fse = new FullSuffixEntry(a.name(),
								entry);
						suffixes.add(fse);
					}
					return true;
				}
				INSTANCE.isReady = true;
				GWT.log("SuffixGuesser: INIT DONE: "
						+ String.valueOf(new Date()));
				return false;
			}
		});
	}
	
	public static Suffixes getSuffixMatcher(Affix affix) {
		switch (affix) {
		case AboutTo:
			return new AboutTo(null);
		case Again:
			return new Again(null);
		case Around:
			return new Around(null);
		case ByAccident:
			return new Accidental(null);
		case CameFor:
			return new ComeForDoing(null);
		case Causative:
			// TODO: Causative, not past tense is special
			return new CausativePast(null);
		case Completely:
			return new Completely(null);
		case OverAndOver:
			return new Repeatedly(null);
		case ToFor:
			return new ForTo(null);
		case WentTo:
			return new WentForDoing(null);
		default:
			break;

		}
		return null;
	}

	private List<String> generateRecursive(String prepend, Affix affix) {
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
			// TODO: Causative, not past tense is special
			list.addAll(combine(affix.isFollowedBy, new CausativePast(prepend)));
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
