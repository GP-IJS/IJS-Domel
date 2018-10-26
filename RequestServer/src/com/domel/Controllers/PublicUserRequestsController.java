package com.domel.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;

import javax.servlet.annotation.WebServlet;

import com.domel.Entity.PublicFullRequest;
import com.domel.Entity.Request;
import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.domel.Entity.User;
import com.domel.Util.FieldsNameMapper;
import com.domel.Util.ResourceLoader;

@WebServlet("/UserRequestsController")
public class PublicUserRequestsController extends AbstractUserRequestsController {

	private static final long serialVersionUID = -66818684394741661L;

	public List<PublicFullRequest> constructResponse(List<Request> userRequests)
	{
		
		List<PublicFullRequest> requests = new ArrayList<PublicFullRequest>();
		FieldsNameMapper fm = ResourceLoader.getFieldsNameMapperInstance();

		for (Request req : userRequests) {
			Result res = this.databaseManager.getResult(req.responseDataID);
			Integer c = databaseManager.getCurrentQueueOrder(req.requestID);
			RequestParameters params = this.databaseManager.getRequestParams(req.requestParametersID);

			try
			{
				requests.add(new PublicFullRequest(req, fm.stringToMap(req.getFullRequestJSON()), fm.objectivesHidden(res), c, params.ProcessingTime));
			}
			catch (Exception e)
			{
				LOGGER.log(Level.SEVERE, "Unable to construct response.");
			}

		}

		return requests;
	}

	@Override
	boolean userHasAccess(User currentUser) {
		return true;
	}

	@Override
	List<Request> getRequests(UUID id) {
		return this.databaseManager.userRequests(id);
	}

}
