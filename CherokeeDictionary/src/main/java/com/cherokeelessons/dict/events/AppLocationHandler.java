package com.cherokeelessons.dict.events;

import com.cherokeelessons.dict.client.HistoryChangeHandler.AppLocation;
import com.cherokeelessons.dict.ui.AnalysisView;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.http.client.URL;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class AppLocationHandler {
	public interface AppLocationEventBinder extends
			EventBinder<AppLocationHandler> {
	}

	private final AppLocationEventBinder binder = GWT
			.create(AppLocationEventBinder.class);

	private final RootPanel rp;
	private final EventBus eventBus;

	public AppLocationHandler(RootPanel rp, EventBus eventBus) {
		binder.bindEventHandlers(this, eventBus);
		this.rp = rp;
		this.eventBus = eventBus;
	}

	private AppLocation current = null;

	@EventHandler
	public void location(AppLocationEvent event) {
		GWT.log("Location: " + event.location.name());
		if (!event.location.equals(current)) {
			rp.clear(true);
			switch (event.location) {
			case Analyzer:
				AnalysisView mainwindow = new AnalysisView(eventBus, rp);
				rp.add(mainwindow);
				break;
			}
		}
	}
	
	private boolean enablepushstate=true;

	/**
	 * Record as new page visit current "view state"
	 * 
	 * @param event
	 */
	@EventHandler
	public void saveState(HistoryTokenEvent event) {
		GWT.log("HISTORY: "+event.hash);
		if (event.replace) {
			replaceState("#" + URL.encode(event.hash), Document.get().getTitle());
			History.replaceItem(event.hash, false);
		} else {
			pushState("#" + URL.encode(event.hash), Document.get().getTitle());
			History.newItem(event.hash, false);
		}
		if (event.trigger) {
			History.fireCurrentHistoryState();
		}
	}

	public static native boolean hasState()/*-{
		try {
			return $wnd.history && $wnd.history.pushState;
		} catch (e) {
		}
		return false;
	}-*/;

	/**
	 * Modern Chrome's do not obey location requests anymore?
	 */
	public static native void replaceState(String hash, String title)/*-{
		$wnd.console.log("Replace state: " + hash);
		try {
			$wnd.history.replaceState(hash, title, hash);
		} catch (e) {
			$wnd.console.log(e);
		}

	}-*/;

	/**
	 * Modern Chrome's do not obey location requests anymore?
	 */
	public static native void pushState(String hash, String title)/*-{
		$wnd.console.log("Push state: " + hash);
		try {
			$wnd.history.pushState(hash, title, hash);
		} catch (e) {
			$wnd.console.log(e);
		}

	}-*/;
}
