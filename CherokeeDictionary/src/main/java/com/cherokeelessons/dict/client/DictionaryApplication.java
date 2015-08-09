package com.cherokeelessons.dict.client;

import com.cherokeelessons.dict.engine.DoAnalysis;
import com.cherokeelessons.dict.events.AppLocationHandler;
import com.cherokeelessons.dict.shared.RestApi;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class DictionaryApplication implements ScheduledCommand {
	
	public static final int WIDTH=800;
	public static final ResettableEventBus eventBus;
	public static final RestApi api;
	
	static {
		api = GWT.create(RestApi.class);
		eventBus = new ResettableEventBus(new SimpleEventBus());
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
		float wanted = WIDTH;
		float scaleby=width/wanted;
		if (scaleby<1f) {
			scaleby=1f;
		}
		rp.getElement().getStyle().setProperty("transform", "scale("+scaleby+")");
	}
	
//	private AppLocationHandler locHandler;
	
	public DictionaryApplication() {
		
	}
	
	private RootPanel rp;
	@Override
	public void execute() {
		RootPanel.get().clear(true);
		RootPanel.getBodyElement().setInnerHTML("<div id='root'"
				+ " style='margin-right: auto; margin-left: auto; margin-top: 10px; transform-origin: center top; max-width:"
				+ " " + (WIDTH-10)
				+ "px; padding: 5px; border: none;'></div>");
		rp = RootPanel.get("root");
		new AppLocationHandler(rp, eventBus);
		new DoAnalysis(eventBus);
		History.addValueChangeHandler(new HistoryChangeHandler(eventBus));
		doResize();
		Window.addResizeHandler(resize);
		History.fireCurrentHistoryState();
	}
}
