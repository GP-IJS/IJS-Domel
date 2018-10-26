package com.domel.Entity;

import java.util.UUID;

import com.google.gson.Gson;

public class User
{
    public UUID userId;
    public String name;
    public String username;
    public String password;
    public Integer privateAccess;
    
	@Override
	public String toString()
	{
		return new Gson().toJson(this);	
	}
}
