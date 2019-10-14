package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class EnableSearchEvent extends GenericEvent {
	public final boolean enable;
	public EnableSearchEvent(boolean enable) {
		this.enable=enable;
	}
}
