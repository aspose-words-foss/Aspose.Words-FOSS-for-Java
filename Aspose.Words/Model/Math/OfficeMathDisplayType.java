// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 27/01/2017 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the display format type of the equation.
/// </summary>
public /*enum*/ final class OfficeMathDisplayType
{
    private OfficeMathDisplayType(){}
    
    /// <summary>
    /// The Office Math is displayed on its own line.
    /// </summary>
    public static final int DISPLAY = 0;

    /// <summary>
    /// The Office Math is displayed inline with the text.
    /// </summary>
    public static final int INLINE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OfficeMathDisplayType value.
    /// </summary>
    public static String getName(/*OfficeMathDisplayType*/int officeMathDisplayType)
    {
    	switch (officeMathDisplayType)
    	{
    		case DISPLAY: return "DISPLAY";
    		case INLINE: return "INLINE";
    		default: return "Unknown OfficeMathDisplayType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OfficeMathDisplayType value.
    /// </summary>
    public static String toString(/*OfficeMathDisplayType*/int officeMathDisplayType)
    {
    	switch (officeMathDisplayType)
    	{
    		case DISPLAY: return "Display";
    		case INLINE: return "Inline";
    		default: return "Unknown OfficeMathDisplayType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OfficeMathDisplayType name.
    /// </summary>
    public static /*OfficeMathDisplayType*/int fromName(String officeMathDisplayTypeName)
    {
    	if ("DISPLAY".equals(officeMathDisplayTypeName)) return DISPLAY;
    	if ("INLINE".equals(officeMathDisplayTypeName)) return INLINE;
    	throw new IllegalArgumentException("Unknown OfficeMathDisplayType name.");
    }
    
    /// <summary>
    /// Returns array of OfficeMathDisplayType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DISPLAY,
    		INLINE,
    	};
    }

    public static final int length = 2;
}

