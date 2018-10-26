package Managers;

import Configuration.IConfiguration;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.TimeUnit;

public class ProcessRunner
{
    static Runtime commandPrompt = Runtime.getRuntime();
    private static String removeSimbols(String s)
    {
        return s.replaceAll("^\"|\"$", "");
    }

    public static boolean runProcess(long timeout, IConfiguration config)
    {
        try
        {
            //Run script and wait for it to finnish or until it runs out of time
            File dir = new File(removeSimbols(config.getTargetPath()));
            Process proc = commandPrompt.exec(removeSimbols(config.getProcessCommand()), null, dir);
            boolean v = proc.waitFor(timeout , TimeUnit.SECONDS);
            //Destroy the process if not finished
            proc.destroy();

            //Try to run script to kill all the proceses
            FileFilter fileFilter = new WildcardFileFilter(config.getCleanupCommand());
            File[] files = dir.listFiles(fileFilter);
            if(files != null) {
                for (File file : files)
                {
                    try {
                        commandPrompt.exec(file.toString(), null, dir);
                    }
                    catch (Exception e)
                    {
                        //Ignore exception if there is no file to cleanup the mess
                    }
                }
            }

            for (String killproc:config.getProcessesToKill())
            {
                try
                {
                    Runtime.getRuntime().exec("taskkill /F /IM " + killproc);
                }
                catch (Exception e)
                {
                    //Ignore exception if there is no task to kill
                }
            }

            return v;
        }
        catch (Exception e)
        {
            return false;
        }
    }
}
