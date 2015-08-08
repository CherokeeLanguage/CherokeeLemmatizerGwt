package com.cherokeelessons.dict.shared;

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
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭳ)+"]")){
			String tmp = syllabary+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭲ=>"+tmp+")|"+maybe;
			}
		}
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭵ)+"]")){
			String tmp = Syllabary.changeForm(syllabary, Vowel.Ꭳ)+"Ꭲ";
			String maybe = StringUtils.defaultString(words.get(tmp));
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
			GWT.log("-ᎡᎢ: "+syllabary);
			String tmp = StringUtils.left(syllabary, syllabary.length()-1);
			GWT.log("-ᎡᎢ: "+tmp);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ)+"Ꭲ";
			GWT.log("-ᎡᎢ: "+tmp);
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?ᎡᎢ=>ᎣᎢ=>"+tmp+")|"+maybe;
			}
			tmp = StringUtils.left(syllabary, syllabary.length()-1);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭵ)+"Ꭲ";
			GWT.log("-ᎥᎢ: "+tmp);
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?ᎡᎢ=>ᎥᎢ=>"+tmp+")|"+maybe;
			}
		}
		if (syllabary.matches(".*["+Suffixes.getVowelSet(Vowel.Ꭵ)+"]Ꭲ")){
			GWT.log("-ᎥᎢ: "+syllabary);
			String tmp = StringUtils.left(syllabary, syllabary.length()-1);
			GWT.log("-ᎥᎢ: "+tmp);
			tmp = Syllabary.changeForm(tmp, Vowel.Ꭳ)+"Ꭲ";
			GWT.log("-ᎥᎢ: "+tmp);
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
		{
			String tmp = syllabary+"Ꭿ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭿ=>"+tmp+")|"+maybe;
			}
		}
		//Maybe a shortening for "-Ꭽ/-Ꭰ" present?
		{
			String tmp = syllabary+"Ꭽ";
			String maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭽ=>"+tmp+")|"+maybe;
			}
			tmp = syllabary+"Ꭰ";
			maybe = StringUtils.defaultString(words.get(tmp));
			if (!StringUtils.isBlank(maybe)){
				return "(?+Ꭰ=>"+tmp+")|"+maybe;
			}
		}
		
		return exact;
	}
}
