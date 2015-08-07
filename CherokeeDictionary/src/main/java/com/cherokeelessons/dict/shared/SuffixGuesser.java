package com.cherokeelessons.dict.shared;

import com.cherokeelessons.dict.shared.Suffixes.AboutTo;
import com.cherokeelessons.dict.shared.Suffixes.Accidental;
import com.cherokeelessons.dict.shared.Suffixes.Again;
import com.cherokeelessons.dict.shared.Suffixes.Around;
import com.cherokeelessons.dict.shared.Suffixes.CausativePast;
import com.cherokeelessons.dict.shared.Suffixes.ComeForDoing;
import com.cherokeelessons.dict.shared.Suffixes.Completely;
import com.cherokeelessons.dict.shared.Suffixes.IntendTo;
import com.cherokeelessons.dict.shared.Suffixes.Just;
import com.cherokeelessons.dict.shared.Suffixes.Repeatedly;
import com.cherokeelessons.dict.shared.Suffixes.ToFor;
import com.cherokeelessons.dict.shared.Suffixes.WentForDoing;

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
		}
		return null;
	}
}
