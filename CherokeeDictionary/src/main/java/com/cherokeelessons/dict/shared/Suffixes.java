package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.List;

import com.cherokeelessons.dict.shared.Syllabary.Vowel;

public class Suffixes {

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

	public static class ForTo extends Suffixes {

		public ForTo(final String _prepend) {

			if (_prepend != null) {
				patterns.add(Syllabary.UnderX + "Ꮟ");
			} else {
				patterns.add("Ꮟ");
			}

			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭱ);
			
			String[] elist = "ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ".split("");
			for (String e : elist) {
				if (prepend != null && !prepend.endsWith(e)) {
					continue;
				}
				if (prepend != null) {
					e = prepend;
				}
				patterns.add(e + "Ꭽ");
				patterns.add(e + "Ꮈ*");
				patterns.add(e + "ᎸᎩ");
				patterns.add(e + "ᎸᎢ");
				patterns.add(e + "Ꮄ");
				patterns.add(e + "ᎴᎢ");
				patterns.add(e + "ᎯᎮᏍᏗ");
				patterns.add(e + "ᎯᎲ");
				patterns.add(e + "ᎯᎲᎩ");
				patterns.add(e + "ᎯᎲᎢ");
				patterns.add(e + "ᎯᎮ");
				patterns.add(e + "ᎯᎮᎢ");
				patterns.add(e + "ᎯᎰ");
				patterns.add(e + "ᎯᎰᎢ");
				patterns.add(e + "ᎯᎯ");
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
				if (prepend != null && !prepend.endsWith(i)) {
					continue;
				}
				if (prepend != null) {
					i = prepend;
				}
				patterns.add(i + "Ꮧ");
				patterns.add(i + "ᏗᏒ*");
				patterns.add(i + "ᏗᏒᎩ");
				patterns.add(i + "ᏗᏒᎢ");
				patterns.add(i + "ᏗᏎ");
				patterns.add(i + "ᏗᏎᎢ");
				patterns.add(i + "ᏗᏎᏍᏗ");
				patterns.add(i + "ᏗᏐ");
				patterns.add(i + "ᏗᏐᎢ");
				patterns.add(i + "ᏗᏏ");
			}
		}
	};

	public static class WithIntent extends Suffixes {

		public WithIntent(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
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
		}
	}

	public static class WentForDoing extends Suffixes {

		public WentForDoing(final String _prepend) {

			String[] elist = "ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ".split("");
			String[] ulist = "ᎤᎫᎱᎷᎽᏄᏊᏑᏚᏡᏧᏭᏳ".split("");
			String[] vlist = "ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ".split("");

			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭱ);
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

			prepend = Syllabary.changeForm(_prepend, Vowel.Ꭴ);
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

			prepend = Syllabary.changeForm(_prepend, Vowel.Ꭵ);
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
		}
	}

	public static class ComeForDoing extends Suffixes {

		public ComeForDoing(final String _prepend) {
			
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			
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
		}
	}

	public static class Around extends Suffixes {

		public Around(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭲ);
			String[] ilist = "ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ".split("");
			for (String i : ilist) {
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
				patterns.add(i + "ᎢᏙᎴ");
				patterns.add(i + "ᎢᏙᎰᎢ");
				patterns.add(i + "ᎢᏙᎰ");
				patterns.add(i + "ᎢᏙᎲᎢ");
				patterns.add(i + "ᎢᏙᎲᎩ");
				patterns.add(i + "ᎢᏙᎲ");
				patterns.add(i + "ᎢᏙᎮᎢ");
				patterns.add(i + "ᎢᏙᎮ");
				patterns.add(i + "ᎢᏙᎮᏍᏗ");
				patterns.add(i + "ᎢᏙᎯ");
				patterns.add(i + "ᎢᏓ");
				patterns.add(i + "ᎢᏓᏍᏗ");
			}
		}
	}

	public static class Completely extends Suffixes {

		public Completely(final String _prepend) {
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭳ);
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
		}
	}

	public static class Accidental extends Suffixes {


		public Accidental(final String _prepend) {
			
			String prepend = Syllabary.changeForm(_prepend, Vowel.Ꭵ);
			
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
		}
	}
}
