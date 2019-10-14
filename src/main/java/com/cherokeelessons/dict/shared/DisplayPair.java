package com.cherokeelessons.dict.shared;

public class DisplayPair {
	public DisplayPair() {
	}
	public DisplayPair(String syll, String pron) {
		this.syll=syll;
		this.pron=pron;
	}
	/**
	 * Syllabary
	 */
	public String syll;
	/**
	 * Pronunciation
	 */
	public String pron;
}