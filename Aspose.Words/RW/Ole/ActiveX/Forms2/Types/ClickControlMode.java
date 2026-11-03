// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies control behavior when the control is clicked.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.12 ClickControlMode.
/// </dev>
/*enum*/ final class ClickControlMode
{
    private ClickControlMode(){}
    
    public static final int INSERTION_POINT = 0x00;
    public static final int SELECT_THEN_INSERT = 0x01;
    public static final int INHERIT = 0xFE;
    public static final int DEFAULT = 0xFF;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ClickControlMode value.
    /// </summary>
    public static String getName(/*ClickControlMode*/int clickControlMode)
    {
    	switch (clickControlMode)
    	{
    		case INSERTION_POINT: return "INSERTION_POINT";
    		case SELECT_THEN_INSERT: return "SELECT_THEN_INSERT";
    		case INHERIT: return "INHERIT";
    		case DEFAULT: return "DEFAULT";
    		default: return "Unknown ClickControlMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ClickControlMode value.
    /// </summary>
    public static String toString(/*ClickControlMode*/int clickControlMode)
    {
    	switch (clickControlMode)
    	{
    		case INSERTION_POINT: return "InsertionPoint";
    		case SELECT_THEN_INSERT: return "SelectThenInsert";
    		case INHERIT: return "Inherit";
    		case DEFAULT: return "Default";
    		default: return "Unknown ClickControlMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ClickControlMode name.
    /// </summary>
    public static /*ClickControlMode*/int fromName(String clickControlModeName)
    {
    	if ("INSERTION_POINT".equals(clickControlModeName)) return INSERTION_POINT;
    	if ("SELECT_THEN_INSERT".equals(clickControlModeName)) return SELECT_THEN_INSERT;
    	if ("INHERIT".equals(clickControlModeName)) return INHERIT;
    	if ("DEFAULT".equals(clickControlModeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown ClickControlMode name.");
    }
    
    /// <summary>
    /// Returns array of ClickControlMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		INSERTION_POINT,
    		SELECT_THEN_INSERT,
    		INHERIT,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

