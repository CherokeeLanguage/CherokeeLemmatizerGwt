package com.cherokeelessons.dict.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import com.cherokeelessons.dict.engine.Suffixes;
import com.cherokeelessons.dict.shared.Syllabary.Vowel;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.opencsv.CSVParser;

import commons.lang3.StringUtils;

public enum BoundPronounsMunger {
	INSTANCE;
	
	final private List<RegExp> patterns = new ArrayList<>();
	
	public List<String> munge(String word) {
		List<String> list = new ArrayList<String>();
		list.add(word);
		if (word.length()<4) {
			return list;
		}
		for (RegExp p: patterns) {
			if (!p.test(word)) {
				continue;
			}
			GWT.log("MATCH: "+p.getSource());
			MatchResult matcher = p.exec(word);
			String prefix = matcher.getGroup(1);
			String stem = matcher.getGroup(2);
			GWT.log("STEM: "+stem);
			
			prefix = StringUtils.right(prefix, 1);
			
			list.add(prefix+stem);
			
			if (Suffixes.getVowelSet(Vowel.Ꭰ).contains(prefix)){
				list.add("ᎤᏩ"+stem);
				list.add("Ꭰ"+stem);
				list.add("Ꭶ"+stem);
				list.add("Ꭷ"+stem);
				list.add("Ꭽ"+stem);
			}
			if (Suffixes.getVowelSet(Vowel.Ꭱ).contains(prefix)){
				list.add("ᎤᏪ"+stem);
				list.add("Ꭱ"+stem);
				list.add("Ꭸ"+stem);
				list.add("Ꭾ"+stem);
			}
			if (Suffixes.getVowelSet(Vowel.Ꭲ).contains(prefix)){
				list.add("Ꭴ"+stem);
				list.add("Ꭰ"+stem);
				list.add("Ꭶ"+stem);
				list.add("Ꭿ"+stem);
			}
			if (Suffixes.getVowelSet(Vowel.Ꭳ).contains(prefix)){
				list.add("ᎤᏬ"+stem);
				list.add("Ꭳ"+stem);
				list.add("Ꭺ"+stem);
				list.add("Ꮀ"+stem);
			}
			if (Suffixes.getVowelSet(Vowel.Ꭵ).contains(prefix)){
				list.add("ᎤᏩ"+stem);
				list.add("Ꭵ"+stem);
				list.add("Ꭼ"+stem);
				list.add("Ꮂ"+stem);
			}
		}
		return list;
	}
	
	private BoundPronounsMunger(){
		String[] lines = ClientResources.INSTANCE.boundpronouns().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				data.add(parser.parseLine(iline.next()));
			} catch (IOException e) {
			}
		}
		for (String[] dataset: data) {
			if (dataset==null || dataset.length==0) {
				continue;
			}
			for (String pattern: dataset) {
				if (StringUtils.isBlank(pattern)) {
					continue;
				}
				pattern = pattern.replace("Ꭶ͓", "[ᎦᎧᎨᎩᎪᎫᎬ]");
				pattern = pattern.replace("Ꮒ͓", "[ᎾᎿᏁᏂᏃᏄᏅ]");
				pattern = pattern.replace("Ꮖ͓", "[ᏆᏇᏈᏉᏊᏋ]");
				pattern = pattern.replace("Ꮣ͓", "[ᏓᏕᏗᏙᏚᏛ]");
				pattern = "^(Ꭲ?" + pattern + ")(.*)";
				RegExp p = RegExp.compile(pattern);// Pattern.compile(pattern);
				patterns.add(p);
			}
		}
		Collections.sort(patterns, sizeDesc);
	}
	private final Comparator<RegExp> sizeDesc=new Comparator<RegExp>() {
		@Override
		public int compare(RegExp arg0, RegExp arg1) {
			if (arg0==arg1) {
				return 0;
			}
			if (arg0.getSource().equals(arg1.getSource())){
				return 0;
			}
			return arg1.getSource().length()-arg0.getSource().length();
		}
	};
}
