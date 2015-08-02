package com.cherokeelessons.dict.shared;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.fusesource.restygwt.client.DirectRestService;
import org.fusesource.restygwt.client.JSONP;

import com.google.gwt.json.client.JSONValue;

@Path("http://www.cherokeedictionary.net/jsonsearch/")
@Produces({"application/javascript"})
@Consumes({"application/javascript"})
public interface RestApi extends DirectRestService {
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	//@GET
	@Path("syll/{query}")
	public Object syll(@PathParam("query")String query);
	
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	@Path("chr/{query}")
	public Object chr(@PathParam("query")String query);
	
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	@Path("en/{query}")
	public Object en(@PathParam("query")String query);
}
