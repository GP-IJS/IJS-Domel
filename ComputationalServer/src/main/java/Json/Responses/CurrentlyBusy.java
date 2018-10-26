package Json.Responses;

public class CurrentlyBusy
{
    private static CurrentlyBusy ourInstance = new CurrentlyBusy();

    public static CurrentlyBusy getInstance() {
        return ourInstance;
    }

    private CurrentlyBusy(){}

    private boolean setBusy = false;


    public boolean isSetBusy()
    {
        return setBusy;
    }

    public void calculationStart()
    {
        this.setBusy = true;
    }

    public void calculationEnd()
    {
        this.setBusy = false;
    }
}
