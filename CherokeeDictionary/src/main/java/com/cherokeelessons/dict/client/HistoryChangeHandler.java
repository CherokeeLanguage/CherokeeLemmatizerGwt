package com.cherokeelessons.dict.client;

import java.util.Arrays;
import java.util.Iterator;
import java.util.logging.Logger;

import com.cherokeelessons.dict.events.AppLocationEvent;
import com.cherokeelessons.dict.events.HistoryTokenEvent;
import com.cherokeelessons.dict.events.ReplaceTextInputEvent;
import com.cherokeelessons.dict.shared.Log;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.Window.Location;
import commons.lang3.StringUtils;

public class HistoryChangeHandler implements ValueChangeHandler<String> {

	private final Logger logger = Log.getGwtLogger(new ConsoleLogHandler2(), this.getClass().getSimpleName());
	
	public static enum AppLocation {
		Invalid, Analyzer;
		public static AppLocation getLocationFor(String value) {
			try {
				return AppLocation.valueOf(value);
			} catch (Exception e) {
				return Invalid;
			}
		}
	}

	public HistoryChangeHandler() {}
	
	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String[] parts=event.getValue().split("&");
		if (parts==null || parts.length==0 || StringUtils.isBlank(parts[0])) {
			Document.get().setTitle("ᏧᏪᏅᏒᎢ - ᏣᎳᎩ ᏗᏕᏠᏆᏙᏗ");
			DictEntryPoint.eventBus.fireEvent(new HistoryTokenEvent("!"+AppLocation.Analyzer.name(), true, true));
			return;
		}
		Iterator<String> iparams = Arrays.asList(parts).iterator();
		AppLocation newLocation = AppLocation.getLocationFor(iparams.next().substring(1));
		if (AppLocation.Invalid.equals(newLocation)) {
			Document.get().setTitle("ᏧᏪᏅᏒᎢ - ᏣᎳᎩ ᏗᏕᏠᏆᏙᏗ");
			DictEntryPoint.eventBus.fireEvent(new HistoryTokenEvent("!"+AppLocation.Analyzer.name(), true, true));
			return;
		}
		
		String rawhash = StringUtils.substringAfter(Location.getHref(), "#");
		iparams = Arrays.asList(rawhash.split("&")).iterator();
		
		DictEntryPoint.eventBus.fireEvent(new AppLocationEvent(newLocation));
		
		while (iparams.hasNext()) {
			String next = iparams.next();
			String tag = StringUtils.substringBefore(next, "=");
			String data = URL.decodeQueryString(StringUtils.substringAfter(next, "="));
			if ("text".equalsIgnoreCase(tag)) {
				DictEntryPoint.eventBus.fireEvent(new ReplaceTextInputEvent(data));
			}
		}
	}
}
