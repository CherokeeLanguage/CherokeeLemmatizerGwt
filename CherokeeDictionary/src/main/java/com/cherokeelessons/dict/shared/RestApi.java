package com.cherokeelessons.dict.shared;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.fusesource.restygwt.client.DirectRestService;
import org.fusesource.restygwt.client.JSONP;

@Path("http://www.cherokeedictionary.net/jsonsearch/")
@Produces({"application/javascript"})
@Consumes({"application/javascript"})
public interface RestApi extends DirectRestService {
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	//@GET
	@Path("syll/{query}")
	public Map<Integer, DictEntry> syll(@PathParam("query")String query);
	
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	@Path("chr/{query}")
	public Map<Integer, DictEntry> chr(@PathParam("query")String query);
	
	@JSONP(callbackParam="callback", failureCallbackParam="failure")
	@Path("en/{query}")
	public Map<Integer, DictEntry> en(@PathParam("query")String query);
}
