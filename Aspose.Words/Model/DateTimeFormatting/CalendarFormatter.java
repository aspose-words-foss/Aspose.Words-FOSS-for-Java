// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Globalization.Calendar;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.ms.System.Text.msStringBuilder;
import com.aspose.ms.System.Threading.CurrentThread;
import com.aspose.Language;
import java.lang.Class;


/// <summary>
/// Formats datetime value according current culture and specific calendar.
/// </summary>
abstract class CalendarFormatter
{
    protected CalendarFormatter(Calendar calendar)
    {
        mCalendar = calendar;
        mCalendarType = calendar.getClass();
    }

    String format(DateTime value, char token, int tokenLength, String formatString, int tokenPosition)
    {
        // WORDSNET-12995 .NET does not understand 'Y', only 'y'.
        // WORDSNET-4350 .Net does not understand 'D', only 'd'. Same for 'S'.
        if ((token == 'Y') || (token == 'D') || (token == 'S'))
            token = Character.toLowerCase(token);

        msCultureInfo culture = getCulture(token, tokenLength);
        msDateTimeFormatInfo formatProvider = getFormatProvider(culture);
        if (formatProvider == null)
            return formatCore(value, token, tokenLength);

        mDayPatternMet = mDayPatternMet || (token == 'd');

        // WORDSNET-14872 .NET treats a single char date/time format string as a standard (non-custom) specifier,
        // should prepend % so it treats it as a custom format string.
        StringBuilder pattern = new StringBuilder(tokenLength + 1);
        if (tokenLength == 1)
            pattern.append('%');

        msStringBuilder.append(pattern, token, tokenLength);

        adjustPattern(value, pattern, token, tokenLength, culture, formatString, tokenPosition);

        // There is a difference between MS Word and .Net formatting of month name for some languages.
        // .Net outputs a genetive form if date pattern is present in the format. Word does not.
        // Besides, .Net outputs the first letter as upper case if day pattern is not present. Word does not.
        // Our implementation is somewhere in between： we make use of a genetive form if day pattern is present *before*
        // the month. Otherwise, we use normal form. So, for August 26 in Russian,
        // for "MMMM dd" we make "Август 26" vs Word's "август 26"
        // for "dd MMMM" we make "26 августа" vs Word's "26 август"
        boolean useGenetiveMonth = useGenetiveMonth(culture) && mDayPatternMet && (token == 'M');
        final String TWO_DIGIT_DAY_PATTERN = "dd";
        if (useGenetiveMonth)
        {
            // This will make ToString() use genetive months.
            msStringBuilder.append(pattern, TWO_DIGIT_DAY_PATTERN);
        }

        String formattedValue = value.toString(pattern.toString(), formatProvider);

        return useGenetiveMonth
            ? formattedValue.substring((0), (0) + (formattedValue.length() - TWO_DIGIT_DAY_PATTERN.length()))
            : formattedValue;
    }

    protected /*virtual*/ void adjustPattern(DateTime value, StringBuilder pattern, char token, int tokenLength, msCultureInfo culture, String formatString, int tokenPosition)
    {
    }

    private msDateTimeFormatInfo getFormatProvider(msCultureInfo culture)
    {
        if (culture == null)
            return null;

        msDateTimeFormatInfo info = culture.getDateTimeFormat();

        // The DateTimeFormatInfo.Calendar setter overrides some properties, e.g. month or day names. So, we do not apply new calendar if the current of the same type.
        if (info.getCalendar().getClass() != mCalendarType)
        {
            info = (msDateTimeFormatInfo)info.deepClone();
            info.setCalendar(getCalendar());
        }

        return info;
    }

    private msCultureInfo getCulture(char token, int tokenLength)
    {
        msCultureInfo culture = CurrentThread.getCurrentCulture();
        if (isCultureSupportsCalendar(culture))
            return culture;

        if (useInvariantCulture(token, tokenLength))
            return getInvariantCulture();

        return null;
    }

    protected abstract boolean useInvariantCulture(char token, int tokenLength);

    protected abstract String formatCore(DateTime value, char token, int tokenLength);

    protected abstract msCultureInfo getInvariantCulture();

    protected Calendar getCalendar() { return mCalendar; };

    private Calendar mCalendar;

    private boolean isCultureSupportsCalendar(msCultureInfo culture)
    {
        for (Calendar calendar : culture.getOptionalCalendars())
        {
            if (calendar.getClass() == mCalendarType)
                return true;
        }

        return false;
    }

    private static boolean useGenetiveMonth(msCultureInfo culture)
    {
        switch (culture.getLCID())
        {
            case (int)Language.BELARUSIAN_BELARUS:
            case (int)Language.CZECH_CZECH_REPUBLIC:
            case (int)Language.GREEK_GREECE:
            case (int)Language.LITHUANIAN_LITHUANIA:
            case (int)Language.POLISH_POLAND:
            case (int)Language.RUSSIAN_MOLDOVA:
            case (int)Language.RUSSIAN_RUSSIA:
            case (int)Language.SLOVAK_SLOVAKIA:
            case (int)Language.UKRAINIAN_UKRAINE:
                return true;
            default:
                return false;
        }
    }

    private boolean mDayPatternMet;

    private /*final*/ Class mCalendarType;
}

