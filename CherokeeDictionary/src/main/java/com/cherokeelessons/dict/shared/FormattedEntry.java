package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.gwt.core.shared.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import commons.lang.StringUtils;

public class FormattedEntry {
	private final DictEntry entry;
	private final List<DisplayPair> pairs = new ArrayList<DisplayPair>();

	public FormattedEntry(DictEntry entry) {
		this.entry = entry;

		// TODO split on ','

		pairs.add(new DisplayPair(entry.syllabaryb, entry.entrytone));
		pairs.add(new DisplayPair(entry.nounadjpluralsyllf,
				entry.nounadjpluraltone));
		pairs.add(new DisplayPair(entry.vfirstpresh, entry.vfirstprestone));
		pairs.add(new DisplayPair(entry.vthirdpastsyllj, entry.vthirdpasttone));
		pairs.add(new DisplayPair(entry.vthirdpressylll, entry.vthirdprestone));
		pairs.add(new DisplayPair(entry.vsecondimpersylln,
				entry.vsecondimpertone));
		pairs.add(new DisplayPair(entry.vthirdinfsyllp, entry.vthirdinftone));

		Iterator<DisplayPair> idp = pairs.iterator();
		while (idp.hasNext()) {
			DisplayPair next = idp.next();
			GWT.log("PAIR: " + next.syll + " -> "
					+ Pronunciaton.asUtf8(next.pron));
			if (StringUtils.isBlank(next.pron) != StringUtils
					.isBlank(next.syll)) {
				// TODO: Dialog Box
				GWT.log("BAD SYLL/PRONOUNCE PAIRING: " + entry.syllabaryb
						+ " - " + entry.definitiond);
				if (!StringUtils.isBlank(next.syll)) {
					next.pron=next.syll;
				}

				continue;
			}
			if (StringUtils.isBlank(next.pron)
					|| StringUtils.isBlank(next.syll)) {
				idp.remove();
				continue;
			}
		}
	}

	public SafeHtml getHtml() {
		SafeHtmlBuilder shb = new SafeHtmlBuilder();
		Iterator<DisplayPair> idp = new ArrayList<>(pairs).iterator();
		if (!idp.hasNext()) {
			return shb.toSafeHtml();
		}
		DisplayPair next = idp.next();

		shb.appendHtmlConstant("<ul class=\"ul_dl\">");

		shb.appendHtmlConstant("<li class=\"li_dt chr\"><span class=\"dt\">");
		shb.appendEscaped(next.syll);
		shb.appendHtmlConstant("</span></li>");

		shb.appendHtmlConstant("<li class=\"li_dd chr\">");
		shb.appendEscaped(" [");
		shb.appendEscaped(Pronunciaton.asUtf8(next.pron));
		shb.appendEscaped("] (");
		shb.appendEscaped(entry.partofspeechc);
		shb.appendEscaped(") ");
		shb.appendHtmlConstant("&ldquo;");
		shb.appendEscaped(entry.definitiond);
		shb.appendHtmlConstant("&rdquo;");
		shb.appendHtmlConstant("</li>");

		if (idp.hasNext()) {
			shb.appendHtmlConstant("<div class='nested'>");
			shb.appendHtmlConstant("<ul class=\"ul_dl\">");
			while (idp.hasNext()) {
				next = idp.next();
				shb.appendHtmlConstant("<li class=\"li_dt chr\"><span class=\"dt\">");
				shb.appendEscaped(next.syll);
				shb.appendHtmlConstant("</span></li>");

				shb.appendHtmlConstant("<li class=\"li_dd chr\">");
				shb.appendEscaped(" [");
				shb.appendEscaped(Pronunciaton.asUtf8(next.pron));
				shb.appendEscaped("]");
				shb.appendHtmlConstant("</li>");
			}
			shb.appendHtmlConstant("</ul>");
			shb.appendHtmlConstant("</div>");
		}

//		shb.appendHtmlConstant("<br/>");
//		shb.appendEscapedLines(entry.toString());

		shb.appendHtmlConstant("</dd>");

		shb.appendHtmlConstant("</ul>");

		return shb.toSafeHtml();
	}
}
