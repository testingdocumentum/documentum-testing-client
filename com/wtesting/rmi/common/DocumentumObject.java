package com.wtesting.rmi.common;

import java.io.Serializable;
import java.util.HashMap;

public class DocumentumObject  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7862936317530251116L;
	HashMap<String,Object> attributies = new HashMap<>() ;
	String dumpObject ;
	String id ;
	public String getDumpObject() {
		return dumpObject;
	}
	public void setDumpObject(String dumpObject) {
		this.dumpObject = dumpObject;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * TODO
	 * @param attributeName
	 * @param value
	 */
	public void put ( String attributeName , Object value ) {
		attributies.put(attributeName, value) ;
	}
}
