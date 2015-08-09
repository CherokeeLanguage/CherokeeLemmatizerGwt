package com.cherokeelessons.dict.client;

import com.cherokeelessons.dict.events.AppLocationEvent;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.user.client.History;
import com.google.web.bindery.event.shared.EventBus;
import commons.lang3.StringUtils;

public class HistoryChangeHandler implements ValueChangeHandler<String> {

	public static enum AppLocation {
		Analyzer;
		public static AppLocation getLocationFor(String value) {
			try {
				return AppLocation.valueOf(value);
			} catch (Exception e) {
				return Analyzer;
			}
		}
	}

	private final EventBus eventBus;
	public HistoryChangeHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@Override
	public void onValueChange(ValueChangeEvent<String> event) {
		String[] parts=event.getValue().split("&");
		if (parts.length==0 || StringUtils.isBlank(parts[0])) {
			History.replaceItem("!"+AppLocation.Analyzer.name());
			Document.get().setTitle("ᏧᏪᏅᏒᎢ - ᏣᎳᎩ ᏗᏕᏠᏆᏙᏗ");
			return;
		}
		GWT.log("EVENT: '" + event.getValue() + "'");
		AppLocation newLocation = AppLocation.getLocationFor(parts[0].substring(1));
		eventBus.fireEvent(new AppLocationEvent(newLocation));
	}

}
