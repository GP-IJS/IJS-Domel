package com.domel.Entity;

import java.sql.Timestamp;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.annotations.Expose;

public class Request
{
    public UUID requestID;

    public UUID userID;
    public UUID requestParametersID;
    public UUID responseDataID;

    public String status;
    public Timestamp arrivalDate;
    public Timestamp startedCalculationDate;
    public Timestamp finishedCalculationDate;
    
    @Expose(serialize = false, deserialize = false)
    private transient String fullRequestJSON;
    
    public void setFullRequestJSON(String json)
    {
    	this.fullRequestJSON=json;
    }
    
    public String getFullRequestJSON()
    {
    	return this.fullRequestJSON;
    }
    
	@Override
	public String toString()
	{
		return new Gson().toJson(this);	
	}
}
