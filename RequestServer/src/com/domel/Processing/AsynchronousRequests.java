package com.domel.Processing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.domel.DBCommunication.IDBManager;
import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AsynchronousRequests implements Runnable
{
	String jsonParams;
	IDBManager dbManager;
	UUID requestID;
	
	String socketIP;
	Integer socketPort;
	Logger LOGGER;

	
	public AsynchronousRequests(RequestParameters params, IDBManager dbManager, UUID requestID, String ip, int socket, Logger LOGGER)
	{
		this.dbManager = dbManager;
		this.requestID = requestID;
		Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
		this.jsonParams = gson.toJson(params);
		this.socketIP = ip;
		this.socketPort = socket;
		this.LOGGER = LOGGER;
	}

	@Override
	public void run()
	{
		  try(Socket socket = new Socket(this.socketIP, this.socketPort))
		  { 
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(),true);
		    BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    
		    //Make JSON 1 line
		    toServer.print(this.jsonParams.trim().replace("\n", "").replace("\r", ""));

		    String line1 = fromServer.readLine();
		    
		    toServer.close();
		    fromServer.close();
		    socket.close();

		    
		    ServerResponse response = new Gson().fromJson(line1, ServerResponse.class);
		    //System.out.println("STATUS: " + response.status);
		    if(response.status.toLowerCase().equals("success"))
		    {
		    	//Save data to tables request and response_data
		    	UUID calculatedResponseValuesID = UUID.randomUUID();
		    	
		    	//Write response details
		    	Result r = response.result;
		    	r.resultId= calculatedResponseValuesID;
		    	this.dbManager.insertResult(r);
		    	
		    	//Write result status and UUID
		    	this.dbManager.updateRequest(this.requestID, 
		    			calculatedResponseValuesID, 
		    			response.status, 
		    			new Timestamp(System.currentTimeMillis()));
		    }
		    else
		    {
		    	
		    	this.dbManager.updateFailedRequest(this.requestID, response.status);
		    }
		  }
		  catch(Exception ex)
		  {
			  LOGGER.log(Level.SEVERE, ex.getMessage());
		  }

	}
}
