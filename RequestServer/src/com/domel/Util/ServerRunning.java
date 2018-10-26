package com.domel.Util;

public class ServerRunning
{
	static private boolean running=true;
	
	public boolean getStatus() { return running; }
	public void setOn() { running=true; }
	public void setOff() { running=false; } 

}
