package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.gwt.core.shared.GWT;

public class SuffixGuesser {

	public static enum Stem {
		Present, Past, Progressive, Immediate, Deverbal
	}

	public static enum Affix {
		AboutTo(), WentTo(AboutTo), CameFor(AboutTo), Around(CameFor, WentTo), ToFor(
				Around), Completely(ToFor), ByAccident(Completely), Causative(
				Completely), OverAndOver(Causative, ByAccident), Again(
				OverAndOver);
		private Affix(Affix... affixs) {
			if (affixs==null) {
				return;
			}
			beFollowedBy.addAll(Arrays.asList(affixs));
			for (Affix affix: affixs) {
				beFollowedBy.addAll(affix.beFollowedBy);
			}
			GWT.log("AFFIX: "+this.name()+" -> "+beFollowedBy.toString());
		}
		public Set<Affix> beFollowedBy=new HashSet<>();
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
		GWT.log("AFFIXES: "+Affix.values());
	}

	public List<FullSuffixEntry> getSuffixes() {
		List<FullSuffixEntry> result = new ArrayList<>();

		/**
		 * primary suffixes
		 */
		for (String suffix : again(null)) {
			result.add(new FullSuffixEntry("Again", suffix));
		}

		for (String suffix : isaboutto(null)) {
			result.add(new FullSuffixEntry("AboutTo", suffix));
		}

		for (String suffix : comefordoing(null)) {
			result.add(new FullSuffixEntry("CameFor", suffix));
		}

		for (String suffix : repeatedly(null)) {
			result.add(new FullSuffixEntry("Repeatedly", suffix));
		}
		for (String suffix : withintent(null)) {
			result.add(new FullSuffixEntry("WithIntent", suffix));
		}
		for (String suffix : wentfordoing(null)) {
			result.add(new FullSuffixEntry("WentTo", suffix));
		}
		for (String suffix : completely(null)) {
			result.add(new FullSuffixEntry("Completely", suffix));
		}
		for (String suffix : accidental(null)) {
			result.add(new FullSuffixEntry("OnAccident", suffix));
		}
		return result;
	}

	private List<String> repeatedly(String prepend) {
		List<String> patterns = new ArrayList<String>();
		String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
		for (String i : ilist) {
			if (prepend != null && !prepend.endsWith(i)) {
				continue;
			}
			if (prepend != null) {
				i = prepend;
			}
			patterns.add(i + "ᎶᎠ");
			patterns.add(i + "ᎶᎥ*");
			patterns.add(i + "ᎶᎥᎩ");
			patterns.add(i + "ᎶᎥᎢ");
			patterns.add(i + "ᎶᎡᎢ");
			patterns.add(i + "ᎶᎡ");
			patterns.add(i + "ᎶᏍᎨᏍᏗ");
			patterns.add(i + "ᎶᏍᎬ");
			patterns.add(i + "ᎶᏍᎬᎩ");
			patterns.add(i + "ᎶᏍᎬᎢ");
			patterns.add(i + "ᎶᏍᎨᎢ");
			patterns.add(i + "ᎶᏍᎨ");
			patterns.add(i + "ᎶᏍᎪ");
			patterns.add(i + "ᎶᏍᎪᎢ");
			patterns.add(i + "ᎶᏍᎩ");
			patterns.add(i + "ᎶᏣ");
			patterns.add(i + "ᎶᏍᏗ");
		}
		return patterns;
	}

