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
		for (String[] data: words) {
			String syllabary = StringUtils.strip(data[0]);
			String definition = StringUtils.strip(data[1]);
			if (StringUtils.isBlank(syllabary)) {
				GWT.log("BAD PARSE? "+Arrays.toString(data));
				continue;
			}
			String prev=StringUtils.defaultString(this.words.get(syllabary));
			if (!StringUtils.isBlank(prev)) {
				prev="|"+prev;
			}
			this.words.put(syllabary, definition+prev);
		}
	}
	
	private final Map<String, String> words=new HashMap<>();
	
	public boolean isStopWord(String syllabary) {
		return false;
	}
	
	public String exact(String syllabary) {
		return StringUtils.defaultString(words.get(syllabary));
	}
	
	public String guessed(String syllabary) {
		String word=StringUtils.defaultString(words.get(syllabary));
		if (!StringUtils.isBlank(word)){
			return syllabary;
		}
		String guess = _guessed(word);
		if (!StringUtils.isBlank(guess)){
			return guess;
		}
		
		return syllabary;
	}
	
	private String _guessed(String syllabary) {
		String exact=StringUtils.defaultString(words.get(syllabary));
		if (!StringUtils.isBlank(exact)){
			return exact;
		}
		
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭳ)+"]")){
			String tmp = syllabary+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭲ=>"+tmp+")|"+maybe;
			}
		}
		
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭵ)+"]")){
			String tmp = syllabary+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭲ=>"+tmp+")|"+maybe;
			}
			
			tmp = Syllabary.changeForm(syllabary, Vowel.Ꭳ)+"Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?Ꭵ=>ᎣᎢ=>"+tmp+")|"+maybe;
			}
		}
		
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭱ)+"]")){
			String tmp = Syllabary.changeForm(syllabary, Vowel.Ꭳ)+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?Ꭱ=>ᎣᎢ=>"+tmp+")|"+maybe;
			}
			tmp = Syllabary.changeForm(syllabary, Vowel.Ꭵ)+"Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?Ꭱ=>ᎥᎢ=>"+tmp+")|"+maybe;
			}
		}
		
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭱ)+"]Ꭲ")){
			String tmp = StringUtils.left(syllabary, syllabary.length()-1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ)+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?ᎡᎢ=>ᎣᎢ=>"+tmp+")|"+maybe;
			}
			tmp = StringUtils.left(syllabary, syllabary.length()-1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭵ)+"Ꭲ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?ᎡᎢ=>ᎥᎢ=>"+tmp+")|"+maybe;
			}
		}
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭵ)+"]Ꭲ")){
			String tmp = StringUtils.left(syllabary, syllabary.length()-1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ)+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?ᎥᎢ=>ᎣᎢ=>"+tmp+")|"+maybe;
			}
		}
		if (syllabary.endsWith("Ꮫ")){
			String tmp = StringUtils.left(syllabary, syllabary.length()-1)+"Ꮣ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?Ꮫ=>Ꮣ=>"+tmp+")|"+maybe;
			}
		}
		//Try generic -Ꭲ form.
		{
			String tmp = syllabary+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭲ=>"+tmp+")|"+maybe;
			}
		}
		
		//Maybe a shortening for "-Ꭿ" number or other?
//		{
//			String tmp = syllabary+"Ꭿ";
//			String maybe = StringUtils.defaultString(words.get(tmp));
//			if (!StringUtils.isBlank(maybe)){
//				return "(?+Ꭿ=>"+tmp+")|"+maybe;
//			}
//		}
		//Maybe a shortening for "-Ꭽ/-Ꭰ" present?
//		{
//			String tmp = syllabary+"Ꭽ";
//			String maybe = StringUtils.defaultString(words.get(tmp));
//			if (!StringUtils.isBlank(maybe)){
//				return "(?+Ꭽ=>"+tmp+")|"+maybe;
//			}
//			tmp = syllabary+"Ꭰ";
//			maybe = StringUtils.defaultString(words.get(tmp));
//			if (!StringUtils.isBlank(maybe)){
//				return "(?+Ꭰ=>"+tmp+")|"+maybe;
//			}
//		}
		
		
		
		return exact;
	}
}
