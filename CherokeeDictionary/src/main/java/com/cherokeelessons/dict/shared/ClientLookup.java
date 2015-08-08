package com.cherokeelessons.dict.shared;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cherokeelessons.dict.shared.Syllabary.Vowel;

import commons.lang3.StringUtils;

public class ClientLookup {
	
	public ClientLookup(List<String[]> words) {
		for (String[] data: words) {
			String prev=StringUtils.defaultString(this.words.get(data[0]));
			if (!StringUtils.isBlank(prev)) {
				prev="|"+prev;
			}
			this.words.put(data[0], data[1]+prev);
		}
	}
	
	public void addEntries(List<String[]> words) {
		for (String[] data: words) {
			String prev=StringUtils.defaultString(this.words.get(data[0]));
			if (!StringUtils.isBlank(prev)) {
				prev="|"+prev;
			}
			this.words.put(data[0], data[1]+prev);
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
		String exact=StringUtils.defaultString(words.get(syllabary));
		if (!StringUtils.isBlank(exact)){
			return exact;
		}
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭵ)+"]")){
			String tmp = syllabary+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭲ=>"+tmp+")|"+maybe;
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
		if (syllabary.endsWith("Ꮫ")){
			String tmp = StringUtils.left(syllabary, syllabary.length()-1)+"Ꮣ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?Ꮫ=>Ꮣ=>"+tmp+")|"+maybe;
			}
		}
		
		return exact;
	}
}
