package com.cherokeelessons.dict.ui.widgets;

import org.gwtbootstrap3.client.shared.event.ModalHiddenEvent;
import org.gwtbootstrap3.client.shared.event.ModalHiddenHandler;
import org.gwtbootstrap3.client.ui.Modal;

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

	private static DialogBoxUiBinder uiBinder = GWT
			.create(DialogBoxUiBinder.class);

	interface DialogBoxUiBinder extends UiBinder<Widget, MessageDialog> {
	}
	
	@UiField
	protected HTML html;
	
	@UiField
	protected Modal modal;
	
	public void show(){
		GWT.log(this.getClass().getSimpleName()+"#rp.add(this)");
		rp.add(this);
		GWT.log(this.getClass().getSimpleName()+"#modal.show");
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
