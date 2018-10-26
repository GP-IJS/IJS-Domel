package com.domel.Controllers;

import java.io.BufferedReader;
import java.sql.Timestamp;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.domel.Entity.Request;
import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.domel.Entity.User;
import com.google.gson.JsonObject;

public abstract class AbstractRequestController extends AbstractSimpleAuthenticationController
{
	private static final long serialVersionUID = 1567267896512427089L;

	protected String parseJSON(HttpServletRequest request)
	{
		StringBuffer jb = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			return null;
		}
		return jb.toString();
	}
	
	protected JsonObject handleRequest(User currentUser, String fullRequest, RequestParameters requestedParameters)
	{
		//ID of the request
		UUID requestUUIDtoken = UUID.randomUUID();
		// Check if parameters already exist in database
		RequestParameters paramsAlreadyInDatabse = this.databaseManager.getRequestParams(requestedParameters);
					
		JsonObject returnedJsonObject = new JsonObject();
		returnedJsonObject.addProperty("id", requestUUIDtoken.toString());
					
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Request requestEntity = new Request();
		requestEntity.requestID = requestUUIDtoken;
		requestEntity.userID = currentUser.userId;
		requestEntity.arrivalDate = timestamp;
		requestEntity.status = "NotCalculated";
		requestEntity.setFullRequestJSON(fullRequest);
		
		if(paramsAlreadyInDatabse==null)
		{
			//No parameters with the same values in DB
			
			// Add new RequestParameters to database
			requestedParameters.parametersId = UUID.randomUUID();
			this.databaseManager.insertRequestParams(requestedParameters);
			
			//Construct request and write it in DB
			requestEntity.requestParametersID = requestedParameters.parametersId;
			
			//Return size of the queue
			int unfinished = this.databaseManager.getNumberofUnsuccessfulRequest();
			returnedJsonObject.addProperty("in_queue", unfinished);
		}
		else
		{
			//Parameters with same values already in DB
			Result result = this.databaseManager.getResultForExistingRequestParameters(paramsAlreadyInDatabse.parametersId);
			requestEntity.requestParametersID = paramsAlreadyInDatabse.parametersId;
			
			if(result==null)
			{
				//Return size of the queue
				int unfinished = this.databaseManager.getNumberofUnsuccessfulRequest();
				returnedJsonObject.addProperty("in_queue", unfinished);
			}
			else
			{
				requestEntity.status = "Success";
				requestEntity.finishedCalculationDate = timestamp;
				requestEntity.startedCalculationDate = timestamp;
				requestEntity.responseDataID = result.resultId;
				returnedJsonObject.addProperty("in_queue", -1);
			}
		}
		this.databaseManager.insertRequest(requestEntity);
		return returnedJsonObject;
		//response.getWriter().append(returnedJsonObject.toString());
	}
}
