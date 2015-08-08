package com.cherokeelessons.dict.client;

import org.fusesource.restygwt.client.Defaults;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.shared.GWT;

public class DictEntryPoint implements EntryPoint {

	static {
		Defaults.setRequestTimeout(120*1000);
	}
	
	@Override
	public void onModuleLoad() {
		Scheduler.get().scheduleDeferred(new DictionaryApplication());
	}
}
