package com.cherokeelessons.dict.ui;

import java.util.ArrayList;
import java.util.List;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.FormGroup;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.PageHeader;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelFooter;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.TextBox;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.gwt.HTMLPanel;

import com.cherokeelessons.dict.client.DictionaryApplication;
import com.cherokeelessons.dict.shared.DictEntry;
import com.cherokeelessons.dict.shared.SearchResponse;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;

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
	
	@UiHandler("btn_search")
	public void onSearch(final ClickEvent event) {
		btn_search.state().loading();
		DictionaryApplication.api.syll(textBox.getValue(), display_it);
	}
	
	private MethodCallback<SearchResponse> display_it = new MethodCallback<SearchResponse>() {

		@Override
		public void onFailure(Method method, Throwable exception) {
			btn_search.state().reset();
			HTMLPanel panel = new HTMLPanel("ᎤᏲᏳ!<br/>"+exception.getMessage());
			rp.add(panel);
		}

		@Override
		public void onSuccess(Method method, SearchResponse sr) {
			btn_search.state().reset();
			if (sr==null) {
				GWT.log("ᎤᏲᏳ!");
				return;
			}
			GWT.log("COUNT: "+sr.data.size());
			for (DictEntry entry: sr.data) {
				final Panel p = new Panel(PanelType.SUCCESS);
				PanelHeader ph = new PanelHeader();
				Heading h = new Heading(HeadingSize.H3);
				h.setText(entry.definitiond);
				PanelBody pb = new PanelBody();
				HTMLPanel hp = new HTMLPanel(entry.toString().replace(", ", "<br/>").replace("=", ": ")+"<br/>");
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
	};

}
