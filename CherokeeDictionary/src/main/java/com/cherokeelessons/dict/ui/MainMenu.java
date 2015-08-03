package com.cherokeelessons.dict.ui;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.TextBox;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MainMenu extends Composite {

	@UiField
	protected PageHeader pageHeader;

	@UiField
	protected FormGroup formGroup;

	@UiField
	protected TextBox textBox;

	@UiField
	protected Button btn_analyze;

	@UiField
	protected Button btn_search;

	private static MainMenuUiBinder uiBinder = GWT
			.create(MainMenuUiBinder.class);

	protected interface MainMenuUiBinder extends UiBinder<Widget, MainMenu> {
	}

	public MainMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("btn_analyze")
	public void onAnalyze(final ClickEvent event) {
		btn_analyze.state().loading();
		new Timer() {
			@Override
			public void run() {
				btn_analyze.state().reset();
			}
		}.schedule(2000);
	}
	
	@UiHandler("btn_search")
	public void onSearch(final ClickEvent event) {
		btn_search.state().loading();
		new Timer() {
			@Override
			public void run() {
				btn_search.state().reset();
			}
		}.schedule(2000);
	}
}
