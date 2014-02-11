package com.test.tbo;

import com.documentum.fc.client.DfDocument;
import com.documentum.fc.client.IDfBusinessObject;
import com.documentum.fc.client.IDfDocument;
import com.documentum.fc.common.DfException;

public class TestTypeTBO extends DfDocument implements IDfDocument,IDfBusinessObject {
	
	@Override
	protected synchronized void doSave(boolean keepLock, String versionLabels,
			Object[] extendedArgs) throws DfException {
		this.setString("test_string", "it is client_tbo") ;
		super.doSave(keepLock, versionLabels, extendedArgs) ;
	}

	@Override
	public String getVendorString() {
		// TODO Auto-generated method stub
		return "Documentun Testing Software";
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return "1.0";
	}

	@Override
	public boolean isCompatible(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean supportsFeature(String arg0) {
		// TODO Auto-generated method stub
		return false;
	}

}
