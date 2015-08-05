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
		if (_class != null && !_class.isEmpty())
			builder.append("_class=").append(_class).append("\n");
		builder.append("id=").append(id).append("\n");
		if (crossreferencet != null && !crossreferencet.isEmpty())
			builder.append("crossreferencet=").append(crossreferencet)
					.append("\n");
		if (definitiond != null && !definitiond.isEmpty())
			builder.append("definitiond=").append(definitiond).append("\n");
		if (definitionlarge != null && !definitionlarge.isEmpty())
			builder.append("definitionlarge=").append(definitionlarge)
					.append("\n");
		if (entrya != null && !entrya.isEmpty())
			builder.append("entrya=").append(entrya).append("\n");
		if (entrytone != null && !entrytone.isEmpty())
			builder.append("entrytone=").append(entrytone).append("\n");
		if (entrytranslit != null && !entrytranslit.isEmpty())
			builder.append("entrytranslit=").append(entrytranslit).append("\n");
		if (etymology != null && !etymology.isEmpty())
			builder.append("etymology=").append(etymology).append("\n");
		if (nounadjplurale != null && !nounadjplurale.isEmpty())
			builder.append("nounadjplurale=").append(nounadjplurale)
					.append("\n");
		if (nounadjpluralsyllf != null && !nounadjpluralsyllf.isEmpty())
			builder.append("nounadjpluralsyllf=").append(nounadjpluralsyllf)
					.append("\n");
		if (nounadjpluraltone != null && !nounadjpluraltone.isEmpty())
			builder.append("nounadjpluraltone=").append(nounadjpluraltone)
					.append("\n");
		if (nounadjpluraltranslit != null && !nounadjpluraltranslit.isEmpty())
			builder.append("nounadjpluraltranslit=")
					.append(nounadjpluraltranslit).append("\n");
		if (partofspeechc != null && !partofspeechc.isEmpty())
			builder.append("partofspeechc=").append(partofspeechc).append("\n");
		if (sentenceenglishs != null && !sentenceenglishs.isEmpty())
			builder.append("sentenceenglishs=").append(sentenceenglishs)
					.append("\n");
		if (sentenceq != null && !sentenceq.isEmpty())
			builder.append("sentenceq=").append(sentenceq).append("\n");
		if (sentencesyllr != null && !sentencesyllr.isEmpty())
			builder.append("sentencesyllr=").append(sentencesyllr).append("\n");
		if (sentencetranslit != null && !sentencetranslit.isEmpty())
			builder.append("sentencetranslit=").append(sentencetranslit)
					.append("\n");
		if (source != null && !source.isEmpty())
			builder.append("source=").append(source).append("\n");
		if (syllabaryb != null && !syllabaryb.isEmpty())
			builder.append("syllabaryb=").append(syllabaryb).append("\n");
		if (vfirstpresg != null && !vfirstpresg.isEmpty())
			builder.append("vfirstpresg=").append(vfirstpresg).append("\n");
		if (vfirstpresh != null && !vfirstpresh.isEmpty())
			builder.append("vfirstpresh=").append(vfirstpresh).append("\n");
		if (vfirstprestone != null && !vfirstprestone.isEmpty())
			builder.append("vfirstprestone=").append(vfirstprestone)
					.append("\n");
		if (vfirstprestranslit != null && !vfirstprestranslit.isEmpty())
			builder.append("vfirstprestranslit=").append(vfirstprestranslit)
					.append("\n");
		if (vsecondimperm != null && !vsecondimperm.isEmpty())
			builder.append("vsecondimperm=").append(vsecondimperm).append("\n");
		if (vsecondimpersylln != null && !vsecondimpersylln.isEmpty())
			builder.append("vsecondimpersylln=").append(vsecondimpersylln)
					.append("\n");
		if (vsecondimpertone != null && !vsecondimpertone.isEmpty())
			builder.append("vsecondimpertone=").append(vsecondimpertone)
					.append("\n");
		if (vsecondimpertranslit != null && !vsecondimpertranslit.isEmpty())
			builder.append("vsecondimpertranslit=")
					.append(vsecondimpertranslit).append("\n");
		if (vthirdinfo != null && !vthirdinfo.isEmpty())
			builder.append("vthirdinfo=").append(vthirdinfo).append("\n");
		if (vthirdinfsyllp != null && !vthirdinfsyllp.isEmpty())
			builder.append("vthirdinfsyllp=").append(vthirdinfsyllp)
					.append("\n");
		if (vthirdinftone != null && !vthirdinftone.isEmpty())
			builder.append("vthirdinftone=").append(vthirdinftone).append("\n");
		if (vthirdinftranslit != null && !vthirdinftranslit.isEmpty())
			builder.append("vthirdinftranslit=").append(vthirdinftranslit)
					.append("\n");
		if (vthirdpasti != null && !vthirdpasti.isEmpty())
			builder.append("vthirdpasti=").append(vthirdpasti).append("\n");
		if (vthirdpastsyllj != null && !vthirdpastsyllj.isEmpty())
			builder.append("vthirdpastsyllj=").append(vthirdpastsyllj)
					.append("\n");
		if (vthirdpasttone != null && !vthirdpasttone.isEmpty())
			builder.append("vthirdpasttone=").append(vthirdpasttone)
					.append("\n");
		if (vthirdpasttranslit != null && !vthirdpasttranslit.isEmpty())
			builder.append("vthirdpasttranslit=").append(vthirdpasttranslit)
					.append("\n");
		if (vthirdpresk != null && !vthirdpresk.isEmpty())
			builder.append("vthirdpresk=").append(vthirdpresk).append("\n");
		if (vthirdpressylll != null && !vthirdpressylll.isEmpty())
			builder.append("vthirdpressylll=").append(vthirdpressylll)
					.append("\n");
		if (vthirdprestone != null && !vthirdprestone.isEmpty())
			builder.append("vthirdprestone=").append(vthirdprestone)
					.append("\n");
		if (vthirdprestranslit != null && !vthirdprestranslit.isEmpty())
			builder.append("vthirdprestranslit=").append(vthirdprestranslit);
		builder.append("]");
		return builder.toString();
	}
	
}
