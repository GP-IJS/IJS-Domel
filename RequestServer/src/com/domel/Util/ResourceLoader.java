package com.domel.Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

import com.domel.DBCommunication.IDBManager;
import com.domel.DBCommunication.MySQLManager;

public class ResourceLoader
{
	private ResourceLoader() {}
	
	private static final Object propertieslock = new Object();
	private static volatile Properties propertiesInstance;
	
	private static final Object fieldsNameslock = new Object();
	private static volatile FieldsNameMapper fieldsNamesInstance;
	
	private static final Object databaselock = new Object();
	private static volatile IDBManager databaseInstance;
	
	private static final Object loggerlock = new Object();
	private static volatile Logger loggerInstance;
	
	private static final Object serverRunningLock = new Object();
	private static volatile ServerRunning serverRunningInstance;
	
	public static ServerRunning getServerRunning() {
		ServerRunning r = serverRunningInstance;
	    if (r == null) {
	        synchronized (serverRunningLock) {
	            r = serverRunningInstance;
	            if (r == null) {  
	            	r = new ServerRunning();
		    		serverRunningInstance = r;
	            }
	        }
	    }
	    return r;
	}
	
	public static FieldsNameMapper getFieldsNameMapperInstance()
	{
		FieldsNameMapper r = fieldsNamesInstance;
	    if (r == null) {
	        synchronized (fieldsNameslock) {
	            r = fieldsNamesInstance;
	            if (r == null) {
	            	try
	                {
		    	    	File configDir = new File(System.getProperty("catalina.base"), "conf");
		    			File propsConfigFile = new File(configDir, "domel_parameter_mappings.properties");
		    			System.out.println("Parameter mappings file loaded from: " + configDir);
		    			InputStream propStream = new FileInputStream(propsConfigFile);
		    			Properties pp = new Properties();
		    			pp.load(propStream);
		    			
		    			r = new FieldsNameMapper(ResourceLoader.getLoggerInstance());
		    			
		    			for(Object key : pp.keySet())
		    			{
		    				String[] parts = pp.get(key).toString().split(",");
		    				double min = Double.parseDouble(parts[1].trim());
		    				double max = Double.parseDouble(parts[2].trim());
		    				r.addPropertyAlias(parts[0].trim(), key.toString(), min, max, parts[3].trim());
		    			}
		    			
		    			File objConfigFile = new File(configDir, "domel_objective_mappings.properties");
		    			System.out.println("Objective mappings file loaded from: " + configDir);
		    			InputStream objStream = new FileInputStream(objConfigFile);
		    			Properties op = new Properties();
		    			op.load(objStream);

		    			for(Object key : op.keySet())
		    			{
		    				String[] parts = op.get(key).toString().split(",");
		    				double min = Double.parseDouble(parts[1].trim());
		    				double max = Double.parseDouble(parts[2].trim());
		    				r.addObjectiveAlias(parts[0].trim(), key.toString(), min, max);
		    			}		    			
		    			
	            		fieldsNamesInstance = r;
	                }
	        	    catch(Exception ex)
	        	    {
	        	    	System.out.println("Error while creating fields name mapper");
	        	    	System.out.println(ex.getMessage());
	        	    }

	            }
	        }
	    }
	    return r;
	}
	
	public static Logger getLoggerInstance() {
		Logger r = loggerInstance;
	    if (r == null) {
	        synchronized (loggerlock) {
	            r = loggerInstance;
	            if (r == null) {
	            	try
	                {
		            	DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
		    	    	Date date = new Date();
		    			
		    	    	String[] tokens = ResourceLoader.getPropertiesInstance().getProperty("log_file_path").split("\\.(?=[^\\.]+$)");
		    	    	r = Logger.getLogger(Class.class.getName());
		    	    	r.addHandler(new FileHandler(tokens[0]+"_"+dateFormat.format(date)+"."+tokens[1], true));
			            loggerInstance = r;
	                }
	        	    catch(Exception ex)
	        	    {
	        	    	System.out.println("Error while creating logging file.");
	        	    	System.out.println(ex.getMessage());
	        	    }

	            }
	        }
	    }
	    return r;
	}
	
	
	public static IDBManager getDatabaseInstance() {
		IDBManager r = databaseInstance;
	    if (r == null) {
	        synchronized (databaselock) {
	            r = databaseInstance;
	            if (r == null) {
	            	try
	                {
	            	Properties props = ResourceLoader.getPropertiesInstance();
	    	    	r = new MySQLManager(props.getProperty("database_url"),
	    	    			props.getProperty("username"),
	    	    			props.getProperty("password"),
	    	    			ResourceLoader.getLoggerInstance()); //TODO
		            databaseInstance = r;
	                }
	        	    catch(Exception ex)
	        	    {
	        	    	System.out.println("Error while creating database.");
	        	    	System.out.println(ex.getMessage());
	        	    }
	            }
	        }
	    }
	    return r;
	}

	public static Properties getPropertiesInstance() {
		Properties r = propertiesInstance;
	    if (r == null) {
	        synchronized (propertieslock) {
	            r = propertiesInstance;
	            if (r == null) {  
	                try
	                {
		    	    	File configDir = new File(System.getProperty("catalina.base"), "conf");
		    			File configFile = new File(configDir, "domel.properties");
		    			System.out.println("Config file loaded from: " + configDir);
		    			InputStream stream = new FileInputStream(configFile);
		    			r = new Properties();
		    			r.load(stream);
		                propertiesInstance = r;
	                }
	        	    catch(Exception ex)
	        	    {
	        	    	System.out.println("Error while reading configuration file.");
	        	    	System.out.println(ex.getMessage());
	        	    }
	            }
	        }
	    }
	    return r;
	}
}
