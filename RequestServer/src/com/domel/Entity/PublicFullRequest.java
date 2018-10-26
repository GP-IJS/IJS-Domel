package com.domel.Entity;

import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

public class PublicFullRequest
{
	//Request request;
	UUID requestID;
	String status;
	Map<String,Object> parameters;
	Map<String,Object> result;
	Integer in_queue = null;
	Timestamp arrival_date = null;
	Timestamp finished_date = null;
	Timestamp started_date = null;
	Integer processing_time = 0;
	
	public PublicFullRequest(Request r, Map<String,Object> pr, Map<String,Object> res, Integer counter, Integer processing_time)
	{
		this.requestID=r.requestID;
		this.status = r.status;
		this.parameters = pr;
		this.result = res;
		this.in_queue=counter;
		this.arrival_date = r.arrivalDate;
		this.finished_date = r.finishedCalculationDate;
		this.started_date = r.startedCalculationDate;
		this.processing_time = processing_time;
	}
}
