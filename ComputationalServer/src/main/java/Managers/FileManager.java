package Managers;

import Json.RequestedParameters;
import Json.Result;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.lang.reflect.Field;
import java.util.HashMap;

public class FileManager
{
    String parametersFileName;
    String resultsFileName;
    public FileManager(String parametersFileName, String resultsFileName, String targetPath)
    {
        this.parametersFileName = targetPath + parametersFileName;
        this.resultsFileName = targetPath + resultsFileName;
    }

    public boolean generateFile(RequestedParameters params) throws IOException
    {
        PrintWriter out = null;
        try
        {
            out = new PrintWriter(this.parametersFileName);
            out.println(params.toString());
            return true;
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            return false;
        }
        finally
        {
            if(out!=null)
                out.close();
        }

    }

    public boolean deleteResults()
    {
        try
        {
            File file = new File(this.resultsFileName);
            return file.delete();
        }
        catch (Exception ex)
        {
            return false;
        }
    }

    public Result readResults()
    {
        HashMap<String, Double> map = new HashMap<>();
        try(BufferedReader br = new BufferedReader(new FileReader(this.resultsFileName)))
        {
            for(String line; (line = br.readLine()) != null; )
            {
                //Remove characters from field names
                String[] parts = line.split("=");
                map.put(parts[0].replace("-", "_").replace(" ", ""), Double.parseDouble(parts[1].trim()));
            }
            br.close();
        }
        catch (IOException e)
        {
            return null;
        }


        //Map field using reflection
        Result res = new Result();
        Class ftClass = res.getClass();
        for (String key : map.keySet())
        {
            try
            {
                Field f1 = ftClass.getField(key);
                f1.set(res, map.get(key));
            }
            catch (Exception ex){return null;}
        }
        return res;
    }
}
