package com.cherokeelessons.cards;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import commons.lang3.StringUtils;


@SuppressWarnings("serial")
public class Card implements Serializable, Comparable<Card> {
	private static final boolean debug = false;
	public int id;

	public List<String> challenge = new ArrayList<>();
	public List<String> answer = new ArrayList<>();
	public String key;
	public String pgroup;
	public String vgroup;
	public boolean reversed;

	public Card() {
	}

	public Card(Card card) {
		answer.addAll(card.answer);
		challenge.addAll(card.challenge);
		id = card.id;
		key = card.key;
		pgroup = card.pgroup;
		vgroup = card.vgroup;
	}

	@Override
	public int compareTo(Card o) {
		return sortKey().compareTo(o.sortKey());
	}

	private String sortKey() {
		StringBuilder key = new StringBuilder();
		if (challenge.size() != 0) {
			String tmp = challenge.get(0);
			tmp = tmp.replaceAll("[¹²³⁴ɂ" + SpecialChars.SPECIALS + "]", "");
			tmp = StringUtils.substringBefore(tmp, ",");
			if (tmp.matches(".*[Ꭰ-Ᏼ].*")) {
				tmp = tmp.replaceAll("[^Ꭰ-Ᏼ]", "");
			}
			String length = tmp.length() + "";
			while (length.length() < 4) {
				length = "0" + length;
			}
			key.append(length);
			key.append("+");
			key.append(tmp);
			key.append("+");
		}
		for (String s : challenge) {
			key.append(s);
			key.append("+");
		}
		for (String s : answer) {
			key.append(s);
			key.append("+");
		}
		if (debug) {
			this.key = key.toString();
		}
		return key.toString();
	}

	/**
	 * id of card in main deck based on pgroup/vgroup combinations
	 */
//	public String getId() {
//		return (pgroup + "+" + vgroup).intern();
//	};

}
