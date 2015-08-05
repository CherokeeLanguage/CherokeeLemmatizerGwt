package com.cherokeelessons.dict.ui;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Alert;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelFooter;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.AlertType;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.gwt.HTMLPanel;

import com.cherokeelessons.dict.client.DictionaryApplication;
import com.cherokeelessons.dict.shared.DictEntry;
import com.cherokeelessons.dict.shared.FormattedEntry;
import com.cherokeelessons.dict.shared.SearchResponse;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
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
import commons.lang.StringUtils;

public class SyllabarySearch extends Composite {
	
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

	protected interface MainMenuUiBinder extends UiBinder<Widget, SyllabarySearch> {
	}

	private final RootPanel rp;
	public SyllabarySearch(RootPanel rp) {
		initWidget(uiBinder.createAndBindUi(this));
		this.rp=rp;
	}

	@UiHandler("btn_reset")
	public void onClearResults(final ClickEvent event) {
		for (Panel panel: panels) {
			panel.clear();
			panel.removeFromParent();
		}
		panels.clear();
	}
	
	private final List<Panel> panels=new ArrayList<Panel>();
	
	@UiHandler("btn_analyze")
	public void onAnalyze(final ClickEvent event) {
		//btn_analyze.state().loading();
	}
	
	Alert alert = new Alert("EMPTY SEARCHES ARE NOT ALLOWED.", AlertType.DANGER);
	
	@UiHandler("btn_search")
	public void onSearch(final ClickEvent event) {
		alert.removeFromParent();
		btn_search.state().loading();
		String value = textBox.getValue();
		if (StringUtils.isBlank(value)) {
			alert.setDismissable(true);
			rp.add(alert);
			btn_search.state().reset();
			return;
		}
		DictionaryApplication.api.syll(StringUtils.strip(value), display_it);
	}
	
	private void process(SearchResponse sr) {
		GWT.log("COUNT: "+sr.data.size());
		for (DictEntry entry: sr.data) {
			GWT.log("Entry: "+entry.id);
//				if (!entry.source.equals("ced")){
//					continue;
//				}
			final Panel p = new Panel(PanelType.SUCCESS);
			Style style = p.getElement().getStyle();
			style.setWidth(480, Unit.PX);
			style.setDisplay(Display.INLINE_BLOCK);
			style.setFloat(Style.Float.LEFT);
			style.setMarginTop(10, Unit.PX);
			style.setMarginLeft(4, Unit.PX);
			style.setMarginRight(4, Unit.PX);
			PanelHeader ph = new PanelHeader();
			Heading h = new Heading(HeadingSize.H5);
			h.setText(entry.definitiond);
			ph.add(h);
			PanelBody pb = new PanelBody();
			HTMLPanel hp = new HTMLPanel(new FormattedEntry(entry).getHtml());
			Button dismiss = new Button("DISMISS");
			dismiss.addClickHandler(new ClickHandler() {
				@Override
				public void onClick(ClickEvent event) {
					p.clear();
					p.removeFromParent();
					GWT.log("Panel Removed: "+Boolean.valueOf(panels.remove(p)));
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
			HTMLPanel panel = new HTMLPanel("onFailure: ᎤᏲᏳ!<br/>"+exception.getMessage());
			rp.add(panel);
			throw new RuntimeException(exception);
		}

		@Override
		public void onSuccess(Method method, final SearchResponse sr) {
			btn_search.state().reset();
			if (sr==null) {
				HTMLPanel panel = new HTMLPanel("ᎤᏲᏳ! SearchResponse is NULL");
				rp.add(panel);
				return;
			}
			Scheduler.get().scheduleDeferred(new ScheduledCommand() {
				@Override
				public void execute() {
					onClearResults(null);
					process(sr);
				}
			});
		}
	};

}