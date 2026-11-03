// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 17/05/2023 by Nikolay Sezganov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents type of font file format.
/// </summary>
/// <remarks>
/// See https://www.w3.org/TR/css-fonts-3/#fontformats
/// </remarks>
/*enum*/ final class CssFontFaceFormat
{
    private CssFontFaceFormat(){}
    
    public static final int UNKNOWN = 0;
    public static final int COLLECTION = 1;
    public static final int EMBEDDED_OPENTYPE = 2;
    public static final int OPEN_TYPE = 3;
    public static final int SVG = 4;
    public static final int TRUE_TYPE = 5;
    public static final int WOFF = 6;
    public static final int WOFF_2 = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssFontFaceFormat value.
    /// </summary>
    public static String getName(/*CssFontFaceFormat*/int cssFontFaceFormat)
    {
    	switch (cssFontFaceFormat)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case COLLECTION: return "COLLECTION";
    		case EMBEDDED_OPENTYPE: return "EMBEDDED_OPENTYPE";
    		case OPEN_TYPE: return "OPEN_TYPE";
    		case SVG: return "SVG";
    		case TRUE_TYPE: return "TRUE_TYPE";
    		case WOFF: return "WOFF";
    		case WOFF_2: return "WOFF_2";
    		default: return "Unknown CssFontFaceFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssFontFaceFormat value.
    /// </summary>
    public static String toString(/*CssFontFaceFormat*/int cssFontFaceFormat)
    {
    	switch (cssFontFaceFormat)
    	{
    		case UNKNOWN: return "Unknown";
    		case COLLECTION: return "Collection";
    		case EMBEDDED_OPENTYPE: return "EmbeddedOpentype";
    		case OPEN_TYPE: return "OpenType";
    		case SVG: return "Svg";
    		case TRUE_TYPE: return "TrueType";
    		case WOFF: return "Woff";
    		case WOFF_2: return "Woff2";
    		default: return "Unknown CssFontFaceFormat value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssFontFaceFormat name.
    /// </summary>
    public static /*CssFontFaceFormat*/int fromName(String cssFontFaceFormatName)
    {
    	if ("UNKNOWN".equals(cssFontFaceFormatName)) return UNKNOWN;
    	if ("COLLECTION".equals(cssFontFaceFormatName)) return COLLECTION;
    	if ("EMBEDDED_OPENTYPE".equals(cssFontFaceFormatName)) return EMBEDDED_OPENTYPE;
    	if ("OPEN_TYPE".equals(cssFontFaceFormatName)) return OPEN_TYPE;
    	if ("SVG".equals(cssFontFaceFormatName)) return SVG;
    	if ("TRUE_TYPE".equals(cssFontFaceFormatName)) return TRUE_TYPE;
    	if ("WOFF".equals(cssFontFaceFormatName)) return WOFF;
    	if ("WOFF_2".equals(cssFontFaceFormatName)) return WOFF_2;
    	throw new IllegalArgumentException("Unknown CssFontFaceFormat name.");
    }
    
    /// <summary>
    /// Returns array of CssFontFaceFormat values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		COLLECTION,
    		EMBEDDED_OPENTYPE,
    		OPEN_TYPE,
    		SVG,
    		TRUE_TYPE,
    		WOFF,
    		WOFF_2,
    	};
    }

    public static final int length = 8;
}

