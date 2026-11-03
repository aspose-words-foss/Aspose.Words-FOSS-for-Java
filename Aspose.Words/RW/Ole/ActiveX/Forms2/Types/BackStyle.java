// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/12/2018 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the background style for control.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.96.1 VariousPropertiesBitfield.
/// </dev>
/*enum*/ final class BackStyle
{
    private BackStyle(){}
    
    /// <summary>
    /// Opaque style.
    /// </summary>
    public static final int OPAQUE = 0;

    /// <summary>
    /// Transparent style.
    /// </summary>
    public static final int TRANSPARENT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer BackStyle value.
    /// </summary>
    public static String getName(/*BackStyle*/int backStyle)
    {
    	switch (backStyle)
    	{
    		case OPAQUE: return "OPAQUE";
    		case TRANSPARENT: return "TRANSPARENT";
    		default: return "Unknown BackStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer BackStyle value.
    /// </summary>
    public static String toString(/*BackStyle*/int backStyle)
    {
    	switch (backStyle)
    	{
    		case OPAQUE: return "Opaque";
    		case TRANSPARENT: return "Transparent";
    		default: return "Unknown BackStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by BackStyle name.
    /// </summary>
    public static /*BackStyle*/int fromName(String backStyleName)
    {
    	if ("OPAQUE".equals(backStyleName)) return OPAQUE;
    	if ("TRANSPARENT".equals(backStyleName)) return TRANSPARENT;
    	throw new IllegalArgumentException("Unknown BackStyle name.");
    }
    
    /// <summary>
    /// Returns array of BackStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		OPAQUE,
    		TRANSPARENT,
    	};
    }

    public static final int length = 2;
}

