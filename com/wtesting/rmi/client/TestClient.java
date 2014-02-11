package com.wtesting.rmi.client;



import java.io.FileInputStream;
import java.io.ObjectOutput;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RMISecurityManager;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.documentum.fc.common.IDfId;
import com.healthmarketscience.rmiio.SimpleRemoteInputStream;
import com.wtesting.resulting.Result;
import com.wtesting.rmi.common.DocumentumObject;
import com.wtesting.rmi.server.ITestDocumentum;


public class TestClient {

	
	public static String NAMING_SERVER = "ITestDoocumentumServer" ;
	public static int port = 0 ;
	public static String SERVER_IP = "ask_server_ip" ; 
	
	 public static void main(String[] args) {

		 
	        System.setSecurityManager(new RMISecurityManager());
	        String tboClass = "D://Projects//rmiclient//bin//com//test//tbo//TestTypeTBO.class";
	        String lfClass = "D://Projects//rmiclient//bin//com//testing//lfaction//LFActionFinish.class";
	        
	        System.out.println("Client started");

		    Remote remote;
			try {
				
				 remote = Naming.lookup("rmi://" + SERVER_IP + ":" + port + "/" + NAMING_SERVER);
				//remote = Naming.lookup(NAMING_SERVER);
			    ITestDocumentum server = null;
			    if(remote instanceof ITestDocumentum)
			        server = (ITestDocumentum) remote;
			    else 
			    	throw new Exception("server not found");
			    
			    String identity = server.getIdentity() ;
			    System.out.println("identity" + identity);
			    
			    // Send class of TBO.
			    System.out.println("SENDING FILE: " + tboClass);
			    byte []s =  IOUtils.toByteArray(new FileInputStream(tboClass)) ; 
			    SimpleRemoteInputStream istream = new SimpleRemoteInputStream(new FileInputStream(tboClass));
			    server.sendFile(istream.export() ,s , "TestTypeTBO.class" , "com\\test\\tbo" , identity );
			    
			    
			    s =  IOUtils.toByteArray(new FileInputStream(lfClass)) ;
			    // Send Lifecycle Action class
			    System.out.println("SENDING FILE: " + lfClass);
			    istream = new SimpleRemoteInputStream(new FileInputStream(lfClass));
			    server.sendFile(istream.export() , s ,"LFActionFinish.class" , "com//testing//lfaction" , identity );			    
			    
			    server.endUploadFile(identity) ;			    
			    
			    // 1 Example. Save Object.
			    // Change attribute, save and check result 
		        IDfId documentId = saveObject( server , identity );
		        
		        
		        //2 Example. run workflow 
		        IDfId processId = server.findObjectByQuery("select r_object_id from dm_process where object_name = 'TestWork' ", identity) ;
		        Result res = server.runWorkflow(documentId, processId, identity) ; 
			   
				printResult(res , "Workflow result " ); 			
				
				//3 Example. Promote document.  and promote document
				 promoteObject( server , identity );
				
				 System.out.println(" run end ") ;
				 server.endTest(identity) ;
				 System.out.println(" run end complete ") ;
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	 }

	 
	/**
	 * print new and changed objects , audit and new queue 
	 * @param res
	 * @param prefix
	 */
	private static void printResult(Result res , String prefix ) {
		Collection<DocumentumObject> col = res.getSavedObject().values() ;
		for ( DocumentumObject object : col ) {
			System.out.println( prefix + ":: dump object is " + object.getDumpObject());
		} 	
		for ( String auditString : res.getAudit() ) {
			System.out.println(prefix + ":: audit object is " + auditString );
		}
		Map<String, ArrayList<String>> m = res.getQueue() ;
		for ( String id : m.keySet()) {
			for ( String s : m.get(id)) {
				System.out.println( prefix + ":: queue is. id " + id + ". " + s );
			}
		}		
	}
	

	private static IDfId saveObject( ITestDocumentum server, String identity) {
		IDfId objectId = null ; 
		try{
 
			objectId = server.findObjectByQuery("select r_object_id from testtype where object_name = 'test_object'", identity) ;
			
			System.out.println("find object is " + objectId.getId() );
			HashMap<String,Object> mp = new HashMap<>();
			mp.put("test_string", "changed string") ;
			mp.put("test_int", new Integer(50)) ;
			System.out.println("save object" );
			Result result = server.saveObject(objectId, identity, mp) ;
			printResult(result, " saveobject ") ;
			
		}catch(Exception e){
		    e.printStackTrace();
		}
		return objectId ; 
	}
	 
	
	private static IDfId promoteObject( ITestDocumentum server, String identity) {
		IDfId objectId = null ; 
		try{
			
			
			server.reset(identity) ;
		    // find object for promote
			objectId = server.findObjectByQuery("select r_object_id from testtype where object_name = '11123'", identity) ;
			System.out.println("promote object" );
			Result result = server.promoteObject(objectId, identity) ;
			printResult(result, " promote ") ;

		}catch(Exception e){
		    e.printStackTrace();
		}
		return objectId ; 
	}
	
	
//	 public static void sendAllClasses ( String origDirName , String directory, String identity , ITestDocumentum server ) {
//			File currentDir = new File(directory);
//		 
//			File bigdir = new File(origDirName).getAbsoluteFile();
//			URI bigdirUri = bigdir.toURI(); 		 
//			
//			File files[] = currentDir.listFiles();	
//			for (File file : files){	
//				System.out.println(" try send " +  file.getName());	
//				if (file.isDirectory()){
//					sendAllClasses(origDirName , file.getAbsolutePath(), identity , server );
//				} else  {	
//					SimpleRemoteInputStream istream2;
//					try {
//						istream2 = new SimpleRemoteInputStream(new FileInputStream(file.getAbsoluteFile()));
//						
//						String relative = bigdirUri.relativize(file.toURI()).getPath();
//						
//						System.out.println(" relative " + relative ) ;
//						server.sendFile(istream2.export(), file.getName() , relative , identity );
//						
//						
//					} catch (RemoteException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					} catch (IOException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}	
//	 }
}


