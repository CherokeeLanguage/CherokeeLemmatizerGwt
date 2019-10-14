package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class AnalyzeEvent extends GenericEvent {
	public AnalyzeEvent(String query) {
		this.query=query;
	}

	public final String query;
}
