import Configuration.ConfigDefault;
import Configuration.ConfigFile;
import Configuration.IConfiguration;
import Json.RequestedParameters;
import Json.Responses.CurrentlyBusy;
import Managers.FileManager;
import ResponseManager.MetaDataResponse;
import ResponseManager.MultithreadedResponse;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;

public class ServerStart
{
    public static void main(String []args) throws IOException
    {
        IConfiguration conf = null;
        if(args.length>0) {
            try {
                conf = new ConfigFile(args[0]);
                conf.debugView("Configuration file loaded from: " + args[0]);
            } catch (Exception ex) {
                conf = new ConfigDefault();
                conf.debugView("No configuration file found: " + args[0]);
                conf.debugView("No config file found/error while parsing.");
            }
        }

        if(conf==null)
        {
            conf = new ConfigDefault();
            conf.debugView("Default config file used. (no input path provided)");
        }
        conf.debugView(conf.toString());


        FileManager fileManager;
        ServerSocket serverSocket;
        try
        {
            fileManager = new FileManager(conf.getParametersFileName(), conf.getResultsFileName(), conf.getTargetPath());

            int serverPort = conf.getPort();
            conf.debugView("Starting server...");
            serverSocket = new ServerSocket(serverPort);
        }
        catch (Exception ex)
        {
            conf.debugView("Exception while starting server.");
            conf.debugView(ex.toString());
            System.exit(0);
            return;
        }

        CurrentlyBusy singleton = CurrentlyBusy.getInstance();
        while(true)
        {
            try
            {
                Socket server = serverSocket.accept();

                PrintWriter toClient = new PrintWriter(server.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(server.getInputStream()));

                String line = fromClient.readLine();

                if(line == null || line.equals(""))
                {
                    toClient.close();
                    server.close();
                    continue;
                }

                if(line.equals("meta"))
                {
                    Thread t = new Thread(new MetaDataResponse(toClient, server, singleton));
                    t.start();
                    continue;
                }

                if(singleton.isSetBusy())
                    continue;

                conf.debugView("Just connected to " + server.getRemoteSocketAddress());
                conf.debugView("Full request: (" + new Date().toString() + ")" + line);



                RequestedParameters par;
                try
                {
                    par = new Gson().fromJson(line, RequestedParameters.class);
                    System.out.println(par.ScriptTimeout);
                }
                catch (Exception ex)
                {
                    conf.debugView("Error while parsing JSON.");
                    continue;
                }

                Thread t = new Thread(new MultithreadedResponse(conf, toClient, par, fileManager,
                        server, singleton, par.ScriptTimeout));
                t.start();
            }
            catch(UnknownHostException ex)
            {
                conf.debugView("Unknown host.");
                ex.printStackTrace();
            }
            catch(IOException ex)
            {
                conf.debugView("Error reading one of the files.");
                ex.printStackTrace();
            }
            catch (Exception ex)
            {
                conf.debugView("General exception. ");
                ex.printStackTrace();
            }
        }

    }
}
