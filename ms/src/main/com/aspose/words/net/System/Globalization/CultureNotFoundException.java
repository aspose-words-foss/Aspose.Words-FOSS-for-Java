package com.aspose.words.net.System.Globalization;

/**
 * Map for .Net's System.Globalization.CultureNotFoundException
 */
public final class CultureNotFoundException extends RuntimeException
{
    public CultureNotFoundException(Exception e)
    {
        super(e);
    }

    public CultureNotFoundException(int cultureId)
    {
        super("Culture ID '" + cultureId + "' is not supported");
    }

    public CultureNotFoundException(String cultureName)
    {
        super("Culture '" + cultureName + "' is not supported");
    }

    public CultureNotFoundException(String cultureName, String altName)
    {
        super("Culture '" + cultureName + "' and Alter name '" + altName + "' are not supported");
    }
}
