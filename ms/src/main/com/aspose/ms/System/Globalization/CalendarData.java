package com.aspose.ms.System.Globalization;

import com.aspose.ms.System.msString;

final class CalendarData
{

    void setCalendarId(int id)
    {
        mCalendarId = id;
    }

    void setLongDatePattern(String longDatePattern)
    {
        saLongDates = new String[]{longDatePattern};
    }

    String getLongDatePattern() { return saLongDates.length > 0 ? saLongDates[0] : null; }

    void setLongTimePattern(String longTimePattern)
    {
        mLongTimePattern = longTimePattern;
    }

    String getLongTimePattern() { return mLongTimePattern; }

    void setShortDatePattern(String shortDatePattern)
    {
        saShortDates = new String[]{shortDatePattern};
    }

    String getShortDatePattern() { return saShortDates.length > 0 ? saShortDates[0] : null; }

    void setShortTimePattern(String shortTimePattern)
    {
        mShortTimePattern = shortTimePattern;
    }

    public String getShortTimePattern()
    {
        return mShortTimePattern;
    }

    void setMonthDayPattern(String monthDayPattern)
    {
        sMonthDay = monthDayPattern;
    }

    String getMonthDayPattern() { return sMonthDay; }

    void setYearMonthPattern(String yearMonthPattern)
    {
        saYearMonths = new String[]{yearMonthPattern};
    }

    String getYearMonthPattern() { return saYearMonths.length > 0 ? saYearMonths[0] : null; }

    void setDateSeparator(String dateSeparator)
    {
        mDateSeparator = dateSeparator;
    }

    public String getDateSeparator()
    {
        return mDateSeparator;
    }

    void setTimeSeparator(String timeSeparator)
    {
        mTimeSeparator = timeSeparator;
    }

    public String getTimeSeparator()
    {
        return mTimeSeparator;
    }

    void setFullDateTimePattern(String fullDateTimePattern)
    {
        mFullDateTimePattern = fullDateTimePattern;
    }

    public String getFullDateTimePattern()
    {
        return mFullDateTimePattern;
    }

    void setPMDesignator(String pmDesignator)
    {
        mPMDesignator = pmDesignator;
    }

    public String getPMDesignator()
    {
        return mPMDesignator;
    }

    void setAMDesignator(String amDesignator)
    {
        mAMDesignator = amDesignator;
    }

    public String getAMDesignator()
    {
        return mAMDesignator;
    }

    void setRFC1123Pattern(String rfc1123Pattern)
    {
        mRfc1123Pattern = rfc1123Pattern;
    }

    public String getRfc1123Pattern()
    {
        return mRfc1123Pattern;
    }

    void setSortableDateTimePattern(String sortableDateTimePattern)
    {
        mSortableDateTimePattern = sortableDateTimePattern;
    }

    public String getSortableDateTimePattern()
    {
        return mSortableDateTimePattern;
    }

    void setUniversalSortableDateTimePattern(String universalSortableDateTimePattern)
    {
        mUniversalSortableDateTimePattern = universalSortableDateTimePattern;
    }

    public String getUniversalSortableDateTimePattern()
    {
        return mUniversalSortableDateTimePattern;
    }

    public static CalendarData getInvariant()
    {
        return gInvariant;
    }

    // Max calendars
    public static final int MAX_CALENDARS = 23;
    private static final CalendarData gInvariant;

    CalendarData()
    {

    }

    static
    {
        // Set our default/gregorian US calendar data
        // Calendar IDs are 1-based, arrays are 0 based.
        CalendarData invariant = new CalendarData();
        // Set default data for calendar
        // Note that we don't load resources since this IS NOT supposed to change (by definition)
        invariant.sNativeName = "Gregorian Calendar";  // Calendar Name

        // Year
        invariant.iTwoDigitYearMax = 2029; // Max 2 digit year (for Y2K bug data entry)
        invariant.iCurrentEra = 1; // Current era #

        // Formats
        invariant.saShortDates = new String[]{"MM/dd/yyyy", "yyyy-MM-dd"};          // short date format
        invariant.saLongDates = new String[]{"dddd, dd MMMM yyyy"};                 // long date format
        invariant.saYearMonths = new String[]{"yyyy MMMM"};                         // year month format
        invariant.sMonthDay = "MMMM dd";                                            // Month day pattern

        // Calendar Parts Names
        invariant.saEraNames = new String[]{"A.D."};     // Era names
        invariant.saAbbrevEraNames = new String[]{"AD"};      // Abbreviated Era names
        invariant.saAbbrevEnglishEraNames = new String[]{"AD"};     // Abbreviated era names in English
        invariant.saDayNames = new String[]{"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};// day names
        invariant.saAbbrevDayNames = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};     // abbreviated day names
        invariant.saSuperShortDayNames = new String[]{"Su", "Mo", "Tu", "We", "Th", "Fr", "Sa"};      // The super short day names
        invariant.saMonthNames = new String[]{"January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December", msString.Empty}; // month names
        invariant.saAbbrevMonthNames = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun",
                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec", msString.Empty}; // abbreviated month names
        invariant.saMonthGenitiveNames = invariant.saMonthNames;              // Genitive month names (same as month names for invariant)
        invariant.saAbbrevMonthGenitiveNames = invariant.saAbbrevMonthNames;    // Abbreviated genitive month names (same as abbrev month names for invariant)
        invariant.saLeapYearMonthNames = invariant.saMonthNames;              // leap year month names are unused in Gregorian English (invariant)

        invariant.bUseUserOverrides = false;
        invariant.mAMDesignator = "AM";
        invariant.mPMDesignator = "PM";
        invariant.mDateSeparator = "/";
        invariant.mFullDateTimePattern = "dddd, dd MMMM yyyy HH:mm:ss";
        invariant.mLongTimePattern = "HH:mm:ss";
        invariant.mRfc1123Pattern = "ddd, dd MMM yyyy HH':'mm':'ss 'GMT'";
        invariant.mShortTimePattern = "HH:mm";
        invariant.mSortableDateTimePattern = "yyyy'-'MM'-'dd'T'HH':'mm':'ss";
        invariant.mUniversalSortableDateTimePattern = "yyyy'-'MM'-'dd HH':'mm':'ss'Z'";
        invariant.mTimeSeparator = ":";

        // Calendar was built, go ahead and assign it...
        gInvariant = invariant;
    }

    private String sNativeName;
    private int iTwoDigitYearMax;
    private int iCurrentEra;
    private String[] saShortDates;
    private String[] saLongDates;
    private String[] saYearMonths;
    private String sMonthDay;
    private String[] saEraNames;
    private String[] saAbbrevEraNames;
    private String[] saAbbrevEnglishEraNames;
    private String[] saDayNames;
    private String[] saAbbrevDayNames;
    private String[] saSuperShortDayNames;
    private String[] saMonthNames;
    private String[] saAbbrevMonthNames;
    private String[] saMonthGenitiveNames;
    private String[] saAbbrevMonthGenitiveNames;
    private String[] saLeapYearMonthNames;
    private boolean bUseUserOverrides;
    private int mCalendarId;
    private String mLongTimePattern;
    private String mShortTimePattern;
    private String mDateSeparator;
    private String mTimeSeparator;
    private String mFullDateTimePattern;
    private String mPMDesignator;
    private String mAMDesignator;
    private String mRfc1123Pattern;
    private String mSortableDateTimePattern;
    private String mUniversalSortableDateTimePattern;
}
