package com.cherokeelessons.dict.engine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.gwtbootstrap3.client.ui.Button;
import org.gwtbootstrap3.client.ui.Heading;
import org.gwtbootstrap3.client.ui.Label;
import org.gwtbootstrap3.client.ui.Panel;
import org.gwtbootstrap3.client.ui.PanelBody;
import org.gwtbootstrap3.client.ui.PanelFooter;
import org.gwtbootstrap3.client.ui.PanelHeader;
import org.gwtbootstrap3.client.ui.constants.HeadingSize;
import org.gwtbootstrap3.client.ui.constants.LabelType;
import org.gwtbootstrap3.client.ui.constants.PanelType;
import org.gwtbootstrap3.client.ui.gwt.HTMLPanel;

import com.cherokeelessons.dict.client.ClientDictionary;
import com.cherokeelessons.dict.client.DictionaryApplication;
import com.cherokeelessons.dict.engine.Affixes.AffixResult;
import com.cherokeelessons.dict.events.AbortEvent;
import com.cherokeelessons.dict.events.AddAnalysisPanelEvent;
import com.cherokeelessons.dict.events.AnalysisCompleteEvent;
import com.cherokeelessons.dict.events.AnalyzeEvent;
import com.cherokeelessons.dict.events.ClearResultsEvent;
import com.cherokeelessons.dict.events.RemovePanelEvent;
import com.cherokeelessons.dict.events.ResetInputEvent;
import com.cherokeelessons.dict.events.UiEnableEvent;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import commons.lang3.StringUtils;

public class DoAnalysis {
	public static interface DoAnalysisBinder extends EventBinder<DoAnalysis> {}
	private final DoAnalysisBinder binder = GWT.create(DoAnalysisBinder.class);
	
	private final EventBus eventBus;
	public DoAnalysis(EventBus eventBus) {
		binder.bindEventHandlers(this, eventBus);
		this.eventBus=eventBus;
	}
	
	@EventHandler
	void abort(AbortEvent event) {
		
	}
	
	@EventHandler
	void analyze(AnalyzeEvent event) {
		String value = StringUtils.strip(event.query);
		value = value.replaceAll("[^Ꭰ-Ᏼ0-9]", " ");
		value = value.replaceAll(" +", " ");
		if (StringUtils.isBlank(value)) {
			eventBus.fireEvent(new ResetInputEvent());
			eventBus.fireEvent(new UiEnableEvent(true));
			return;
		}
		eventBus.fireEvent(new UiEnableEvent(false));
		eventBus.fireEvent(new ClearResultsEvent());
		final List<ScheduledCommand> cmds = new ArrayList<>();
		List<String> words = new ArrayList<>(Arrays.asList(StringUtils.split(value)));
		ListIterator<String> iwords = words.listIterator();
		/*
		 * ᎢᏗ can be added to other than past tense... this seems to change bare "Ꭰ" + "Ꭲ" to "Ꭱ"
		 */
		while (iwords.hasNext()) {
			String word=iwords.next();
			if (word.endsWith("ᎡᏗ")){
//				iwords.add(StringUtils.removeEnd(word, "ᎡᏗ")+"ᎠᎢᏗ");
			}
		}
		
		for (final String word : words) {
			cmds.add(new ScheduledCommand() {
				@Override
				public void execute() {
					PanelType type = PanelType.DEFAULT;
					SafeHtmlBuilder shb = new SafeHtmlBuilder();
					List<AffixResult> matched = SuffixGuesser.INSTANCE
							.getMatches(word);
					if (matched.size() != 0) {
						type = PanelType.SUCCESS;
					}
					StringBuilder affixedStem = new StringBuilder();
					String innerstem = "";
					for (AffixResult match : matched) {
						for (String stem: match.stem) {
							shb.appendEscaped(match.stem + "+" + match.suffix + ":"
									+ match.desc);
							String info = ClientDictionary.INSTANCE
									.guess(stem);
							if (!StringUtils.isBlank(info)) {
								shb.appendHtmlConstant("<br/><span style='color: navy; font-weight: bold;'>");
								shb.appendEscapedLines(info.replace("|", "\n"));
								shb.appendHtmlConstant("</span><br/>");
							}
							shb.appendHtmlConstant("<br />");
						}
						if (match.desc.contains("*")) {
							type = PanelType.DANGER;
						}
						for (String stem: match.stem) {
							affixedStem.insert(0, "[+"+match.suffix+"]");
							innerstem = stem;
						}
					}
					affixedStem.insert(0, innerstem);
					SafeHtmlBuilder affixedStemHtml = new SafeHtmlBuilder();

					String info = ClientDictionary.INSTANCE.guess(word);
					if (!StringUtils.isBlank(info)) {
						affixedStemHtml
								.appendHtmlConstant("<span style='color: navy; font-weight: bold;'>");
						affixedStemHtml.appendEscapedLines(info.replace("|",
								"\n"));
						affixedStemHtml.appendHtmlConstant("</span><br/>");
					}

					if (affixedStem.length() != 0) {
						affixedStemHtml
								.appendHtmlConstant("<span style='font-style: italic; font-weight: bold;'>");
						affixedStemHtml.appendEscaped(affixedStem.toString());
						affixedStemHtml.appendHtmlConstant("</span><br/>");
					}
					affixedStemHtml.append(shb.toSafeHtml());

					final Panel p = new Panel(type);
					Style style = p.getElement().getStyle();
					style.setWidth((DictionaryApplication.WIDTH - 20) / 3 - 5,
							Unit.PX);
					style.setDisplay(Display.INLINE_BLOCK);
					style.setMarginRight(5, Unit.PX);
					style.setVerticalAlign(Style.VerticalAlign.TOP);
					PanelHeader ph = new PanelHeader();
					Heading h = new Heading(HeadingSize.H5);
					h.setText(word);
					ph.add(h);
					h.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
					Label source = new Label(LabelType.INFO);
					ph.add(source);
					source.getElement().getStyle().setFloat(Style.Float.RIGHT);
					source.setText("[analysis]");
					PanelBody pb = new PanelBody();

					HTMLPanel hp = new HTMLPanel(affixedStemHtml.toSafeHtml());

					PanelFooter pf = new PanelFooter();
					Button dismiss = new Button("DISMISS");
					dismiss.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(ClickEvent event) {
							eventBus.fireEvent(new RemovePanelEvent(p));
						}
					});
					pf.add(dismiss);
					p.add(ph);
					p.add(pb);
					p.add(pf);

					pb.add(hp);
					eventBus.fireEvent(new AddAnalysisPanelEvent(p));
				}
			});
		}
		cmds.add(new ScheduledCommand() {
			@Override
			public void execute() {
				eventBus.fireEvent(new AnalysisCompleteEvent());
			}
		});
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			@Override
			public void execute() {
				if (cmds.size() > 0) {
					ScheduledCommand cmd = cmds.get(0);
					cmds.remove(0);
					Scheduler.get().scheduleDeferred(cmd);
					Scheduler.get().scheduleDeferred(this);
					return;
				}
			}
		});
	}

}
