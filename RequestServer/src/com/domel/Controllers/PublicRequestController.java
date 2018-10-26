package com.domel.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domel.Entity.RequestParameters;
import com.domel.Entity.User;
import com.domel.Util.FieldsNameMapper;
import com.domel.Util.ResourceLoader;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

@WebServlet("/RequestController")
public class PublicRequestController extends AbstractRequestController
{
	private static final long serialVersionUID = -7334907446361036029L;

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
	
			// Parse parameters
			FieldsNameMapper fm = ResourceLoader.getFieldsNameMapperInstance();
			Gson gson = new Gson(); 
			String fullRequest = this.parseJSON(request);
			Map<String,Object> map = new HashMap<String,Object>();
			map = (Map<String,Object>) gson.fromJson(fullRequest, map.getClass());
			
			Map<String, String> realRequest = new HashMap<String, String>();
			for (String key : map.keySet())
			{
				try
				{
					String realName = fm.aliasToProperty(key);
					double realParam = fm.scaleProperty(realName, Double.parseDouble(map.get(key).toString().trim()));
					realRequest.put(realName, String.valueOf(realParam));
				}
				catch(Exception e)
				{
					response.getWriter().append(this.getErrorJson("Invalid parameters"));
					return;
				}
			}
			

			RequestParameters requestedParameters = new Gson().fromJson(new Gson().toJson(realRequest), RequestParameters.class);
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
