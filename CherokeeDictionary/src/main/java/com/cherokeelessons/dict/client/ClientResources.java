package com.cherokeelessons.dict.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface ClientResources extends ClientBundle {
	
	public static final ClientResources INSTANCE = GWT.create(ClientResources.class);

	@Source("text/ᎪᎸᏅᏱ.csv")
	public TextResource ᎪᎸᏅᏱ();
	
	@Source("text/ᎹᎦᎵ.csv")
	public TextResource ᎹᎦᎵ();

	@Source("text/CED.csv")
	public TextResource CED();
	
	@Source("text/boundpronouns.csv")
	public TextResource boundpronouns();

}