package com.cherokeelessons.dict.events;

import com.google.web.bindery.event.shared.binder.GenericEvent;

public class HistoryTokenEvent extends GenericEvent {
	public final boolean trigger;
	public final boolean replace;
	public final String hash;
	public HistoryTokenEvent(String hash) {
		this.hash=hash;
		replace=false;
		trigger=false;
	}
	public HistoryTokenEvent(String hash, boolean replace) {
		this.hash=hash;
		this.replace=replace;
		trigger=false;
	}
	public HistoryTokenEvent(String hash, boolean replace, boolean trigger) {
		this.hash=hash;
		this.replace=replace;
		this.trigger=trigger;
	}
}
