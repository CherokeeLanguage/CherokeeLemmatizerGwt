package com.cherokeelessons.dict.ui;

import com.cherokeelessons.dict.events.MessageEvent;
import com.cherokeelessons.dict.ui.widgets.MessageDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class DialogManager {
	
	public static interface AnalyzerEventBinder extends EventBinder<DialogManager> {};
	private final AnalyzerEventBinder binder = GWT.create(AnalyzerEventBinder.class);
	private final EventBus eventBus;
	private final RootPanel rp;
	
	public DialogManager(RootPanel rp, EventBus eventBus) {
		binder.bindEventHandlers(this, eventBus);
		this.rp=rp;
		this.eventBus=eventBus;
	}

	@EventHandler
	public void onMessageEvent(MessageEvent event) {
		new MessageDialog(rp, event.title, event.message).show();
	}
}
