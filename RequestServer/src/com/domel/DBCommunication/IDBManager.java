package com.domel.DBCommunication;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.domel.Entity.*;

import javafx.util.Pair;

public interface IDBManager
{
    void insertRequestParams(RequestParameters insert);
    RequestParameters getRequestParams(UUID id);
    RequestParameters getRequestParams(RequestParameters parameters);
    void insertResult(Result insert);
    Result getResult(UUID id);
    Request getRequest(UUID id);
    User getUser(String username, String password);
    void insertRequest(Request request);
    void updateRequest(UUID requestID, UUID responseID, String status, Timestamp processingCompleted);
    void updateFailedRequest(UUID requestID, String status);
    void updateRequestsWithEqualParams(UUID parametersID, UUID responseID, String status, Timestamp processingCompleted, Timestamp processingStarted);
    void updateRequestsWithFailedStatus(UUID parametersID, String status, Timestamp processingCompleted, Timestamp processingStarted);
    Result getResultForExistingRequestParameters(UUID parametersID);
    List<Request> userRequests(UUID userId);
    List<Request> allRequests();
    
    Pair<UUID, RequestParameters> getUnsuccessfulRequest();
    void calculationStarted(UUID RequestId, Timestamp time);
    void setExecutionTime(UUID parametersID, int time);
    int getNumberofUnsuccessfulRequest();
    int getCurrentQueueOrder(UUID requestId);
    Map<String, Object> getStatus();
    
    


}
