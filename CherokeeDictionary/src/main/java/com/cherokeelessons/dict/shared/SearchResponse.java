package com.cherokeelessons.dict.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SearchResponse implements Serializable {
	public List<DictEntry> data=new ArrayList<DictEntry>();
}
