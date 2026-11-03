// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/01/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the behavior when the user double-clicks a form or an item on the form.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.18 DblClickControlMode.
/// </dev>
/*enum*/ final class DblClickControlMode
{
    private DblClickControlMode(){}
    
    public static final int SELECT_TEXT = 0x00;
    public static final int EDIT_CODE = 0x01;
    public static final int EDIT_PROPERTIES = 0x02;
    public static final int INHERIT = 0xFE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DblClickControlMode value.
    /// </summary>
    public static String getName(/*DblClickControlMode*/int dblClickControlMode)
    {
    	switch (dblClickControlMode)
    	{
    		case SELECT_TEXT: return "SELECT_TEXT";
    		case EDIT_CODE: return "EDIT_CODE";
    		case EDIT_PROPERTIES: return "EDIT_PROPERTIES";
    		case INHERIT: return "INHERIT";
    		default: return "Unknown DblClickControlMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DblClickControlMode value.
    /// </summary>
    public static String toString(/*DblClickControlMode*/int dblClickControlMode)
    {
    	switch (dblClickControlMode)
    	{
    		case SELECT_TEXT: return "SelectText";
    		case EDIT_CODE: return "EditCode";
    		case EDIT_PROPERTIES: return "EditProperties";
    		case INHERIT: return "Inherit";
    		default: return "Unknown DblClickControlMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DblClickControlMode name.
    /// </summary>
    public static /*DblClickControlMode*/int fromName(String dblClickControlModeName)
    {
    	if ("SELECT_TEXT".equals(dblClickControlModeName)) return SELECT_TEXT;
    	if ("EDIT_CODE".equals(dblClickControlModeName)) return EDIT_CODE;
    	if ("EDIT_PROPERTIES".equals(dblClickControlModeName)) return EDIT_PROPERTIES;
    	if ("INHERIT".equals(dblClickControlModeName)) return INHERIT;
    	throw new IllegalArgumentException("Unknown DblClickControlMode name.");
    }
    
    /// <summary>
    /// Returns array of DblClickControlMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SELECT_TEXT,
    		EDIT_CODE,
    		EDIT_PROPERTIES,
    		INHERIT,
    	};
    }

    public static final int length = 4;
}