	private Collection<? extends String> again(String prepend) {
		List<String> patterns = new ArrayList<String>();
		String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
		for (String i : ilist) {
			if (prepend != null && !prepend.endsWith(i)) {
				continue;
			}
			if (prepend != null) {
				i = prepend;
			}
			patterns.add(i + "ᏏᎭ");
			patterns.add(i + "ᏌᏅ*");
			patterns.add(i + "ᏌᏅᎩ");
			patterns.add(i + "ᏌᏅᎢ");
			patterns.add(i + "ᏌᏁᎢ");
			patterns.add(i + "ᏌᏁ");
			patterns.add(i + "ᏏᏍᎨᏍᏗ");
			patterns.add(i + "ᏏᏍᎬ");
			patterns.add(i + "ᏏᏍᎬᎩ");
			patterns.add(i + "ᏏᏍᎬᎢ");
			patterns.add(i + "ᏏᏍᎨᎢ");
			patterns.add(i + "ᏏᏍᎨ");
			patterns.add(i + "ᏏᏍᎪ");
			patterns.add(i + "ᏏᏍᎪᎢ");
			patterns.add(i + "ᏏᏍᎩ");
			patterns.add(i + "Ꮜ");
			patterns.add(i + "ᏐᏗ");
		}
		return patterns;
	}

	private Collection<? extends String> isaboutto(String prepend) {
		List<String> patterns = new ArrayList<String>();
		String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
		for (String i : ilist) {
			if (prepend != null && !prepend.endsWith(i)) {
				continue;
			}
			if (prepend != null) {
				i = prepend;
			}
			patterns.add(i + "Ꮧ");
			patterns.add(i + "ᏗᏒ");
			patterns.add(i + "ᏗᏒᎩ");
			patterns.add(i + "ᏗᏒᎢ");
			patterns.add(i + "ᏗᏎ");
			patterns.add(i + "ᏗᏎᎢ");
			patterns.add(i + "ᏗᏎᏍᏗ");
			patterns.add(i + "ᏗᏐ");
			patterns.add(i + "ᏗᏐᎢ");
			patterns.add(i + "ᏗᏏ");
		}
		return patterns;

	}

	private Collection<? extends String> withintent(String prepend) {
		List<String> patterns = new ArrayList<String>();
		String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
		for (String i : ilist) {
			if (prepend != null && !prepend.endsWith(i)) {
				continue;
			}
			if (prepend != null) {
				i = prepend;
			}
			patterns.add(i + "Ꮢ*");
			patterns.add(i + "ᏒᎩ");
			patterns.add(i + "ᏒᎢ");
			patterns.add(i + "Ꮞ");
			patterns.add(i + "ᏎᎢ");
			patterns.add(i + "ᏎᏍᏗ");
			patterns.add(i + "Ꮠ");
			patterns.add(i + "ᏐᎢ");
			patterns.add(i + "Ꮟ");
		}
		return patterns;
	}

	private Collection<? extends String> wentfordoing(String prepend) {

		List<String> patterns = new ArrayList<String>();
		String[] elist = "ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ".split("");
		String[] ulist = "ᎤᎫᎱᎷᎽᏄᏊᏑᏚᏡᏧᏭᏳ".split("");
		String[] vlist = "ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ".split("");

		for (String e : elist) {
			if (prepend != null && !prepend.endsWith(e)) {
				continue;
			}
			if (prepend != null) {
				e = prepend;
			}
			patterns.add(e + "Ꭶ");
			patterns.add(e + "Ꮎ");
		}

		for (String u : ulist) {
			if (prepend != null && !prepend.endsWith(u)) {
				continue;
			}
			if (prepend != null) {
				u = prepend;
			}
			patterns.add(u + "ᎦᎮᏍᏗ");
			patterns.add(u + "ᎦᎲ");
			patterns.add(u + "ᎦᎲᎩ");
			patterns.add(u + "ᎦᎲᎢ");
			patterns.add(u + "ᎮᎢ");
			patterns.add(u + "Ꭾ");
			patterns.add(u + "ᎰᎢ");
			patterns.add(u + "Ꮀ");
			patterns.add(u + "Ꭿ");
			patterns.add(u + "Ꭶ");
		}

		for (String v : vlist) {
			if (prepend != null && !prepend.endsWith(v)) {
				continue;
			}
			if (prepend != null) {
				v = prepend;
			}
			patterns.add(v + "Ꮢ*");
			patterns.add(v + "ᏒᎩ");
			patterns.add(v + "ᏒᎢ");
			patterns.add(v + "Ꮞ");
			patterns.add(v + "ᏎᎢ");
		}
		return patterns;
	}

