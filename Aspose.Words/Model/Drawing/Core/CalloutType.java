// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies type of a callout.
/// </summary>
/*enum*/ final class CalloutType
{
    private CalloutType(){}
    
    public static final int RIGHT_ANGLE = 1;
    public static final int ONE_SEGMENT = 2;
    public static final int TWO_SEGMENT = 3;
    public static final int THREE_SEGMENT = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CalloutType value.
    /// </summary>
    public static String getName(/*CalloutType*/int calloutType)
    {
    	switch (calloutType)
    	{
    		case RIGHT_ANGLE: return "RIGHT_ANGLE";
    		case ONE_SEGMENT: return "ONE_SEGMENT";
    		case TWO_SEGMENT: return "TWO_SEGMENT";
    		case THREE_SEGMENT: return "THREE_SEGMENT";
    		default: return "Unknown CalloutType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CalloutType value.
    /// </summary>
    public static String toString(/*CalloutType*/int calloutType)
    {
    	switch (calloutType)
    	{
    		case RIGHT_ANGLE: return "RightAngle";
    		case ONE_SEGMENT: return "OneSegment";
    		case TWO_SEGMENT: return "TwoSegment";
    		case THREE_SEGMENT: return "ThreeSegment";
    		default: return "Unknown CalloutType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CalloutType name.
    /// </summary>
    public static /*CalloutType*/int fromName(String calloutTypeName)
    {
    	if ("RIGHT_ANGLE".equals(calloutTypeName)) return RIGHT_ANGLE;
    	if ("ONE_SEGMENT".equals(calloutTypeName)) return ONE_SEGMENT;
    	if ("TWO_SEGMENT".equals(calloutTypeName)) return TWO_SEGMENT;
    	if ("THREE_SEGMENT".equals(calloutTypeName)) return THREE_SEGMENT;
    	throw new IllegalArgumentException("Unknown CalloutType name.");
    }
    
    /// <summary>
    /// Returns array of CalloutType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		RIGHT_ANGLE,
    		ONE_SEGMENT,
    		TWO_SEGMENT,
    		THREE_SEGMENT,
    	};
    }

    public static final int length = 4;
}

