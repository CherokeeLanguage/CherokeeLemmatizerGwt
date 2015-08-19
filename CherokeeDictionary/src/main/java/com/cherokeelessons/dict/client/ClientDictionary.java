package com.cherokeelessons.dict.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.cherokeelessons.dict.engine.ClientLookup;
import com.cherokeelessons.dict.shared.Log;
import com.opencsv.CSVParser;

public enum ClientDictionary {
	INSTANCE;
	
	private final Logger log = Log.getGwtLogger(new ConsoleLogHandler2(), this.getClass().getSimpleName());
	
	private final  ClientLookup lookup;
	
	private ClientDictionary() {
		lookup = new ClientLookup();
		loadMagali();
		loadCED();
		loadRaven();
		
		log.info("ClientDictionary: ᎣᏍᏛ "+lookup.guess("ᎣᏍᏛ"));
		log.setLevel(Level.WARNING);
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
		String[] lines = ClientResources.INSTANCE.raven().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				data.add(parser.parseLine(iline.next()));
			} catch (IOException e) {
			}
		}
		lookup.addEntries("raven", data);
	}
	
	private void loadMagali() {
		String[] lines = ClientResources.INSTANCE.magali().getText().split("\n");
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
		lookup.addEntries("ᎹᎦᎵ", data);
	}
	
	private void loadCED() {
		String[] lines = ClientResources.INSTANCE.ced().getText().split("\n");
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
		lookup.addEntries("CED", data);
	}
	
}
