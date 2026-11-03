// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.54 ST_ResizeHandlesStr (Resize Handle)
/// </summary>
/*enum*/ final class DmlResizeHandles
{
    private DmlResizeHandles(){}
    
    public static final int EXACT = 0;
    public static final int RELATIVE = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlResizeHandles value.
    /// </summary>
    public static String getName(/*DmlResizeHandles*/int dmlResizeHandles)
    {
    	switch (dmlResizeHandles)
    	{
    		case EXACT: return "EXACT";
    		case RELATIVE: return "RELATIVE";
    		default: return "Unknown DmlResizeHandles value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlResizeHandles value.
    /// </summary>
    public static String toString(/*DmlResizeHandles*/int dmlResizeHandles)
    {
    	switch (dmlResizeHandles)
    	{
    		case EXACT: return "Exact";
    		case RELATIVE: return "Relative";
    		default: return "Unknown DmlResizeHandles value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlResizeHandles name.
    /// </summary>
    public static /*DmlResizeHandles*/int fromName(String dmlResizeHandlesName)
    {
    	if ("EXACT".equals(dmlResizeHandlesName)) return EXACT;
    	if ("RELATIVE".equals(dmlResizeHandlesName)) return RELATIVE;
    	throw new IllegalArgumentException("Unknown DmlResizeHandles name.");
    }
    
    /// <summary>
    /// Returns array of DmlResizeHandles values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		EXACT,
    		RELATIVE,
    	};
    }

    public static final int length = 2;
}

