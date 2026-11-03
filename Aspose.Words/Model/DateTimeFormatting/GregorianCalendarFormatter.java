// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********

import com.aspose.ms.System.Globalization.GregorianCalendar;
import com.aspose.ms.System.DateTime;
import com.aspose.ms.System.Globalization.msCultureInfo;
import com.aspose.LanguageOnly;
import com.aspose.StringUtil;
import com.aspose.ms.System.Text.msStringBuilder;


class GregorianCalendarFormatter extends CalendarFormatter
{
    public GregorianCalendarFormatter()
    {
    	super(new GregorianCalendar());
	
    }

    protected /*override*/ void adjustPattern(DateTime value, StringBuilder pattern, char token, int tokenLength, msCultureInfo culture, String formatString, int tokenPosition)
    {
        if (!LanguageOnly.compare(culture.getLCID(), LanguageOnly.FRENCH))
            return;

        if (token != 'd' || tokenLength != 1)
            return;

        if (value.getDay() != 1)
            return;

        if (!StringUtil.startsWithOrdinalIgnoreCase(formatString.substring(tokenPosition), "d MMMM"))
            return;

        msStringBuilder.append(pattern, "er");
    }

    protected /*override*/ boolean useInvariantCulture(char token, int tokenLength)
    {
        return false;
    }

    protected /*override*/ String formatCore(DateTime value, char token, int tokenLength)
    {
        throw new IllegalStateException();
    }

    protected /*override*/ msCultureInfo getInvariantCulture() { throw new IllegalStateException(); }
}
