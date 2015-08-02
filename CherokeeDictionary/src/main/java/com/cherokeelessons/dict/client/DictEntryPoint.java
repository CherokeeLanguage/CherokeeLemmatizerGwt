package com.cherokeelessons.dict.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.shared.GWT;

public class DictEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		GWT.log("onModuleLoad");
//		Defaults.setServiceRoot("http://www.cherokeedictionary.net/jsonsearch/");
		Scheduler.get().scheduleDeferred(new DictionaryApplication());
	}

}
