package com.cherokeelessons.dict.ui;

import java.util.logging.Logger;

import com.cherokeelessons.dict.client.DictEntryPoint;
import com.cherokeelessons.dict.events.MessageEvent;
import com.cherokeelessons.dict.events.MustWaitEvent;
import com.cherokeelessons.dict.events.MustWaitEventDismiss;
import com.cherokeelessons.dict.ui.widgets.MessageDialog;
import com.cherokeelessons.dict.ui.widgets.MustWaitDialog;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

public class DialogManager {
	
	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	public interface DialogEventBinder extends EventBinder<DialogManager> {};
	private final DialogEventBinder binder_dialog = GWT.create(DialogEventBinder.class);
	
	private final RootPanel rp;
	
	public DialogManager(RootPanel rp) {
		this.rp=rp;
		binder_dialog.bindEventHandlers(this, DictEntryPoint.eventBus);
		logger.info("onLoad#" + String.valueOf(binder_dialog));
	}

	@EventHandler
	public void onMessageEvent(MessageEvent event) {
		logger.info(this.getClass().getSimpleName()+"#Event#onMessageEvent");
		MessageDialog messageDialog = new MessageDialog(rp, event.title, event.message);
		logger.info(this.getClass().getSimpleName()+"#messageDialog");
		messageDialog.show();
		logger.info(this.getClass().getSimpleName()+"#messageDialog#show");
	}
	
	private MustWaitDialog mwDialog=null;
	@EventHandler
	public void onMustWait(MustWaitEvent event) {
		logger.info(this.getClass().getSimpleName()+"#Event#onMustWait");
		if (mwDialog!=null) {
			mwDialog.hide();
		}
		mwDialog=new MustWaitDialog(rp, event.title, event.message);
	}
	
	@EventHandler
	public void onMustWaitDismiss(MustWaitEventDismiss event) {
		logger.info(this.getClass().getSimpleName()+"#Event#onMustWaitDismiss");
		if (mwDialog!=null) {
			mwDialog.hide();
			mwDialog=null;
		}
	}
}
