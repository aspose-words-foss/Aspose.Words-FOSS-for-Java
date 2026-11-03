// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 09/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the display style of the tabs of a control.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.87 TabStyle.
/// </dev>
/*enum*/ final class TabStyle
{
    private TabStyle(){}
    
    public static final int TABS = 0;
    public static final int BUTTONS = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer TabStyle value.
    /// </summary>
    public static String getName(/*TabStyle*/int tabStyle)
    {
    	switch (tabStyle)
    	{
    		case TABS: return "TABS";
    		case BUTTONS: return "BUTTONS";
    		default: return "Unknown TabStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer TabStyle value.
    /// </summary>
    public static String toString(/*TabStyle*/int tabStyle)
    {
    	switch (tabStyle)
    	{
    		case TABS: return "Tabs";
    		case BUTTONS: return "Buttons";
    		default: return "Unknown TabStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by TabStyle name.
    /// </summary>
    public static /*TabStyle*/int fromName(String tabStyleName)
    {
    	if ("TABS".equals(tabStyleName)) return TABS;
    	if ("BUTTONS".equals(tabStyleName)) return BUTTONS;
    	throw new IllegalArgumentException("Unknown TabStyle name.");
    }
    
    /// <summary>
    /// Returns array of TabStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TABS,
    		BUTTONS,
    	};
    }

    public static final int length = 2;
}

