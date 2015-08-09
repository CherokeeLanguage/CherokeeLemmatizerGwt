package com.cherokeelessons.dict.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelFooter;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.LabelType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.gwt.HTMLPanel;

import com.cherokeelessons.dict.client.DictionaryApplication;
import com.cherokeelessons.dict.events.AddPanelEvent;
import com.cherokeelessons.dict.events.AnalysisCompleteEvent;
import com.cherokeelessons.dict.events.AnalyzeEvent;
import com.cherokeelessons.dict.events.ClearResultsEvent;
import com.cherokeelessons.dict.events.RemovePanelEvent;
import com.cherokeelessons.dict.events.ResetInputEvent;
import com.cherokeelessons.dict.events.SearchResponseEvent;
import com.cherokeelessons.dict.events.UiEnableEvent;
import com.cherokeelessons.dict.shared.DictEntry;
import com.cherokeelessons.dict.shared.FormattedEntry;
import com.cherokeelessons.dict.shared.SearchResponse;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;
import commons.lang3.StringUtils;

public class SyllabarySearch extends Composite {
	public static interface AnalyzerEventBinder extends EventBinder<SyllabarySearch> {};
	private final AnalyzerEventBinder binder = GWT.create(AnalyzerEventBinder.class);

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
	
	@UiField
	protected Button btn_reset;
	
	@EventHandler
	public void enable(UiEnableEvent event) {
		btn_analyze.setEnabled(event.enable);
		btn_search.setEnabled(event.enable);
		btn_reset.setEnabled(event.enable);
		textBox.setEnabled(event.enable);
		if (event.enable) {
			btn_analyze.state().reset();
			btn_search.state().reset();
		}
	}
	
	@EventHandler
	public void onClearResults(ClearResultsEvent event) {
		GWT.log("ClearResultsEvent");
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
		eventBus.fireEvent(new UiEnableEvent(true));
	}

	private static MainMenuUiBinder uiBinder = GWT
			.create(MainMenuUiBinder.class);

	protected interface MainMenuUiBinder extends
			UiBinder<Widget, SyllabarySearch> {
	}

	private final RootPanel rp;
	private final EventBus eventBus;
	public SyllabarySearch(EventBus eventBus, RootPanel rp) {
		initWidget(uiBinder.createAndBindUi(this));
		binder.bindEventHandlers(this, eventBus);
		Document.get().setTitle("ᎤᎪᎵᏰᏗ - ᏣᎳᎩ ᏗᏕᏠᏆᏙᏗ");
		this.rp = rp;
		this.eventBus = eventBus;
	}

	@UiHandler("btn_reset")
	public void onClearResults(final ClickEvent event) {
		eventBus.fireEvent(new ClearResultsEvent());
		eventBus.fireEvent(new ResetInputEvent());
	}
	
	@EventHandler
	public void onResetInput(ResetInputEvent event) {
		textBox.setValue("");
	}

	private final List<Panel> panels = new ArrayList<Panel>();

	@UiHandler("btn_analyze")
	public void onAnalyze(final ClickEvent event) {
		btn_analyze.state().loading();
		alert.removeFromParent();
		String value = textBox.getValue();
		if (StringUtils.isBlank(value)) {
			alert.setDismissable(true);
			alert.setText("NOTHING TO ANALYZE.");
			rp.add(alert);
			btn_analyze.state().reset();
			return;
		}
		textBox.setEnabled(false);
		eventBus.fireEvent(new AnalyzeEvent(textBox.getValue()));
	}

	private final Alert alert = new Alert("", AlertType.DANGER);

	@UiHandler("btn_search")
	public void onSearch(final ClickEvent event) {
		alert.removeFromParent();
		btn_search.state().loading();
		String value = textBox.getValue();
		if (StringUtils.isBlank(value)) {
			alert.setDismissable(true);
			alert.setText("EMPTY SEARCHES ARE NOT ALLOWED.");
			rp.add(alert);
			btn_search.state().reset();
			return;
		}
		DictionaryApplication.api.syll(StringUtils.strip(value), display_it);
	}
	
	@EventHandler
	public void removePanel(RemovePanelEvent event) {
		Panel p = event.p;
		p.clear();
		p.removeFromParent();
		GWT.log("Panel Removed: "
				+ Boolean.valueOf(panels.remove(p)));
	}
	
	@EventHandler
	public void addPanel(AddPanelEvent event) {
		rp.add(event.p);
		panels.add(event.p);
	}

	@EventHandler
	public void process(SearchResponseEvent event) {
		SearchResponse sr = event.response;
		GWT.log("COUNT: " + sr.data.size());
		for (DictEntry entry : sr.data) {
			// if (!entry.source.equals("ced")){
			// continue;
			// }
			final Panel p = new Panel(PanelType.SUCCESS);
			Style style = p.getElement().getStyle();
			style.setWidth((DictionaryApplication.WIDTH-20)/2-5, Unit.PX);
			style.setDisplay(Display.INLINE_BLOCK);
			style.setMarginRight(5, Unit.PX);
			style.setVerticalAlign(Style.VerticalAlign.TOP);
			PanelHeader ph = new PanelHeader();
			Heading h = new Heading(HeadingSize.H5);
			ph.add(h);
			h.setText(entry.definitiond);
			h.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
			Label source = new Label(LabelType.INFO);
			ph.add(source);
			source.getElement().getStyle().setFloat(Style.Float.RIGHT);
			source.setText("["+entry.source+"]");
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
			rp.add(p);
			panels.add(p);
		}
	}

	private MethodCallback<SearchResponse> display_it = new MethodCallback<SearchResponse>() {

		@Override
		public void onFailure(Method method, Throwable exception) {
			btn_search.state().reset();
			HTMLPanel panel = new HTMLPanel("onFailure: ᎤᏲᏳ!<br/>"
					+ exception.getMessage());
			rp.add(panel);
			throw new RuntimeException(exception);
		}

		@Override
		public void onSuccess(Method method, final SearchResponse sr) {
			btn_search.state().reset();
			if (sr == null) {
				HTMLPanel panel = new HTMLPanel("ᎤᏲᏳ! SearchResponse is NULL");
				rp.add(panel);
				return;
			}
			GWT.log("SearchResponse");
			eventBus.fireEvent(new ClearResultsEvent());
			eventBus.fireEvent(new SearchResponseEvent(sr));
		}
	};

}
