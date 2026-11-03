// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies side position alignment of title or legend.
/// Corresponds to the 2.24.4.15 ST_PosAlign simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class PositionAlignment
{
    private PositionAlignment(){}
    
    /// <summary>
    /// The chart element is positioned at the top of the side.
    /// </summary>
    public static final int MINIMUM = 0;
    /// <summary>
    /// The chart element is positioned at the center of the side.
    /// </summary>
    public static final int CENTER = 1;
    /// <summary>
    /// The chart element is positioned at the bottom of the side.
    /// </summary>
    public static final int MAXIMUM = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer PositionAlignment value.
    /// </summary>
    public static String getName(/*PositionAlignment*/int positionAlignment)
    {
    	switch (positionAlignment)
    	{
    		case MINIMUM: return "MINIMUM";
    		case CENTER: return "CENTER";
    		case MAXIMUM: return "MAXIMUM";
    		default: return "Unknown PositionAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer PositionAlignment value.
    /// </summary>
    public static String toString(/*PositionAlignment*/int positionAlignment)
    {
    	switch (positionAlignment)
    	{
    		case MINIMUM: return "Minimum";
    		case CENTER: return "Center";
    		case MAXIMUM: return "Maximum";
    		default: return "Unknown PositionAlignment value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by PositionAlignment name.
    /// </summary>
    public static /*PositionAlignment*/int fromName(String positionAlignmentName)
    {
    	if ("MINIMUM".equals(positionAlignmentName)) return MINIMUM;
    	if ("CENTER".equals(positionAlignmentName)) return CENTER;
    	if ("MAXIMUM".equals(positionAlignmentName)) return MAXIMUM;
    	throw new IllegalArgumentException("Unknown PositionAlignment name.");
    }
    
    /// <summary>
    /// Returns array of PositionAlignment values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MINIMUM,
    		CENTER,
    		MAXIMUM,
    	};
    }

    public static final int length = 3;
}

