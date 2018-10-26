package com.domel.Processing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.domel.DBCommunication.IDBManager;
import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.domel.Util.ServerRunning;
import com.google.gson.Gson;

import javafx.util.Pair;

@SuppressWarnings("restriction")
public class AsynchronousUnfinishedRunner implements Runnable {
	IDBManager dbManager;
	String socketIP;
	Integer socketPort;
	Logger LOGGER;
	long scriptTimeout;
	long socketTimeout;
	ServerRunning running;

	private static boolean activeRequests = false;
	
	public AsynchronousUnfinishedRunner(IDBManager dbManager, String ip, int socket, Logger LOGGER, ServerRunning running, long scriptTimeout,
			long socketTimeout) {
		this.dbManager = dbManager;
		this.socketIP = ip;
		this.socketPort = socket;
		this.LOGGER = LOGGER;
		this.scriptTimeout = scriptTimeout;
		this.socketTimeout = socketTimeout;
		this.running = running;
	}

	@Override
	public void run() {
		try {

			if (activeRequests == true) {
				return;
			}
			activeRequests = true;

			SocketAddress sockaddr = new InetSocketAddress(this.socketIP, this.socketPort);
			
			Pair<UUID, RequestParameters> pair = this.dbManager.getUnsuccessfulRequest();
			if (pair == null) {
				return;
			}

			
			try (Socket socket = new Socket())
			{
				socket.connect(sockaddr);
				socket.setSoTimeout((int)(this.socketTimeout*1000));

				RequestParameters params = pair.getValue();
				params.ScriptTimeout = this.scriptTimeout;

				String jsonParams = params.toString();

				Timestamp start = new Timestamp(System.currentTimeMillis());
				
				PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
				BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				toServer.println(jsonParams.trim().replace("\n", "").replace("\r", ""));

				String stringResponse = fromServer.readLine();
				
				
				ServerResponse response = new Gson().fromJson(stringResponse, ServerResponse.class);
				
				if(running.getStatus()==false)
				{
					running.setOn();
					LOGGER.log(Level.SEVERE,"Connection active.");
					//System.out.println("Connection regained.");
				}
				this.running.setOn();
				
				
				toServer.close();
				fromServer.close();
				
				//System.out.println("REP: " + response.status.toLowerCase());
				Timestamp end = new Timestamp(System.currentTimeMillis());
				if (response.status.toLowerCase().equals("success")) {
					
					// Save data to tables request and response_data
					UUID calculatedResponseValuesID = UUID.randomUUID();

					// Write response details
					Result r = response.result;
					r.resultId = calculatedResponseValuesID;
					this.dbManager.insertResult(r);

					//this.dbManager.calculationStarted(pair.getKey(), start);
					int seconds = (int)((end.getTime()-start.getTime())/1000);
					this.dbManager.setExecutionTime(params.parametersId, seconds);
					
					// Write result status and UUID
					this.dbManager.updateRequestsWithEqualParams(pair.getValue().parametersId,
							calculatedResponseValuesID, response.status, end, start);

				} else {
					// Write error in request table
					this.dbManager.updateRequestsWithFailedStatus(pair.getValue().parametersId, response.status, end, start);
				}
			}
			catch(SocketTimeoutException e)
			{
				//System.out.println("TIMEOUT"); 
				//this.dbManager.updateRequestsWithFailedStatus(pair.getValue().parametersId, "SessionTimeout");
				return;
			}
			catch(ConnectException e)
			{
				if(running.getStatus()==true)
				{
					running.setOff();
					LOGGER.log(Level.SEVERE,"Connection lost.");
				}
				return;
			}
			catch (Exception e) {
				if(running.getStatus()==true)
				{
					running.setOff();
					LOGGER.log(Level.SEVERE,"Connection lost.");
				}
				return;
			}

		} catch (Exception ex) {
			LOGGER.log(Level.SEVERE, "Can't get unproccessed request");
		} finally {
			activeRequests = false;
		}

	}

}
