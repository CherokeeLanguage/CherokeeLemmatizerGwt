package com.cherokeelessons.dict.client;

import java.util.Map;

import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.REST;

import com.cherokeelessons.dict.shared.RestApi;
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
		Object noop=REST.withCallback(console_log_it).call(api).syll("ᎤᏍᏗ");
	}
	
	MethodCallback<Object> console_log_it = new MethodCallback<Object>() {

		@Override
		public void onFailure(Method method, Throwable exception) {
			GWT.log(exception.getMessage());
		}

		@Override
		public void onSuccess(Method method, Object _response) {
			if (_response==null) {
				GWT.log("ᎤᏲᏒ!");
				return;
			}
			if (_response instanceof Map) {
				for (Object obj: ((Map)_response).values()) {
					GWT.log(obj.getClass().getSimpleName()+": "+String.valueOf(obj));
				}
			}
//			SearchResponse sr = _CodecSearchResponse.decode("{"+_response.toString()+"}");
//			GWT.log("COUNT: "+sr.size());
		}
	};

}
