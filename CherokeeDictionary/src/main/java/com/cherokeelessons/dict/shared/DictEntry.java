package com.cherokeelessons.dict.shared;

import org.fusesource.restygwt.client.Json;

public class DictEntry {
	@Json(name="class")
	public String _class;
	public int id;
	public String crossreferencet;
	public String definitiond;
	public String definitionlarge;
	public String entrya;
	public String entrytone;
	public String entrytranslit;
	public String etymology;
	public String nounadjplurale;
	public String nounadjpluralsyllf;
	public String nounadjpluraltone;
	public String nounadjpluraltranslit;
	public String partofspeechc;
	public String sentenceenglishs;
	public String sentenceq;
	public String sentencesyllr;
	public String sentencetranslit;
	public String source;
	public String syllabaryb;
	public String vfirstpresg;
	public String vfirstpresh;
	public String vfirstprestone;
	public String vfirstprestranslit;
	public String vsecondimperm;
	public String vsecondimpersylln;
	public String vsecondimpertone;
	public String vsecondimpertranslit;
	public String vthirdinfo;
	public String vthirdinfsyllp;
	public String vthirdinftone;
	public String vthirdinftranslit;
	public String vthirdpasti;
	public String vthirdpastsyllj;
	public String vthirdpasttone;
	public String vthirdpasttranslit;
	public String vthirdpresk;
	public String vthirdpressylll;
	public String vthirdprestone;
	public String vthirdprestranslit;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DictEntry [");
		if (_class != null)
			builder.append("_class=").append(_class).append(", ");
		builder.append("id=").append(id).append(", ");
		if (crossreferencet != null)
			builder.append("crossreferencet=").append(crossreferencet)
					.append(", ");
		if (definitiond != null)
			builder.append("definitiond=").append(definitiond).append(", ");
		if (definitionlarge != null)
			builder.append("definitionlarge=").append(definitionlarge)
					.append(", ");
		if (entrya != null)
			builder.append("entrya=").append(entrya).append(", ");
		if (entrytone != null)
			builder.append("entrytone=").append(entrytone).append(", ");
		if (entrytranslit != null)
			builder.append("entrytranslit=").append(entrytranslit).append(", ");
		if (etymology != null)
			builder.append("etymology=").append(etymology).append(", ");
		if (nounadjplurale != null)
			builder.append("nounadjplurale=").append(nounadjplurale)
					.append(", ");
		if (nounadjpluralsyllf != null)
			builder.append("nounadjpluralsyllf=").append(nounadjpluralsyllf)
					.append(", ");
		if (nounadjpluraltone != null)
			builder.append("nounadjpluraltone=").append(nounadjpluraltone)
					.append(", ");
		if (nounadjpluraltranslit != null)
			builder.append("nounadjpluraltranslit=")
					.append(nounadjpluraltranslit).append(", ");
		if (partofspeechc != null)
			builder.append("partofspeechc=").append(partofspeechc).append(", ");
		if (sentenceenglishs != null)
			builder.append("sentenceenglishs=").append(sentenceenglishs)
					.append(", ");
		if (sentenceq != null)
			builder.append("sentenceq=").append(sentenceq).append(", ");
		if (sentencesyllr != null)
			builder.append("sentencesyllr=").append(sentencesyllr).append(", ");
		if (sentencetranslit != null)
			builder.append("sentencetranslit=").append(sentencetranslit)
					.append(", ");
		if (source != null)
			builder.append("source=").append(source).append(", ");
		if (syllabaryb != null)
			builder.append("syllabaryb=").append(syllabaryb).append(", ");
		if (vfirstpresg != null)
			builder.append("vfirstpresg=").append(vfirstpresg).append(", ");
		if (vfirstpresh != null)
			builder.append("vfirstpresh=").append(vfirstpresh).append(", ");
		if (vfirstprestone != null)
			builder.append("vfirstprestone=").append(vfirstprestone)
					.append(", ");
		if (vfirstprestranslit != null)
			builder.append("vfirstprestranslit=").append(vfirstprestranslit)
					.append(", ");
		if (vsecondimperm != null)
			builder.append("vsecondimperm=").append(vsecondimperm).append(", ");
		if (vsecondimpersylln != null)
			builder.append("vsecondimpersylln=").append(vsecondimpersylln)
					.append(", ");
		if (vsecondimpertone != null)
			builder.append("vsecondimpertone=").append(vsecondimpertone)
					.append(", ");
		if (vsecondimpertranslit != null)
			builder.append("vsecondimpertranslit=")
					.append(vsecondimpertranslit).append(", ");
		if (vthirdinfo != null)
			builder.append("vthirdinfo=").append(vthirdinfo).append(", ");
		if (vthirdinfsyllp != null)
			builder.append("vthirdinfsyllp=").append(vthirdinfsyllp)
					.append(", ");
		if (vthirdinftone != null)
			builder.append("vthirdinftone=").append(vthirdinftone).append(", ");
		if (vthirdinftranslit != null)
			builder.append("vthirdinftranslit=").append(vthirdinftranslit)
					.append(", ");
		if (vthirdpasti != null)
			builder.append("vthirdpasti=").append(vthirdpasti).append(", ");
		if (vthirdpastsyllj != null)
			builder.append("vthirdpastsyllj=").append(vthirdpastsyllj)
					.append(", ");
		if (vthirdpasttone != null)
			builder.append("vthirdpasttone=").append(vthirdpasttone)
					.append(", ");
		if (vthirdpasttranslit != null)
			builder.append("vthirdpasttranslit=").append(vthirdpasttranslit)
					.append(", ");
		if (vthirdpresk != null)
			builder.append("vthirdpresk=").append(vthirdpresk).append(", ");
		if (vthirdpressylll != null)
			builder.append("vthirdpressylll=").append(vthirdpressylll)
					.append(", ");
		if (vthirdprestone != null)
			builder.append("vthirdprestone=").append(vthirdprestone)
					.append(", ");
		if (vthirdprestranslit != null)
			builder.append("vthirdprestranslit=").append(vthirdprestranslit);
		builder.append("]");
		return builder.toString();
	}
	
}