	private Collection<? extends String> comefordoing(String prepend) {

		List<String> patterns = new ArrayList<String>();
		String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
		for (String i : ilist) {
			if (prepend != null && !prepend.endsWith(i)) {
				continue;
			}
			if (prepend != null) {
				i = prepend;
			}
			patterns.add(i + "Ꭶ");
			patterns.add(i + "Ꮈ*");
			patterns.add(i + "ᎸᎩ");
			patterns.add(i + "ᎸᎢ");
			patterns.add(i + "Ꮄ");
			patterns.add(i + "ᎴᎢ");
			patterns.add(i + "ᎯᎮᏍᏗ");
			patterns.add(i + "ᎯᎲ");
			patterns.add(i + "ᎯᎲᎩ");
			patterns.add(i + "ᎯᎲᎢ");
			patterns.add(i + "ᎯᎮ");
			patterns.add(i + "ᎯᎮᎢ");
			patterns.add(i + "ᎯᎰ");
			patterns.add(i + "ᎯᎰᎢ");
			patterns.add(i + "ᎯᎯ");
			patterns.add(i + "Ꭶ");
			patterns.add(i + "ᏍᏗ");
		}
		return patterns;
	}

	private Collection<? extends String> completely(String prepend) {
		List<String> patterns = new ArrayList<String>();
		String[] olist = "ᎣᎪᎰᎶᎼᏃᏉᏐᏙᏠᏦᏬᏲ".split("");
		for (String o : olist) {
			if (prepend != null && !prepend.endsWith(o)) {
				continue;
			}
			if (prepend != null) {
				o = prepend;
			}
			patterns.add(o + "ᎲᏍᎦ");
			patterns.add(o + "Ꮕ*");
			patterns.add(o + "ᏅᎩ");
			patterns.add(o + "ᏅᎢ");
			patterns.add(o + "Ꮑ");
			patterns.add(o + "ᏁᎢ");
			patterns.add(o + "ᎲᏍᎨᏍᏗ");
			patterns.add(o + "ᎲᏍᎬ");
			patterns.add(o + "ᎲᏍᎬᎩ");
			patterns.add(o + "ᎲᏍᎬᎢ");
			patterns.add(o + "ᎲᏍᎨ");
			patterns.add(o + "ᎲᏍᎨᎢ");
			patterns.add(o + "ᎲᏍᎪ");
			patterns.add(o + "ᎲᏍᎪᎢ");
			patterns.add(o + "ᎲᏍᎩ");
			patterns.add(o + "Ꮏ");
			patterns.add(o + "ᎲᏍᏗ");
		}
		return patterns;
	}

	/**
	 * 1
	 * 
	 * @return
	 */
	private Collection<? extends String> accidental(String prepend) {

		List<String> patterns = new ArrayList<String>();
		String[] vlist = "ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ".split("");

		for (String v : vlist) {
			if (prepend != null && !prepend.endsWith(v)) {
				continue;
			}
			if (prepend != null) {
				v = prepend;
			} else {
				v = "";
			}

			patterns.add("ᏙᏓᏅ*");
			patterns.add("ᏙᏓᏅᎩ");
			patterns.add("ᏙᏓᏅᎢ");
			patterns.add("ᏙᏓᏁ");
			patterns.add("ᏙᏓᏁᎢ");
			patterns.add("ᏙᏗᏍᎨᏍᏗ");
			patterns.add("ᏙᏗᏍᎨᏍᏗ");
			patterns.add("ᏙᏗᏍᎬᎩ");
			patterns.add("ᏙᏗᏍᎬᎢ");
			patterns.add("ᏙᏗᏍᎨ");
			patterns.add("ᏙᏗᏍᎨᎢ");
			patterns.add("ᏙᏗᏍᎪ");
			patterns.add("ᏙᏗᏍᎪᎢ");
			patterns.add("ᏙᏗᏍᎩ");
			patterns.add("ᏙᏓ");
			patterns.add("ᏙᏗ");
		}
		return patterns;
	}
}
