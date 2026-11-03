// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether the control is oriented vertically or horizontally.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.58 Orientation.
/// </dev>
/*enum*/ final class FormOrientation /*: uint*/
{
    private FormOrientation(){}
    
    public static final /*uint*/int VERTICAL = 0;
    public static final /*uint*/int HORIZONTAL = 1;
    public static final /*uint*/int AUTO = (int) 0xFFFFFFFFL;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FormOrientation value.
    /// </summary>
    public static String getName(/*FormOrientation*//*uint*/int formOrientation)
    {
    	if (VERTICAL == formOrientation) return "VERTICAL";
    	if (HORIZONTAL == formOrientation) return "HORIZONTAL";
    	if (AUTO == formOrientation) return "AUTO";
    	return "Unknown FormOrientation value.";
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FormOrientation value.
    /// </summary>
    public static String toString(/*FormOrientation*//*uint*/int formOrientation)
    {
    	if (VERTICAL == formOrientation) return "Vertical";
    	if (HORIZONTAL == formOrientation) return "Horizontal";
    	if (AUTO == formOrientation) return "Auto";
    	return "Unknown FormOrientation value.";
    }
    
    /// <summary>
    /// Returns integer representation by FormOrientation name.
    /// </summary>
    public static /*FormOrientation*//*uint*/int fromName(String formOrientationName)
    {
    	if ("VERTICAL".equals(formOrientationName)) return VERTICAL;
    	if ("HORIZONTAL".equals(formOrientationName)) return HORIZONTAL;
    	if ("AUTO".equals(formOrientationName)) return AUTO;
    	throw new IllegalArgumentException("Unknown FormOrientation name.");
    }
    
    /// <summary>
    /// Returns array of FormOrientation values.
    /// </summary>
    public static /*uint*/int[] getValues()
    {
    	return new /*uint*/int[]
    	{
    		VERTICAL,
    		HORIZONTAL,
    		AUTO,
    	};
    }

    public static final int length = 3;
}

