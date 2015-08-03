package com.cherokeelessons.dict.client;

import java.util.Map;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.cherokeelessons.dict.shared.DictEntry;
import com.cherokeelessons.dict.shared.RestApi;
import com.cherokeelessons.dict.shared.SearchResponse;
import com.cherokeelessons.dict.ui.MainMenu;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dev.shell.BrowserChannel;
import com.google.gwt.dom.client.Document;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Navigator;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class DictionaryApplication implements ScheduledCommand {

	private static final CodecSearchResponse _CodecSearchResponse;
	
	private static final RestApi api;
	static {
		api = GWT.create(RestApi.class);
		_CodecSearchResponse=GWT.create(CodecSearchResponse.class);
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
		GWT.log("SCALE: "+scaleby);
		rp.getElement().getStyle().setProperty("transform", "scale("+scaleby+")");
	}
	
	private RootPanel rp;
	@Override
	public void execute() {
		RootPanel.get().clear(true);
		RootPanel.getBodyElement().setInnerHTML("<div id='root' style='margin-right: auto; margin-left: auto; margin-top: 10px; transform-origin: center top; max-width: 1004px; padding: 5px; border: solid 2px;'></div>");
		rp = RootPanel.get("root");
		doResize();
		
		MainMenu mainwindow = new MainMenu();
		rp.add(mainwindow);
		
		Window.addResizeHandler(resize);
		
		@SuppressWarnings("unused")
		Map<Integer, DictEntry> noop=REST.withCallback(console_log_it).call(api).syll("ᎤᏍᏗ");
	}
	
	MethodCallback<Map<Integer, DictEntry>> console_log_it = new MethodCallback<Map<Integer, DictEntry>>() {

		@Override
		public void onFailure(Method method, Throwable exception) {
			GWT.log("ᎤᏲᏳ!");
			GWT.log(exception.getMessage());
		}

		@Override
		public void onSuccess(Method method, Map<Integer, DictEntry> _response) {
			if (_response==null) {
				GWT.log("ᎤᏲᏳ!");
				return;
			}
			if (!(_response instanceof Map)) {
				GWT.log("ᎤᏲᏒ!");
				return;
			}
			SearchResponse sr = new SearchResponse();
			sr.data.addAll(((Map<Integer, DictEntry>)_response).values());
			GWT.log("COUNT: "+sr.data.size());
			for (DictEntry entry: sr.data) {
				GWT.log(entry.id+": "+entry.syllabaryb);
			}
		}
	};

}
