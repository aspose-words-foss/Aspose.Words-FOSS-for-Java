// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/01/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.DateTime;
import com.aspose.Debug;
import com.aspose.FormatterPal;
import com.aspose.ms.System.Text.RegularExpressions.MatchCollection;
import com.aspose.ms.System.Text.RegularExpressions.Match;
import com.aspose.ms.System.msString;
import com.aspose.ms.System.Text.RegularExpressions.Group;
import com.aspose.ms.System.Text.RegularExpressions.Regex;
import com.aspose.ms.System.Text.RegularExpressions.RegexOptions;


/// <summary>
/// Represents a constant whose value is date/time.
/// </summary>
class DateTimeConstant extends Constant
{
    DateTimeConstant(DateTime value)
    {
    	this(value, CalendarType.GREGORIAN);
	
    }

    DateTimeConstant(DateTime value, /*CalendarType*/int calendarType)
    {
        mValue = value;
        mCalendarType = calendarType;
    }

    /*override*/ String tryFormatDateTime(String format, int eastAsianLanguageId, IFieldResultFormatter resultFormatter)
    {
        Debug.assertTrue(format != null);

        String formattedResult = null;
        if (resultFormatter != null)
            formattedResult = resultFormatter.formatDateTime(mValue.toJava(), format, mCalendarType);

        return (formattedResult != null ? formattedResult : WordUtil.formatDateTime(mValue, format, eastAsianLanguageId, mCalendarType));
    }

    /*override*/ /*ConstantType*/int getConstantType() { return ConstantType.DATE_TIME; }

    /*override*/ String getValueString() { return mValue.toString(); }

    static DateTimeConstant tryParse(String value)
    {
        DateTime valueDateTime = FormatterPal.tryParseDateTimeInvariant(value);

        if (DateTime.equals(valueDateTime, DateTime.MinValue))
            valueDateTime = tryExtractDateTime(value);

        return !DateTime.equals(valueDateTime, DateTime.MinValue)
            ? new DateTimeConstant(valueDateTime)
            : null;
    }

    private static DateTime tryExtractDateTime(String value)
    {
        MatchCollection matches = G_DATE_TIME_REGEX.matches(value);

        for (int index = matches.getCount() - 1; index >= 0; index--)
        {
            Match match = matches.get(index);

            DateTime result = tryParseDateTimeInvariant(match.getValue());
            if (!DateTime.equals(result, DateTime.MinValue))
                return result;

            String matchWithoutApMpDesignator = getMatchWithoutApMpDesignator(match);
            result = FormatterPal.tryParseDateTimeInvariant(matchWithoutApMpDesignator);
            if (!DateTime.equals(result, DateTime.MinValue))
                return result;
        }

        return DateTime.MinValue;
    }

    private static DateTime tryParseDateTimeInvariant(String value)
    {
        return msString.isNullOrEmpty(value)
            ? DateTime.MinValue
            : FormatterPal.tryParseDateTimeInvariant(value);
    }

    private static String getMatchWithoutApMpDesignator(Match match)
    {
        Group group = match.getGroups().get(1);

        if (!group.getSuccess())
            return null;

        String matchValue = match.getValue();
        String groupValue = group.getValue();

        return msString.remove(matchValue, matchValue.length() - groupValue.length());
    }

    private /*final*/ DateTime mValue;
    private /*final*/ /*CalendarType*/int mCalendarType;

    private static /*final*/ Regex G_DATE_TIME_REGEX = new Regex("\\d+[\\./]\\d+[\\./]\\d+(?:\\s+\\d+[\\.:]\\d+(?:[\\.:]\\d+)?(\\s+(?:am|pm))?)?", RegexOptions.COMPILED | RegexOptions.IGNORE_CASE);
}

