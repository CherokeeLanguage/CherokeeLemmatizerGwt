package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class MustWaitEvent extends GenericEvent {
	public final String title;
	public final String message;
	public MustWaitEvent(String title, String message) {
		this.title=title;
		this.message=message;
	}
}
