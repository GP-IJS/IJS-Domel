package com.domel.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.domel.Entity.RequestParameters;
import com.domel.Entity.Result;
import com.google.gson.Gson;

public class FieldsNameMapper
{
	private Map<String, String> alias2property =  new HashMap<String, String>();
	private Map<String, String> property2alias =  new HashMap<String, String>();
	
	private Map<String, String> alias2objective =  new HashMap<String, String>();
	private Map<String, String> objective2alias =  new HashMap<String, String>();
	
	private Map<String, Double> propertyMin =  new HashMap<String, Double>();
	private Map<String, Double> propertyMax =  new HashMap<String, Double>();
	private Map<String, String> propertyType =  new HashMap<String, String>();
	
	private Map<String, Double> objectiveMin =  new HashMap<String, Double>();
	private Map<String, Double> objectiveMax =  new HashMap<String, Double>();
	
	private List<String> intFields = new ArrayList<>();
	
	private Logger LOGGER;
	
	public FieldsNameMapper(Logger LOGGER)
	{
		this.LOGGER = LOGGER;
	}


	public List<String> intFields()
	{
		return intFields;
	}
	
	
	public void addPropertyAlias(String alias, String property, double min, double max, String type)
	{
		this.alias2property.put(alias, property);
		this.property2alias.put(property, alias);
		
		this.propertyMax.put(property, max);
		this.propertyMin.put(property, min);
		
		this.propertyType.put(property, type);
		
		if(type.toLowerCase().equals("int") || type.toLowerCase().equals("integer"))
		{
			intFields.add(property);
		}
		
	}
	
	public void addObjectiveAlias(String alias, String objective, double min, double max)
	{
		this.alias2objective.put(alias, objective);
		this.objective2alias.put(objective, alias);
		
		this.objectiveMax.put(objective, max);
		this.objectiveMin.put(objective, min);
	}
	
	public String propertyToAlias(String property) throws Exception
	{
		try
		{
			return this.property2alias.get(property);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly map field: [" + property + "].");
			throw new Exception("Can't properly map field: [" + property + "].");
		}
	}
	
	public String aliasToProperty(String alias) throws Exception
	{
		try
		{
			return this.alias2property.get(alias);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly map field: [" + alias + "].");
			throw new Exception("Can't properly map field: [" + alias + "].");
		}
	}
	
	public String objectiveToAlias(String objective) throws Exception
	{
		try
		{
			return this.objective2alias.get(objective);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly map objective: [" + objective + "].");
			throw new Exception("Can't properly map objective: [" + objective + "].");
		}
	}
	
	public String aliasToObjective(String objective) throws Exception
	{
		try
		{
			return this.alias2objective.get(objective);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly map objective: [" + objective + "].");
			throw new Exception("Can't properly map objective: [" + objective + "].");
		}
	}
	
	public double scaleProperty(String property, double value) throws Exception
	{
		try
		{
			double min = this.propertyMin.get(property);
			double max = this.propertyMax.get(property);
			
			//Scale integer values
			if(this.propertyType.get(property).toLowerCase().equals("int"))
			{
				double val = Math.floor(min + value*(max-min+1));
				if(val==max+1)
					return max;
				return val;
			}
			
			return min + value*(max-min);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly scale field: [" + property + "].");
			throw new Exception("Can't properly scale field: [" + property + "].");
		}
	}
	
	public double scaleObjective(String objective, double value) throws Exception
	{
		try
		{
			double min = this.objectiveMin.get(objective);
			double max = this.objectiveMax.get(objective);
			return (value-min)/(max-min);
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly scale objective: [" + objective + "].");
			throw new Exception("Can't properly scale objective: [" + objective + "].");
		}
	}
	
	public Map<String,Object> stringToMap(String json)
	{
		Gson gson = new Gson(); 
		Map<String,Object> parametersMap = new HashMap<String,Object>();
		parametersMap = (Map<String,Object>) gson.fromJson(json, parametersMap.getClass());
		return parametersMap;
	}
	
	public Map<String,Object> parametersVisible(RequestParameters rp)
	{
		Gson gson = new Gson(); 
		Map<String,Object> map = new HashMap<String,Object>();
		map = (Map<String,Object>) gson.fromJson(rp.toString(), map.getClass());
		return map;
	}
	
	public Map<String,Object> objectivesVisible(Result res)
	{
		if(res==null)
			return null;
		Gson gson = new Gson(); 
		Map<String,Object> map = new HashMap<String,Object>();
		map = (Map<String,Object>) gson.fromJson(res.toString(), map.getClass());
		return map;
	}
	
	public Map<String,Object> objectivesHidden(Result res) throws Exception
	{
		Map<String,Object> map = new HashMap<String,Object>();
		if(res==null)
			return map;
		try
		{
			FieldsNameMapper fm = ResourceLoader.getFieldsNameMapperInstance();
			Gson gson = new Gson(); 

			map = (Map<String,Object>) gson.fromJson(res.toString(), map.getClass());
			Map<String,Object> mappedMap = new HashMap<String,Object>();
			
			for (String key : map.keySet())
			{
				String n = fm.objectiveToAlias(key);
				if(n==null)
				{
					mappedMap.put(key, map.get(key));
				}
				else
				{	
					double val = new Double(map.get(key).toString());
					mappedMap.put(n, fm.scaleObjective(key, val));
				}
			}
			return mappedMap;
		}
		catch(Exception ex)
		{
			LOGGER.log(Level.SEVERE, "Can't properly hide fields.");
			throw new Exception("Can't properly hide fields.");
		}
	}
	
}
