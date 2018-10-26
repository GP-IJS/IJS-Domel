package com.domel.Processing;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MetaRunner
{
	String socketIP;
	Integer socketPort;
	Logger LOGGER;
	
	public MetaRunner(String ip, int socket, Logger LOGGER)
	{
		this.socketIP = ip;
		this.socketPort = socket;
		this.LOGGER = LOGGER;
	}
	
	public boolean testConnection()
	{
		SocketAddress sockaddr = new InetSocketAddress(this.socketIP, this.socketPort);
		
		try (Socket socket = new Socket())
		{
			socket.connect(sockaddr);
			socket.setSoTimeout(5000);
			
			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			toServer.println("meta");
			String stringResponse = fromServer.readLine();
			//System.out.println("OK " + stringResponse);
			
			toServer.close();
			fromServer.close();
			socket.close();
			return true;
		}
		catch (Exception e)
		{
			return false;
		}
	}

}
