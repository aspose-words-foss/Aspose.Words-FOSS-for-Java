// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 08/10/2015 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********



/// <summary>
/// Specifies the type of a <see cref="MorphDataControl"/>.
/// </summary>
/*enum*/ final class DisplayStyle
{
    private DisplayStyle(){}
    
    public static final int TEXT = 1;
    public static final int LIST = 2;
    public static final int COMBO = 3;
    public static final int CHECK_BOX = 4;
    public static final int OPTION_BUTTON = 5;
    public static final int TOGGLE = 6;
    public static final int DROP_LIST = 7;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DisplayStyle value.
    /// </summary>
    public static String getName(/*DisplayStyle*/int displayStyle)
    {
    	switch (displayStyle)
    	{
    		case TEXT: return "TEXT";
    		case LIST: return "LIST";
    		case COMBO: return "COMBO";
    		case CHECK_BOX: return "CHECK_BOX";
    		case OPTION_BUTTON: return "OPTION_BUTTON";
    		case TOGGLE: return "TOGGLE";
    		case DROP_LIST: return "DROP_LIST";
    		default: return "Unknown DisplayStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DisplayStyle value.
    /// </summary>
    public static String toString(/*DisplayStyle*/int displayStyle)
    {
    	switch (displayStyle)
    	{
    		case TEXT: return "Text";
    		case LIST: return "List";
    		case COMBO: return "Combo";
    		case CHECK_BOX: return "CheckBox";
    		case OPTION_BUTTON: return "OptionButton";
    		case TOGGLE: return "Toggle";
    		case DROP_LIST: return "DropList";
    		default: return "Unknown DisplayStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DisplayStyle name.
    /// </summary>
    public static /*DisplayStyle*/int fromName(String displayStyleName)
    {
    	if ("TEXT".equals(displayStyleName)) return TEXT;
    	if ("LIST".equals(displayStyleName)) return LIST;
    	if ("COMBO".equals(displayStyleName)) return COMBO;
    	if ("CHECK_BOX".equals(displayStyleName)) return CHECK_BOX;
    	if ("OPTION_BUTTON".equals(displayStyleName)) return OPTION_BUTTON;
    	if ("TOGGLE".equals(displayStyleName)) return TOGGLE;
    	if ("DROP_LIST".equals(displayStyleName)) return DROP_LIST;
    	throw new IllegalArgumentException("Unknown DisplayStyle name.");
    }
    
    /// <summary>
    /// Returns array of DisplayStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		LIST,
    		COMBO,
    		CHECK_BOX,
    		OPTION_BUTTON,
    		TOGGLE,
    		DROP_LIST,
    	};
    }

    public static final int length = 7;
}

