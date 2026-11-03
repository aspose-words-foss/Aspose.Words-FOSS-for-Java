// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class OleColorType
{
    private OleColorType(){}
    
    public static final int DEFAULT = 0x00;
    public static final int PALETTE_ENTRY = 0x01;
    public static final int RGB_COLOR = 0x02;
    public static final int SYSTEM_PALETTE = 0x80;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer OleColorType value.
    /// </summary>
    public static String getName(/*OleColorType*/int oleColorType)
    {
    	switch (oleColorType)
    	{
    		case DEFAULT: return "DEFAULT";
    		case PALETTE_ENTRY: return "PALETTE_ENTRY";
    		case RGB_COLOR: return "RGB_COLOR";
    		case SYSTEM_PALETTE: return "SYSTEM_PALETTE";
    		default: return "Unknown OleColorType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer OleColorType value.
    /// </summary>
    public static String toString(/*OleColorType*/int oleColorType)
    {
    	switch (oleColorType)
    	{
    		case DEFAULT: return "Default";
    		case PALETTE_ENTRY: return "PaletteEntry";
    		case RGB_COLOR: return "RgbColor";
    		case SYSTEM_PALETTE: return "SystemPalette";
    		default: return "Unknown OleColorType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by OleColorType name.
    /// </summary>
    public static /*OleColorType*/int fromName(String oleColorTypeName)
    {
    	if ("DEFAULT".equals(oleColorTypeName)) return DEFAULT;
    	if ("PALETTE_ENTRY".equals(oleColorTypeName)) return PALETTE_ENTRY;
    	if ("RGB_COLOR".equals(oleColorTypeName)) return RGB_COLOR;
    	if ("SYSTEM_PALETTE".equals(oleColorTypeName)) return SYSTEM_PALETTE;
    	throw new IllegalArgumentException("Unknown OleColorType name.");
    }
    
    /// <summary>
    /// Returns array of OleColorType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DEFAULT,
    		PALETTE_ENTRY,
    		RGB_COLOR,
    		SYSTEM_PALETTE,
    	};
    }

    public static final int length = 4;
}

