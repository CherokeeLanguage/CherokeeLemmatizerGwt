package com.cherokeelessons.dict.ui;

import com.cherokeelessons.dict.events.MessageEvent;
import com.cherokeelessons.dict.events.MustWaitEvent;
import com.cherokeelessons.dict.events.MustWaitEventDismiss;
import com.cherokeelessons.dict.ui.widgets.MessageDialog;
import com.cherokeelessons.dict.ui.widgets.MustWaitDialog;
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
		this.eventBus=eventBus;
		this.rp=rp;
		binder.bindEventHandlers(this, this.eventBus);
	}

	@EventHandler
	public void onMessageEvent(MessageEvent event) {
		new MessageDialog(rp, event.title, event.message).show();
	}
	
	private MustWaitDialog mwDialog=null;
	@EventHandler
	public void onMustWait(MustWaitEvent event) {
		if (mwDialog!=null) {
			mwDialog.hide();
		}
		mwDialog=new MustWaitDialog(rp, event.title, event.message);
	}
	
	@EventHandler
	public void onMustWaitDismiss(MustWaitEventDismiss event) {
		if (mwDialog!=null) {
			mwDialog.hide();
			mwDialog=null;
		}
	}
}
