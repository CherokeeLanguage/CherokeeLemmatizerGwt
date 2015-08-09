package com.cherokeelessons.dict.events;

import com.cherokeelessons.dict.client.HistoryChangeHandler.AppLocation;
import com.cherokeelessons.dict.ui.AnalysisView;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class AppLocationHandler {
	public interface AppLocationEventBinder extends EventBinder<AppLocationHandler> {
	}
	private final AppLocationEventBinder binder = GWT.create(AppLocationEventBinder.class);
	
	private final RootPanel rp;
	private final EventBus eventBus;
	public AppLocationHandler(RootPanel rp, EventBus eventBus) {
		binder.bindEventHandlers(this, eventBus);
		this.rp=rp;
		this.eventBus=eventBus;
	}
	
	private AppLocation current=null;
	@EventHandler
	public void location(AppLocationEvent event) {
		GWT.log("Location: "+event.location.name());
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
}
