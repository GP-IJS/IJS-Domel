package Configuration;

public class ConfigDefault implements IConfiguration {
    @Override
    public String getResultsFileName() {
        return "results.txt";
    }

    @Override
    public String getParametersFileName() {
        return "parameters.txt";
    }

    @Override
    public int getPort() {
        return 10001;
    }

    @Override
    public void debugView(String error) {
        System.out.println(error);
    }

    @Override
    public String getProcessCommand() {
        return "TestExeApp.exe";
    }

    @Override
    public String getTargetPath(){ return null; }

    @Override
    public String getCleanupCommand() { return null; }

    @Override
    public String[] getProcessesToKill() { return new String[0]; }

    @Override
    public String toString() {
        return "\n---------------- Default Config. Parameters ----------------\n" +
                "Results File Name: " + this.getResultsFileName() + "\n"+
                "Parameters File Name: " + this.getParametersFileName()+"\n"+
                "Process Command: " + this.getProcessCommand()+"\n"+
                "Port: " + this.getPort()+"\n"+
                "Targer Path: /\n"+
                "------------------------------------------------------------\n";
    }
}
