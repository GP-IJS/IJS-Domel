package com.domel.Controllers;

import java.util.Base64;
import java.util.Properties;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domel.DBCommunication.IDBManager;
import com.domel.Entity.User;
import com.domel.Util.ResourceLoader;
import com.google.gson.JsonObject;



public abstract class AbstractSimpleAuthenticationController extends HttpServlet
{
	private static final long serialVersionUID = 9160986324152337449L;
	
	public static final Logger LOGGER = ResourceLoader.getLoggerInstance();
	IDBManager databaseManager = ResourceLoader.getDatabaseInstance();
	Properties props = ResourceLoader.getPropertiesInstance();

	protected void doGet(HttpServletRequest request, HttpServletResponse response){}

	protected void doPost(HttpServletRequest request, HttpServletResponse response){}
	
    protected User authenticate(HttpServletRequest request)
    {
    	try
    	{
			String authHeader = request.getHeader("authorization");
			
			String encodedValue = authHeader.split(" ")[1];
			byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
			String[] arr = new String(decodedBytes).split(":", 2);
			return this.databaseManager.getUser(arr[0], arr[1]);
    	}
    	catch(Exception ex)
    	{
    		return null;
    	}
    }
    
	protected String getErrorJson(String error)
	{
		JsonObject jo = new JsonObject();
		jo.addProperty("error", error);
		return jo.toString();
	}

}
