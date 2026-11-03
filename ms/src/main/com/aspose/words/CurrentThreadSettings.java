package com.aspose.words;

import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.words.net.System.Globalization.CultureInfo;
import com.aspose.ms.System.Threading.CurrentThread;

import java.util.Locale;
import java.util.TimeZone;

/**
 * This class helps to set thread-isolated Locale and Time Zone for a Aspose.Words application.
 */
public final class CurrentThreadSettings
{
    private CurrentThreadSettings() {}

    /**
     * Returns {@link Locale} that used by the current Aspose.Words thread.
     */
    public static Locale getLocale()
    {
        return CurrentThread.getCurrentCulture().getLocale();
    }

    /**
     * Sets {@link Locale} for the current Aspose.Words thread using locale name.
     */
    public static void setLocale(String localeName)
    {
        CurrentThread.setCurrentCulture(localeName);
    }

    /**
     * Sets {@link Locale} for the current Aspose.Words thread.
     */
    public static void setLocale(Locale locale)
    {
        CurrentThread.setCurrentCulture(new msCultureInfo(locale));
    }

    /**
     * Returns {@link TimeZone} that used by the current Aspose.Words thread.
     */
    public static TimeZone getTimeZone()
    {
        return CurrentThread.getTimeZone();
    }

    /**
     * Sets {@link TimeZone} for the current Aspose.Words thread.
     */
    public static void setTimeZone(TimeZone timeZone)
    {
        CurrentThread.setTimeZone(timeZone);
    }
}