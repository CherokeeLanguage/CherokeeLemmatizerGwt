package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class MessageEvent extends GenericEvent {
	public final String title;
	public final String message;
	public MessageEvent(String title, String message) {
		this.title=title;
		this.message=message;
	}
}
