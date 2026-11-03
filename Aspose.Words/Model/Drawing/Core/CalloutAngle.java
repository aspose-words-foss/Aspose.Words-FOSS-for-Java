// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/07/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the angle of a callout.
/// </summary>
/*enum*/ final class CalloutAngle
{
    private CalloutAngle(){}
    
    public static final int ANY = 0;
    public static final int ANGLE_30 = 1;
    public static final int ANGLE_45 = 2;
    public static final int ANGLE_60 = 3;
    public static final int ANGLE_90 = 4;
    public static final int ANGLE_0 = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CalloutAngle value.
    /// </summary>
    public static String getName(/*CalloutAngle*/int calloutAngle)
    {
    	switch (calloutAngle)
    	{
    		case ANY: return "ANY";
    		case ANGLE_30: return "ANGLE_30";
    		case ANGLE_45: return "ANGLE_45";
    		case ANGLE_60: return "ANGLE_60";
    		case ANGLE_90: return "ANGLE_90";
    		case ANGLE_0: return "ANGLE_0";
    		default: return "Unknown CalloutAngle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CalloutAngle value.
    /// </summary>
    public static String toString(/*CalloutAngle*/int calloutAngle)
    {
    	switch (calloutAngle)
    	{
    		case ANY: return "Any";
    		case ANGLE_30: return "Angle30";
    		case ANGLE_45: return "Angle45";
    		case ANGLE_60: return "Angle60";
    		case ANGLE_90: return "Angle90";
    		case ANGLE_0: return "Angle0";
    		default: return "Unknown CalloutAngle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CalloutAngle name.
    /// </summary>
    public static /*CalloutAngle*/int fromName(String calloutAngleName)
    {
    	if ("ANY".equals(calloutAngleName)) return ANY;
    	if ("ANGLE_30".equals(calloutAngleName)) return ANGLE_30;
    	if ("ANGLE_45".equals(calloutAngleName)) return ANGLE_45;
    	if ("ANGLE_60".equals(calloutAngleName)) return ANGLE_60;
    	if ("ANGLE_90".equals(calloutAngleName)) return ANGLE_90;
    	if ("ANGLE_0".equals(calloutAngleName)) return ANGLE_0;
    	throw new IllegalArgumentException("Unknown CalloutAngle name.");
    }
    
    /// <summary>
    /// Returns array of CalloutAngle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ANY,
    		ANGLE_30,
    		ANGLE_45,
    		ANGLE_60,
    		ANGLE_90,
    		ANGLE_0,
    	};
    }

    public static final int length = 6;
}

