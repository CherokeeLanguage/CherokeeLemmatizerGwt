package com.cherokeelessons.dict.events;

import com.cherokeelessons.dict.shared.SearchResponse;
import com.google.web.bindery.event.shared.binder.GenericEvent;

public class SearchResponseEvent extends GenericEvent {
	public final SearchResponse response;
	public SearchResponseEvent(SearchResponse response) {
		this.response = response;
	}
}
