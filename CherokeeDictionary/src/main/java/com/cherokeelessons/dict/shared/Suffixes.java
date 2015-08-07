package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.cherokeelessons.dict.shared.Syllabary.Vowel;

import commons.lang.StringUtils;

public abstract class Suffixes {

	protected boolean vowelFixStem=true;
	
	private static final Map<String, String> vowelSets;

	static {
		vowelSets = new HashMap<>();
		vowelSets.put("Ꭰ", "ᎠᎦᎧᎭᎳᎹᎾᎿᏀᏆᏌᏓᏔᏜᏝᏣᏩᏯ");
		vowelSets.put("Ꭱ", "ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ");
		vowelSets.put("Ꭲ", "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ");
		vowelSets.put("Ꭳ", "ᎣᎪᎰᎶᎼᏃᏉᏐᏙᏠᏦᏬᏲ");
		vowelSets.put("Ꭴ", "ᎤᎫᎱᎷᎽᏄᏊᏑᏚᏡᏧᏭᏳ");
		vowelSets.put("Ꭵ", "ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ");
	}

	@SuppressWarnings("unused")
	private Comparator<String> byLengthDesc = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == o2) {
				return 0;
			}
			if (o1 == null) {
				return 1;
			}
			if (o1.length() != o2.length()) {
				return o2.length() - o1.length();
			}
			return o1.compareTo(o2);
		}
	};

	public static class MatchResult {
		public boolean isMatch = false;
		public String stem;
		public String suffix;
		public String mode;
		public String desc;
	}

	protected String splitAsCompletive(String syllabary, String suffix) {
		if (suffix.matches("^[ᎠᎡᎢᎣᎤᎥ].*")) {
			syllabary = StringUtils.removeEnd(syllabary, suffix.substring(1));
			syllabary = Syllabary.changeForm(syllabary, Vowel.Ꭵ)+"Ꭲ";
		} else {
			syllabary = StringUtils.removeEnd(syllabary, suffix);
			syllabary = Syllabary.changeForm(syllabary, Vowel.Ꭵ)+"Ꭲ";
		}
		return syllabary;
	}
	
	protected String splitAsIs(String syllabary, String suffix) {
		if (suffix.matches("^[ᎠᎡᎢᎣᎤᎥ].*")) {
			syllabary = StringUtils.removeEnd(syllabary, suffix.substring(1));
		} else {
			syllabary = StringUtils.removeEnd(syllabary, suffix);
		}
		return syllabary;
	}

	protected String splitAsIncompletive(String syllabary, String suffix) {
		return syllabary;
	}

	public MatchResult match(String syllabary) {
		Iterator<String> ipat = patterns.iterator();
		Iterator<String> spat = suffixes.iterator();
		while (ipat.hasNext()) {
			String ending = ipat.next();
			String suffix = spat.next();
			if (syllabary.endsWith(ending)) {
				MatchResult matchResult = new MatchResult();
				matchResult.isMatch = true;
				matchResult.suffix = suffix;
				if (vowelFixStem) {
					matchResult.stem = splitAsCompletive(syllabary, suffix);
				} else {
					matchResult.stem = splitAsIs(syllabary, suffix);
				}
				return matchResult;
			}
		}
		return new MatchResult();
	}

	public List<String> getPatterns() {
		return patterns;
	}

	protected final List<String> patterns;
	protected final List<String> suffixes;

	protected void addSet(String vowel, String suffix) {
		String vowelSet = vowelSets.get(vowel);
		if (vowelSet == null) {
			patterns.add(suffix);
			suffixes.add(suffix);
			return;
		}
		String[] syllables = vowelSet.split("");
		for (String s : syllables) {
			if (StringUtils.isBlank(s)) {
				continue;
			}
			patterns.add(s + suffix);
			suffixes.add(vowel + suffix);
		}
	}

	public Suffixes() {
		patterns = new ArrayList<String>();
		suffixes = new ArrayList<String>();
	}

	public static class Repeatedly extends Suffixes {

		public Repeatedly() {
			addSet("Ꭲ", "ᎶᎠ");
			addSet("Ꭲ", "ᎶᎥ");
			addSet("Ꭲ", "ᎶᎥᎩ");
			addSet("Ꭲ", "ᎶᎥᎢ");
			addSet("Ꭲ", "ᎶᎡᎢ");
			addSet("Ꭲ", "ᎶᎡ");
			addSet("Ꭲ", "ᎶᏍᎨᏍᏗ");
			addSet("Ꭲ", "ᎶᏍᎬ");
			addSet("Ꭲ", "ᎶᏍᎬᎩ");
			addSet("Ꭲ", "ᎶᏍᎬᎢ");
			addSet("Ꭲ", "ᎶᏍᎨᎢ");
			addSet("Ꭲ", "ᎶᏍᎨ");
			addSet("Ꭲ", "ᎶᏍᎪ");
			addSet("Ꭲ", "ᎶᏍᎪᎢ");
			addSet("Ꭲ", "ᎶᏍᎩ");
			addSet("Ꭲ", "ᎶᏣ");
			addSet("Ꭲ", "ᎶᏍᏗ");
		}
	}

	public static class CausativePast extends Suffixes {

		public CausativePast() {
			for (String s : new String[] { "Ꮝ", "" }) {
				addSet("", s + "ᏓᏅ");
				addSet("", s + "ᏓᏅᎩ");
				addSet("", s + "ᏓᏅᎢ");
				addSet("", s + "ᏓᏁᎢ");
				addSet("", s + "ᏓᏁ");
			}
		}
	}

	public static class ToFor extends Suffixes {

		public ToFor() {

			addSet("", "Ꮟ");

			// prc
			addSet("Ꭱ", "Ꭽ");
			// past
			addSet("Ꭱ", "Ꮈ");
			addSet("Ꭱ", "ᎸᎩ");
			addSet("Ꭱ", "ᎸᎢ");
			addSet("Ꭱ", "ᎴᎢ");
			addSet("Ꭱ", "Ꮄ");
			// progressive
			addSet("Ꭱ", "ᎮᏍᏗ");
			addSet("Ꭱ", "Ꮂ");
			addSet("Ꭱ", "ᎲᎩ");
			addSet("Ꭱ", "ᎲᎢ");
			addSet("Ꭱ", "Ꭾ");
			addSet("Ꭱ", "ᎮᎢ");
			addSet("Ꭱ", "Ꮀ");
			addSet("Ꭱ", "ᎰᎢ");
			addSet("Ꭱ", "Ꭿ");
			addSet("Ꭱ", "Ꮅ");
			addSet("Ꭱ", "Ꮧ");
		}
	}

	public static class Again extends Suffixes {

		public Again() {
			addSet("Ꭲ", "ᏏᎭ");
			addSet("Ꭲ", "ᏌᏅ");
			addSet("Ꭲ", "ᏌᏅᎩ");
			addSet("Ꭲ", "ᏌᏅᎢ");
			addSet("Ꭲ", "ᏌᏁᎢ");
			addSet("Ꭲ", "ᏌᏁ");
			addSet("Ꭲ", "ᏏᏍᎨᏍᏗ");
			addSet("Ꭲ", "ᏏᏍᎬ");
			addSet("Ꭲ", "ᏏᏍᎬᎩ");
			addSet("Ꭲ", "ᏏᏍᎬᎢ");
			addSet("Ꭲ", "ᏏᏍᎨᎢ");
			addSet("Ꭲ", "ᏏᏍᎨ");
			addSet("Ꭲ", "ᏏᏍᎪ");
			addSet("Ꭲ", "ᏏᏍᎪᎢ");
			addSet("Ꭲ", "ᏏᏍᎩ");
			addSet("Ꭲ", "Ꮜ");
			addSet("Ꭲ", "ᏐᏗ");
		}
	}

	public static class AboutTo extends Suffixes {
		public AboutTo() {
			// present
			addSet("Ꭲ", "Ꮧ");
			// past
			addSet("Ꭲ", "ᏗᏒ");
			addSet("Ꭲ", "ᏗᏒᎩ");
			addSet("Ꭲ", "ᏗᏒᎢ");
			addSet("Ꭲ", "ᏗᏎ");
			addSet("Ꭲ", "ᏗᏎᎢ");
			// progressive
			addSet("Ꭲ", "ᏗᏍᎨᏍᏗ");
			addSet("Ꭲ", "ᏗᏍᎬᎩ");
			addSet("Ꭲ", "ᏗᏍᎬᎢ");
			addSet("Ꭲ", "ᏗᏍᎬ");
			addSet("Ꭲ", "ᏗᏍᎨᎢ");
			addSet("Ꭲ", "ᏗᏍᎨ");
			addSet("Ꭲ", "ᏗᏍᎪᎢ");
			addSet("Ꭲ", "ᏗᏍᎪ");
			addSet("Ꭲ", "ᏗᏍᎩ");
			// immeidate
			addSet("Ꭲ", "ᏕᎾ");
		}
	};

	public static class IntendTo extends Suffixes {
		public IntendTo() {
			// progressive
			addSet("Ꭲ", "ᏎᏍᏗ");
			addSet("Ꭲ", "Ꮢ");
			addSet("Ꭲ", "ᏒᎩ");
			addSet("Ꭲ", "ᏒᎢ");
			addSet("Ꭲ", "ᏎᎢ");
			addSet("Ꭲ", "Ꮞ");
		}
	};

	public static class Just extends Suffixes {
		public Just() {
			vowelFixStem=false;
			// 1844
			addSet("", "Ꮙ");
			// CED
			addSet("", "Ꮚ");
		}
	};
	
	public static class But extends Suffixes {
		public But() {
			vowelFixStem=false;
			addSet("", "ᏍᎩᏂ");
		}
	};
	
	public static class YesNo extends Suffixes {
		public YesNo() {
			vowelFixStem=false;
			addSet("", "Ꮝ");
			addSet("", "ᏍᎪ");
		}
	};
	
	public static class YesYes extends Suffixes {
		public YesYes() {
			vowelFixStem=false;
			addSet("", "Ꮷ");
		}
	};
	
	public static class Place extends Suffixes {
		public Place() {
			vowelFixStem=false;
			// 1844
			addSet("", "Ᏹ");
		}
	};
	
	public static class SoAnd extends Suffixes {
		public SoAnd() {
			vowelFixStem=false;
			addSet("", "Ꮓ");
		}
	};

	public static class WithIntent extends Suffixes {

		public WithIntent() {
			addSet("Ꭲ", "Ꮢ");
			addSet("Ꭲ", "ᏒᎩ");
			addSet("Ꭲ", "ᏒᎢ");
			addSet("Ꭲ", "Ꮞ");
			addSet("Ꭲ", "ᏎᎢ");
			addSet("Ꭲ", "ᏎᏍᏗ");
			addSet("Ꭲ", "Ꮠ");
			addSet("Ꭲ", "ᏐᎢ");
			addSet("Ꭲ", "Ꮟ");
		}
	}

	public static class WentForDoing extends Suffixes {

		public WentForDoing() {

			addSet("Ꭱ", "Ꭶ");
			addSet("Ꭱ", "Ꮎ");

			addSet("Ꭱ", "ᎨᏍᏗ");
			addSet("Ꭱ", "Ꭼ");
			addSet("Ꭱ", "ᎬᎩ");
			addSet("Ꭱ", "ᎬᎢ");
			addSet("Ꭱ", "ᎨᎢ");
			addSet("Ꭱ", "Ꭸ");
			addSet("Ꭱ", "ᎪᎢ");
			addSet("Ꭱ", "Ꭺ");
			addSet("Ꭱ", "Ꭹ");

			addSet("Ꭴ", "Ꭶ");

			addSet("Ꭵ", "Ꮢ");
			addSet("Ꭵ", "ᏒᎩ");
			addSet("Ꭵ", "ᏒᎢ");
			addSet("Ꭵ", "Ꮞ");
			addSet("Ꭵ", "ᏎᎢ");
			addSet("Ꭵ", "ᏍᏗ");
		}
	}

	public static class ComeForDoing extends Suffixes {

		public ComeForDoing() {

			addSet("Ꭲ", "Ꭶ");
			addSet("Ꭲ", "Ꮈ");
			addSet("Ꭲ", "ᎸᎩ");
			addSet("Ꭲ", "ᎸᎢ");
			addSet("Ꭲ", "Ꮄ");
			addSet("Ꭲ", "ᎴᎢ");
			addSet("Ꭲ", "ᎯᎮᏍᏗ");
			addSet("Ꭲ", "ᎯᎲ");
			addSet("Ꭲ", "ᎯᎲᎩ");
			addSet("Ꭲ", "ᎯᎲᎢ");
			addSet("Ꭲ", "ᎯᎮ");
			addSet("Ꭲ", "ᎯᎮᎢ");
			addSet("Ꭲ", "ᎯᎰ");
			addSet("Ꭲ", "ᎯᎰᎢ");
			addSet("Ꭲ", "ᎯᎯ");
			addSet("Ꭲ", "Ꭶ");
			addSet("Ꭲ", "ᏍᏗ");
		}
	}

	public static class Around extends Suffixes {

		public Around() {
			addSet("Ꭲ", "ᏙᎭ");
			addSet("Ꭲ", "ᏙᎸ");
			addSet("Ꭲ", "ᏙᎸᎩ");
			addSet("Ꭲ", "ᏙᎸᎢ");
			addSet("Ꭲ", "ᏙᎴᎢ");
			addSet("Ꭲ", "ᏙᎴ");
			addSet("Ꭲ", "ᏙᎰᎢ");
			addSet("Ꭲ", "ᏙᎰ");
			addSet("Ꭲ", "ᏙᎲᎢ");
			addSet("Ꭲ", "ᏙᎲᎩ");
			addSet("Ꭲ", "ᏙᎲ");
			addSet("Ꭲ", "ᏙᎮᎢ");
			addSet("Ꭲ", "ᏙᎮ");
			addSet("Ꭲ", "ᏙᎮᏍᏗ");
			addSet("Ꭲ", "ᏙᎯ");
			addSet("Ꭲ", "Ꮣ");
			addSet("Ꭲ", "ᏓᏍᏗ");
		}
	}

	public static class Completely extends Suffixes {

		public Completely() {
			// present
			addSet("Ꭳ", "ᎲᏍᎦ");
			// past
			addSet("Ꭳ", "Ꮕ");
			addSet("Ꭳ", "ᏅᎩ");
			addSet("Ꭳ", "ᏅᎢ");
			addSet("Ꭳ", "Ꮑ");
			addSet("Ꭳ", "ᏁᎢ");
			// progressive
			addSet("Ꭳ", "ᎲᏍᎪ");
			addSet("Ꭳ", "ᎲᏍᎪᎢ");
			addSet("Ꭳ", "ᎲᏍᎬ");
			addSet("Ꭳ", "ᎲᏍᎬᎩ");
			addSet("Ꭳ", "ᎲᏍᎬᎢ");
			addSet("Ꭳ", "ᎲᏍᎨ");
			addSet("Ꭳ", "ᎲᏍᎨᎢ");
			addSet("Ꭳ", "ᎲᏍᎨᏍᏗ");
			addSet("Ꭳ", "ᎲᏍᎩ");
			// immediate
			addSet("Ꭳ", "Ꮏ");
			// deverbal
			addSet("Ꭳ", "ᎲᏍᏗ");
		}
	}

	public static class Accidental extends Suffixes {

		public Accidental() {
			// present
			// past
			addSet("", "ᏙᏓᏅ");
			addSet("", "ᏙᏓᏅᎩ");
			addSet("", "ᏙᏓᏅᎢ");
			addSet("", "ᏙᏓᏁ");
			addSet("", "ᏙᏓᏁᎢ");
			// progressive
			addSet("", "ᏙᏗᏍᎪ");
			addSet("", "ᏙᏗᏍᎪᎢ");
			addSet("", "ᏙᏗᏍᎬ");
			addSet("", "ᏙᏗᏍᎬᎩ");
			addSet("", "ᏙᏗᏍᎬᎢ");
			addSet("", "ᏙᏗᏍᎨ");
			addSet("", "ᏙᏗᏍᎨᎢ");
			addSet("", "ᏙᏗᏍᎩ");
			// immediate
			addSet("", "ᏙᏓ");
			// deverbal
			addSet("", "ᏙᏗ");
		}
	}
}
