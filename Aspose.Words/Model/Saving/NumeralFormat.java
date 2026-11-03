// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 04/08/2012 by Denis Shvydkiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Indicates the symbol set that is used to represent numbers
/// while rendering to fixed page formats.
/// </summary>
public /*enum*/ final class NumeralFormat
{
    private NumeralFormat(){}
    
    /// <summary>
    /// European numerals: 0123456789.
    /// </summary>
    public static final int EUROPEAN = 0;
    /// <summary>
    /// Numerals used in Arabic: ٠١٢٣٤٥٦٧٨٩.
    /// Unicode range U+0660 - u+0669.
    /// </summary>
    public static final int ARABIC_INDIC = 1;
    /// <summary>
    /// Numerals used in Persian and Urdu: ۰۱۲۳۴۵۶۷۸۹.
    /// Unicode range U+06F0 - u+06F9.
    /// </summary>
    public static final int EASTERN_ARABIC_INDIC = 2;
    /// <summary>
    /// Symbol set is decided from context(locale and RTL property).
    /// </summary>
    public static final int CONTEXT = 3;
    /// <summary>
    /// THIS OPTION IS NOT SUPPORTED.
    /// Symbol set is decided from regional settings.
    /// </summary>
    public static final int SYSTEM = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer NumeralFormat value.
    /// </summary>
    public static String getName(/*NumeralFormat*/int numeralFormat)
    {
    	switch (numeralFormat)
    	{
    		case EUROPEAN: return "EUROPEAN";
    		case ARABIC_INDIC: return "ARABIC_INDIC";
    		case EASTERN_ARABIC_INDIC: return "EASTERN_ARABIC_INDIC";
    		case CONTEXT: return "CONTEXT";
    		case SYSTEM: return "SYSTEM";
    		default: return "Unknown NumeralFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer NumeralFormat value.
    /// </summary>
    public static String toString(/*NumeralFormat*/int numeralFormat)
    {
    	switch (numeralFormat)
    	{
    		case EUROPEAN: return "European";
    		case ARABIC_INDIC: return "ArabicIndic";
    		case EASTERN_ARABIC_INDIC: return "EasternArabicIndic";
    		case CONTEXT: return "Context";
    		case SYSTEM: return "System";
    		default: return "Unknown NumeralFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by NumeralFormat name.
    /// </summary>
    public static /*NumeralFormat*/int fromName(String numeralFormatName)
    {
    	if ("EUROPEAN".equals(numeralFormatName)) return EUROPEAN;
    	if ("ARABIC_INDIC".equals(numeralFormatName)) return ARABIC_INDIC;
    	if ("EASTERN_ARABIC_INDIC".equals(numeralFormatName)) return EASTERN_ARABIC_INDIC;
    	if ("CONTEXT".equals(numeralFormatName)) return CONTEXT;
    	if ("SYSTEM".equals(numeralFormatName)) return SYSTEM;
    	throw new IllegalArgumentException("Unknown NumeralFormat name.");
    }
    
    /// <summary>
    /// Returns array of NumeralFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EUROPEAN,
    		ARABIC_INDIC,
    		EASTERN_ARABIC_INDIC,
    		CONTEXT,
    		SYSTEM,
    	};
    }

    public static final int length = 5;
}

