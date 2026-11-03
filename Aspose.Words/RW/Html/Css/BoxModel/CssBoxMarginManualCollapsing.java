// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 21/01/2015 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Collapsing of two adjacent CSS box margins performed by AW itself (not by MS Word).
/// </summary>
/// <remarks>
/// In certain situations (for example, around floating tables) MS Word's margin collapsing rules differ from CSS rules,
/// and AW has to manually collapse margins (recalculate their values) to make sure the result looks as specified by CSS. 
/// </remarks>
/*enum*/ final class CssBoxMarginManualCollapsing
{
    private CssBoxMarginManualCollapsing(){}
    
    /// <summary>
    /// Margins do not collapse (are separate from each other).
    /// </summary>
    public static final int SEPARATE = 0;
    /// <summary>
    /// Margins do collapse. The resulting margin is equal to the maximum of the two margins.
    /// </summary>
    public static final int COLLAPSE = 1;
    /// <summary>
    /// Margins do collapse. The resulting margin is zero.
    /// </summary>
    public static final int ZERO = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssBoxMarginManualCollapsing value.
    /// </summary>
    public static String getName(/*CssBoxMarginManualCollapsing*/int cssBoxMarginManualCollapsing)
    {
    	switch (cssBoxMarginManualCollapsing)
    	{
    		case SEPARATE: return "SEPARATE";
    		case COLLAPSE: return "COLLAPSE";
    		case ZERO: return "ZERO";
    		default: return "Unknown CssBoxMarginManualCollapsing value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssBoxMarginManualCollapsing value.
    /// </summary>
    public static String toString(/*CssBoxMarginManualCollapsing*/int cssBoxMarginManualCollapsing)
    {
    	switch (cssBoxMarginManualCollapsing)
    	{
    		case SEPARATE: return "Separate";
    		case COLLAPSE: return "Collapse";
    		case ZERO: return "Zero";
    		default: return "Unknown CssBoxMarginManualCollapsing value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssBoxMarginManualCollapsing name.
    /// </summary>
    public static /*CssBoxMarginManualCollapsing*/int fromName(String cssBoxMarginManualCollapsingName)
    {
    	if ("SEPARATE".equals(cssBoxMarginManualCollapsingName)) return SEPARATE;
    	if ("COLLAPSE".equals(cssBoxMarginManualCollapsingName)) return COLLAPSE;
    	if ("ZERO".equals(cssBoxMarginManualCollapsingName)) return ZERO;
    	throw new IllegalArgumentException("Unknown CssBoxMarginManualCollapsing name.");
    }
    
    /// <summary>
    /// Returns array of CssBoxMarginManualCollapsing values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SEPARATE,
    		COLLAPSE,
    		ZERO,
    	};
    }

    public static final int length = 3;
}

