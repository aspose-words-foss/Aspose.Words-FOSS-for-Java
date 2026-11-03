package com.aspose.ms.System.Globalization;

/// <summary>
/// Defines different rules for determining the first week of the year.
/// </summary>
public /*enum*/ final class CalendarWeekRule
{
    private CalendarWeekRule(){}
    
    public static final int FIRST_DAY = 0;
    public static final int FIRST_FULL_WEEK = 1;
    public static final int FIRST_FOUR_DAY_WEEK = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CalendarWeekRule value.
    /// </summary>
    public static String getName(/*CalendarWeekRule*/int calendarWeekRule)
    {
    	switch (calendarWeekRule)
    	{
    		case FIRST_DAY: return "FIRST_DAY";
    		case FIRST_FULL_WEEK: return "FIRST_FULL_WEEK";
    		case FIRST_FOUR_DAY_WEEK: return "FIRST_FOUR_DAY_WEEK";
    		default: return "Unknown CalendarWeekRule value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CalendarWeekRule value.
    /// </summary>
    public static String toString(/*CalendarWeekRule*/int calendarWeekRule)
    {
    	switch (calendarWeekRule)
    	{
    		case FIRST_DAY: return "FirstDay";
    		case FIRST_FULL_WEEK: return "FirstFullWeek";
    		case FIRST_FOUR_DAY_WEEK: return "FirstFourDayWeek";
    		default: return "Unknown CalendarWeekRule value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CalendarWeekRule name.
    /// </summary>
    public static /*CalendarWeekRule*/int fromName(String calendarWeekRuleName)
    {
    	if ("FIRST_DAY".equals(calendarWeekRuleName)) return FIRST_DAY;
    	if ("FIRST_FULL_WEEK".equals(calendarWeekRuleName)) return FIRST_FULL_WEEK;
    	if ("FIRST_FOUR_DAY_WEEK".equals(calendarWeekRuleName)) return FIRST_FOUR_DAY_WEEK;
    	throw new IllegalArgumentException("Unknown CalendarWeekRule name.");
    }
    
    /// <summary>
    /// Returns array of CalendarWeekRule values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		FIRST_DAY,
    		FIRST_FULL_WEEK,
    		FIRST_FOUR_DAY_WEEK,
    	};
    }

    public static final int length = 3;
}
