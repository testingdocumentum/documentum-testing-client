package com.testing.lfaction;

import com.documentum.fc.client.IDfModule;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.lifecycle.IDfLifecycleUserAction;
import com.documentum.operations.IDfFile;

public class LFActionInit implements IDfModule,IDfLifecycleUserAction{  
	  
	  
	  
	  
	  public String getVersion() {  
	  // TODO Auto-generated method stub  
	  return "1.0";  
	  }  
	  
	  
	  public String getVendorString() {  
	  // TODO Auto-generated method stub  
		  return "Documentum Testing Software";  
	  }   
	  
	  
	  public boolean isCompatible(String arg0) {  
	  // TODO Auto-generated method stub  
		  return false;  
	  }  
	  
	  
	  
	  
	  @Override  
	  public void userAction(IDfSysObject doc, String userName, String targetState) throws DfException  
	  {  
	  
	  
		  try  
		  {  
              
              doc.setString("subject", "Init") ;
              doc.save() ;
		  } catch ( DfException e) {
		  
		  }
	  }
}
