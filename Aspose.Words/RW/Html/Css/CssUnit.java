// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/09/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the type of a CSS dimension property (a number with units).
/// At the moment fully supports only absolute length units.
/// Relative units are needed to output relative font size for instance.
/// And restricted support is added for them.
/// 
/// Don't rearrange this as there is a related table with conversion factors.
/// </summary>
/*enum*/ final class CssUnit
{
    private CssUnit(){}
    
    public static final int NONE = 0;
    /// <summary>
    /// Absolute length. Inches.
    /// </summary>
    public static final int IN = 1;
    /// <summary>
    /// Absolute length. Centimeters.
    /// </summary>
    public static final int CM = 2;
    /// <summary>
    /// Absoute length. Millimeters.
    /// </summary>
    public static final int MM = 3;
    /// <summary>
    /// Absolute length. Points.
    /// </summary>
    public static final int PT = 4;
    /// <summary>
    /// Absolute length. Picas. 1pc = 12pt
    /// </summary>
    public static final int PC = 5;
    /// <summary>
    /// CSS says its relative length, but I treat this as absolute length 96dpi. 
    /// Really it should be recalculated according to screen resolution or any given 
    /// resolution on import. It's another candidate to "LoadOptions".
    /// </summary>
    public static final int PX = 6;
    /// <summary>
    /// Relative length based on the current font size.
    /// </summary>
    public static final int EM = 7;
    /// <summary>
    /// Relative length based on the so-named 'x-height' of the current font.
    /// </summary>
    public static final int EX = 8;
    /// <summary>
    /// Relative length based on the root element font size. In an HTML document, the root element is the html element.
    /// </summary>
    public static final int REM = 9;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssUnit value.
    /// </summary>
    public static String getName(/*CssUnit*/int cssUnit)
    {
    	switch (cssUnit)
    	{
    		case NONE: return "NONE";
    		case IN: return "IN";
    		case CM: return "CM";
    		case MM: return "MM";
    		case PT: return "PT";
    		case PC: return "PC";
    		case PX: return "PX";
    		case EM: return "EM";
    		case EX: return "EX";
    		case REM: return "REM";
    		default: return "Unknown CssUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssUnit value.
    /// </summary>
    public static String toString(/*CssUnit*/int cssUnit)
    {
    	switch (cssUnit)
    	{
    		case NONE: return "None";
    		case IN: return "In";
    		case CM: return "Cm";
    		case MM: return "Mm";
    		case PT: return "Pt";
    		case PC: return "Pc";
    		case PX: return "Px";
    		case EM: return "Em";
    		case EX: return "Ex";
    		case REM: return "Rem";
    		default: return "Unknown CssUnit value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssUnit name.
    /// </summary>
    public static /*CssUnit*/int fromName(String cssUnitName)
    {
    	if ("NONE".equals(cssUnitName)) return NONE;
    	if ("IN".equals(cssUnitName)) return IN;
    	if ("CM".equals(cssUnitName)) return CM;
    	if ("MM".equals(cssUnitName)) return MM;
    	if ("PT".equals(cssUnitName)) return PT;
    	if ("PC".equals(cssUnitName)) return PC;
    	if ("PX".equals(cssUnitName)) return PX;
    	if ("EM".equals(cssUnitName)) return EM;
    	if ("EX".equals(cssUnitName)) return EX;
    	if ("REM".equals(cssUnitName)) return REM;
    	throw new IllegalArgumentException("Unknown CssUnit name.");
    }
    
    /// <summary>
    /// Returns array of CssUnit values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		IN,
    		CM,
    		MM,
    		PT,
    		PC,
    		PX,
    		EM,
    		EX,
    		REM,
    	};
    }

    public static final int length = 10;
}

