package com.aspose.ms.System.Globalization;

public class HebrewNumberParsingContext
{
    int state;
    int result;

    public HebrewNumberParsingContext(int result)
    {
        this.state = HebrewNumber.HS.Start;
        this.result = 0;
    }

}
