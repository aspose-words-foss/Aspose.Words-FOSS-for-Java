// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/03/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// This enum specifies parent label layout types.
/// Corresponds to the 2.24.4.14 ST_ParentLabelLayout simple type [MS-ODRAWXML].
/// </summary>
/*enum*/ final class ParentLabelLayout
{
    private ParentLabelLayout(){}
    
    /// <summary>
    /// No parent labels are shown.
    /// </summary>
    public static final int NONE = 0;

    /// <summary>
    /// The parent label layout is a banner above the category.
    /// </summary>
    public static final int BANNER = 1;

    /// <summary>
    /// The parent label is laid out within the category.
    /// </summary>
    public static final int OVERLAPPING = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ParentLabelLayout value.
    /// </summary>
    public static String getName(/*ParentLabelLayout*/int parentLabelLayout)
    {
    	switch (parentLabelLayout)
    	{
    		case NONE: return "NONE";
    		case BANNER: return "BANNER";
    		case OVERLAPPING: return "OVERLAPPING";
    		default: return "Unknown ParentLabelLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ParentLabelLayout value.
    /// </summary>
    public static String toString(/*ParentLabelLayout*/int parentLabelLayout)
    {
    	switch (parentLabelLayout)
    	{
    		case NONE: return "None";
    		case BANNER: return "Banner";
    		case OVERLAPPING: return "Overlapping";
    		default: return "Unknown ParentLabelLayout value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ParentLabelLayout name.
    /// </summary>
    public static /*ParentLabelLayout*/int fromName(String parentLabelLayoutName)
    {
    	if ("NONE".equals(parentLabelLayoutName)) return NONE;
    	if ("BANNER".equals(parentLabelLayoutName)) return BANNER;
    	if ("OVERLAPPING".equals(parentLabelLayoutName)) return OVERLAPPING;
    	throw new IllegalArgumentException("Unknown ParentLabelLayout name.");
    }
    
    /// <summary>
    /// Returns array of ParentLabelLayout values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		BANNER,
    		OVERLAPPING,
    	};
    }

    public static final int length = 3;
}

