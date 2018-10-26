package com.domel.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.annotation.WebServlet;

import com.domel.Entity.PublicFullRequest;
import com.domel.Entity.Request;
import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.domel.Entity.User;
import com.domel.Util.FieldsNameMapper;
import com.domel.Util.ResourceLoader;

@WebServlet("/PrivateUserRequestsController")
public class PrivateUserRequestsController extends AbstractUserRequestsController {

	private static final long serialVersionUID = -7400103485749933335L;
	
	public List<PublicFullRequest> constructResponse(List<Request> userRequests) {
		List<PublicFullRequest> requests = new ArrayList<PublicFullRequest>();
		FieldsNameMapper fm = ResourceLoader.getFieldsNameMapperInstance();

		for (Request req : userRequests) {
			RequestParameters params = this.databaseManager.getRequestParams(req.requestParametersID);
			Result res = this.databaseManager.getResult(req.responseDataID);
			Integer c = databaseManager.getCurrentQueueOrder(req.requestID);
			requests.add(new PublicFullRequest(req, fm.parametersVisible(params), fm.objectivesVisible(res), c, params.ProcessingTime));
		}

		return requests;
	}

	@Override
	boolean userHasAccess(User currentUser) {
		return currentUser.privateAccess==1;
	}

	@Override
	List<Request> getRequests(UUID id) {
		return this.databaseManager.userRequests(id);
	}

}
