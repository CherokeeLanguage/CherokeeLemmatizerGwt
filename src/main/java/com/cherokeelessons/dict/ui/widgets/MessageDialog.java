package com.cherokeelessons.dict.ui.widgets;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.gwtbootstrap3.client.shared.event.ModalHiddenEvent;
import org.gwtbootstrap3.client.shared.event.ModalHiddenHandler;
import org.gwtbootstrap3.client.ui.Modal;

import com.cherokeelessons.dict.client.ConsoleLogHandler2;
import com.cherokeelessons.dict.shared.Log;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

public class MessageDialog extends Composite {
	
	private final Logger logger = Log.getGwtLogger(new ConsoleLogHandler2(), this.getClass().getSimpleName());

	private static DialogBoxUiBinder uiBinder = GWT
			.create(DialogBoxUiBinder.class);

	interface DialogBoxUiBinder extends UiBinder<Widget, MessageDialog> {
	}
	
	@UiField
	protected HTML html;
	
	@UiField
	protected Modal modal;
	
	public void show(){
		rp.add(this);
		modal.show();
	}
	
	public void hide(){
		modal.hide();
	}

	private final RootPanel rp;
	public MessageDialog(RootPanel rp, String title, String message) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rp=rp;
		this.html.setHTML(message);
		this.modal.setTitle(title);
		init();
	}

	private void init() {
		this.modal.setClosable(true);
		this.modal.setFade(true);
		this.modal.addHiddenHandler(new ModalHiddenHandler() {
			@Override
			public void onHidden(ModalHiddenEvent evt) {
				logger.info("removing from parent: "+modal.getTitle());
				MessageDialog.this.removeFromParent();
			}
		});
	}
	
	public MessageDialog(RootPanel rp, String title, SafeHtml message) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rp=rp;
		this.html.setHTML(message);
		this.modal.setTitle(title);
		init();
	}

	@UiHandler("ok")
	public void onOk(final ClickEvent event){
		this.modal.hide();
	}
}
