package com.domel.Controllers;

import java.io.IOException;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domel.Entity.RequestParameters;
import com.domel.Entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/PrivateRequestController")
public class PrivateRequestController extends AbstractRequestController
{
	private static final long serialVersionUID = 3019107666326350583L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
	{
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try
		{
			User currentUser = this.authenticate(request);
			
			if (currentUser == null)
			{
				response.getWriter().append(this.getErrorJson("Invalid username or password"));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			if (currentUser.privateAccess!=1)
			{
				response.getWriter().append(this.getErrorJson("No private access"));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}	
			
			// Parse parameters
			String fullRequest = parseJSON(request);
			RequestParameters requestedParameters = new Gson().fromJson(fullRequest, RequestParameters.class);
			if(requestedParameters == null)
			{
				response.getWriter().append(this.getErrorJson("Invalid parameters"));
				return;
			}
			
			JsonObject returnedJsonObject = this.handleRequest(currentUser, fullRequest, requestedParameters);
			response.getWriter().append(returnedJsonObject.toString());
			
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, ex.getClass().getName());
			try
			{
				response.getWriter().append(this.getErrorJson("A error has occurred. Contact administrator"));
			}
			catch (IOException e)
			{
			}
			return;
		}
	}
}
