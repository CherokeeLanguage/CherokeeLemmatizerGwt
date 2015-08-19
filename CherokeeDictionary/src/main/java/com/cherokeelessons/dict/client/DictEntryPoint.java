package com.cherokeelessons.dict.client;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.fusesource.restygwt.client.Defaults;

import com.cherokeelessons.dict.shared.RestApi;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class DictEntryPoint implements EntryPoint {

	public static RestApi api = GWT.create(RestApi.class);
	
	public static SimpleEventBus eventBus=GWT.create(SimpleEventBus.class);
	
	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	public DictEntryPoint() {
	}
	
	static {
		Defaults.setRequestTimeout(120 * 1000);
	}

	private UncaughtExceptionHandler handler = new UncaughtExceptionHandler() {
		@Override
		public void onUncaughtException(Throwable e) {
			Logger.getGlobal().log(Level.SEVERE, "onUncaughtException", e);
		}
	};

	@Override
	public void onModuleLoad() {
		GWT.setUncaughtExceptionHandler(handler);
		logger.log(Level.INFO, "DictionaryApplication#");
		DictionaryApplication app = new DictionaryApplication();
		Scheduler.get().scheduleFinally(app);
	}
}
