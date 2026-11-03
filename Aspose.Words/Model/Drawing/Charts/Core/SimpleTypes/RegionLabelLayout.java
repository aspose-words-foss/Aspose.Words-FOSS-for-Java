// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies the layout type for region labels of a geospatial series.
/// Corresponds to the 2.24.4.17 ST_RegionLabelLayout simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class RegionLabelLayout
{
    private RegionLabelLayout(){}
    
    /// <summary>
    /// Specifies that no region labels appear in a geospatial series.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// Specifies that region labels only appear if they can fit in their respective containing geometries
    /// in a geospatial series.
    /// </summary>
    public static final int BEST_FIT_ONLY = 1;

    /// <summary>
    /// Specifies that all region labels appear.
    /// </summary>
    public static final int SHOW_ALL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer RegionLabelLayout value.
    /// </summary>
    public static String getName(/*RegionLabelLayout*/int regionLabelLayout)
    {
    	switch (regionLabelLayout)
    	{
    		case NONE: return "NONE";
    		case BEST_FIT_ONLY: return "BEST_FIT_ONLY";
    		case SHOW_ALL: return "SHOW_ALL";
    		default: return "Unknown RegionLabelLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer RegionLabelLayout value.
    /// </summary>
    public static String toString(/*RegionLabelLayout*/int regionLabelLayout)
    {
    	switch (regionLabelLayout)
    	{
    		case NONE: return "None";
    		case BEST_FIT_ONLY: return "BestFitOnly";
    		case SHOW_ALL: return "ShowAll";
    		default: return "Unknown RegionLabelLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by RegionLabelLayout name.
    /// </summary>
    public static /*RegionLabelLayout*/int fromName(String regionLabelLayoutName)
    {
    	if ("NONE".equals(regionLabelLayoutName)) return NONE;
    	if ("BEST_FIT_ONLY".equals(regionLabelLayoutName)) return BEST_FIT_ONLY;
    	if ("SHOW_ALL".equals(regionLabelLayoutName)) return SHOW_ALL;
    	throw new IllegalArgumentException("Unknown RegionLabelLayout name.");
    }
    
    /// <summary>
    /// Returns array of RegionLabelLayout values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BEST_FIT_ONLY,
    		SHOW_ALL,
    	};
    }

    public static final int length = 3;
}

