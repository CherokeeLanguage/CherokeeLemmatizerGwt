package com.cherokeelessons.dict.events;

import com.cherokeelessons.dict.engine.DoAnalysis;
import com.cherokeelessons.dict.ui.AnalysisView;
import com.cherokeelessons.dict.ui.DialogManager;
import com.google.gwt.core.client.GWT;
import com.google.web.bindery.event.shared.binder.EventBinder;

public interface Binders {
	public static interface DialogEventBinder extends EventBinder<DialogManager> {};
	public static final DialogEventBinder binder_dialog = GWT.create(DialogEventBinder.class);
	
	public static interface AppLocationEventBinder extends	EventBinder<AppLocationHandler> {}
	public static final AppLocationEventBinder binder_applocation=GWT.create(AppLocationEventBinder.class);
	
	public static interface AnalysisViewEventBinder extends EventBinder<AnalysisView> {};
	public static final AnalysisViewEventBinder binder_analysisView = GWT.create(AnalysisViewEventBinder.class);
	
	public static interface DoAnalysisBinder extends EventBinder<DoAnalysis> {}

	public static final DoAnalysisBinder binder_analyzer = GWT.create(DoAnalysisBinder.class);
}
