package com.aspose.ms.System.Globalization;

/// <summary>
/// Specifies whether a calendar is solar-based, lunar-based, or lunisolar-based.
/// </summary>
public /*enum*/ final class CalendarAlgorithmType
{
    private CalendarAlgorithmType(){}
    
    public static final int UNKNOWN = 0;
    public static final int SOLAR_CALENDAR = 1;
    public static final int LUNAR_CALENDAR = 2;
    public static final int LUNISOLAR_CALENDAR = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CalendarAlgorithmType value.
    /// </summary>
    public static String getName(/*CalendarAlgorithmType*/int calendarAlgorithmType)
    {
    	switch (calendarAlgorithmType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case SOLAR_CALENDAR: return "SOLAR_CALENDAR";
    		case LUNAR_CALENDAR: return "LUNAR_CALENDAR";
    		case LUNISOLAR_CALENDAR: return "LUNISOLAR_CALENDAR";
    		default: return "Unknown CalendarAlgorithmType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CalendarAlgorithmType value.
    /// </summary>
    public static String toString(/*CalendarAlgorithmType*/int calendarAlgorithmType)
    {
    	switch (calendarAlgorithmType)
    	{
    		case UNKNOWN: return "Unknown";
    		case SOLAR_CALENDAR: return "SolarCalendar";
    		case LUNAR_CALENDAR: return "LunarCalendar";
    		case LUNISOLAR_CALENDAR: return "LunisolarCalendar";
    		default: return "Unknown CalendarAlgorithmType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CalendarAlgorithmType name.
    /// </summary>
    public static /*CalendarAlgorithmType*/int fromName(String calendarAlgorithmTypeName)
    {
    	if ("UNKNOWN".equals(calendarAlgorithmTypeName)) return UNKNOWN;
    	if ("SOLAR_CALENDAR".equals(calendarAlgorithmTypeName)) return SOLAR_CALENDAR;
    	if ("LUNAR_CALENDAR".equals(calendarAlgorithmTypeName)) return LUNAR_CALENDAR;
    	if ("LUNISOLAR_CALENDAR".equals(calendarAlgorithmTypeName)) return LUNISOLAR_CALENDAR;
    	throw new IllegalArgumentException("Unknown CalendarAlgorithmType name.");
    }
    
    /// <summary>
    /// Returns array of CalendarAlgorithmType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		SOLAR_CALENDAR,
    		LUNAR_CALENDAR,
    		LUNISOLAR_CALENDAR,
    	};
    }

    public static final int length = 4;
}
