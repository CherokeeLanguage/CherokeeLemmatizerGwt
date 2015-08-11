package com.cherokeelessons.dict.client;

import com.cherokeelessons.dict.engine.DoAnalysis;
import com.cherokeelessons.dict.events.AppLocationHandler;
import com.cherokeelessons.dict.shared.RestApi;
import com.cherokeelessons.dict.ui.DialogManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.web.bindery.event.shared.ResettableEventBus;
import com.google.web.bindery.event.shared.SimpleEventBus;

public class DictionaryApplication implements ScheduledCommand {

	public static final int WIDTH = 800;
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
			if (doResizeTimer != null) {
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
		float scaleby = width / wanted;
		float height = Window.getClientHeight();
		if (scaleby < 1f) {
			scaleby = 1f;
		}
		Style style = rp.getElement().getStyle();
		style.setProperty("transform", "scale(" + scaleby + ")");
		style.setProperty("minHeight", ((int) (height / scaleby) - 10) + "px");
	}

	public DictionaryApplication() {
	}

	private RootPanel rp;

	@Override
	public void execute() {
		RootPanel.get().clear(true);
		rp = RootPanel.get();
		// rp.getElement().setPropertyString("style",
		// "transform-origin: center top;");
		Style style = rp.getElement().getStyle();
		String[] engines = new String[] { "", "webkit", "ms", "Moz", "O",
				"khtml" };
		String value = "center top";
		String name = "TransformOrigin";
		for (String prefix : engines) {
			style.setProperty(prefix + name, value);
		}
		style.setProperty("marginRight", "auto");
		style.setProperty("marginLeft", "auto");
		style.setProperty("marginTop", "10px");
		style.setProperty("maxWidth", (WIDTH - 10) + "px");
		style.setProperty("padding", "5px");
		style.setProperty("border", "none");
		new AppLocationHandler(rp, eventBus);
		new DoAnalysis(eventBus);
		new DialogManager(rp, eventBus);
		History.addValueChangeHandler(new HistoryChangeHandler(eventBus));
		doResize();
		Window.addResizeHandler(resize);
		History.fireCurrentHistoryState();
	}
}
