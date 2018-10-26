package com.domel.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domel.Entity.PublicFullRequest;
import com.domel.Entity.Request;
import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.domel.Entity.User;
import com.domel.Util.FieldsNameMapper;
import com.domel.Util.ResourceLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/ResultController")
public class PublicResultController extends AbstractSimpleAuthenticationController
{
	private static final long serialVersionUID = -5168429802860735455L;

	public PublicResultController()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try
		{
			//Error for unauthenticated user
			User currentUser = this.authenticate(request);
			if(currentUser==null)
			{
				response.getWriter().append(this.getErrorJson("Invalid username or password"));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			String requestIDString = request.getParameter("requestID");
			if(requestIDString == null || requestIDString.equals(""))
			{
				response.getWriter().append(this.getErrorJson("Invalid requestID parameter"));
				return;
			}
			
			UUID requestID = UUID.fromString(requestIDString);
			if(requestID == null)
			{
				response.getWriter().append(this.getErrorJson("Invalid requestID parameter"));
				return;
			}
			Request r = databaseManager.getRequest(requestID);
			if(r==null)
			{
				response.getWriter().append(this.getErrorJson("Invalid requestID parameter"));
				return;
			}
			
			RequestParameters rp = databaseManager.getRequestParams(r.requestParametersID);
			FieldsNameMapper fm = ResourceLoader.getFieldsNameMapperInstance();
			
			if(r.status.equals("Success"))
			{
				
				Result rr = databaseManager.getResult(r.responseDataID);
				response.getWriter().append(new Gson().toJson(new PublicFullRequest(r, fm.stringToMap(r.getFullRequestJSON()), fm.objectivesHidden(rr), 0, rp.ProcessingTime)));
				
			}
			else
			{
				Integer c = databaseManager.getCurrentQueueOrder(requestID);
				PublicFullRequest pr = new PublicFullRequest(r, fm.stringToMap(r.getFullRequestJSON()), null , c, null);
				response.getWriter().append(new Gson().toJson(pr));
			}
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
