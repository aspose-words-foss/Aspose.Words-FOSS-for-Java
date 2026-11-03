// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 2020/02/10 by Konstantin Sidorenko

package com.aspose.words.net.System.Globalization;

import com.aspose.ms.System.Globalization.msDateTimeFormatInfo;
import com.aspose.words.ReservedForInternalUse;

/**
 * Map for .Net's System.Globalization.DateTimeFormatInfo.
 */
public class DateTimeFormatInfo
{
    //Used by msDateTimeFormatInfo.toJava() for PublicApiChangeMap.
    @ReservedForInternalUse
    public DateTimeFormatInfo(msDateTimeFormatInfo formatInfo)
    {
        msFormatInfo = formatInfo;
    }

    //Used by msDateTimeFormatInfo.fromJava() for PublicApiChangeMap.
    @ReservedForInternalUse
    public msDateTimeFormatInfo getMsFormatInfo()
    {
        return msFormatInfo;
    }

    public String[] getMonthNames()
    {
        return msFormatInfo.getMonthNames();
    }

    public void setMonthNames(String[] monthNames)
    {
        msFormatInfo.setMonthNames(monthNames);
    }

    public void setMonthGenitiveNames(String[] monthNames)
    {
        msFormatInfo.setMonthGenitiveNames(monthNames);
    }

    public String[] getAbbreviatedMonthNames()
    {
        return msFormatInfo.getAbbreviatedMonthNames();
    }

    public void setAbbreviatedMonthNames(String[] abbreviatedMonthNames)
    {
        msFormatInfo.setAbbreviatedMonthNames(abbreviatedMonthNames);
    }

    public void setAbbreviatedMonthGenitiveNames(String[] abbreviatedMonthNames)
    {
        msFormatInfo.setAbbreviatedMonthGenitiveNames(abbreviatedMonthNames);
    }

    public String[] getDayNames()
    {
        return msFormatInfo.getDayNames();
    }

    public void setDayNames(String[] dayNames)
    {
        msFormatInfo.setDayNames(dayNames);
    }

    public String[] getAbbreviatedDayNames()
    {
        return msFormatInfo.getAbbreviatedDayNames();
    }

    public void setAbbreviatedDayNames(String[] abbreviatedDayNames)
    {
        msFormatInfo.setAbbreviatedDayNames(abbreviatedDayNames);
    }

    public String[] getShortestDayNames()
    {
        return msFormatInfo.getShortestDayNames();
    }

    public void setShortestDayNames(String[] shortestDayNames)
    {
        msFormatInfo.setShortestDayNames(shortestDayNames);
    }

    public String getAMDesignator()
    {
        return msFormatInfo.getAMDesignator();
    }

    public void setAMDesignator(String amDesignator)
    {
        msFormatInfo.setAMDesignator(amDesignator);
    }

    public String getPMDesignator()
    {
        return msFormatInfo.getPMDesignator();
    }

    public void setPMDesignator(String pmDesignator)
    {
        msFormatInfo.setPMDesignator(pmDesignator);
    }

    public String getShortDatePattern()
    {
        return msFormatInfo.getShortDatePattern();
    }

    public void setShortDatePattern(String shortDatePattern)
    {
        msFormatInfo.setShortDatePattern(shortDatePattern);
    }

    public String getLongDatePattern()
    {
        return msFormatInfo.getLongDatePattern();
    }

    public void setLongDatePattern(String longDatePat)
    {
        msFormatInfo.setLongDatePattern(longDatePat);
    }

    public String getLongTimePattern()
    {
        return msFormatInfo.getLongTimePattern();
    }

    public void setLongTimePattern(String longTimePattern)
    {
        msFormatInfo.setLongTimePattern(longTimePattern);
    }

    public String getShortTimePattern()
    {
        return msFormatInfo.getShortTimePattern();
    }

    public void setShortTimePattern(String shortTimePatt)
    {
        msFormatInfo.setShortTimePattern(shortTimePatt);
    }

    private msDateTimeFormatInfo msFormatInfo;
}
