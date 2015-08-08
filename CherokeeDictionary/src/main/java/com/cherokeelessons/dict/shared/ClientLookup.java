package com.cherokeelessons.dict.shared;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cherokeelessons.dict.shared.Syllabary.Vowel;
import com.google.gwt.core.shared.GWT;

import commons.lang3.StringUtils;

public class ClientLookup {

	public ClientLookup() {
	}

	public void addEntries(List<String[]> words) {
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
			this.words.put(syllabary, definition + prev);
		}
	}

	private final Map<String, String> words = new HashMap<>();

	public boolean isStopWord(String syllabary) {
		return false;
	}

	public String exact(String syllabary) {
		return StringUtils.defaultString(words.get(syllabary));
	}

	public String guessed(String word) {
		String definition = StringUtils.defaultString(words.get(word));
		if (!StringUtils.isBlank(definition)) {
			return definition;
		}

		// put any conscious forms... into dictionary forms and try
		if (word.matches(".*[" + Suffixes.getVowelSet(Vowel.Ꭵ) + "]Ꭹ")) {
			String tmp = StringUtils.left(word, word.length() - 1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ) + "Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?ᎥᎩ=>ᎣᎢ=>" + tmp + ")|" + maybe;
			}
			tmp = StringUtils.left(word, word.length() - 1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭵ) + "Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)) {
				return "(?ᎥᎩ=>ᎥᎢ=>" + tmp + ")|" + maybe;
			}
		}

		definition = _guessed(word);
		if (!StringUtils.isBlank(definition)) {
			return definition;
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
