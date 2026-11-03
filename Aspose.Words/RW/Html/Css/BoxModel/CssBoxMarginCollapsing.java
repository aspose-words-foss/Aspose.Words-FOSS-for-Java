// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 20/10/2013 by Victor Chebotok

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Collapsing behavior of two CSS box margin values.
/// </summary>
/*enum*/ final class CssBoxMarginCollapsing
{
    private CssBoxMarginCollapsing(){}
    
    /// <summary>
    /// One margin value is added to the other value.
    /// </summary>
    public static final int ADD = 0;

    /// <summary>
    /// One margin value is collapsed with the other value.
    /// </summary>
    public static final int COLLAPSE = 1;

    /// <summary>
    /// One margin value is used and the other is ignored.
    /// </summary>
    public static final int SEPARATE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CssBoxMarginCollapsing value.
    /// </summary>
    public static String getName(/*CssBoxMarginCollapsing*/int cssBoxMarginCollapsing)
    {
    	switch (cssBoxMarginCollapsing)
    	{
    		case ADD: return "ADD";
    		case COLLAPSE: return "COLLAPSE";
    		case SEPARATE: return "SEPARATE";
    		default: return "Unknown CssBoxMarginCollapsing value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CssBoxMarginCollapsing value.
    /// </summary>
    public static String toString(/*CssBoxMarginCollapsing*/int cssBoxMarginCollapsing)
    {
    	switch (cssBoxMarginCollapsing)
    	{
    		case ADD: return "Add";
    		case COLLAPSE: return "Collapse";
    		case SEPARATE: return "Separate";
    		default: return "Unknown CssBoxMarginCollapsing value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CssBoxMarginCollapsing name.
    /// </summary>
    public static /*CssBoxMarginCollapsing*/int fromName(String cssBoxMarginCollapsingName)
    {
    	if ("ADD".equals(cssBoxMarginCollapsingName)) return ADD;
    	if ("COLLAPSE".equals(cssBoxMarginCollapsingName)) return COLLAPSE;
    	if ("SEPARATE".equals(cssBoxMarginCollapsingName)) return SEPARATE;
    	throw new IllegalArgumentException("Unknown CssBoxMarginCollapsing name.");
    }
    
    /// <summary>
    /// Returns array of CssBoxMarginCollapsing values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ADD,
    		COLLAPSE,
    		SEPARATE,
    	};
    }

    public static final int length = 3;
}

