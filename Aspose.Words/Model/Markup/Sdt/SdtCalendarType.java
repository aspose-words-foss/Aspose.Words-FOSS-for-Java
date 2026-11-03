// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/07/2010 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the possible types of calendars which can be used to specify <see cref="StructuredDocumentTag.CalendarType"/>
/// in an Office Open XML document.
/// </summary>
public /*enum*/ final class SdtCalendarType
{
    private SdtCalendarType(){}
    
    /// <summary>
    /// Used as default value in OOXML. Equals <see cref="Gregorian"/>.
    /// </summary>
    public static final int DEFAULT = 0;

    /// <summary>
    /// Specifies that the Gregorian calendar, as defined in ISO  8601, shall be used. 
    /// This calendar should be localized into the appropriate language.
    /// </summary>
    public static final int GREGORIAN = DEFAULT;

    /// <summary>
    /// Specifies that the Gregorian calendar, as defined in ISO 8601, shall be used.
    /// The values for this calendar should be presented in Arabic.
    /// </summary>
    public static final int GREGORIAN_ARABIC = 1;

    /// <summary>
    /// Specifies that the Gregorian calendar, as defined in ISO 8601, shall be used.
    /// The values for this calendar should be presented in Middle East French.
    /// </summary>
    public static final int GREGORIAN_ME_FRENCH = 2;

    /// <summary>
    /// Specifies that the Gregorian calendar, as defined in ISO 8601, shall be used.
    /// The values for this calendar should be presented in English.
    /// </summary>
    public static final int GREGORIAN_US = 3;

    /// <summary>
    /// Specifies that the Gregorian calendar, as defined in ISO 8601, shall be used.
    /// The values for this calendar should be the representation of the English strings in the corresponding Arabic characters 
    /// (the Arabic transliteration of the English for the Gregorian calendar).
    /// </summary>
    public static final int GREGORIAN_XLIT_ENGLISH = 4;

    /// <summary>
    /// Specifies that the Gregorian calendar, as defined in ISO 8601, shall be used.
    /// The values for this calendar should be the representation of the French strings in the corresponding Arabic characters 
    /// (the Arabic transliteration of the French for the Gregorian calendar).
    /// </summary>
    public static final int GREGORIAN_XLIT_FRENCH = 5;

    /// <summary>
    /// Specifies that the Hebrew lunar calendar, as described by the Gauss formula for Passover [CITATION] 
    /// and The Complete Restatement of Oral Law (Mishneh Torah),shall be used.
    /// </summary>
    public static final int HEBREW = 6;

    /// <summary>
    /// Specifies that the Hijri lunar calendar, as described by the Kingdom of Saudi Arabia, 
    /// Ministry of Islamic Affairs, Endowments, Da‘wah and Guidance, shall be used.
    /// </summary>
    public static final int HIJRI = 7;

    /// <summary>
    /// Specifies that the Japanese Emperor Era calendar, as described by 
    /// Japanese Industrial Standard JIS X 0301, shall be used.
    /// </summary>
    public static final int JAPAN = 8;

    /// <summary>
    /// Specifies that the Korean Tangun Era calendar, 
    /// as described by Korean Law Enactment No. 4, shall be used.
    /// </summary>
    public static final int KOREA = 9;

    /// <summary>
    /// Specifies that no calendar should be used.
    /// </summary>
    /// <remarks>
    /// Usually in AW, None is the first and default value for enums, but not in this case.
    /// None is not default for OOXML, instead <see cref="Gregorian"/> is default and is first member of this enum.
    /// </remarks>
    public static final int NONE = 10;

    /// <summary>
    /// Specifies that the Saka Era calendar, as described by the Calendar Reform Committee of India, 
    /// as part of the Indian Ephemeris and Nautical Almanac, shall be used.
    /// </summary>
    public static final int SAKA = 11;

    /// <summary>
    /// Specifies that the Taiwanese calendar, as defined by the Chinese National Standard CNS 7648, shall be used.
    /// </summary>
    public static final int TAIWAN = 12;

    /// <summary>
    /// Specifies that the Thai calendar, as defined by the Royal Decree of H.M. King Vajiravudh (Rama VI) in 
    /// Royal Gazette B. E. 2456 (1913 A.D.) and by the decree of Prime Minister Phibunsongkhram (1941 A.D.) to 
    /// start the year on the Gregorian January 1 and to map year zero to Gregorian year 543 B.C., shall be used.
    /// </summary>
    public static final int THAI = 13;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer SdtCalendarType value.
    /// </summary>
    public static String getName(/*SdtCalendarType*/int sdtCalendarType)
    {
    	switch (sdtCalendarType)
    	{
    		case DEFAULT:
    		/*case GREGORIAN:*/
    			return "DEFAULT" + " | " + "GREGORIAN";
    		case GREGORIAN_ARABIC: return "GREGORIAN_ARABIC";
    		case GREGORIAN_ME_FRENCH: return "GREGORIAN_ME_FRENCH";
    		case GREGORIAN_US: return "GREGORIAN_US";
    		case GREGORIAN_XLIT_ENGLISH: return "GREGORIAN_XLIT_ENGLISH";
    		case GREGORIAN_XLIT_FRENCH: return "GREGORIAN_XLIT_FRENCH";
    		case HEBREW: return "HEBREW";
    		case HIJRI: return "HIJRI";
    		case JAPAN: return "JAPAN";
    		case KOREA: return "KOREA";
    		case NONE: return "NONE";
    		case SAKA: return "SAKA";
    		case TAIWAN: return "TAIWAN";
    		case THAI: return "THAI";
    		default: return "Unknown SdtCalendarType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer SdtCalendarType value.
    /// </summary>
    public static String toString(/*SdtCalendarType*/int sdtCalendarType)
    {
    	switch (sdtCalendarType)
    	{
    		case DEFAULT:
    		/*case GREGORIAN:*/
    			return "Default" + " | " + "Gregorian";
    		case GREGORIAN_ARABIC: return "GregorianArabic";
    		case GREGORIAN_ME_FRENCH: return "GregorianMeFrench";
    		case GREGORIAN_US: return "GregorianUs";
    		case GREGORIAN_XLIT_ENGLISH: return "GregorianXlitEnglish";
    		case GREGORIAN_XLIT_FRENCH: return "GregorianXlitFrench";
    		case HEBREW: return "Hebrew";
    		case HIJRI: return "Hijri";
    		case JAPAN: return "Japan";
    		case KOREA: return "Korea";
    		case NONE: return "None";
    		case SAKA: return "Saka";
    		case TAIWAN: return "Taiwan";
    		case THAI: return "Thai";
    		default: return "Unknown SdtCalendarType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by SdtCalendarType name.
    /// </summary>
    public static /*SdtCalendarType*/int fromName(String sdtCalendarTypeName)
    {
    	if ("DEFAULT".equals(sdtCalendarTypeName)) return DEFAULT;
    	if ("GREGORIAN".equals(sdtCalendarTypeName)) return GREGORIAN;
    	if ("GREGORIAN_ARABIC".equals(sdtCalendarTypeName)) return GREGORIAN_ARABIC;
    	if ("GREGORIAN_ME_FRENCH".equals(sdtCalendarTypeName)) return GREGORIAN_ME_FRENCH;
    	if ("GREGORIAN_US".equals(sdtCalendarTypeName)) return GREGORIAN_US;
    	if ("GREGORIAN_XLIT_ENGLISH".equals(sdtCalendarTypeName)) return GREGORIAN_XLIT_ENGLISH;
    	if ("GREGORIAN_XLIT_FRENCH".equals(sdtCalendarTypeName)) return GREGORIAN_XLIT_FRENCH;
    	if ("HEBREW".equals(sdtCalendarTypeName)) return HEBREW;
    	if ("HIJRI".equals(sdtCalendarTypeName)) return HIJRI;
    	if ("JAPAN".equals(sdtCalendarTypeName)) return JAPAN;
    	if ("KOREA".equals(sdtCalendarTypeName)) return KOREA;
    	if ("NONE".equals(sdtCalendarTypeName)) return NONE;
    	if ("SAKA".equals(sdtCalendarTypeName)) return SAKA;
    	if ("TAIWAN".equals(sdtCalendarTypeName)) return TAIWAN;
    	if ("THAI".equals(sdtCalendarTypeName)) return THAI;
    	throw new IllegalArgumentException("Unknown SdtCalendarType name.");
    }
    
    /// <summary>
    /// Returns array of SdtCalendarType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		GREGORIAN,
    		GREGORIAN_ARABIC,
    		GREGORIAN_ME_FRENCH,
    		GREGORIAN_US,
    		GREGORIAN_XLIT_ENGLISH,
    		GREGORIAN_XLIT_FRENCH,
    		HEBREW,
    		HIJRI,
    		JAPAN,
    		KOREA,
    		NONE,
    		SAKA,
    		TAIWAN,
    		THAI,
    	};
    }

    public static final int length = 15;
}

