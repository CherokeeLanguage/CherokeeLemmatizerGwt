package com.cherokeelessons.dict.client;

import java.util.Map;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.cherokeelessons.dict.shared.DictEntry;
import com.cherokeelessons.dict.shared.RestApi;
import com.cherokeelessons.dict.shared.SearchResponse;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

public class DictionaryApplication implements ScheduledCommand {

	private static final CodecSearchResponse _CodecSearchResponse;
	
	private static final RestApi api;
	static {
		api = GWT.create(RestApi.class);
		_CodecSearchResponse=GWT.create(CodecSearchResponse.class);
	}
	
	@Override
	public void execute() {
		@SuppressWarnings("unused")
		Map<Integer, DictEntry> noop=REST.withCallback(console_log_it).call(api).syll("ᎤᏍᏗ");
	}
	
	MethodCallback<Map<Integer, DictEntry>> console_log_it = new MethodCallback<Map<Integer, DictEntry>>() {

		@Override
		public void onFailure(Method method, Throwable exception) {
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
			sr.results.addAll(((Map<Integer, DictEntry>)_response).values());
			GWT.log("COUNT: "+sr.results.size());
			for (DictEntry entry: sr.results) {
				GWT.log(entry.id+": "+entry.syllabaryb);
			}
		}
	};

}
