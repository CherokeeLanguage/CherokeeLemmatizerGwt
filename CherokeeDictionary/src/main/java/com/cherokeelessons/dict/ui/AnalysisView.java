package com.cherokeelessons.dict.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.FormLabel;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelFooter;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.TextArea;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.LabelType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.gwt.HTMLPanel;

import com.cherokeelessons.dict.client.DictionaryApplication;
import com.cherokeelessons.dict.events.AddAnalysisPanelEvent;
import com.cherokeelessons.dict.events.AddSearchResultPanelEvent;
import com.cherokeelessons.dict.events.AnalysisCompleteEvent;
import com.cherokeelessons.dict.events.AnalyzeEvent;
import com.cherokeelessons.dict.events.Binders;
import com.cherokeelessons.dict.events.ClearResultsEvent;
import com.cherokeelessons.dict.events.EnableSearchEvent;
import com.cherokeelessons.dict.events.HistoryTokenEvent;
import com.cherokeelessons.dict.events.MessageEvent;
import com.cherokeelessons.dict.events.RemovePanelEvent;
import com.cherokeelessons.dict.events.ReplaceTextInputEvent;
import com.cherokeelessons.dict.events.ResetInputEvent;
import com.cherokeelessons.dict.events.SearchResponseEvent;
import com.cherokeelessons.dict.events.UiEnableEvent;
import com.cherokeelessons.dict.shared.DictEntry;
import com.cherokeelessons.dict.shared.FormattedEntry;
import com.cherokeelessons.dict.shared.SearchResponse;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Window.Location;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.HandlerRegistration;
import com.google.web.bindery.event.shared.binder.EventHandler;

import commons.lang3.StringUtils;

public class AnalysisView extends Composite {

	// @UiField
	// protected PageHeader pageHeader;

	@UiField
	protected FormGroup formGroup;

	@UiField
	protected FormLabel formLabel;

	@UiField
	protected TextArea textArea;

	@UiField
	protected Button btn_analyze;

	@UiField
	protected Button btn_search;

	@UiField
	protected Button btn_reset;

	@UiField
	protected Button btn_clear;

	@EventHandler
	public void setText(ReplaceTextInputEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#setText");
		textArea.setValue(event.text);
	}

	@EventHandler
	public void enable(UiEnableEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#enable");
		btn_analyze.setEnabled(event.enable);
		btn_search.setEnabled(event.enable);
		btn_reset.setEnabled(event.enable);
		btn_clear.setEnabled(event.enable);
		textArea.setEnabled(event.enable);
		if (event.enable) {
			btn_analyze.state().reset();
			btn_search.state().reset();
		}
	}

	@EventHandler
	public void onClearResults(ClearResultsEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#onClearResults");
		Iterator<Panel> ip = panels.iterator();
		while (ip.hasNext()) {
			Panel next = ip.next();
			next.clear();
			next.removeFromParent();
			ip.remove();
		}
	}

	@EventHandler
	public void onCompletion(AnalysisCompleteEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#onCompletion");
		eventBus.fireEvent(new UiEnableEvent(true));
	}

	private static MainMenuUiBinder uiBinder = GWT
			.create(MainMenuUiBinder.class);

	protected interface MainMenuUiBinder extends UiBinder<Widget, AnalysisView> {
	}

	private final RootPanel rp;
	private final EventBus eventBus;

	private KeyPressHandler keypress = new KeyPressHandler() {
		@Override
		public void onKeyPress(KeyPressEvent event) {
			if (event.isControlKeyDown() && event.isAltKeyDown()
					&& event.getCharCode() == 's') {
				eventBus.fireEvent(new EnableSearchEvent(!btn_search
						.isVisible()));
			}
		}
	};

	private HandlerRegistration reg;

	private String prevTitle;

	@Override
	protected void onLoad() {
		super.onLoad();
		reg = Binders.binder_analysisView.bindEventHandlers(this, eventBus);
		GWT.log("onLoad#" + String.valueOf(Binders.binder_analysisView));
		prevTitle = Document.get().getTitle();
		Document.get().setTitle("ᎤᎪᎵᏰᏗ - ᏣᎳᎩ ᏗᏕᏠᏆᏙᏗ");
	}

	@Override
	protected void onUnload() {
		super.onUnload();
		reg.removeHandler();
		GWT.log("onUnload#" + String.valueOf(Binders.binder_analysisView));
		Document.get().setTitle(prevTitle);
	}

