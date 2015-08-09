package com.cherokeelessons.dict.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cherokeelessons.dict.shared.ClientLookup;
import com.google.gwt.core.client.GWT;
import com.opencsv.CSVParser;

import commons.lang3.StringUtils;

public enum ClientDictionary {
	INSTANCE;
	
	private final  ClientLookup lookup;
	
	private ClientDictionary() {
		lookup = new ClientLookup();
		loadᎹᎦᎵ();
		loadCED();
		loadRaven();
		
		GWT.log("ClientDictionary: ᎣᏍᏛ "+lookup.guess("ᎣᏍᏛ"));
	}
	
	public String guess(String syllabaryWord) {
		String guess = lookup.guess(syllabaryWord);
		return guess;
	}
	
	public String exact(String syllabaryWord) {
		String exact = lookup.exact(syllabaryWord);
		return exact;
	}
	
	private void loadRaven() {
		String[] lines = ClientResources.INSTANCE.ᎪᎸᏅᏱ().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				data.add(parser.parseLine(iline.next()));
			} catch (IOException e) {
			}
		}
		lookup.addEntries(data);
	}
	
	private void loadᎹᎦᎵ() {
		String[] lines = ClientResources.INSTANCE.ᎹᎦᎵ().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				String next = iline.next();
				if (!next.contains("[ᎹᎦᎵ]")){
					next += " [ᎹᎦᎵ]";
				}
				data.add(parser.parseLine(next));
			} catch (IOException e) {
			}
		}
		lookup.addEntries(data);
	}
	
	private void loadCED() {
		String[] lines = ClientResources.INSTANCE.CED().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				String next = iline.next();
				data.add(parser.parseLine(next));
			} catch (IOException e) {
			}
		}
		lookup.addEntries(data);
	}
	
}
