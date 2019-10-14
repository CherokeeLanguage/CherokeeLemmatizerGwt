package com.cherokeelessons.dict.events;

import com.cherokeelessons.dict.client.HistoryChangeHandler.AppLocation;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class AppLocationEvent extends GenericEvent {
	public AppLocation location;
	public AppLocationEvent(AppLocation location) {
		this.location=location;
	}
}
