package com.cherokeelessons.dict.shared;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.JSONP;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

@Path("http://www.cherokeedictionary.net/jsonsearch/")
public interface RestApi extends RestService {
	
	@Path("syll/{query}?regex=true")
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	public void syll(@PathParam("query")String query, MethodCallback<SearchResponse> callback);
	
	@Path("chr/{query}?regex=true")
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	public void chr(@PathParam("query")String query, MethodCallback<SearchResponse> callback);
	
	@Path("en/{query}?regex=true")
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	public void en(@PathParam("query")String query, MethodCallback<SearchResponse> callback);
}
