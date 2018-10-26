package ResponseManager;

import Json.Responses.CurrentlyBusy;
import Json.Responses.MetaData;
import com.google.gson.Gson;

import java.io.PrintWriter;
import java.net.Socket;

public class MetaDataResponse implements Runnable
{
    PrintWriter writer;
    Socket server;
    Gson gson;
    CurrentlyBusy singleton;

    public MetaDataResponse(PrintWriter writer, Socket server, CurrentlyBusy singleton)
    {
        super();
        this.writer = writer;
        this.server = server;
        this.gson = new Gson();
        this.singleton = singleton;
    }

    @Override
    public void run()
    {
        try
        {
            MetaData r = new MetaData();
            r.setBusy(this.singleton.isSetBusy());
            writer.println(this.gson.toJson(r));

            writer.close();
            server.close();
        }
        catch (Exception e)
        {

        }
    }
}
