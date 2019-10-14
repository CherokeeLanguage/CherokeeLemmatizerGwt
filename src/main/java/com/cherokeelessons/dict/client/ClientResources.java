package com.cherokeelessons.dict.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface ClientResources extends ClientBundle {
	
	public static final ClientResources INSTANCE = GWT.create(ClientResources.class);

	@Source("text/raven.csv")
	public TextResource raven();
	
	@Source("text/magali.csv")
	public TextResource magali();

	@Source("text/CED.csv")
	public TextResource ced();
	
	@Source("text/boundpronouns.csv")
	public TextResource boundpronouns();

}