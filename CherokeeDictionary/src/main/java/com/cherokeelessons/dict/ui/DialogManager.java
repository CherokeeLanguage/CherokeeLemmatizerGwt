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

	public interface DialogEventBinder extends EventBinder<DialogManager> {};
	private final DialogEventBinder binder_dialog = GWT.create(DialogEventBinder.class);
	
	private final EventBus eventBus;
	private final RootPanel rp;
	
	public DialogManager(RootPanel rp, EventBus eventBus) {
		this.eventBus=eventBus;
		this.rp=rp;
		binder_dialog.bindEventHandlers(this, this.eventBus);
		GWT.log("onLoad#" + String.valueOf(binder_dialog));
	}

	@EventHandler
	public void onMessageEvent(MessageEvent event) {
		GWT.log(this.getClass().getSimpleName()+"#Event#onMessageEvent");
		MessageDialog messageDialog = new MessageDialog(rp, event.title, event.message);
		GWT.log(this.getClass().getSimpleName()+"#messageDialog");
		messageDialog.show();
		GWT.log(this.getClass().getSimpleName()+"#messageDialog#show");
	}
	
	private MustWaitDialog mwDialog=null;
	@EventHandler
	public void onMustWait(MustWaitEvent event) {
		GWT.log(this.getClass().getSimpleName()+"#Event#onMustWait");
		if (mwDialog!=null) {
			mwDialog.hide();
		}
		mwDialog=new MustWaitDialog(rp, event.title, event.message);
	}
	
	@EventHandler
	public void onMustWaitDismiss(MustWaitEventDismiss event) {
		GWT.log(this.getClass().getSimpleName()+"#Event#onMustWaitDismiss");
		if (mwDialog!=null) {
			mwDialog.hide();
			mwDialog=null;
		}
	}
}
