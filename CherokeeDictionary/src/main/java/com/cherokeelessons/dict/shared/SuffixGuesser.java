package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SuffixGuesser {
	public SuffixGuesser() {
	}
	
	public List<String> getSuffixes(String word) {
		List<String> result = new ArrayList<String>();
		
		result.addAll(accidental(word));
		result.addAll(completely(word));
		result.addAll(comefordoing(word));
		result.addAll(wentfordoing(word));
		result.addAll(withintent(word));
		result.addAll(isaboutto(word));
		result.addAll(repeatedly(word));
		return result;
	}

	private Collection<? extends String> repeatedly(String word) {
		String i = "[ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ]";
		String pattern1=i+"ᎶᎠ";
		String pattern2=i+"(ᎶᎥ[ᎩᎢ]?|ᎶᎡᎢ?)";
		String pattern3=i+"ᎶᏍ(ᎨᏍᏗ|Ꭼ[ᎩᎢ]?|ᎨᎢ?|ᎪᎢ?|Ꭹ)";
		String pattern4=i+"ᎶᏣ";
		String pattern5=i+"ᎶᏍᏗ";
		return null;
	}

	private Collection<? extends String> isaboutto(String word) {
		String i = "[ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ]";
		String pattern1=i+"Ꮧ";
		String pattern2=i+"Ꮧ(ᏎᏍᏗ|Ꮢ[ᎩᎢ]?|ᏎᎢ?|ᏐᎢ?|Ꮟ)";
		return null;
	}

	private Collection<? extends String> withintent(String word) {
		String i = "[ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ]";
		String pattern1=i+"(ᏎᏍᏗ|Ꮢ[ᎩᎢ]?|ᏎᎢ?|ᏐᎢ?|Ꮟ)";
		return null;
	}

	private Collection<? extends String> wentfordoing(String word) {
		String e = "[ᎡᎨᎮᎴᎺᏁᏇᏎᏕᏖᏞᏤᏪᏰ]";
		String u = "[ᎤᎫᎱᎷᎽᏄᏊᏑᏚᏡᏧᏭᏳ]";
		String v = "[ᎥᎬᎲᎸᏅᏋᏒᏛᏢᏨᏮᏴ]";
		String pattern1=e+"Ꭶ";
		String pattern2=v+"(Ꮢ[ᎩᎢ]?|ᏎᎢ?)";
		String pattern3=u+"Ꭶ(ᎮᏍᏗ|Ꮂ[ᎩᎢ]?|ᎮᎢ?|ᎰᎢ?|Ꭿ)";
		String pattern4=u+"Ꭶ";
		String pattern5=e+"Ꮎ";
		String pattern6=v+"ᏍᏗ";
		return null;
	}

	private Collection<? extends String> comefordoing(String word) {
		
		String i = "[ᎢᎩᎯᎵᎻᏂᏈᏏᏗᏘᏟᏥᏫᏱ]";
		String pattern1=i+"Ꭶ";
		String pattern2=i+"(Ꮈ[ᎩᎢ]?|ᎴᎢ?)";
		String pattern3=i+"Ꭿ(ᎮᏍᏗ|Ꮂ[ᎩᎢ]?|ᎮᎢ?|ᎰᎢ?|Ꭿ)";
		String pattern4=i+"Ꭶ";
		String pattern5=i+"ᏍᏗ";
		
		return null;
	}

	private Collection<? extends String> completely(String word) {
		String o = "[ᎣᎪᎰᎶᎼᏃᏉᏐᏙᏠᏦᏬᏲ]";
		String pattern1=o+"ᎲᏍᎦ";
		String pattern2=o+"(Ꮕ[ᎩᎢ]?|ᏁᎢ?)";
		String pattern3=o+"ᎲᏍ(ᎨᏍᏗ|Ꭼ[ᎩᎢ]?|ᎨᎢ?|ᎪᎢ?|Ꭹ)";
		String pattern4=o+"Ꮏ";
		String pattern5=o+"ᎲᏍᏗ";
		
		return null;
	}

	private Collection<? extends String> accidental(String word) {
		
		String pattern1="ᏙᏓ(Ꮕ[ᎩᎢ]?|ᏁᎢ?)";
		String pattern2="ᏙᏗᏍ(ᎨᏍᏗ|Ꭼ[ᎩᎢ]?|ᎨᎢ?|ᎪᎢ?|Ꭹ)";
		String pattern3="ᏙᏓ";
		String pattern4="ᏙᏗ";
		
		return null;
	}
}
