package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import com.cherokeelessons.dict.shared.Syllabary;
import com.cherokeelessons.dict.shared.Syllabary.Vowel;
import com.google.gwt.core.shared.GWT;
import commons.lang3.StringUtils;

public class ClientLookup {

	public ClientLookup() {
	}

	public void addEntries(String tag, List<String[]> words) {
		for (String[] data : words) {
			String syllabary = StringUtils.strip(data[0]);
			String definition = StringUtils.strip(data[1]);
			if (StringUtils.isBlank(syllabary)) {
				GWT.log("BAD PARSE? " + Arrays.toString(data));
				continue;
			}
			String prev = StringUtils.defaultString(this.words.get(syllabary));
			if (!StringUtils.isBlank(prev)) {
				prev = "|" + prev;
			}
			this.words.put(syllabary, definition + " [" + tag + "]" + prev);
			/*
			 * put in alternate lookup matches if entry does not yet exist
			 */
			for (String prefix: new String[]{"Ꮣ","Ꮥ","Ꮧ","Ꮹ","Ꮺ","Ꮻ","Ꮽ","Ꮎ","Ꮑ","Ꮒ","Ꮔ","Ꮕ"}) {
				if (syllabary.startsWith(prefix)) {
					String tmp = StringUtils.removeStart(syllabary, prefix);
					if (this.words.get(tmp)!=null) {
						continue;
					}
					prev = StringUtils.defaultString(this.words.get(tmp));
					if (!StringUtils.isBlank(prev)) {
						prev = "|" + prev;
					}
					this.words.put(tmp, definition + " [" + tag + "]" + prev);
				}
			}
		}
	}

	private final Map<String, String> words = new HashMap<>();

	public boolean isStopWord(String syllabary) {
		return false;
	}

	public String exact(String syllabary) {
		List<String> deprefixedlist = new ArrayList<>(getDeprefixed(syllabary));
		for (String deprefixed : deprefixedlist) {
			String definition = StringUtils
					.defaultString(words.get(deprefixed));
			if (!StringUtils.isBlank(definition)) {
				definition += " {" + syllabary + "}";
				return definition;
			}
		}
		return "";
	}

	private String[] Ᏹ = { "Ꮿ", "Ᏸ", "Ᏹ", "Ᏺ", "Ᏻ", "Ᏼ" };
	private String[] Ꮻ = { "Ꮹ", "Ꮺ", "Ꮻ", "Ꮼ", "Ꮽ", "Ꮾ" };
	private String[] Ꮒ = { "Ꮎ", "Ꮏ", "Ꮑ", "Ꮒ", "Ꮓ", "Ꮔ", "Ꮕ" };
	private String[] ᎢᏱ = { "ᎢᏯ", "ᎢᏰ", "ᎢᏱ", "ᎢᏲ", "ᎢᏳ", "ᎢᏴ" };
	private String[] Ꮧ = { "Ꮴ", "Ꮧ", "Ꮶ", "Ꮷ" };
	private String[] Ꮥ = { "Ꮣ", "Ꮥ", "Ꮩ", "Ꮪ", "Ꮫ" };
	private String[] Ꭶ1 = { "Ꭶ", "Ꭼ" };
	private String[] Ꭶ = { "Ꭶ", "Ꭷ", "Ꭸ", "Ꭹ", "Ꭺ", "Ꭻ", "Ꭼ" };
	private Comparator<String> bySizeDesc = new Comparator<String>() {
		@Override
		public int compare(String o1, String o2) {
			if (o1 == null) {
				return -1;
			}
			if (o2 == null) {
				return 1;
			}
			return Integer.compare(o2.length(), o1.length());
		}
	};

