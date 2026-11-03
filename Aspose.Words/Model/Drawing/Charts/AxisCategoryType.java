// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 06/09/2017 by Alexander Zhiltsov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies type of a category axis.
/// </summary>
public /*enum*/ final class AxisCategoryType
{
    private AxisCategoryType(){}
    
    /// <summary>
    /// Specifies that type of a category axis is determined automatically based on data.
    /// </summary>
    public static final int AUTOMATIC = 0;

    /// <summary>
    /// Specifies an axis of an arbitrary set of categories.
    /// </summary>
    public static final int CATEGORY = 1;

    /// <summary>
    /// Specifies a time category axis.
    /// </summary>
    public static final int TIME = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer AxisCategoryType value.
    /// </summary>
    public static String getName(/*AxisCategoryType*/int axisCategoryType)
    {
    	switch (axisCategoryType)
    	{
    		case AUTOMATIC: return "AUTOMATIC";
    		case CATEGORY: return "CATEGORY";
    		case TIME: return "TIME";
    		default: return "Unknown AxisCategoryType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer AxisCategoryType value.
    /// </summary>
    public static String toString(/*AxisCategoryType*/int axisCategoryType)
    {
    	switch (axisCategoryType)
    	{
    		case AUTOMATIC: return "Automatic";
    		case CATEGORY: return "Category";
    		case TIME: return "Time";
    		default: return "Unknown AxisCategoryType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by AxisCategoryType name.
    /// </summary>
    public static /*AxisCategoryType*/int fromName(String axisCategoryTypeName)
    {
    	if ("AUTOMATIC".equals(axisCategoryTypeName)) return AUTOMATIC;
    	if ("CATEGORY".equals(axisCategoryTypeName)) return CATEGORY;
    	if ("TIME".equals(axisCategoryTypeName)) return TIME;
    	throw new IllegalArgumentException("Unknown AxisCategoryType name.");
    }
    
    /// <summary>
    /// Returns array of AxisCategoryType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		AUTOMATIC,
    		CATEGORY,
    		TIME,
    	};
    }

    public static final int length = 3;
}

