// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 19/11/2014 by Edward Voronov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a orientation cell enumeration should follow.
/// </summary>
/*enum*/ final class CellEnumerationOrientation
{
    private CellEnumerationOrientation(){}
    
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CellEnumerationOrientation value.
    /// </summary>
    public static String getName(/*CellEnumerationOrientation*/int cellEnumerationOrientation)
    {
    	switch (cellEnumerationOrientation)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		default: return "Unknown CellEnumerationOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CellEnumerationOrientation value.
    /// </summary>
    public static String toString(/*CellEnumerationOrientation*/int cellEnumerationOrientation)
    {
    	switch (cellEnumerationOrientation)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		default: return "Unknown CellEnumerationOrientation value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CellEnumerationOrientation name.
    /// </summary>
    public static /*CellEnumerationOrientation*/int fromName(String cellEnumerationOrientationName)
    {
    	if ("HORIZONTAL".equals(cellEnumerationOrientationName)) return HORIZONTAL;
    	if ("VERTICAL".equals(cellEnumerationOrientationName)) return VERTICAL;
    	throw new IllegalArgumentException("Unknown CellEnumerationOrientation name.");
    }
    
    /// <summary>
    /// Returns array of CellEnumerationOrientation values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		VERTICAL,
    	};
    }

    public static final int length = 2;
}
