package ResponseManager;

import Configuration.IConfiguration;
import Json.Error;
import Json.RequestedParameters;
import Json.Responses.CalculationResponse;
import Json.Responses.CurrentlyBusy;
import Json.Result;
import Managers.FileManager;
import Managers.ProcessRunner;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.Date;

public class MultithreadedResponse implements Runnable
{
    private PrintWriter writer;
    private RequestedParameters par;
    private FileManager fileManager;
    private Socket server;
    private long timeout;
    private Gson gson;
    private CurrentlyBusy singleton;
    private IConfiguration conf;

    public MultithreadedResponse(IConfiguration conf,
                                 PrintWriter writer,
                                 RequestedParameters par,
                                 FileManager fileManager,
                                 Socket server,
                                 CurrentlyBusy singleton,
                                 long timeout)
    {
        super();
        this.conf = conf;
        this.writer = writer;
        this.par = par;
        this.timeout = timeout;
        this.fileManager = fileManager;
        this.server = server;
        this.gson = new Gson();
        this.singleton = singleton;
    }

    @Override
    public void run()
    {
        try
        {
            if(singleton.isSetBusy())
            {
                writer.print(new Gson().toJson(CalculationResponse.Failure(Error.PreviousRequestStillProcessing)));
                writer.close();
                server.close();
                return;
            }
        }
        catch (Exception ex)
        {
            conf.debugView("Error while busy and trying to respond");
        }

        try
        {
            this.singleton.calculationStart();
            fileManager.deleteResults();

            if(!fileManager.generateFile(par))
            {
                conf.debugView("Failed to create parameters file");
                writer.println(this.gson.toJson(CalculationResponse.Failure(Error.FailedToCreateParametersFile)));
            }

            if(!ProcessRunner.runProcess(this.timeout, conf))
            {
                conf.debugView("Failed execute procces/timeout");
                writer.println(this.gson.toJson(CalculationResponse.Failure(Error.ProccesFailedToFinnish)));
            }


            Result res = fileManager.readResults();
            if(res == null)
                writer.println(this.gson.toJson(CalculationResponse.Failure(Error.FailedToReadResultsFile)));

            writer.println(this.gson.toJson(CalculationResponse.Success(res)));

            writer.close();
            server.close();
            conf.debugView("Response send (" + new Date().toString() + ")");
        }
        catch (Exception e)
        {
            conf.debugView("Error while running process.");
            e.printStackTrace();
        }
        finally
        {
            this.singleton.calculationEnd();
            conf.debugView("Waiting for client.");
        }
    }
}
