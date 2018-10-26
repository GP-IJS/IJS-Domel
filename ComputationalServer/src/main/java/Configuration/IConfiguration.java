package Configuration;

public interface IConfiguration
{
    String getResultsFileName();
    String getParametersFileName();
    int getPort();
    void debugView(String error);
    String getProcessCommand();
    String getTargetPath();
    String getCleanupCommand();
    String[] getProcessesToKill();

}
