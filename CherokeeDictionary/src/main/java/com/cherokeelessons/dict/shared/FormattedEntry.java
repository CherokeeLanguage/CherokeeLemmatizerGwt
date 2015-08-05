package com.cherokeelessons.dict.shared;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;

public class FormattedEntry {
	private final DictEntry entry;
	private final List<DisplayPair> pairs=new ArrayList<DisplayPair>();
	public FormattedEntry(DictEntry entry) {
		this.entry=entry;
		
		
		
	}
	
	public SafeHtml getHtml(){
		SafeHtmlBuilder shb=new SafeHtmlBuilder();
		shb.appendHtmlConstant("<ul class=\"ul_dl\">");
		
		shb.appendHtmlConstant("<li class=\"li_dt chr\"><span class=\"dt\">");
		shb.appendEscaped(entry.syllabaryb);
		shb.appendHtmlConstant("</span></li>");
		
		shb.appendHtmlConstant("<li class=\"li_dd chr\">");
		shb.appendEscaped(" [");
		shb.appendEscaped(Pronunciaton.asUtf8(entry.entrytone));
		shb.appendEscaped("] (");
		shb.appendEscaped(entry.partofspeechc);
		shb.appendEscaped(") ");
		shb.appendHtmlConstant("&ldquo;");
		shb.appendEscaped(entry.definitiond);
		shb.appendHtmlConstant("&rdquo;");
		
		shb.appendHtmlConstant("</li>");
		
		shb.appendHtmlConstant("<br/>");
		shb.appendEscaped(entry.toString());
		
		shb.appendHtmlConstant("</dd>");
		
		shb.appendHtmlConstant("</ul>");
		
		return shb.toSafeHtml();
	}
}
