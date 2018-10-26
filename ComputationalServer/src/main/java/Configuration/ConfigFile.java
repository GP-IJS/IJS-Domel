package Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigFile implements IConfiguration
{
    String res;
    String par;
    int port;
    String command;
    boolean debug;
    String targetPath;
    String cleanupCommand;
    String[] processesToKill;

    public ConfigFile(String filePath) throws IOException {
        String configFilePath = filePath;
        Properties prop = new Properties();
        InputStream input = new FileInputStream(configFilePath);
        prop.load(input);

        res = prop.getProperty("resultsFileName");
        par = prop.getProperty("parametersFileName");
        port = Integer.parseInt(prop.getProperty("serverPort"));
        command = prop.getProperty("processCommand");
        targetPath = prop.getProperty("targetPath");
        cleanupCommand = prop.getProperty("cleanupCommand");

        String[] procList = prop.getProperty("processesToKill").split(",");
        processesToKill = new String[procList.length];
        for (int i = 0; i < procList.length; i++)
        {
            processesToKill[i] = procList[i].trim();
        }



        if(prop.getProperty("debugView").equals("0"))
            debug = false;
        else if(prop.getProperty("debugView").equals("1"))
            debug = true;
        else
            throw new IOException("Invalid debugView value.");
    }

    @Override
    public String getResultsFileName() {return this.res;}

    @Override
    public String getParametersFileName() {return this.par;}

    @Override
    public int getPort() {return this.port;}

    @Override
    public void debugView(String error)
    {
        if(debug==true)
            System.out.println(error);
    }

    @Override
    public String getProcessCommand() {
        return this.command;
    }

    @Override
    public String getTargetPath() { return this.targetPath; }

    @Override
    public String getCleanupCommand() { return this.cleanupCommand; }

    @Override
    public String[] getProcessesToKill()
    {
        return this.processesToKill;
    }

    @Override
    public String toString() {
        return "\n-------------------- Config. Parameters --------------------\n" +
                "Results File Name: " + this.getResultsFileName() + "\n"+
                "Parameters File Name: " + this.getParametersFileName()+"\n"+
                "Process Command: " + this.getProcessCommand()+"\n"+
                "Port: " + this.getPort()+"\n"+
                "TargetPath: " + this.getTargetPath()+"\n"+
                "------------------------------------------------------------\n";
    }

}
