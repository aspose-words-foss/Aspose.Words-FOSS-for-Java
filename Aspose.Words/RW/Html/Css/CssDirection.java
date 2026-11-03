// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/04/2014 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Text direction of an HTML element.
/// </summary>
/*enum*/ final class CssDirection
{
    private CssDirection(){}
    
    /// <summary>
    /// Text direction is not specified in CSS declarations of the element.
    /// </summary>
    public static final int UNSPECIFIED = 0;

    /// <summary>
    /// Text direction of the element is left-to-right.
    /// </summary>
    public static final int LTR = 1;

    /// <summary>
    /// Text direction of the element is right-to-left.
    /// </summary>
    public static final int RTL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssDirection value.
    /// </summary>
    public static String getName(/*CssDirection*/int cssDirection)
    {
    	switch (cssDirection)
    	{
    		case UNSPECIFIED: return "UNSPECIFIED";
    		case LTR: return "LTR";
    		case RTL: return "RTL";
    		default: return "Unknown CssDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssDirection value.
    /// </summary>
    public static String toString(/*CssDirection*/int cssDirection)
    {
    	switch (cssDirection)
    	{
    		case UNSPECIFIED: return "Unspecified";
    		case LTR: return "Ltr";
    		case RTL: return "Rtl";
    		default: return "Unknown CssDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssDirection name.
    /// </summary>
    public static /*CssDirection*/int fromName(String cssDirectionName)
    {
    	if ("UNSPECIFIED".equals(cssDirectionName)) return UNSPECIFIED;
    	if ("LTR".equals(cssDirectionName)) return LTR;
    	if ("RTL".equals(cssDirectionName)) return RTL;
    	throw new IllegalArgumentException("Unknown CssDirection name.");
    }
    
    /// <summary>
    /// Returns array of CssDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNSPECIFIED,
    		LTR,
    		RTL,
    	};
    }

    public static final int length = 3;
}

