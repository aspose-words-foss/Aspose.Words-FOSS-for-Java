// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.38 ST_HueDir (Hue Direction)
/// </summary>
/*enum*/ final class DmlHueDirection
{
    private DmlHueDirection(){}
    
    public static final int COUNTERCLOCKWISE = 0;
    public static final int CLOCKWISE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlHueDirection value.
    /// </summary>
    public static String getName(/*DmlHueDirection*/int dmlHueDirection)
    {
    	switch (dmlHueDirection)
    	{
    		case COUNTERCLOCKWISE: return "COUNTERCLOCKWISE";
    		case CLOCKWISE: return "CLOCKWISE";
    		default: return "Unknown DmlHueDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlHueDirection value.
    /// </summary>
    public static String toString(/*DmlHueDirection*/int dmlHueDirection)
    {
    	switch (dmlHueDirection)
    	{
    		case COUNTERCLOCKWISE: return "Counterclockwise";
    		case CLOCKWISE: return "Clockwise";
    		default: return "Unknown DmlHueDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlHueDirection name.
    /// </summary>
    public static /*DmlHueDirection*/int fromName(String dmlHueDirectionName)
    {
    	if ("COUNTERCLOCKWISE".equals(dmlHueDirectionName)) return COUNTERCLOCKWISE;
    	if ("CLOCKWISE".equals(dmlHueDirectionName)) return CLOCKWISE;
    	throw new IllegalArgumentException("Unknown DmlHueDirection name.");
    }
    
    /// <summary>
    /// Returns array of DmlHueDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		COUNTERCLOCKWISE,
    		CLOCKWISE,
    	};
    }

    public static final int length = 2;
}

