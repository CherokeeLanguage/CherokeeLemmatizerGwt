package com.cherokeelessons.dict.client;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.cherokeelessons.dict.shared.ClientLookup;
import com.cherokeelessons.dict.shared.RestApi;
import com.cherokeelessons.dict.ui.SyllabarySearch;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.opencsv.CSVParser;

public class DictionaryApplication implements ScheduledCommand {
	
	public static final int WIDTH=800;
	
	public static ClientLookup lookup;
	public static final RestApi api;
	static {
		api = GWT.create(RestApi.class);
		lookup = new ClientLookup();
		loadᎹᎦᎵ();
		loadCED();
		loadRaven();
		GWT.log("LOOKUP: ᎣᏍᏛ "+lookup.guessed("ᎣᏍᏛ"));
	}
	private static void loadRaven() {
		String[] lines = ClientResources.INSTANCE.ᎪᎸᏅᏱ().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				data.add(parser.parseLine(iline.next()));
			} catch (IOException e) {
			}
		}
		lookup.addEntries(data);
	}
	
	private static void loadᎹᎦᎵ() {
		String[] lines = ClientResources.INSTANCE.ᎹᎦᎵ().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				String next = iline.next();
				if (!next.contains("[ᎹᎦᎵ]")){
					next += " [ᎹᎦᎵ]";
				}
				data.add(parser.parseLine(next));
			} catch (IOException e) {
			}
		}
		lookup.addEntries(data);
	}
	
	private static void loadCED() {
		String[] lines = ClientResources.INSTANCE.CED().getText().split("\n");
		CSVParser parser = new CSVParser();
		Iterator<String> iline = Arrays.asList(lines).iterator();
		List<String[]> data=new ArrayList<>();
		while (iline.hasNext()) {
			try {
				String next = iline.next();
				data.add(parser.parseLine(next));
			} catch (IOException e) {
			}
		}
		lookup.addEntries(data);
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
	
	private RootPanel rp;
	@Override
	public void execute() {
		RootPanel.get().clear(true);
		RootPanel.getBodyElement().setInnerHTML("<div id='root'"
				+ " style='margin-right: auto; margin-left: auto; margin-top: 10px; transform-origin: center top; max-width:"
				+ " " + (WIDTH-10)
				+ "px; padding: 5px; border: none;'></div>");
		rp = RootPanel.get("root");
		doResize();
		
		SyllabarySearch mainwindow = new SyllabarySearch(rp);
		rp.add(mainwindow);
		
		Window.addResizeHandler(resize);
	}
}
