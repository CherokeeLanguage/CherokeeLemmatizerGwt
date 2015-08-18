package com.cherokeelessons.dict.client;

import org.fusesource.restygwt.client.Defaults;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.GWT;

public class DictEntryPoint implements EntryPoint {

	static {
		Defaults.setRequestTimeout(120 * 1000);
	}

	private UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
		@Override
		public void onUncaughtException(Throwable e) {
			GWT.log("onUncaughtException", e);
		}
	};

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(handler);
		BuildInfo bi = GWT.create(BuildInfo.class);
		GWT.log("Built: " + bi.getBuildTimestamp());
		/*
		 * ok... if we do a "scheduleFinally" the dialog event works on Safari,
		 * else it doesn't?
		 */
		Scheduler.get().scheduleFinally(new DictionaryApplication());
	}
}
