package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ListIterator;

import com.cherokeelessons.dict.shared.Syllabary.Vowel;
import com.google.gwt.core.shared.GWT;

import commons.lang.StringUtils;

public class Suffixes {

	private Comparator<String> byLengthDesc=new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1==o2) {
				return 0;
			}
			if (o1==null) {
				return 1;
			}
			if (o1.length()!=o2.length()) {
				return o2.length()-o1.length();
			}
			return o1.compareTo(o2);
		}
	};

	public String match(String syllabary) {
		List<String> xpan = new ArrayList<String>(patterns);
		ListIterator<String> l = xpan.listIterator();
		while(l.hasNext()) {
			String n = l.next();
			if (n.endsWith("*")) {
				l.set(n.replace("*", ""));
			}
		}
		Collections.sort(xpan, byLengthDesc);
		for (String ending: xpan) {
			if (syllabary.endsWith(ending)) {
				return ending;
			}
		}
		return "";
	}
	
	public List<String>  getPatterns() {
		return patterns;
	}
	
	protected final List<String> patterns;
	
	public Suffixes() {
		patterns = new ArrayList<String>();
	}

	public static class Repeatedly extends Suffixes {

		public Repeatedly(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
				if (StringUtils.isBlank(i)){
					continue;
				}
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
		}
	}
	
	public static class CausativePast extends Suffixes {

		public CausativePast(final String _prepend) {
			String s;
			String prepend = StringUtils.defaultString(_prepend);
			if (StringUtils.isBlank(prepend)){
				s="Ꮝ";
			} else {
				String x = Syllabary.changeForm(prepend, Vowel.Ꭲ);
				if ("ᎢᏫᏱᏂᎵ".contains(x.substring(x.length()-1))){
					s="Ꮝ";
				} else {
					s="";
				}
			}
			patterns.add(prepend+s+"ᏓᏅ*");
			patterns.add(prepend+s+"ᏓᏅᎩ");
			patterns.add(prepend+s+"ᏓᏅᎢ");
			patterns.add(prepend+s+"ᏓᏁᎢ");
			patterns.add(prepend+s+"ᏓᏁ");
		}
	}

	public static class ToFor extends Suffixes {

		public ToFor(final String _prepend) {

			if (_prepend != null) {
				patterns.add(Syllabary.UnderX + "Ꮟ");
			} else {
				patterns.add("Ꮟ");
			}

			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭱ);
			
			String[] elist = "ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ".split("");
			for (String e : elist) {
				if (StringUtils.isBlank(e)){
					continue;
				}
				if (prepend != null && !prepend.endsWith(e)) {
					continue;
				}
				if (prepend != null) {
					e = prepend;
				}
				//prc
				patterns.add(e + "Ꭽ");
				//past
				patterns.add(e + "Ꮈ*");
				patterns.add(e + "ᎸᎩ");
				patterns.add(e + "ᎸᎢ");
				patterns.add(e + "ᎴᎢ");
				patterns.add(e + "Ꮄ");
				//progressive
				patterns.add(e + "ᎮᏍᏗ");
				patterns.add(e + "Ꮂ");
				patterns.add(e + "ᎲᎩ");
				patterns.add(e + "ᎲᎢ");
				patterns.add(e + "Ꭾ");
				patterns.add(e + "ᎮᎢ");
				patterns.add(e + "Ꮀ");
				patterns.add(e + "ᎰᎢ");
				patterns.add(e + "Ꭿ");
				patterns.add(e + "Ꮅ");
				patterns.add(e + "Ꮧ");
			}
		}
	}

	public static class Again extends Suffixes {

		public Again(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
				if (StringUtils.isBlank(i)){
					continue;
				}
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
		}
	}

	public static class AboutTo extends Suffixes {
		public AboutTo(String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
				if (StringUtils.isBlank(i)){
					continue;
				}
				if (prepend != null && !prepend.endsWith(i)) {
					continue;
				}
				if (prepend != null) {
					i = prepend;
				}
				//present
				patterns.add(i + "Ꮧ");
				//past
				patterns.add(i + "ᏗᏒ*");
				patterns.add(i + "ᏗᏒᎩ");
				patterns.add(i + "ᏗᏒᎢ");
				patterns.add(i + "ᏗᏎ");
				patterns.add(i + "ᏗᏎᎢ");
				//progressive
				patterns.add(i + "ᏗᏍᎨᏍᏗ");
				patterns.add(i + "ᏗᏍᎬᎩ");
				patterns.add(i + "ᏗᏍᎬᎢ");
				patterns.add(i + "ᏗᏍᎬ");
				patterns.add(i + "ᏗᏍᎨᎢ");
				patterns.add(i + "ᏗᏍᎨ");
				patterns.add(i + "ᏗᏍᎪᎢ");
				patterns.add(i + "ᏗᏍᎪ");
				patterns.add(i + "ᏗᏍᎩ");
				//immeidate
				patterns.add(i + "ᏕᎾ");
			}
		}
	};

	public static class WithIntent extends Suffixes {

		public WithIntent(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
				if (StringUtils.isBlank(i)){
					continue;
				}
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
		}
	}

	public static class WentForDoing extends Suffixes {

		public WentForDoing(final String _prepend) {

			String[] elist = "ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ".split("");
			String[] ulist = "ᎤᎫᎱᎷᎽᏄᏊᏑᏚᏡᏧᏭᏳ".split("");
			String[] vlist = "ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ".split("");

			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭱ);
			for (String e : elist) {
				if (StringUtils.isBlank(e)){
					continue;
				}
				if (StringUtils.isBlank(e)){
					continue;
				}
				if (prepend != null && !prepend.endsWith(e)) {
					continue;
				}
				if (prepend != null) {
					e = prepend;
				}
				patterns.add(e + "Ꭶ");
				patterns.add(e + "Ꮎ");
				
				patterns.add(e + "ᎨᏍᏗ");
				patterns.add(e + "Ꭼ");
				patterns.add(e + "ᎬᎩ");
				patterns.add(e + "ᎬᎢ");
				patterns.add(e + "ᎨᎢ");
				patterns.add(e + "Ꭸ");
				patterns.add(e + "ᎪᎢ");
				patterns.add(e + "Ꭺ");
				patterns.add(e + "Ꭹ");
			}

			prepend = Syllabary.changeForm(_prepend, Vowel.Ꭴ);
			for (String u : ulist) {
				if (StringUtils.isBlank(u)){
					continue;
				}
				if (prepend != null && !prepend.endsWith(u)) {
					continue;
				}
				if (prepend != null) {
					u = prepend;
				}
				patterns.add(u + "Ꭶ");
			}

			prepend = Syllabary.changeForm(_prepend, Vowel.Ꭵ);
			for (String v : vlist) {
				if (StringUtils.isBlank(v)){
					continue;
				}
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
				patterns.add(v + "ᏍᏗ");
			}
		}
	}

	public static class ComeForDoing extends Suffixes {

		public ComeForDoing(final String _prepend) {
			
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
				if (StringUtils.isBlank(i)){
					continue;
				}
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
		}
	}

	public static class Around extends Suffixes {

		public Around(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
				if (StringUtils.isBlank(i)){
					continue;
				}
				if (prepend != null && !prepend.endsWith(i)) {
					continue;
				}
				if (prepend != null) {
					i = prepend;
				}
				patterns.add(i + "ᏙᎭ");
				patterns.add(i + "ᏙᎸ*");
				patterns.add(i + "ᏙᎸᎩ");
				patterns.add(i + "ᏙᎸᎢ");
				patterns.add(i + "ᏙᎴᎢ");
				patterns.add(i + "ᏙᎴ");
				patterns.add(i + "ᏙᎰᎢ");
				patterns.add(i + "ᏙᎰ");
				patterns.add(i + "ᏙᎲᎢ");
				patterns.add(i + "ᏙᎲᎩ");
				patterns.add(i + "ᏙᎲ");
				patterns.add(i + "ᏙᎮᎢ");
				patterns.add(i + "ᏙᎮ");
				patterns.add(i + "ᏙᎮᏍᏗ");
				patterns.add(i + "ᏙᎯ");
				patterns.add(i + "Ꮣ");
				patterns.add(i + "ᏓᏍᏗ");
			}
		}
	}

	public static class Completely extends Suffixes {

		public Completely(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭳ);
			String[] olist = "ᎣᎪᎰᎶᎼᏃᏉᏐᏙᏠᏦᏬᏲ".split("");
			for (String o : olist) {
				if (StringUtils.isBlank(o)){
					continue;
				}
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
		}
	}

	public static class Accidental extends Suffixes {


		public Accidental(final String _prepend) {
			
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭵ);
			
			String[] vlist = "ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ".split("");

			for (String v : vlist) {
				if (StringUtils.isBlank(v)){
					continue;
				}
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
		}
	}
}
