package com.domel.Controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.domel.Entity.PublicFullRequest;
import com.domel.Entity.Request;
import com.domel.Entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public abstract class AbstractUserRequestsController extends AbstractSimpleAuthenticationController {
	private static final long serialVersionUID = -275874561896072250L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
	    response.setContentType("application/json");
	    response.setCharacterEncoding("UTF-8");
		try {
			User currentUser = this.authenticate(request);
			if (currentUser == null)
			{
				response.getWriter().append(this.getErrorJson("Invalid username or password"));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
			
			if (this.userHasAccess(currentUser)==false)
			{
				response.getWriter().append(this.getErrorJson("No private access"));
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}

			List<Request> userRequests = this.getRequests(currentUser.userId);//this.databaseManager.userRequests(currentUser.userId);
			List<PublicFullRequest> fullRequests = this.constructResponse(userRequests);

			response.getWriter().append(new Gson().toJson(fullRequests));
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
			try {
				response.getWriter().append(this.getErrorJson("A error has occurred. Contact administrator"));
			} catch (IOException ex) {
			}
			return;

		}
	}

	abstract List<PublicFullRequest> constructResponse(List<Request> userRequests);
	abstract boolean userHasAccess(User currentUser);
	abstract List<Request> getRequests(UUID id);
}
