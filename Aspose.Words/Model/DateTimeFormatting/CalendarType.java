// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2016 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a calendar.
/// </summary>
public /*enum*/ final class CalendarType
{
    private CalendarType(){}
    
    /// <summary>
    /// The Gregorian calendar.
    /// </summary>
    public static final int GREGORIAN = 0;

    /// <summary>
    /// The Hijri Lunar calendar.
    /// </summary>
    public static final int HIJRI = 1;

    /// <summary>
    /// The Hebrew Lunar calendar.
    /// </summary>
    public static final int HEBREW = 2;

    /// <summary>
    /// The Saka Era calendar.
    /// </summary>
    public static final int SAKA_ERA = 3;

    /// <summary>
    /// The Um-al-Qura calendar.
    /// </summary>
    public static final int UM_AL_QURA = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CalendarType value.
    /// </summary>
    public static String getName(/*CalendarType*/int calendarType)
    {
    	switch (calendarType)
    	{
    		case GREGORIAN: return "GREGORIAN";
    		case HIJRI: return "HIJRI";
    		case HEBREW: return "HEBREW";
    		case SAKA_ERA: return "SAKA_ERA";
    		case UM_AL_QURA: return "UM_AL_QURA";
    		default: return "Unknown CalendarType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CalendarType value.
    /// </summary>
    public static String toString(/*CalendarType*/int calendarType)
    {
    	switch (calendarType)
    	{
    		case GREGORIAN: return "Gregorian";
    		case HIJRI: return "Hijri";
    		case HEBREW: return "Hebrew";
    		case SAKA_ERA: return "SakaEra";
    		case UM_AL_QURA: return "UmAlQura";
    		default: return "Unknown CalendarType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CalendarType name.
    /// </summary>
    public static /*CalendarType*/int fromName(String calendarTypeName)
    {
    	if ("GREGORIAN".equals(calendarTypeName)) return GREGORIAN;
    	if ("HIJRI".equals(calendarTypeName)) return HIJRI;
    	if ("HEBREW".equals(calendarTypeName)) return HEBREW;
    	if ("SAKA_ERA".equals(calendarTypeName)) return SAKA_ERA;
    	if ("UM_AL_QURA".equals(calendarTypeName)) return UM_AL_QURA;
    	throw new IllegalArgumentException("Unknown CalendarType name.");
    }
    
    /// <summary>
    /// Returns array of CalendarType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		GREGORIAN,
    		HIJRI,
    		HEBREW,
    		SAKA_ERA,
    		UM_AL_QURA,
    	};
    }

    public static final int length = 5;
}
