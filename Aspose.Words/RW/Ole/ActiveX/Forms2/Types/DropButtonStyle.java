// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/03/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the symbol displayed on the drop button.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.22 DropButtonStyle
/// </dev>
/*enum*/ final class DropButtonStyle
{
    private DropButtonStyle(){}
    
    public static final int PLAIN = 0x00;
    public static final int ARROW = 0x01;
    public static final int ELLIPSIS = 0x02;
    public static final int REDUCE = 0x03;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DropButtonStyle value.
    /// </summary>
    public static String getName(/*DropButtonStyle*/int dropButtonStyle)
    {
    	switch (dropButtonStyle)
    	{
    		case PLAIN: return "PLAIN";
    		case ARROW: return "ARROW";
    		case ELLIPSIS: return "ELLIPSIS";
    		case REDUCE: return "REDUCE";
    		default: return "Unknown DropButtonStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DropButtonStyle value.
    /// </summary>
    public static String toString(/*DropButtonStyle*/int dropButtonStyle)
    {
    	switch (dropButtonStyle)
    	{
    		case PLAIN: return "Plain";
    		case ARROW: return "Arrow";
    		case ELLIPSIS: return "Ellipsis";
    		case REDUCE: return "Reduce";
    		default: return "Unknown DropButtonStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DropButtonStyle name.
    /// </summary>
    public static /*DropButtonStyle*/int fromName(String dropButtonStyleName)
    {
    	if ("PLAIN".equals(dropButtonStyleName)) return PLAIN;
    	if ("ARROW".equals(dropButtonStyleName)) return ARROW;
    	if ("ELLIPSIS".equals(dropButtonStyleName)) return ELLIPSIS;
    	if ("REDUCE".equals(dropButtonStyleName)) return REDUCE;
    	throw new IllegalArgumentException("Unknown DropButtonStyle name.");
    }
    
    /// <summary>
    /// Returns array of DropButtonStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PLAIN,
    		ARROW,
    		ELLIPSIS,
    		REDUCE,
    	};
    }

    public static final int length = 4;
}

