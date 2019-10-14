package com.cherokeelessons.dict.client;

import java.util.logging.Logger;

import com.cherokeelessons.dict.engine.DoAnalysis;
import com.cherokeelessons.dict.events.AppLocationHandler;
import com.cherokeelessons.dict.events.MessageEvent;
import com.cherokeelessons.dict.shared.Log;
import com.cherokeelessons.dict.ui.DialogManager;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.History;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;

public class DictionaryApplication implements ScheduledCommand {

	public static final int WIDTH = 800;
	
	private final Logger logger = Log.getGwtLogger(new ConsoleLogHandler2(), this.getClass().getSimpleName());

	private Timer doResizeTimer;
	private final ResizeHandler resize = new ResizeHandler() {
		@Override
		public void onResize(ResizeEvent event) {
			logger.info("onResize");
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
		logger.info("doResize");
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
		
		rp = RootPanel.get();
		
		new AppLocationHandler(rp);
		new DoAnalysis();
		new DialogManager(rp);
		
		History.addValueChangeHandler(new HistoryChangeHandler());
		doResize();
		Window.addResizeHandler(resize);
		History.fireCurrentHistoryState();
		
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
		
		BuildInfo info = GWT.create(BuildInfo.class);
		StringBuilder sb = new StringBuilder();
		sb.append("By using this application you agree you understand the following:\n\n");
		sb.append("1) This is NOT A TRANSLATOR!\n");
		sb.append("2) This will NOT PROVIDE ENGLISH TRANSLATIONS!\n");
		sb.append("3) Only those studying the language FOR REAL will make any sense of this.\n");
		sb.append("4) Ads and links to other sites will be displayed sometime in the future.\n");
		sb.append("5) NO WARRANTY! NO MERCHANTABILITY! NO GUARANTEE!\n");
		sb.append("6) This software will most certainly suggest WRONG meanings.\n");
		sb.append("7) This software will most certainly provide WRONG work breakdowns.\n\n");
		sb.append("This software was last udpated: ");
		sb.append(DateTimeFormat.getFormat("MMM dd, yyyy - HH:mm:ss a zzz").format(info.getBuildTimestamp()));
		DictEntryPoint.eventBus.fireEvent(new MessageEvent("ᎠᎪᎵᏰᏙᏗ ᏣᎳᎩ ᎦᏬᏂᎯᏍᏗ",sb.toString().replace("\n", "<br/>")));
	}
}
