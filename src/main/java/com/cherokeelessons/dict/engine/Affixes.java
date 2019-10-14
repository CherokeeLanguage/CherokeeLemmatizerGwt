package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cherokeelessons.dict.client.ConsoleLogHandler2;
import com.cherokeelessons.dict.shared.Log;
import com.cherokeelessons.dict.shared.Syllabary;
import com.cherokeelessons.dict.shared.Syllabary.Vowel;

import commons.lang3.StringUtils;

public abstract class Affixes {

	protected final Logger logger = Log.getGwtLogger(new ConsoleLogHandler2(), this.getClass().getSimpleName());
	
	protected boolean completiveStem = true;

	private static final Map<String, String> vowelSets;

	public static String getVowelSet(Vowel vowel) {
		return vowelSets.get(vowel.name());
	}

	static {
		vowelSets = new HashMap<>();
		vowelSets.put("Ꭰ", "ᎠᎦᎧᎭᎳᎾᎿᏀᏆᏌᏓᏔᏜᏝᏣᏩᏯ");
		vowelSets.put("Ꭱ", "ᎡᎨᎮᎴᏁᏇᏎᏕᏖᏞᏤᏪᏰ");
		vowelSets.put("Ꭲ", "ᎢᎩᎯᎵᏂᏈᏏᏗᏘᏟᏥᏫᏱ");
		vowelSets.put("Ꭳ", "ᎣᎪᎰᎶᏃᏉᏐᏙᏠᏦᏬᏲ");
		vowelSets.put("Ꭴ", "ᎤᎫᎱᎷᏄᏊᏑᏚᏡᏧᏭᏳ");
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

	public static class AffixResult {
		public boolean isMatch = false;
		public String stem;// = new ArrayList<>();
		public String suffix;
		public String mode;
		public String desc;

		@Override
		public String toString() {
			StringBuilder builder = new StringBuilder();
			builder.append("AffixResult [");
			if (suffix != null)
				builder.append("suffix=").append(suffix).append(", ");
			if (stem != null)
				builder.append("stem=").append(stem);
			builder.append("]");
			return builder.toString();
		}
	}

	protected String splitAsCompletive(String syllabary, String suffix) {
		if (suffix.matches("^[ᎠᎡᎢᎣᎤᎥ].*")) {
			syllabary = StringUtils.removeEnd(syllabary, suffix.substring(1));
			syllabary = Syllabary.changeForm(syllabary, Vowel.Ꭵ) + "Ꭲ";
		} else {
			syllabary = StringUtils.removeEnd(syllabary, suffix);
			syllabary = Syllabary.changeForm(syllabary, Vowel.Ꭵ) + "Ꭲ";
		}
		// if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭵ)+"]ᎥᎢ")){
		// syllabary=StringUtils.left(syllabary, syllabary.length()-2)+"Ꭲ";
		// }
		return syllabary;
	}

	protected String splitAsIs(String syllabary, String suffix) {
		syllabary = StringUtils.removeEnd(syllabary, suffix);
		return syllabary;
	}

	protected String splitAsIncompletive(String syllabary, String suffix) {
		return syllabary;
	}

	public AffixResult match(String syllabary) {
		Iterator<String> ipat = patterns.iterator();
		Iterator<String> spat = suffixes.iterator();
		while (ipat.hasNext()) {
			String ending = ipat.next();
			String suffix = spat.next();
			if (syllabary.length() < 3 || syllabary.length() <= ending.length()) {
				continue;
			}
			if (syllabary.endsWith(ending)) {
				AffixResult matchResult = new AffixResult();
				matchResult.isMatch = true;
				matchResult.suffix = suffix;
				if (completiveStem) {
					matchResult.stem=splitAsCompletive(syllabary, suffix);
				} else {
					matchResult.stem=splitAsIs(syllabary, suffix);
				}

				return matchResult;
			}
		}
		return new AffixResult();
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

	public Affixes() {
		patterns = new ArrayList<String>();
		suffixes = new ArrayList<String>();
		
	}

	public static class Repeatedly extends Affixes {

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

	private static class CausativePresent extends Affixes {
		@Override
		public AffixResult match(String syllabary) {
			AffixResult match = super.match(syllabary);
			if (!match.isMatch) {
				return match;
			}
			String root = StringUtils.remove(syllabary, match.suffix);
			if (match.suffix.startsWith("Ꮝ")){
				
			}
			match.stem=root+"Ꭰ";
			return match;
		}
		public CausativePresent() {
			completiveStem = false;
			for (String s : new String[] { "Ꮝ", "" }) {
				addSet("", s + "ᏗᎭ");
			}
		}
	}
	
	private static class CausativePast extends Affixes {
		@Override
		public AffixResult match(String syllabary) {
			AffixResult match = super.match(syllabary);
			if (!match.isMatch) {
				return match;
			}
			String root = StringUtils.remove(syllabary, match.suffix);
			if (match.suffix.startsWith("Ꮝ")){
				
			}
			match.stem=root+"Ꭰ";
			return match;
		}
		public CausativePast() {
			completiveStem = false;
			addSet("", "ᏍᏔᏅ");
			addSet("", "ᏍᏔᏅᎩ");
			addSet("", "ᏍᏔᏅᎢ");
			addSet("", "ᏍᏔᏁᎢ");
			addSet("", "ᏍᏔᏓᏁ");
			addSet("", "ᏍᏓᏅ");
			addSet("", "ᏍᏓᏅᎩ");
			addSet("", "ᏍᏓᏅᎢ");
			addSet("", "ᏍᏓᏁᎢ");
			addSet("", "ᏍᏓᏓᏁ");
			addSet("", "ᏓᏅ");
			addSet("", "ᏓᏅᎩ");
			addSet("", "ᏓᏅᎢ");
			addSet("", "ᏓᏁᎢ");
			addSet("", "ᏓᏁ");
		}
	}

	private static class CausativeProgressive extends Affixes {
		@Override
		public AffixResult match(String syllabary) {
			AffixResult match = super.match(syllabary);
			if (!match.isMatch) {
				return match;
			}
			String root = StringUtils.remove(syllabary, match.suffix);
			if (match.suffix.startsWith("Ꮝ")){
				
			}
			String oform = Syllabary.changeForm(root, Vowel.Ꭳ)+"Ꭲ";
			logger.info("OFORM: "+oform);
			match.stem=oform;
			return match;
		}
		public CausativeProgressive() {
			completiveStem = false;
			for (String s : new String[] { "ᏍᏘ", "Ꮧ" }) {
				addSet("", s + "ᏍᎪ");
				addSet("", s + "ᏍᎪᎢ");
				addSet("", s + "ᏍᎬ");
				addSet("", s + "ᏍᎬᎩ");
				addSet("", s + "ᏍᎬᎢ");
				addSet("", s + "ᏍᎨ");
				addSet("", s + "ᏍᎨᎢ");
				addSet("", s + "ᏍᎨᏍᏗ");
				addSet("", s + "ᏍᎩ");
			}
		}
	}

	private static class CausativeImmediate extends Affixes {
		public CausativeImmediate() {
			completiveStem = false;
			addSet("", "ᏍᏔ");
			addSet("", "ᏍᏓ");
			addSet("", "Ꮣ");
		}
	}

	private static class CausativeDeverbal extends Affixes {
		public CausativeDeverbal() {
			completiveStem = false;
			addSet("", "ᏍᏙᏗ");
			addSet("", "ᏙᏗ");
		}
	}

	public static class Causative extends Affixes {
		private final List<Affixes> forms = new ArrayList<Affixes>();

		@Override
		public AffixResult match(String syllabary) {
			for (Affixes affix : forms) {
				AffixResult match = affix.match(syllabary);
				if (match.isMatch) {
					return match;
				}
			}
			return new AffixResult();
		}

		public Causative() {
			forms.add(new CausativePresent());
			forms.add(new CausativePast());
			forms.add(new CausativeProgressive());
			forms.add(new CausativeImmediate());
			forms.add(new CausativeDeverbal());
		}
	}

	public static class ToForᏏ extends Affixes {
		public ToForᏏ() {
			completiveStem = false;
			addSet("", "Ꮟ");
		}
	}

	public static class ToFor extends Affixes {
		public ToFor() {
			// prc
			addSet("Ꭱ", "Ꭽ");
			// past
			addSet("Ꭱ", "Ꮈ");
			addSet("Ꭱ", "ᎸᎩ");
			addSet("Ꭱ", "ᎸᎢ");
			addSet("Ꭱ", "ᎴᎢ");
			addSet("Ꭱ", "Ꮄ");
			// progressive
			addSet("Ꭱ", "Ꮀ");
			addSet("Ꭱ", "ᎰᎢ");
			addSet("Ꭱ", "Ꮂ");
			addSet("Ꭱ", "ᎲᎩ");
			addSet("Ꭱ", "ᎲᎢ");
			addSet("Ꭱ", "Ꭾ");
			addSet("Ꭱ", "ᎮᎢ");
			addSet("Ꭱ", "ᎮᏍᏗ");
			addSet("Ꭱ", "Ꭿ");
			// immediate
			addSet("Ꭱ", "Ꮅ");
			// infinitive
			addSet("Ꭱ", "Ꮧ");
		}
	}

	public static class Again extends Affixes {

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

	public static class AboutTo extends Affixes {
		public AboutTo() {
			completiveStem = false;
			// simpleSplitStem=true;
			// vowelFixStem=false;
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

	public static class IntendTo extends Affixes {
		public IntendTo() {
			completiveStem = true;
			// progressive
			addSet("Ꭲ", "ᏎᏍᏗ");
			addSet("Ꭲ", "Ꮢ");
			addSet("Ꭲ", "ᏒᎩ");
			addSet("Ꭲ", "ᏒᎢ");
			addSet("Ꭲ", "ᏎᎢ");
			addSet("Ꭲ", "Ꮞ");
		}
	};

	public static class Just extends Affixes {
		public Just() {
			completiveStem = false;
			// 1844
			addSet("", "Ꮙ");
			// CED
			addSet("", "Ꮚ");
		}
	};

	public static class Very extends Affixes {
		public Very() {
			completiveStem = false;
			addSet("", "Ᏻ");
		}
	};

	public static class Truly extends Affixes {
		public Truly() {
			completiveStem = false;
			addSet("", "Ꮿ");
			addSet("", "ᏯᎢ");
		}
	};

	public static class Towards extends Affixes {
		public Towards() {
			completiveStem = false;
			// 1844
			addSet("", "ᏗᏢ");
			// CED
			addSet("", "ᏗᏜ");
		}
	};

	public static class But extends Affixes {
		public But() {
			completiveStem = false;
			addSet("", "ᏍᎩᏂ");
		}
	};

	public static class YesNo extends Affixes {
		public YesNo() {
			completiveStem = false;
			addSet("", "Ꮝ");
			addSet("", "ᏍᎪ");
		}
	};

	public static class YesYes extends Affixes {
		public YesYes() {
			completiveStem = false;
			addSet("", "Ꮷ");
		}
	};

	public static class Place extends Affixes {
		public Place() {
			completiveStem = false;
			// 1844
			addSet("", "Ᏹ");
		}
	};

	public static class InOnAt extends Affixes {
		@Override
		public AffixResult match(String syllabary) {
			AffixResult match = super.match(syllabary);
			if (!match.isMatch) {
				return match;
			}
			if (!match.suffix.equals("Ꭿ")){
				match.suffix="Ꭿ";
				match.stem=Syllabary.changeForm(match.stem, Vowel.Ꭰ);
			}
			return match;
		}
		public InOnAt() {
			completiveStem = false;
			// 1844
			addSet("Ꭳ", "Ꭿ");
			addSet("", "Ꭿ");
		}
	};

	public static class SoAnd extends Affixes {
		public SoAnd() {
			completiveStem = false;
			addSet("", "Ꮓ");
		}
	};

	public static class WithIntent extends Affixes {

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

	public static class WentForDoing extends Affixes {

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

	public static class ComeForDoing extends Affixes {

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

	public static class Around extends Affixes {

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

	public static class Completely extends Affixes {

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

	public static class Accidental extends Affixes {

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

	public static class AptTo extends Affixes {
		public AptTo() {
			// present
			addSet("Ꭰ", "Ꮤ");
			// progressive
			addSet("Ꭰ", "Ꮩ");
			addSet("Ꭰ", "ᏙᎢ");
			addSet("Ꭰ", "Ꮫ");
			addSet("Ꭰ", "ᏛᎩ");
			addSet("Ꭰ", "ᏛᎢ");
			addSet("Ꭰ", "Ꮥ");
			addSet("Ꭰ", "ᏕᎢ");
			addSet("Ꭰ", "ᏕᏍᏗ");
		}
	}
}
