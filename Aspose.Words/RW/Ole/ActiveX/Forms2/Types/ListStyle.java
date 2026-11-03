// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/11/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the visual appearance of the list in a ListBox or ComboBox.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.42 ListStyle
/// </dev>
/*enum*/ final class ListStyle
{
    private ListStyle(){}
    
    public static final int PLAIN = 0x00;
    public static final int OPTION = 0x01;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ListStyle value.
    /// </summary>
    public static String getName(/*ListStyle*/int listStyle)
    {
    	switch (listStyle)
    	{
    		case PLAIN: return "PLAIN";
    		case OPTION: return "OPTION";
    		default: return "Unknown ListStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ListStyle value.
    /// </summary>
    public static String toString(/*ListStyle*/int listStyle)
    {
    	switch (listStyle)
    	{
    		case PLAIN: return "Plain";
    		case OPTION: return "Option";
    		default: return "Unknown ListStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ListStyle name.
    /// </summary>
    public static /*ListStyle*/int fromName(String listStyleName)
    {
    	if ("PLAIN".equals(listStyleName)) return PLAIN;
    	if ("OPTION".equals(listStyleName)) return OPTION;
    	throw new IllegalArgumentException("Unknown ListStyle name.");
    }
    
    /// <summary>
    /// Returns array of ListStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		PLAIN,
    		OPTION,
    	};
    }

    public static final int length = 2;
}

