package Json.Responses;

public class MetaData
{
    private String serverVersion = "1";
    private boolean isBusy;

    public boolean isBusy() {
        return isBusy;
    }

    public void setBusy(boolean busy) {
        isBusy = busy;
    }

    public String getServerVersion() {
        return serverVersion;
    }

}
