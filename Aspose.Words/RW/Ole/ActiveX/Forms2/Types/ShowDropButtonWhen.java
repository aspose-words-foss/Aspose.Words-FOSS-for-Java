// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies when to show the drop button for a ComboBox.
/// </summary>
/*enum*/ final class ShowDropButtonWhen
{
    private ShowDropButtonWhen(){}
    
    public static final int NEVER = 0;
    public static final int FOCUS = 1;
    public static final int ALWAYS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ShowDropButtonWhen value.
    /// </summary>
    public static String getName(/*ShowDropButtonWhen*/int showDropButtonWhen)
    {
    	switch (showDropButtonWhen)
    	{
    		case NEVER: return "NEVER";
    		case FOCUS: return "FOCUS";
    		case ALWAYS: return "ALWAYS";
    		default: return "Unknown ShowDropButtonWhen value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ShowDropButtonWhen value.
    /// </summary>
    public static String toString(/*ShowDropButtonWhen*/int showDropButtonWhen)
    {
    	switch (showDropButtonWhen)
    	{
    		case NEVER: return "Never";
    		case FOCUS: return "Focus";
    		case ALWAYS: return "Always";
    		default: return "Unknown ShowDropButtonWhen value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ShowDropButtonWhen name.
    /// </summary>
    public static /*ShowDropButtonWhen*/int fromName(String showDropButtonWhenName)
    {
    	if ("NEVER".equals(showDropButtonWhenName)) return NEVER;
    	if ("FOCUS".equals(showDropButtonWhenName)) return FOCUS;
    	if ("ALWAYS".equals(showDropButtonWhenName)) return ALWAYS;
    	throw new IllegalArgumentException("Unknown ShowDropButtonWhen name.");
    }
    
    /// <summary>
    /// Returns array of ShowDropButtonWhen values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NEVER,
    		FOCUS,
    		ALWAYS,
    	};
    }

    public static final int length = 3;
}

