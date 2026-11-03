// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/08/2006 by Roman Korchagin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the types of connection points.
/// </summary>
/*enum*/ final class ConnectionSiteType
{
    private ConnectionSiteType(){}
    
    /// <summary>
    /// No connection points.
    /// </summary>
    public static final int NONE = 0;
    /// <summary>
    /// The edit points of the shape are used as connection points.
    /// </summary>
    public static final int SEGMENTS = 1;
    /// <summary>
    /// A custom array of connection points.
    /// </summary>
    public static final int CUSTOM = 2;
    /// <summary>
    /// Standard four connection points at midpoints of top, bottom, left and right sides.
    /// </summary>
    public static final int RECT = 3;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ConnectionSiteType value.
    /// </summary>
    public static String getName(/*ConnectionSiteType*/int connectionSiteType)
    {
    	switch (connectionSiteType)
    	{
    		case NONE: return "NONE";
    		case SEGMENTS: return "SEGMENTS";
    		case CUSTOM: return "CUSTOM";
    		case RECT: return "RECT";
    		default: return "Unknown ConnectionSiteType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ConnectionSiteType value.
    /// </summary>
    public static String toString(/*ConnectionSiteType*/int connectionSiteType)
    {
    	switch (connectionSiteType)
    	{
    		case NONE: return "None";
    		case SEGMENTS: return "Segments";
    		case CUSTOM: return "Custom";
    		case RECT: return "Rect";
    		default: return "Unknown ConnectionSiteType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ConnectionSiteType name.
    /// </summary>
    public static /*ConnectionSiteType*/int fromName(String connectionSiteTypeName)
    {
    	if ("NONE".equals(connectionSiteTypeName)) return NONE;
    	if ("SEGMENTS".equals(connectionSiteTypeName)) return SEGMENTS;
    	if ("CUSTOM".equals(connectionSiteTypeName)) return CUSTOM;
    	if ("RECT".equals(connectionSiteTypeName)) return RECT;
    	throw new IllegalArgumentException("Unknown ConnectionSiteType name.");
    }
    
    /// <summary>
    /// Returns array of ConnectionSiteType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SEGMENTS,
    		CUSTOM,
    		RECT,
    	};
    }

    public static final int length = 4;
}

