package com.domel.Controllers;

import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.domel.DBCommunication.IDBManager;
import com.domel.Processing.AsynchronousUnfinishedRunner;
import com.domel.Util.ResourceLoader;

@WebListener
public class PeriodicalExecuter implements ServletContextListener
{
    private ScheduledExecutorService scheduler;
	private IDBManager databaseManager = ResourceLoader.getDatabaseInstance();
	private Properties props = ResourceLoader.getPropertiesInstance();

    
    @Override
    public void contextInitialized(ServletContextEvent event)
    {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        
	    try
	    {
	    	scheduler.scheduleAtFixedRate(new Job(this.databaseManager, this.props), 0, 
	    			Integer.parseInt(this.props.getProperty("process_new_request_seconds")), 
	    			TimeUnit.SECONDS);

	    }
	    catch(Exception ex)
	    {
	    	System.out.println("Error while creating scheduler.");
	    	System.out.println(ex.getMessage());
	    }
        
        
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        scheduler.shutdownNow();
    }
    
    class Job implements Runnable
    {
    	IDBManager databaseManager;
    	Properties props;
    	
    	public Job(IDBManager databaseManager, Properties props)
    	{
    		this.databaseManager = databaseManager;
    		this.props = props;

    	}

        @Override
        public void run()
        {
			Runnable runnable = new AsynchronousUnfinishedRunner(this.databaseManager, 
					this.props.getProperty("server_ip"), 
					Integer.parseInt(this.props.getProperty("server_port")),
					AbstractSimpleAuthenticationController.LOGGER,
					ResourceLoader.getServerRunning(), 
					Long.parseLong(this.props.getProperty("script_timeout_seconds")),
					Long.parseLong(this.props.getProperty("socket_timeout_seconds")));
			new Thread(runnable).start();
        }

    }
}