	/**
	 * Deprefix based on simple lookups. Has hard-coded exceptions as certain
	 * combinations don't happen! (Ꮒ followed by Ꮒ for example!)
	 * 
	 * @param syllabary
	 * @return
	 */
	private Collection<String> getDeprefixed(String syllabary) {
		Set<String> list = new HashSet<String>();
		list.add(syllabary);
		if (StringUtils.startsWithAny(syllabary, Ᏹ)
				&& !StringUtils.startsWithAny(syllabary.substring(1), Ᏹ)) {
			int sub = 0;
			for (String prefix : Ᏹ) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
			}
			list.add(syllabary);
		}
		if (StringUtils.startsWithAny(syllabary, Ꮻ)
				&& !StringUtils.startsWithAny(syllabary.substring(1), Ꮻ)) {
			int sub = 0;
			for (String prefix : Ꮻ) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
			}
			list.add(syllabary);
		}
		if (StringUtils.startsWithAny(syllabary, Ꮒ)
				&& !StringUtils.startsWithAny(syllabary.substring(1), Ꮒ)) {
			int sub = 0;
			for (String prefix : Ꮒ) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
			}
			list.add(syllabary);
		}
		if (StringUtils.startsWithAny(syllabary, ᎢᏱ)) {
			int sub = 0;
			for (String prefix : ᎢᏱ) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
			}
			list.add(syllabary);
		}
		if (StringUtils.startsWithAny(syllabary, Ꮧ)) {
			int sub = 0;
			for (String prefix : Ꮧ) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
				//sometimes Ꮧ- is hiding an Ꭰ-
				list.add("Ꭰ"+syllabary.substring(1));
			}
			list.add(syllabary);
		}

		if (StringUtils.startsWithAny(syllabary, Ꮥ)) {
			int sub = 0;
			for (String prefix : Ꮥ) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
			}
			list.add(syllabary);
		}
		if (syllabary.length()<3) {
			return list;
		}
		if (StringUtils.startsWithAny(syllabary, Ꭶ)) {
			list.addAll(BoundPronounsMunger.INSTANCE.munge("Ꭰ" + syllabary));
		}
		if (StringUtils.startsWithAny(syllabary, Ꭶ1)) {
			int sub = 0;
			for (String prefix : Ꭶ1) {
				sub = syllabary.startsWith(prefix) ? Math.max(sub,
						prefix.length()) : sub;
			}
			syllabary = syllabary.substring(sub - 1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			fixstem: {
				if (syllabary.startsWith("Ꮹ") && lat.equals("v")) {
					syllabary = syllabary.substring(1);
					lat = "u";
					break fixstem;
				}
				if (lat.equals("v")) {
					lat = "a";
					break fixstem;
				}
				if (syllabary.startsWith("Ꭹ")) {
					lat = null;
				}
				if (syllabary.startsWith("Ᏺ")) {
					syllabary = syllabary.substring(1);
					lat = "o";
				}
			}
			String pre = lat != null ? StringUtils.defaultString(Syllabary
					.lat2chr(lat)) : "";
			syllabary = pre + syllabary;
			if (syllabary.startsWith("Ꭲ")) {
				syllabary = syllabary.substring(1);
			}
			list.add(syllabary);
		}
		list.addAll(BoundPronounsMunger.INSTANCE.munge(syllabary));
		return list;
	}

	public String guess(String _word) {
		String definition = StringUtils.defaultString(words.get(_word));
		if (!StringUtils.isBlank(definition)) {
			return definition;
		}
		List<String> defixedlist = new ArrayList<>(getDeprefixed(_word));

		ListIterator<String> liter = defixedlist.listIterator();
		while (liter.hasNext()) {
			String next = liter.next();
			if (next.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭵ) + "]Ꭹ")) {
				String tmp = StringUtils.left(next, next.length() - 1);
				tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ) + "Ꭲ";
				liter.add(tmp);
				tmp = StringUtils.left(next, next.length() - 1);
				tmp = Syllabary.changeForm(tmp, Vowel.Ꭵ) + "Ꭲ";
				liter.add(tmp);
			}
		}

		Collections.sort(defixedlist, bySizeDesc);

		for (String word : defixedlist) {
			//see if it might be stored with a "Ꮣ", "Ꮥ", "Ꮧ", "Ꮤ", "Ꮨ", ... in front in the dictionary
			for (String prefix: new String[]{"", "Ꮣ", "Ꮥ", "Ꮧ", "Ꮤ", "Ꮦ", "Ꮨ", "Ꮹ", "Ꮺ", "Ꮻ", "Ꮎ", "Ꮑ", "Ꮒ", "Ꮕ"}) {
				GWT.log("lookup: "+prefix+word);
				definition = _guessed(prefix+word);
				if (!StringUtils.isBlank(definition)) {
					definition += " {" + word + "}";
					return definition;
				}
			}
		}
		return "";
	}

	private String _guessed(String word) {

		String definition = StringUtils.defaultString(words.get(word));
		if (!StringUtils.isBlank(definition)) {
			return definition;
		}

		if (word.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭳ) + "]")) {
			String tmp = word + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭲ=>" + tmp + ")|" + maybe;
			}
		}

		if (word.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭵ) + "]")) {
			String tmp = word + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭲ=>" + tmp + ")|" + maybe;
			}

			tmp = Syllabary.changeForm(word, Vowel.Ꭳ) + "Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?Ꭵ=>ᎣᎢ=>" + tmp + ")|" + maybe;
			}
		}

		if (word.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭱ) + "]")) {
			String tmp = Syllabary.changeForm(word, Vowel.Ꭳ) + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?Ꭱ=>ᎣᎢ=>" + tmp + ")|" + maybe;
			}
			tmp = Syllabary.changeForm(word, Vowel.Ꭵ) + "Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?Ꭱ=>ᎥᎢ=>" + tmp + ")|" + maybe;
			}
		}

		if (word.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭱ) + "]Ꭲ")) {
			String tmp = StringUtils.left(word, word.length() - 1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ) + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?ᎡᎢ=>ᎣᎢ=>" + tmp + ")|" + maybe;
			}
			tmp = StringUtils.left(word, word.length() - 1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭵ) + "Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?ᎡᎢ=>ᎥᎢ=>" + tmp + ")|" + maybe;
			}
		}
		if (word.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭵ) + "]Ꭲ")) {
			String tmp = StringUtils.left(word, word.length() - 1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ) + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?ᎥᎢ=>ᎣᎢ=>" + tmp + ")|" + maybe;
			}
		}

		if (word.endsWith("Ꮫ")) {
			String tmp = StringUtils.left(word, word.length() - 1) + "Ꮣ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?Ꮫ=>Ꮣ=>" + tmp + ")|" + maybe;
			}
		}
		// try -Ᏹ => -Ꭲ
		if (word.endsWith("Ᏹ")) {
			String maybe = StringUtils.defaultString(words.get(StringUtils
					.left(word, word.length() - 1) + "Ꭲ"));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ᏹ=>Ꭲ)|" + maybe;
			}
		}
		
		if (word.length()<3) {
			return "";
		}

		// Try generic -Ꭲ form.
		{
			String tmp = word + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭲ=>" + tmp + ")|" + maybe;
			}
		}

		// If it ends in Ꭵ see if it might really be a
		// glottal-stop/h-alternation variant
		if (word.endsWith("Ꭵ")) {
			String tmp = StringUtils.left(word, word.length() - 1) + "ᎲᎢ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭵ=>ᎲᎢ)|" + maybe;
			}
			tmp = StringUtils.left(word, word.length() - 1) + "ᎰᎢ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭵ=>ᎰᎢ)|" + maybe;
			}
		}

		/*
		 * see if it might be a simple Ꮧ=>Ꭰ or Ꭰ=>Ꮧ prefix change to find a
		 * dictionary entry.
		 */
		if (word.startsWith("Ꭰ")) {
			String tmp = "Ꮧ" + word.substring(1);
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(Ꭰ+?=>Ꮧ+?)|" + maybe;
			}
		}
		if (word.startsWith("Ꮧ")) {
			String tmp = "Ꭰ" + word.substring(1);
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(Ꮧ+?=>Ꭰ+?)|" + maybe;
			}
		}

		if (word.startsWith("Ꮷ")) {
			String tmp = "Ꭴ" + word.substring(1);
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(Ꮷ+?=>Ꭴ+?)|" + maybe;
			}
		}

//		{
//			/*
//			 * Maybe a shortening for "-Ꭽ/-Ꭰ" present?
//			 */
//			String tmp = word + "Ꭽ";
//			String maybe = StringUtils.defaultString(words.get(tmp));
//			if (!StringUtils.isBlank(maybe)) {
//				return "(?+Ꭽ=>" + tmp + ")|" + maybe;
//			}
//			tmp = word + "Ꭰ";
//			maybe = StringUtils.defaultString(words.get(tmp));
//			if (!StringUtils.isBlank(maybe)) {
//				return "(?+Ꭰ=>" + tmp + ")|" + maybe;
//			}
//		}

		{
			/*
			 * Maybe dialect variant where "-Ꭲ" for immediate stem where CED shows
			 * "-Ꭰ"?
			 */
			if (word.matches(".*[" + Affixes.getVowelSet(Vowel.Ꭲ) + "]")) {
				String tmp = Syllabary.changeForm(word, Vowel.Ꭰ);
				String maybe = StringUtils.defaultString(words.get(tmp));
				if (!StringUtils.isBlank(maybe)) {
					return "(?+Ꭲ=>+Ꭰ)|" + maybe;
				}
			}
		}

		return definition;
	}
}
