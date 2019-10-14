package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class UiEnableEvent extends GenericEvent {
	public final boolean enable;
	public UiEnableEvent(boolean enable) {
		this.enable=enable;
	}
}
