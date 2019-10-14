package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class ReplaceTextInputEvent extends GenericEvent {
	public final String text;
	public ReplaceTextInputEvent(String text) {
		this.text=text;
	}
}
