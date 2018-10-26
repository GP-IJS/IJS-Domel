package Json.Responses;

import Json.Error;
import Json.Result;

public class CalculationResponse
{
    Error status;
    Result result;

    private CalculationResponse(Error status, Result result)
    {
        this.status = status;
        this.result=result;
    }

    public static CalculationResponse Success(Result result)
    {
        return new CalculationResponse(Error.Success, result);
    }

    public static CalculationResponse Failure(Error error)
    {
        return new CalculationResponse(error, null);
    }
}
