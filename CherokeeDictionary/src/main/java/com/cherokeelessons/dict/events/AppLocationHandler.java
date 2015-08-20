package com.cherokeelessons.dict.events;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.cherokeelessons.dict.client.ConsoleLogHandler2;
import com.cherokeelessons.dict.client.DictEntryPoint;
import com.cherokeelessons.dict.client.HistoryChangeHandler.AppLocation;
import com.cherokeelessons.dict.shared.Log;
import com.cherokeelessons.dict.ui.AnalysisView;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class AppLocationHandler {
	
	private final Logger logger = Log.getGwtLogger(new ConsoleLogHandler2(), this.getClass().getSimpleName());
	
	protected interface AppLocationEventBinder extends	EventBinder<AppLocationHandler> {
		public final AppLocationEventBinder binder_applocation=GWT.create(AppLocationEventBinder.class);
	}

	private final RootPanel rp;

	public AppLocationHandler(RootPanel rp) {
		
		this.rp = rp;
		AppLocationEventBinder.binder_applocation.bindEventHandlers(this, DictEntryPoint.eventBus);
		logger.info("#" + String.valueOf(AppLocationEventBinder.binder_applocation));
	}

	private AppLocation current = null;

	@EventHandler
	public void location(AppLocationEvent event) {
		logger.info(this.getClass().getSimpleName()+"#Event#location");
		if (!event.location.equals(current)) {
			logger.info("LOCATION CHANGE: " + String.valueOf(current) + " => "
					+ String.valueOf(event.location));
			switch (event.location) {
			case Analyzer:
				AnalysisView mainwindow = new AnalysisView(rp) {

					@Override
					protected void onLoad() {
						super.onLoad();
						onEnableSearch(new EnableSearchEvent(false));
					}
				};
				rp.add(mainwindow);
				break;
			}
		}
	}

	private boolean enablepushstate = true;

	/**
	 * Record as new page visit current "view state"
	 * 
	 * @param event
	 */
	@EventHandler
	public void saveState(HistoryTokenEvent event) {
		if (event.replace) {
//			replaceState("#" + URL.encode(event.hash), Document.get()
//					.getTitle());
			History.replaceItem(event.hash, false);
		} else {
//			pushState("#" + URL.encode(event.hash), Document.get().getTitle());
			History.newItem(event.hash, false);
		}
		if (event.trigger) {
			History.fireCurrentHistoryState();
		}
		logger.info(this.getClass().getSimpleName()+"#Event#saveState#done [trigger="+event.trigger+"]");
	}

//	public static native boolean hasState()/*-{
//		try {
//			return $wnd.history && $wnd.history.pushState;
//		} catch (e) {
//		}
//		return false;
//	}-*/;

	/**
	 * Modern Chrome's do not obey location requests anymore?
	 */
//	public static native void replaceState(String hash, String title)/*-{
//		try {
//			$wnd.history.replaceState(hash, title, hash);
//		} catch (e) {
//			$wnd.console.log(e);
//		}
//
//	}-*/;

	/**
	 * Modern Chrome's do not obey location requests anymore?
	 */
//	public static native void pushState(String hash, String title)/*-{
//		try {
//			$wnd.history.pushState(hash, title, hash);
//		} catch (e) {
//			$wnd.console.log(e);
//		}
//
//	}-*/;
}
