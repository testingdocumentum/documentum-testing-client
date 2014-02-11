package com.wtesting.rmi.server;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

import com.documentum.fc.common.IDfId;
import com.documentum.fc.common.IDfTime;
import com.healthmarketscience.rmiio.RemoteInputStream;
import com.wtesting.resulting.Result;

public interface ITestDocumentum extends Remote {
	
	/**
	 * identity of session
	 * @return
	 * @throws Exception
	 */
	public String getIdentity() throws Exception;
	
	/**
	 * upload classes to server
	 * @param data
	 * @param fileName - classname , e.g. TestTypeTbo.class
	 * @param dirName - package , e.g. com//test//tbo//
	 * @param identity
	 * @throws IOException
	 * @throws RemoteException
	 */
	public void sendFile(RemoteInputStream data , byte[] in , String fileName , String dirName , String identity ) throws IOException, RemoteException ;
	
	/**
	 * mandatory
	 * end of upload classes.
	 * @param identity
	 * @return
	 * @throws Exception
	 */
	public String endUploadFile( String identity ) throws Exception;
	
	/**
	 * find r_object_id of objects
	 * @param query
	 * @param identity
	 * @return
	 * @throws Exception
	 */
    public IDfId findObjectByQuery (String query , String identity) throws Exception;
    
    /**
     * find r_object_id of objects
     * @param Id
     * @param identity
     * @return
     * @throws Exception
     */
    public IDfId findObjectById  ( String Id , String identity ) throws Exception;
    /**
     * 
     * @param id  - r_object_id 
     * @param identity
     * @param attributeValueMap - map of attribute and value
     * @return 
     * @throws IOException
     */
    public Result saveObject  ( IDfId id , String identity , Map <String,Object> attributeValueMap ) throws Exception;
    
    /**
     * promote object
     * @param id
     * @param identity
     * @return
     * @throws Exception
     */
    public Result promoteObject  ( IDfId id , String identity ) throws Exception;
    
    /**
     * run workflow
     * @param documentId - document in package of workflow
     * @param processId
     * @param identity
     * @return
     * @throws Exception
     */
    public Result runWorkflow  ( IDfId documentId , IDfId processId , String identity  ) throws Exception;
    
    /**
     * reset of all changes
     * @param identity
     * @return
     * @throws Exception
     */
    public boolean reset ( String identity ) throws Exception;
    
    /**
     * mandatory. end of testing
     * @param identity
     * @return
     * @throws Exception
     */
    public boolean endTest ( String identity ) throws Exception;
}
