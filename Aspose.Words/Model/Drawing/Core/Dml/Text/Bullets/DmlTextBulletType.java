// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/27/2015 by Alexey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents ST_TextAutonumberScheme (Text Auto-number Schemes)
/// This type specifies a list of automatic numbering schemes.
/// </summary>
/*enum*/ final class DmlTextBulletType
{
    private DmlTextBulletType(){}
    
    /// <summary>
    /// Specifies that no bullet must be applied to the paragraph.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// Specifies that picture bullet must be applied to the paragraph.
    /// </summary>
    public static final int PICTURE = 1;
    /// <summary>
    /// Specifies that character bullet must be applied to the paragraph.
    /// </summary>
    public static final int CHAR = 2;
    public static final int ALPHA_LC_PAREN_BOTH = 3;
    public static final int ALPHA_UC_PAREN_BOTH = 4;
    public static final int ALPHA_LC_PAREN_R = 5;
    public static final int ALPHA_UC_PAREN_R = 6;
    public static final int ALPHA_LC_PERIOD = 7;
    public static final int ALPHA_UC_PERIOD = 8;
    public static final int ARABIC_PAREN_BOTH = 9;
    public static final int ARABIC_PAREN_R = 10;
    public static final int ARABIC_PERIOD = 11;
    public static final int ARABIC_PLAIN = 12;
    public static final int ROMAN_LC_PAREN_BOTH = 13;
    public static final int ROMAN_UC_PAREN_BOTH = 14;
    public static final int ROMAN_LC_PAREN_R = 15;
    public static final int ROMAN_UC_PAREN_R = 16;
    public static final int ROMAN_LC_PERIOD = 17;
    public static final int ROMAN_UC_PERIOD = 18;
    public static final int CIRCLE_NUM_DB_PLAIN = 19;
    public static final int CIRCLE_NUM_WD_BLACK_PLAIN = 20;
    public static final int CIRCLE_NUM_WD_WHITE_PLAIN = 21;
    public static final int ARABIC_DB_PERIOD = 22;
    public static final int ARABIC_DB_PLAIN = 23;
    public static final int EA_1_CHS_PERIOD = 24;
    public static final int EA_1_CHS_PLAIN = 25;
    public static final int EA_1_CHT_PERIOD = 26;
    public static final int EA_1_CHT_PLAIN = 27;
    public static final int EA_1_JPN_CHS_DB_PERIOD = 28;
    public static final int EA_1_JPN_KOR_PLAIN = 29;
    public static final int EA_1_JPN_KOR_PERIOD = 30;
    public static final int ARABIC_1_MINUS = 31;
    public static final int ARABIC_2_MINUS = 32;
    public static final int HEBREW_2_MINUS = 33;
    public static final int THAI_ALPHA_PERIOD = 34;
    public static final int THAI_ALPHA_PAREN_R = 35;
    public static final int THAI_ALPHA_PAREN_BOTH = 36;
    public static final int THAI_NUM_PERIOD = 37;
    public static final int THAI_NUM_PAREN_R = 38;
    public static final int THAI_NUM_PAREN_BOTH = 39;
    public static final int HINDI_ALPHA_PERIOD = 40;
    public static final int HINDI_NUM_PERIOD = 41;
    public static final int HINDI_NUM_PAREN_R = 42;
    public static final int HINDI_ALPHA_1_PERIOD = 43;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextBulletType value.
    /// </summary>
    public static String getName(/*DmlTextBulletType*/int dmlTextBulletType)
    {
    	switch (dmlTextBulletType)
    	{
    		case NONE: return "NONE";
    		case PICTURE: return "PICTURE";
    		case CHAR: return "CHAR";
    		case ALPHA_LC_PAREN_BOTH: return "ALPHA_LC_PAREN_BOTH";
    		case ALPHA_UC_PAREN_BOTH: return "ALPHA_UC_PAREN_BOTH";
    		case ALPHA_LC_PAREN_R: return "ALPHA_LC_PAREN_R";
    		case ALPHA_UC_PAREN_R: return "ALPHA_UC_PAREN_R";
    		case ALPHA_LC_PERIOD: return "ALPHA_LC_PERIOD";
    		case ALPHA_UC_PERIOD: return "ALPHA_UC_PERIOD";
    		case ARABIC_PAREN_BOTH: return "ARABIC_PAREN_BOTH";
    		case ARABIC_PAREN_R: return "ARABIC_PAREN_R";
    		case ARABIC_PERIOD: return "ARABIC_PERIOD";
    		case ARABIC_PLAIN: return "ARABIC_PLAIN";
    		case ROMAN_LC_PAREN_BOTH: return "ROMAN_LC_PAREN_BOTH";
    		case ROMAN_UC_PAREN_BOTH: return "ROMAN_UC_PAREN_BOTH";
    		case ROMAN_LC_PAREN_R: return "ROMAN_LC_PAREN_R";
    		case ROMAN_UC_PAREN_R: return "ROMAN_UC_PAREN_R";
    		case ROMAN_LC_PERIOD: return "ROMAN_LC_PERIOD";
    		case ROMAN_UC_PERIOD: return "ROMAN_UC_PERIOD";
    		case CIRCLE_NUM_DB_PLAIN: return "CIRCLE_NUM_DB_PLAIN";
    		case CIRCLE_NUM_WD_BLACK_PLAIN: return "CIRCLE_NUM_WD_BLACK_PLAIN";
    		case CIRCLE_NUM_WD_WHITE_PLAIN: return "CIRCLE_NUM_WD_WHITE_PLAIN";
    		case ARABIC_DB_PERIOD: return "ARABIC_DB_PERIOD";
    		case ARABIC_DB_PLAIN: return "ARABIC_DB_PLAIN";
    		case EA_1_CHS_PERIOD: return "EA_1_CHS_PERIOD";
    		case EA_1_CHS_PLAIN: return "EA_1_CHS_PLAIN";
    		case EA_1_CHT_PERIOD: return "EA_1_CHT_PERIOD";
    		case EA_1_CHT_PLAIN: return "EA_1_CHT_PLAIN";
    		case EA_1_JPN_CHS_DB_PERIOD: return "EA_1_JPN_CHS_DB_PERIOD";
    		case EA_1_JPN_KOR_PLAIN: return "EA_1_JPN_KOR_PLAIN";
    		case EA_1_JPN_KOR_PERIOD: return "EA_1_JPN_KOR_PERIOD";
    		case ARABIC_1_MINUS: return "ARABIC_1_MINUS";
    		case ARABIC_2_MINUS: return "ARABIC_2_MINUS";
    		case HEBREW_2_MINUS: return "HEBREW_2_MINUS";
    		case THAI_ALPHA_PERIOD: return "THAI_ALPHA_PERIOD";
    		case THAI_ALPHA_PAREN_R: return "THAI_ALPHA_PAREN_R";
    		case THAI_ALPHA_PAREN_BOTH: return "THAI_ALPHA_PAREN_BOTH";
    		case THAI_NUM_PERIOD: return "THAI_NUM_PERIOD";
    		case THAI_NUM_PAREN_R: return "THAI_NUM_PAREN_R";
    		case THAI_NUM_PAREN_BOTH: return "THAI_NUM_PAREN_BOTH";
    		case HINDI_ALPHA_PERIOD: return "HINDI_ALPHA_PERIOD";
    		case HINDI_NUM_PERIOD: return "HINDI_NUM_PERIOD";
    		case HINDI_NUM_PAREN_R: return "HINDI_NUM_PAREN_R";
    		case HINDI_ALPHA_1_PERIOD: return "HINDI_ALPHA_1_PERIOD";
    		default: return "Unknown DmlTextBulletType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextBulletType value.
    /// </summary>
    public static String toString(/*DmlTextBulletType*/int dmlTextBulletType)
    {
    	switch (dmlTextBulletType)
    	{
    		case NONE: return "None";
    		case PICTURE: return "Picture";
    		case CHAR: return "Char";
    		case ALPHA_LC_PAREN_BOTH: return "AlphaLcParenBoth";
    		case ALPHA_UC_PAREN_BOTH: return "AlphaUcParenBoth";
    		case ALPHA_LC_PAREN_R: return "AlphaLcParenR";
    		case ALPHA_UC_PAREN_R: return "AlphaUcParenR";
    		case ALPHA_LC_PERIOD: return "AlphaLcPeriod";
    		case ALPHA_UC_PERIOD: return "AlphaUcPeriod";
    		case ARABIC_PAREN_BOTH: return "ArabicParenBoth";
    		case ARABIC_PAREN_R: return "ArabicParenR";
    		case ARABIC_PERIOD: return "ArabicPeriod";
    		case ARABIC_PLAIN: return "ArabicPlain";
    		case ROMAN_LC_PAREN_BOTH: return "RomanLcParenBoth";
    		case ROMAN_UC_PAREN_BOTH: return "RomanUcParenBoth";
    		case ROMAN_LC_PAREN_R: return "RomanLcParenR";
    		case ROMAN_UC_PAREN_R: return "RomanUcParenR";
    		case ROMAN_LC_PERIOD: return "RomanLcPeriod";
    		case ROMAN_UC_PERIOD: return "RomanUcPeriod";
    		case CIRCLE_NUM_DB_PLAIN: return "CircleNumDbPlain";
    		case CIRCLE_NUM_WD_BLACK_PLAIN: return "CircleNumWdBlackPlain";
    		case CIRCLE_NUM_WD_WHITE_PLAIN: return "CircleNumWdWhitePlain";
    		case ARABIC_DB_PERIOD: return "ArabicDbPeriod";
    		case ARABIC_DB_PLAIN: return "ArabicDbPlain";
    		case EA_1_CHS_PERIOD: return "Ea1ChsPeriod";
    		case EA_1_CHS_PLAIN: return "Ea1ChsPlain";
    		case EA_1_CHT_PERIOD: return "Ea1ChtPeriod";
    		case EA_1_CHT_PLAIN: return "Ea1ChtPlain";
    		case EA_1_JPN_CHS_DB_PERIOD: return "Ea1JpnChsDbPeriod";
    		case EA_1_JPN_KOR_PLAIN: return "Ea1JpnKorPlain";
    		case EA_1_JPN_KOR_PERIOD: return "Ea1JpnKorPeriod";
    		case ARABIC_1_MINUS: return "Arabic1Minus";
    		case ARABIC_2_MINUS: return "Arabic2Minus";
    		case HEBREW_2_MINUS: return "Hebrew2Minus";
    		case THAI_ALPHA_PERIOD: return "ThaiAlphaPeriod";
    		case THAI_ALPHA_PAREN_R: return "ThaiAlphaParenR";
    		case THAI_ALPHA_PAREN_BOTH: return "ThaiAlphaParenBoth";
    		case THAI_NUM_PERIOD: return "ThaiNumPeriod";
    		case THAI_NUM_PAREN_R: return "ThaiNumParenR";
    		case THAI_NUM_PAREN_BOTH: return "ThaiNumParenBoth";
    		case HINDI_ALPHA_PERIOD: return "HindiAlphaPeriod";
    		case HINDI_NUM_PERIOD: return "HindiNumPeriod";
    		case HINDI_NUM_PAREN_R: return "HindiNumParenR";
    		case HINDI_ALPHA_1_PERIOD: return "HindiAlpha1Period";
    		default: return "Unknown DmlTextBulletType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextBulletType name.
    /// </summary>
    public static /*DmlTextBulletType*/int fromName(String dmlTextBulletTypeName)
    {
    	if ("NONE".equals(dmlTextBulletTypeName)) return NONE;
    	if ("PICTURE".equals(dmlTextBulletTypeName)) return PICTURE;
    	if ("CHAR".equals(dmlTextBulletTypeName)) return CHAR;
    	if ("ALPHA_LC_PAREN_BOTH".equals(dmlTextBulletTypeName)) return ALPHA_LC_PAREN_BOTH;
    	if ("ALPHA_UC_PAREN_BOTH".equals(dmlTextBulletTypeName)) return ALPHA_UC_PAREN_BOTH;
    	if ("ALPHA_LC_PAREN_R".equals(dmlTextBulletTypeName)) return ALPHA_LC_PAREN_R;
    	if ("ALPHA_UC_PAREN_R".equals(dmlTextBulletTypeName)) return ALPHA_UC_PAREN_R;
    	if ("ALPHA_LC_PERIOD".equals(dmlTextBulletTypeName)) return ALPHA_LC_PERIOD;
    	if ("ALPHA_UC_PERIOD".equals(dmlTextBulletTypeName)) return ALPHA_UC_PERIOD;
    	if ("ARABIC_PAREN_BOTH".equals(dmlTextBulletTypeName)) return ARABIC_PAREN_BOTH;
    	if ("ARABIC_PAREN_R".equals(dmlTextBulletTypeName)) return ARABIC_PAREN_R;
    	if ("ARABIC_PERIOD".equals(dmlTextBulletTypeName)) return ARABIC_PERIOD;
    	if ("ARABIC_PLAIN".equals(dmlTextBulletTypeName)) return ARABIC_PLAIN;
    	if ("ROMAN_LC_PAREN_BOTH".equals(dmlTextBulletTypeName)) return ROMAN_LC_PAREN_BOTH;
    	if ("ROMAN_UC_PAREN_BOTH".equals(dmlTextBulletTypeName)) return ROMAN_UC_PAREN_BOTH;
    	if ("ROMAN_LC_PAREN_R".equals(dmlTextBulletTypeName)) return ROMAN_LC_PAREN_R;
    	if ("ROMAN_UC_PAREN_R".equals(dmlTextBulletTypeName)) return ROMAN_UC_PAREN_R;
    	if ("ROMAN_LC_PERIOD".equals(dmlTextBulletTypeName)) return ROMAN_LC_PERIOD;
    	if ("ROMAN_UC_PERIOD".equals(dmlTextBulletTypeName)) return ROMAN_UC_PERIOD;
    	if ("CIRCLE_NUM_DB_PLAIN".equals(dmlTextBulletTypeName)) return CIRCLE_NUM_DB_PLAIN;
    	if ("CIRCLE_NUM_WD_BLACK_PLAIN".equals(dmlTextBulletTypeName)) return CIRCLE_NUM_WD_BLACK_PLAIN;
    	if ("CIRCLE_NUM_WD_WHITE_PLAIN".equals(dmlTextBulletTypeName)) return CIRCLE_NUM_WD_WHITE_PLAIN;
    	if ("ARABIC_DB_PERIOD".equals(dmlTextBulletTypeName)) return ARABIC_DB_PERIOD;
    	if ("ARABIC_DB_PLAIN".equals(dmlTextBulletTypeName)) return ARABIC_DB_PLAIN;
    	if ("EA_1_CHS_PERIOD".equals(dmlTextBulletTypeName)) return EA_1_CHS_PERIOD;
    	if ("EA_1_CHS_PLAIN".equals(dmlTextBulletTypeName)) return EA_1_CHS_PLAIN;
    	if ("EA_1_CHT_PERIOD".equals(dmlTextBulletTypeName)) return EA_1_CHT_PERIOD;
    	if ("EA_1_CHT_PLAIN".equals(dmlTextBulletTypeName)) return EA_1_CHT_PLAIN;
    	if ("EA_1_JPN_CHS_DB_PERIOD".equals(dmlTextBulletTypeName)) return EA_1_JPN_CHS_DB_PERIOD;
    	if ("EA_1_JPN_KOR_PLAIN".equals(dmlTextBulletTypeName)) return EA_1_JPN_KOR_PLAIN;
    	if ("EA_1_JPN_KOR_PERIOD".equals(dmlTextBulletTypeName)) return EA_1_JPN_KOR_PERIOD;
    	if ("ARABIC_1_MINUS".equals(dmlTextBulletTypeName)) return ARABIC_1_MINUS;
    	if ("ARABIC_2_MINUS".equals(dmlTextBulletTypeName)) return ARABIC_2_MINUS;
    	if ("HEBREW_2_MINUS".equals(dmlTextBulletTypeName)) return HEBREW_2_MINUS;
    	if ("THAI_ALPHA_PERIOD".equals(dmlTextBulletTypeName)) return THAI_ALPHA_PERIOD;
    	if ("THAI_ALPHA_PAREN_R".equals(dmlTextBulletTypeName)) return THAI_ALPHA_PAREN_R;
    	if ("THAI_ALPHA_PAREN_BOTH".equals(dmlTextBulletTypeName)) return THAI_ALPHA_PAREN_BOTH;
    	if ("THAI_NUM_PERIOD".equals(dmlTextBulletTypeName)) return THAI_NUM_PERIOD;
    	if ("THAI_NUM_PAREN_R".equals(dmlTextBulletTypeName)) return THAI_NUM_PAREN_R;
    	if ("THAI_NUM_PAREN_BOTH".equals(dmlTextBulletTypeName)) return THAI_NUM_PAREN_BOTH;
    	if ("HINDI_ALPHA_PERIOD".equals(dmlTextBulletTypeName)) return HINDI_ALPHA_PERIOD;
    	if ("HINDI_NUM_PERIOD".equals(dmlTextBulletTypeName)) return HINDI_NUM_PERIOD;
    	if ("HINDI_NUM_PAREN_R".equals(dmlTextBulletTypeName)) return HINDI_NUM_PAREN_R;
    	if ("HINDI_ALPHA_1_PERIOD".equals(dmlTextBulletTypeName)) return HINDI_ALPHA_1_PERIOD;
    	throw new IllegalArgumentException("Unknown DmlTextBulletType name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextBulletType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		PICTURE,
    		CHAR,
    		ALPHA_LC_PAREN_BOTH,
    		ALPHA_UC_PAREN_BOTH,
    		ALPHA_LC_PAREN_R,
    		ALPHA_UC_PAREN_R,
    		ALPHA_LC_PERIOD,
    		ALPHA_UC_PERIOD,
    		ARABIC_PAREN_BOTH,
    		ARABIC_PAREN_R,
    		ARABIC_PERIOD,
    		ARABIC_PLAIN,
    		ROMAN_LC_PAREN_BOTH,
    		ROMAN_UC_PAREN_BOTH,
    		ROMAN_LC_PAREN_R,
    		ROMAN_UC_PAREN_R,
    		ROMAN_LC_PERIOD,
    		ROMAN_UC_PERIOD,
    		CIRCLE_NUM_DB_PLAIN,
    		CIRCLE_NUM_WD_BLACK_PLAIN,
    		CIRCLE_NUM_WD_WHITE_PLAIN,
    		ARABIC_DB_PERIOD,
    		ARABIC_DB_PLAIN,
    		EA_1_CHS_PERIOD,
    		EA_1_CHS_PLAIN,
    		EA_1_CHT_PERIOD,
    		EA_1_CHT_PLAIN,
    		EA_1_JPN_CHS_DB_PERIOD,
    		EA_1_JPN_KOR_PLAIN,
    		EA_1_JPN_KOR_PERIOD,
    		ARABIC_1_MINUS,
    		ARABIC_2_MINUS,
    		HEBREW_2_MINUS,
    		THAI_ALPHA_PERIOD,
    		THAI_ALPHA_PAREN_R,
    		THAI_ALPHA_PAREN_BOTH,
    		THAI_NUM_PERIOD,
    		THAI_NUM_PAREN_R,
    		THAI_NUM_PAREN_BOTH,
    		HINDI_ALPHA_PERIOD,
    		HINDI_NUM_PERIOD,
    		HINDI_NUM_PAREN_R,
    		HINDI_ALPHA_1_PERIOD,
    	};
    }

    public static final int length = 44;
}

