package com.cherokeelessons.dict.shared;

import com.google.gwt.core.shared.GWT;

import commons.lang3.StringUtils;

public class Pronunciaton {
	
	public static String asUtf8(String rawDbText) {
		if (StringUtils.isBlank(rawDbText)) {
			return rawDbText;
		}
		rawDbText = rawDbText.replace("a.", "ạ");
		rawDbText = rawDbText.replace("e.", "ẹ");
		rawDbText = rawDbText.replace("i.", "ị");
		rawDbText = rawDbText.replace("o.", "ọ");
		rawDbText = rawDbText.replace("u.", "ụ");
		rawDbText = rawDbText.replace("v.", "ṿ");
		
		rawDbText = rawDbText.replace("A.", "Ạ");
		rawDbText = rawDbText.replace("E.", "Ẹ");
		rawDbText = rawDbText.replace("I.", "Ị");
		rawDbText = rawDbText.replace("O.", "Ọ");
		rawDbText = rawDbText.replace("U.", "Ụ");
		rawDbText = rawDbText.replace("V.", "Ṿ");
		
		rawDbText = rawDbText.replace("1", "¹");
		rawDbText = rawDbText.replace("2", "²");
		rawDbText = rawDbText.replace("3", "³");
		rawDbText = rawDbText.replace("4", "⁴");
		
		rawDbText = rawDbText.replace("?", "ɂ");
		
		//TODO: Fire an EVENT that triggers a modal dialog
		if (rawDbText.contains(".") || rawDbText.matches(".*[0-9].*") || rawDbText.contains("?")){
			GWT.log("BAD PRONUNCIATION ENTRY: "+rawDbText);
		}
		
		return rawDbText;
	}
}
