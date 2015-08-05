package com.cherokeelessons.dict.shared;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Syllabary {
	public static Map<String, String> chr2lat() {
		Map<String, String> syl2lat = new HashMap<String, String>();
		Map<String, String> lat2chr = lat2chr();

		Iterator<String> ikey = lat2chr.keySet().iterator();
		while (ikey.hasNext()) {
			String key = ikey.next();
			if (key.startsWith("hl")) {
				continue;
			}
			if (key.startsWith("ts")) {
				continue;
			}
			if (key.startsWith("ch")) {
				continue;
			}
			if (key.startsWith("qu")) {
				continue;
			}
			if (key.startsWith("k") && !key.startsWith("ka")) {
				continue;
			}
			syl2lat.put(lat2chr.get(key), key);
		}
		return syl2lat;
	}

	public static Map<String, String> lat2chr() {
		int ix = 0;
		String letter;
		String prefix;
		char chrStart = 'Ꭰ';
		String[] vowels = new String[6];

		Map<String, String> latin2syllabary = new HashMap<String, String>();

		vowels[0] = "a";
		vowels[1] = "e";
		vowels[2] = "i";
		vowels[3] = "o";
		vowels[4] = "u";
		vowels[5] = "v";

		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(vowels[ix], letter);
		}

		latin2syllabary.put("ga", "Ꭶ");

		latin2syllabary.put("ka", "Ꭷ");

		prefix = "g";
		chrStart = 'Ꭸ';
		for (ix = 1; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix - 1));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "k";
		chrStart = 'Ꭸ';
		for (ix = 1; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix - 1));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "h";
		chrStart = 'Ꭽ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "l";
		chrStart = 'Ꮃ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "m";
		chrStart = 'Ꮉ';
		for (ix = 0; ix < 5; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		latin2syllabary.put("na", "Ꮎ");
		latin2syllabary.put("hna", "Ꮏ");
		latin2syllabary.put("nah", "Ꮐ");

		prefix = "n";
		chrStart = 'Ꮑ';
		for (ix = 1; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix - 1));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "qu";
		chrStart = 'Ꮖ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "gw";
		chrStart = 'Ꮖ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		latin2syllabary.put("sa", "Ꮜ");
		latin2syllabary.put("s", "Ꮝ");

		prefix = "s";
		chrStart = 'Ꮞ';
		for (ix = 1; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix - 1));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		latin2syllabary.put("da", "Ꮣ");
		latin2syllabary.put("ta", "Ꮤ");
		latin2syllabary.put("de", "Ꮥ");
		latin2syllabary.put("te", "Ꮦ");
		latin2syllabary.put("di", "Ꮧ");
		latin2syllabary.put("ti", "Ꮨ");
		latin2syllabary.put("do", "Ꮩ");
		latin2syllabary.put("to", "Ꮩ");
		latin2syllabary.put("du", "Ꮪ");
		latin2syllabary.put("tu", "Ꮪ");
		latin2syllabary.put("dv", "Ꮫ");
		latin2syllabary.put("tv", "Ꮫ");
		latin2syllabary.put("dla", "Ꮬ");

		prefix = "hl";
		chrStart = 'Ꮭ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "tl";
		chrStart = 'Ꮭ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "j";
		chrStart = 'Ꮳ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "ts";
		chrStart = 'Ꮳ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "ch";
		chrStart = 'Ꮳ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "w";
		chrStart = 'Ꮹ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}

		prefix = "y";
		chrStart = 'Ꮿ';
		for (ix = 0; ix < 6; ix++) {
			letter = Character.toString((char) (chrStart + ix));
			latin2syllabary.put(prefix + vowels[ix], letter);
		}
		return latin2syllabary;
	}
}
