package com.domel.Controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domel.Entity.User;
import com.domel.Processing.AsynchronousUnfinishedRunner;
import com.domel.Processing.MetaRunner;
import com.domel.Util.ResourceLoader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class StatusController
 */
@WebServlet("/StatusController")
public class StatusController extends AbstractSimpleAuthenticationController {
	private static final long serialVersionUID = -2012129902913160265L;

	public StatusController() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
	{
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try
		{
			User currentUser = this.authenticate(request);
			if (currentUser == null) {
				response.getWriter().append(this.getErrorJson("Invalid username or password"));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			Gson gson = new GsonBuilder().create();
			Map<String, Object> responseJson = new HashMap<String, Object>();
			responseJson.put("requests", databaseManager.getStatus());
			
			MetaRunner runnable = new MetaRunner(this.props.getProperty("server_ip"), 
					Integer.parseInt(this.props.getProperty("server_port")),
					AbstractSimpleAuthenticationController.LOGGER);
			boolean test = runnable.testConnection();
			
			
			responseJson.put("server_online", test);
			String json = gson.toJson(responseJson);
			response.getWriter().append(json);

		}
		catch (IOException e)
		{
			LOGGER.log(Level.SEVERE, e.getMessage());
			try {
				response.getWriter().append(this.getErrorJson("A error has occurred. Contact administrator"));
			} catch (IOException ex) {
			}
			return;

		}
	}

}
