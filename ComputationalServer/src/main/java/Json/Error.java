package Json;

public enum Error
{
    Success,
    FailedToCreateParametersFile,//ponovno prec,
    FailedToReadResultsFile,
    ProccesFailedToFinnish,
    PreviousRequestStillProcessing,//ponovno procesiramo
    OtherException
}
