// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 14/10/2019 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Enumerates all possible list markers.
/// </summary>
/*enum*/ final class ListMarker
{
    private ListMarker(){}
    
    public static final int NONE = 0;
    public static final int MINUS = 1;
    public static final int PLUS = 2;
    public static final int ASTERISK = 3;
    public static final int DOT = 4;
    public static final int PARENTHESIS = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ListMarker value.
    /// </summary>
    public static String getName(/*ListMarker*/int listMarker)
    {
    	switch (listMarker)
    	{
    		case NONE: return "NONE";
    		case MINUS: return "MINUS";
    		case PLUS: return "PLUS";
    		case ASTERISK: return "ASTERISK";
    		case DOT: return "DOT";
    		case PARENTHESIS: return "PARENTHESIS";
    		default: return "Unknown ListMarker value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ListMarker value.
    /// </summary>
    public static String toString(/*ListMarker*/int listMarker)
    {
    	switch (listMarker)
    	{
    		case NONE: return "None";
    		case MINUS: return "Minus";
    		case PLUS: return "Plus";
    		case ASTERISK: return "Asterisk";
    		case DOT: return "Dot";
    		case PARENTHESIS: return "Parenthesis";
    		default: return "Unknown ListMarker value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ListMarker name.
    /// </summary>
    public static /*ListMarker*/int fromName(String listMarkerName)
    {
    	if ("NONE".equals(listMarkerName)) return NONE;
    	if ("MINUS".equals(listMarkerName)) return MINUS;
    	if ("PLUS".equals(listMarkerName)) return PLUS;
    	if ("ASTERISK".equals(listMarkerName)) return ASTERISK;
    	if ("DOT".equals(listMarkerName)) return DOT;
    	if ("PARENTHESIS".equals(listMarkerName)) return PARENTHESIS;
    	throw new IllegalArgumentException("Unknown ListMarker name.");
    }
    
    /// <summary>
    /// Returns array of ListMarker values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		MINUS,
    		PLUS,
    		ASTERISK,
    		DOT,
    		PARENTHESIS,
    	};
    }

    public static final int length = 6;
}

