// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the rule for determining the height of an object.
/// </summary>
public /*enum*/ final class HeightRule
{
    private HeightRule(){}
    
    /// <summary>
    /// The height will be at least the specified height in points. It will grow, if needed,
    /// to accommodate all text inside an object.
    /// </summary>
    public static final int AT_LEAST = 0;
    /// <summary>
    /// The height is specified exactly in points. Please note that if the text cannot
    /// fit inside the object of this height, it will appear truncated.
    /// </summary>
    public static final int EXACTLY = 1;
    /// <summary>
    /// The height will grow automatically to accommodate all text inside an object.
    /// </summary>
    public static final int AUTO = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer HeightRule value.
    /// </summary>
    public static String getName(/*HeightRule*/int heightRule)
    {
    	switch (heightRule)
    	{
    		case AT_LEAST: return "AT_LEAST";
    		case EXACTLY: return "EXACTLY";
    		case AUTO: return "AUTO";
    		default: return "Unknown HeightRule value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer HeightRule value.
    /// </summary>
    public static String toString(/*HeightRule*/int heightRule)
    {
    	switch (heightRule)
    	{
    		case AT_LEAST: return "AtLeast";
    		case EXACTLY: return "Exactly";
    		case AUTO: return "Auto";
    		default: return "Unknown HeightRule value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by HeightRule name.
    /// </summary>
    public static /*HeightRule*/int fromName(String heightRuleName)
    {
    	if ("AT_LEAST".equals(heightRuleName)) return AT_LEAST;
    	if ("EXACTLY".equals(heightRuleName)) return EXACTLY;
    	if ("AUTO".equals(heightRuleName)) return AUTO;
    	throw new IllegalArgumentException("Unknown HeightRule name.");
    }
    
    /// <summary>
    /// Returns array of HeightRule values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AT_LEAST,
    		EXACTLY,
    		AUTO,
    	};
    }

    public static final int length = 3;
}

