// Copyright (c) 2001-2022 Aspose Pty Ltd. All Rights Reserved.
// 07/02/12 by Durin Vyacheslav

package com.aspose.words.net.System.Globalization;

import com.aspose.ms.System.Globalization.*;
import com.aspose.words.ReservedForInternalUse;

import java.util.Locale;

/**
 * Map for .Net's System.Globalization.CultureInfo.
 */
public class CultureInfo
{
    //Used by msCultureInfo.fromJava() for PublicApiChangeMap.
    @ReservedForInternalUse
    public CultureInfo(msCultureInfo cultureInfo)
    {
        msCultureInfo = cultureInfo;
    }

    //Used by msCultureInfo.toJava() for PublicApiChangeMap.
    @ReservedForInternalUse
    public msCultureInfo getMsCultureInfo()
    {
        return msCultureInfo;
    }

    /**
     * Constructor with .Net-style Culture Name.
     */
    public CultureInfo(String cultureName)
    {
        msCultureInfo = new msCultureInfo(cultureName);
    }

    /**
     * Constructor with .Net-style Culture Name.
     */
    public CultureInfo(String cultureName, boolean useUserOverride)
    {
        msCultureInfo = new msCultureInfo(cultureName);
    }

    /**
     * Constructor with Java-style Locale.
     */
    public CultureInfo(Locale locale)
    {
        msCultureInfo = new msCultureInfo(locale);
    }

    /**
     * Gets java Locale from .Net-style CultureInfo
     */
    public Locale getLocale(CultureInfo cultureInfo)
    {
        return msCultureInfo.getLocale();
    }

    /**
     * Gets .Net-style DateTimeFormatInfo
     */
    public DateTimeFormatInfo getDateTimeFormat()
    {
        return new DateTimeFormatInfo(msCultureInfo.getDateTimeFormat());
    }

    private msCultureInfo msCultureInfo;
}

