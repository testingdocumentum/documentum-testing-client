package com.testing.lfaction;

import com.documentum.fc.client.IDfModule;
import com.documentum.fc.client.IDfSysObject;
import com.documentum.fc.common.DfException;
import com.documentum.fc.common.DfTime;
import com.documentum.fc.lifecycle.IDfLifecycleUserAction;

public class LFActionFinish  implements IDfModule,IDfLifecycleUserAction{  
	  
	  
	  
	  
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
            
	            doc.setString("subject", "Custom Finish complete") ;
	            doc.save() ;
	            
	            doc.queue(userName , "event", 0, false, new DfTime(), " promote complete") ;
		  } catch ( DfException e) {
		  
		  }
	  }
}
