package com.aspose.ms.System.Globalization;

/// <summary>
/// Defines the different language versions of the Gregorian calendar.
/// </summary>
public /*enum*/ final class GregorianCalendarTypes
{
    private GregorianCalendarTypes(){}
    
    public static final int LOCALIZED = 1;
    public static final int US_ENGLISH = 2;
    public static final int MIDDLE_EAST_FRENCH = 9;
    public static final int ARABIC = 10;
    public static final int TRANSLITERATED_ENGLISH = 11;
    public static final int TRANSLITERATED_FRENCH = 12;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GregorianCalendarTypes value.
    /// </summary>
    public static String getName(/*GregorianCalendarTypes*/int gregorianCalendarTypes)
    {
    	switch (gregorianCalendarTypes)
    	{
    		case LOCALIZED: return "LOCALIZED";
    		case US_ENGLISH: return "US_ENGLISH";
    		case MIDDLE_EAST_FRENCH: return "MIDDLE_EAST_FRENCH";
    		case ARABIC: return "ARABIC";
    		case TRANSLITERATED_ENGLISH: return "TRANSLITERATED_ENGLISH";
    		case TRANSLITERATED_FRENCH: return "TRANSLITERATED_FRENCH";
    		default: return "Unknown GregorianCalendarTypes value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GregorianCalendarTypes value.
    /// </summary>
    public static String toString(/*GregorianCalendarTypes*/int gregorianCalendarTypes)
    {
    	switch (gregorianCalendarTypes)
    	{
    		case LOCALIZED: return "Localized";
    		case US_ENGLISH: return "USEnglish";
    		case MIDDLE_EAST_FRENCH: return "MiddleEastFrench";
    		case ARABIC: return "Arabic";
    		case TRANSLITERATED_ENGLISH: return "TransliteratedEnglish";
    		case TRANSLITERATED_FRENCH: return "TransliteratedFrench";
    		default: return "Unknown GregorianCalendarTypes value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GregorianCalendarTypes name.
    /// </summary>
    public static /*GregorianCalendarTypes*/int fromName(String gregorianCalendarTypesName)
    {
    	if ("LOCALIZED".equals(gregorianCalendarTypesName)) return LOCALIZED;
    	if ("US_ENGLISH".equals(gregorianCalendarTypesName)) return US_ENGLISH;
    	if ("MIDDLE_EAST_FRENCH".equals(gregorianCalendarTypesName)) return MIDDLE_EAST_FRENCH;
    	if ("ARABIC".equals(gregorianCalendarTypesName)) return ARABIC;
    	if ("TRANSLITERATED_ENGLISH".equals(gregorianCalendarTypesName)) return TRANSLITERATED_ENGLISH;
    	if ("TRANSLITERATED_FRENCH".equals(gregorianCalendarTypesName)) return TRANSLITERATED_FRENCH;
    	throw new IllegalArgumentException("Unknown GregorianCalendarTypes name.");
    }
    
    /// <summary>
    /// Returns array of GregorianCalendarTypes values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		LOCALIZED,
    		US_ENGLISH,
    		MIDDLE_EAST_FRENCH,
    		ARABIC,
    		TRANSLITERATED_ENGLISH,
    		TRANSLITERATED_FRENCH,
    	};
    }

    public static final int length = 6;
}
