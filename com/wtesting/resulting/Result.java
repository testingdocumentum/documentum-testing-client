package com.wtesting.resulting;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.wtesting.rmi.common.DocumentumObject;

public class Result implements Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7118502359319118105L;

	//HashMap<String,DocumentumObject> newObject ;
	Map<String,DocumentumObject> savedObject = new HashMap<>();
	
	List<String> audit = new ArrayList<>() ;
	
	Map<String, ArrayList<String>>  queue = new HashMap<>() ;

	public Map<String, ArrayList<String>>  getQueue() {
		return queue;
	}

	public void setQueue(Map<String, ArrayList<String>>  queue) {
		this.queue = queue;
	}

	public Map<String, DocumentumObject> getSavedObject() {
		return savedObject;
	}

	public void addSavedObject(String id ,  DocumentumObject saveObject) {
		this.savedObject.put(id, saveObject) ;
	}

	public List<String> getAudit() {
		return audit;
	}

	public void addAudit(String auditString) {
		audit.add(auditString) ;
	} 
	
	
}
