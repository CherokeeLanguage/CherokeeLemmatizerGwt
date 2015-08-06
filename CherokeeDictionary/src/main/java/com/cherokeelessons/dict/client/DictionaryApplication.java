package com.cherokeelessons.dict.client;

import com.cherokeelessons.dict.shared.RestApi;
import com.cherokeelessons.dict.shared.SuffixGuesser;
import com.cherokeelessons.dict.ui.SyllabarySearch;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class DictionaryApplication implements ScheduledCommand {

	public static final RestApi api;
	static {
		api = GWT.create(RestApi.class);
	}
	
	private Timer doResizeTimer;
	private final ResizeHandler resize = new ResizeHandler() {
		@Override
		public void onResize(ResizeEvent event) {
			if (doResizeTimer!=null) {
				doResizeTimer.cancel();
			}
			doResizeTimer = new Timer() {
				@Override
				public void run() {
					doResize();		
				}
			};
			doResizeTimer.schedule(250);				
		}

	};

	private void doResize() {
		float width = Window.getClientWidth();
		float wanted = 1024;
		float scaleby=width/wanted;
		if (scaleby<1f) {
			scaleby=1f;
		}
		rp.getElement().getStyle().setProperty("transform", "scale("+scaleby+")");
		new SuffixGuesser();
	}
	
	private RootPanel rp;
	@Override
	public void execute() {
		RootPanel.get().clear(true);
		RootPanel.getBodyElement().setInnerHTML("<div id='root' style='margin-right: auto; margin-left: auto; margin-top: 10px; transform-origin: center top; max-width: 1004px; padding: 5px; border: solid 2px;'></div>");
		rp = RootPanel.get("root");
		doResize();
		
		SyllabarySearch mainwindow = new SyllabarySearch(rp);
		rp.add(mainwindow);
		
		Window.addResizeHandler(resize);
	}
}