	public AnalysisView(EventBus eventBus, RootPanel rp) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rp = rp;
		this.eventBus = eventBus;
		addDomHandler(keypress, KeyPressEvent.getType());
		textArea.getElement().getStyle().setProperty("marginLeft", "auto");
		textArea.getElement().getStyle().setProperty("marginRight", "auto");
		textArea.setHeight("6.7em");
	}

	@UiHandler("btn_reset")
	public void onResetAll(final ClickEvent event) {
		eventBus.fireEvent(new ClearResultsEvent());
		eventBus.fireEvent(new ResetInputEvent());
		String token = StringUtils.substringAfter(Location.getHref(), "#");
		token = token.replaceAll("&text=[^&]*", "");
		eventBus.fireEvent(new HistoryTokenEvent(token));
	}

	@UiHandler("btn_clear")
	public void onClearResults(final ClickEvent event) {
		eventBus.fireEvent(new ClearResultsEvent());
	}

	@EventHandler
	public void onResetInput(ResetInputEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#resetInput");
		textArea.setValue("");
	}

	private final List<Panel> panels = new ArrayList<Panel>();

	@UiHandler("btn_analyze")
	public void onAnalyze(final ClickEvent event) {
		Scheduler.get().scheduleFinally(new ScheduledCommand() {
			@Override
			public void execute() {
				// this.pageHeader.setVisible(false);
				formLabel.setVisible(false);
				btn_analyze.state().loading();
				final String value = textArea.getValue();
				if (StringUtils.isBlank(value)) {
					eventBus.fireEvent(new MessageEvent("ERROR",
							"NOTHING TO ANALYZE."));
					btn_analyze.state().reset();
					return;
				}
				String token = StringUtils.substringAfter(Location.getHref(),
						"#");
				token = token.replaceAll("&text=[^&]*", "");
				token = token + "&text=" + value;
				final String h = token;
				GWT.log("1 fire#history " + h);
				eventBus.fireEvent(new HistoryTokenEvent(h));
				GWT.log("2 fire#analyze " + value);
				eventBus.fireEvent(new AnalyzeEvent(value));
				GWT.log("3 fire#done");
			}

		});
	}

	@UiHandler("btn_search")
	public void onSearch(final ClickEvent event) {
		// this.pageHeader.setVisible(false);
		this.formLabel.setVisible(false);
		btn_search.state().loading();
		String value = textArea.getValue();
		if (StringUtils.isBlank(value)) {
			eventBus.fireEvent(new MessageEvent("ERROR",
					"EMPTY SEARCHES ARE NOT ALLOWED."));
			btn_search.state().reset();
			return;
		}
		String token = StringUtils.substringAfter(Location.getHref(), "#");
		token = token.replaceAll("&text=[^&]*", "");
		token = token + "&text=" + value;
		eventBus.fireEvent(new HistoryTokenEvent(token));
		eventBus.fireEvent(new UiEnableEvent(false));
		DictionaryApplication.api.syll(StringUtils.strip(value), display_it);
	}

	@EventHandler
	public void onEnableSearch(EnableSearchEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#enableSearch");
		if (event.enable) {
			btn_search.setVisible(true);
		} else {
			btn_search.setVisible(false);
		}
	}

	@EventHandler
	public void removePanel(RemovePanelEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#removePanel");
		Panel p = event.p;
		p.clear();
		p.removeFromParent();
	}

	@EventHandler
	public void addPanel(AddAnalysisPanelEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#addPanel(analysis)");
		rp.add(event.p);
		panels.add(event.p);
	}

	@EventHandler
	public void addPanel(AddSearchResultPanelEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#addPanel");
		rp.add(event.p);
		panels.add(event.p);
	}

	@EventHandler
	public void process(SearchResponseEvent event) {
		GWT.log(this.getClass().getSimpleName() + "#Event#process");
		int dupes = 0;
		Set<Integer> already = new HashSet<>();
		Set<Integer> duplicates = new HashSet<>();
		SearchResponse sr = event.response;
		GWT.log("COUNT: " + sr.data.size());
		Iterator<DictEntry> isr = sr.data.iterator();
		while (isr.hasNext()) {
			DictEntry entry = isr.next();
			if (already.contains(entry.id)) {
				GWT.log("DUPLICATE RECORD IN RESPONSE: " + entry.id);
				dupes++;
				duplicates.add(entry.id);
				isr.remove();
				continue;
			}
			already.add(entry.id);
		}
		for (DictEntry entry : sr.data) {
			final Panel p = new Panel(PanelType.SUCCESS);
			Style style = p.getElement().getStyle();
			style.setWidth((DictionaryApplication.WIDTH - 20) / 2 - 6, Unit.PX);
			style.setDisplay(Display.INLINE_BLOCK);
			style.setMarginRight(5, Unit.PX);
			style.setVerticalAlign(Style.VerticalAlign.TOP);
			PanelHeader ph = new PanelHeader();
			Heading h = new Heading(HeadingSize.H5);
			ph.add(h);
			String hdr = entry.definitiond + "<br/>[" + entry.id + "]";
			if (duplicates.contains(entry.id)) {
				hdr += " (DUPE DETECTED!)";
			}
			h.getElement().setInnerHTML(hdr);
			h.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			Label source = new Label(LabelType.INFO);
			ph.add(source);
			source.getElement().getStyle().setFloat(Style.Float.RIGHT);
			source.setText("[" + entry.source + "]");
			PanelBody pb = new PanelBody();
			HTMLPanel hp = new HTMLPanel(new FormattedEntry(entry).getHtml());
			Button dismiss = new Button("DISMISS");
			dismiss.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					eventBus.fireEvent(new RemovePanelEvent(p));
				}
			});
			PanelFooter pf = new PanelFooter();
			pf.add(dismiss);
			p.add(ph);
			p.add(pb);
			p.add(pf);

			pb.add(hp);
			eventBus.fireEvent(new AddSearchResultPanelEvent(p));
		}
		if (dupes > 0) {
			eventBus.fireEvent(new MessageEvent("ERROR", dupes
					+ " DUPES IN RESPONSE!"));
		}
		eventBus.fireEvent(new UiEnableEvent(true));
	}

	private MethodCallback<SearchResponse> display_it = new MethodCallback<SearchResponse>() {
		@Override
		public void onFailure(Method method, Throwable exception) {
			btn_search.state().reset();
			eventBus.fireEvent(new MessageEvent("FAILURE",
					"onFailure: ᎤᏲᏳ!<br/>" + exception.getMessage()));
			eventBus.fireEvent(new UiEnableEvent(true));
			throw new RuntimeException(exception);
		}

		@Override
		public void onSuccess(Method method, final SearchResponse sr) {
			btn_search.state().reset();
			if (sr == null) {
				eventBus.fireEvent(new MessageEvent("FAILURE",
						"ᎤᏲᏳ! SearchResponse is NULL"));
				return;
			}
			GWT.log("SearchResponse: " + sr.data.size());
			eventBus.fireEvent(new ClearResultsEvent());
			eventBus.fireEvent(new SearchResponseEvent(sr));
		}
	};

}
