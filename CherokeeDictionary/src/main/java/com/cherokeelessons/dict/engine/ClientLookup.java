package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cherokeelessons.dict.client.BoundPronounsMunger;
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
			this.words.put(syllabary, definition + " ["+tag+"]" + prev);
		}
	}

	private final Map<String, String> words = new HashMap<>();

	public boolean isStopWord(String syllabary) {
		return false;
	}

	public String exact(String syllabary) {
		List<String> deprefixedlist = getDeprefixed(syllabary);
		for (String deprefixed : deprefixedlist) {
			String definition = StringUtils
					.defaultString(words.get(deprefixed));
			if (!StringUtils.isBlank(definition)) {
				definition+=" {"+syllabary+"}";
				return definition;
			}
		}
		return "";
	}

	private String[] Ᏹ = { "Ꮿ", "Ᏸ", "Ᏹ", "Ᏺ", "Ᏻ", "Ᏼ" };
	private String[] Ꮻ = { "Ꮹ", "Ꮺ", "Ꮻ", "Ꮼ", "Ꮽ", "Ꮾ" };
	private String[] Ꮒ = { "Ꮎ", "Ꮏ", "Ꮑ", "Ꮒ", "Ꮓ", "Ꮔ", "Ꮕ" };
	private String[] Ꮧ = { "Ꮴ", "Ꮧ", "Ꮶ", "Ꮷ" };
	private String[] Ꮥ = { "Ꮣ", "Ꮥ", "Ꮩ", "Ꮪ", "Ꮫ" };
	private String[] Ꭶ1 = { "Ꭶ", "Ꭼ" };

	private List<String> getDeprefixed(String syllabary) {
		GWT.log("Deprefixing: "+syllabary);
		List<String> list = new ArrayList<String>();
		list.add(syllabary);
		boolean zapᎢ=false;
		if (StringUtils.startsWithAny(syllabary, Ᏹ)) {
			GWT.log("Deprefixing Ᏹ: "+syllabary);
			int sub=0;
			for (String prefix: Ᏹ) {
				sub = syllabary.startsWith(prefix)?Math.max(sub, prefix.length()):sub;
			}
			syllabary = syllabary.substring(sub-1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary; 
			list.add(syllabary);
			zapᎢ=true;
		}
		String test=syllabary;
		if (zapᎢ&&syllabary.startsWith("Ꭲ")){
			test=syllabary.substring(1);
		}
		if (StringUtils.startsWithAny(test, Ꮻ)) {
			syllabary=test;
			GWT.log("Deprefixing Ꮻ: "+syllabary);
			int sub=0;
			for (String prefix: Ꮻ) {
				sub = syllabary.startsWith(prefix)?Math.max(sub, prefix.length()):sub;
			}
			syllabary = syllabary.substring(sub-1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary; 
			list.add(syllabary);
			zapᎢ=true;
		}
		test=syllabary;
		if (zapᎢ&&syllabary.startsWith("Ꭲ")){
			test=syllabary.substring(1);
		}
		if (StringUtils.startsWithAny(test, Ꮒ)) {
			syllabary=test;
			GWT.log("Deprefixing Ꮒ: "+syllabary);
			int sub=0;
			for (String prefix: Ꮒ) {
				sub = syllabary.startsWith(prefix)?Math.max(sub, prefix.length()):sub;
			}
			syllabary = syllabary.substring(sub-1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary; 
			list.add(syllabary);
			zapᎢ=true;
		}
		test=syllabary;
		if (zapᎢ&&syllabary.startsWith("Ꭲ")){
			test=syllabary.substring(1);
		}
		if (StringUtils.startsWithAny(test, Ꮧ)) {
			syllabary=test;
			GWT.log("Deprefixing Ꮧ: "+syllabary);
			int sub=0;
			for (String prefix: Ꮧ) {
				sub = syllabary.startsWith(prefix)?Math.max(sub, prefix.length()):sub;
			}
			syllabary = syllabary.substring(sub-1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary; 
			list.add(syllabary);
			zapᎢ=true;
		}
		test=syllabary;
		if (zapᎢ&&syllabary.startsWith("Ꭲ")){
			test=syllabary.substring(1);
		}
		if (StringUtils.startsWithAny(test, Ꮥ)) {
			syllabary=test;
			GWT.log("Deprefixing Ꮥ: "+syllabary);
			int sub=0;
			for (String prefix: Ꮥ) {
				sub = syllabary.startsWith(prefix)?Math.max(sub, prefix.length()):sub;
			}
			syllabary = syllabary.substring(sub-1);
			String l = StringUtils.left(syllabary, 1);
			syllabary = syllabary.substring(1);
			String lat = Syllabary.chr2lat(l).substring(1);
			syllabary = Syllabary.lat2chr(lat) + syllabary; 
			list.add(syllabary);
			zapᎢ=true;
		}
		test=syllabary;
		if (zapᎢ&&syllabary.startsWith("Ꭲ")){
			test=syllabary.substring(1);
		}
		if (StringUtils.startsWithAny(test, Ꭶ1)) {
			syllabary=test;
			GWT.log("Deprefixing Ꭶ: "+syllabary);
			int sub=0;
			for (String prefix: Ꭶ1) {
				sub = syllabary.startsWith(prefix)?Math.max(sub, prefix.length()):sub;
			}
			syllabary = syllabary.substring(sub-1);
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
			list.add(syllabary);
			zapᎢ=true;
		}
		if (zapᎢ==true&&syllabary.startsWith("Ꭲ")){
			list.add(syllabary.substring(1));
		}
		list.addAll(thirdPersonPronouns(syllabary));
		return list;
	}

	/**
	 * tries to convert into bare "he/she" form.
	 * @param syllabary
	 * @return
	 */
	private List<String> thirdPersonPronouns(String syllabary) {
		return BoundPronounsMunger.INSTANCE.munge(syllabary);
	}

	public String guess(String _word) {
		String definition = StringUtils.defaultString(words.get(_word));
		if (!StringUtils.isBlank(definition)) {
			return definition;
		}

		List<String> defixedlist = getDeprefixed(_word);
		for (String word : defixedlist) {
			// put any conscious forms... into dictionary forms and try
			if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭵ) + "]Ꭹ")) {
				String tmp = StringUtils.left(word, word.length() - 1);
				tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ) + "Ꭲ";
				String maybe = _guessed(tmp);
				if (!StringUtils.isBlank(maybe)) {
					return "(?ᎥᎩ=>ᎣᎢ=>" + tmp + ")|" + maybe;
				}
				tmp = StringUtils.left(word, word.length() - 1);
				tmp = Syllabary.changeForm(tmp, Vowel.Ꭵ) + "Ꭲ";
				maybe = _guessed(tmp);
				if (!StringUtils.isBlank(maybe)) {
					return "(?ᎥᎩ=>ᎥᎢ=>" + tmp + ")|" + maybe;
				}
			}

			definition = _guessed(word);
			if (!StringUtils.isBlank(definition)) {
				definition+=" {"+word+"}";
				return definition;
			}
		}
		return "";
	}

	private String _guessed(String word) {
		String definition = StringUtils.defaultString(words.get(word));
		if (!StringUtils.isBlank(definition)) {
			return definition;
		}

		if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭳ) + "]")) {
			String tmp = word + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭲ=>" + tmp + ")|" + maybe;
			}
		}

		if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭵ) + "]")) {
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

		if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭱ) + "]")) {
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

		if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭱ) + "]Ꭲ")) {
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
		if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭵ) + "]Ꭲ")) {
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
		// Try generic -Ꭲ form.
		{
			String tmp = word + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?+Ꭲ=>" + tmp + ")|" + maybe;
			}
		}

		// Maybe a shortening for "-Ꭿ" number or other?
		// {
		// String tmp = syllabary+"Ꭿ";
		// String maybe = StringUtils.defaultString(words.get(tmp));
		// if (!StringUtils.isBlank(maybe)){
		// return "(?+Ꭿ=>"+tmp+")|"+maybe;
		// }
		// }
		// Maybe a shortening for "-Ꭽ/-Ꭰ" present?
		// {
		// String tmp = syllabary+"Ꭽ";
		// String maybe = StringUtils.defaultString(words.get(tmp));
		// if (!StringUtils.isBlank(maybe)){
		// return "(?+Ꭽ=>"+tmp+")|"+maybe;
		// }
		// tmp = syllabary+"Ꭰ";
		// maybe = StringUtils.defaultString(words.get(tmp));
		// if (!StringUtils.isBlank(maybe)){
		// return "(?+Ꭰ=>"+tmp+")|"+maybe;
		// }
		// }

		return definition;
	}
}
