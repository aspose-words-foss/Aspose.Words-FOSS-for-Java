// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 24/01/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 20.1.10.37 ST_PathFillMode (Path Fill Mode)
/// This simple type specifies the manner in which a path should be filled. 
/// The lightening and darkening of a path allow for certain parts of the 
/// shape to be colored lighter or darker depending on user preference.
/// </summary>
/*enum*/ final class DmlPathFillMode
{
    private DmlPathFillMode(){}
    
    /// <summary>
    /// This specifies that the corresponding path 
    /// should have a darker shaded color applied to it's fill.
    /// </summary>
    public static final int DARKEN = 0;
    /// <summary>
    /// This specifies that the corresponding path 
    /// should have a slightly darker shaded color applied to it's fill.
    /// </summary>
    public static final int DARKEN_LESS = 1;
    /// <summary>
    /// This specifies that the corresponding path 
    /// should have a lightly shaded color applied to it's fill.
    /// </summary>
    public static final int LIGHTEN = 2;
    /// <summary>
    /// This specifies that the corresponding path 
    /// should have a slightly lighter shaded color applied to it's fill.
    /// </summary>
    public static final int LIGHTEN_LESS = 3;
    /// <summary>
    /// This specifies that the corresponding path 
    /// should have no fill.
    /// </summary>
    public static final int NONE = 4;
    /// <summary>
    /// This specifies that the corresponding path 
    /// should have a normally shaded color applied to it's fill.
    /// </summary>
    public static final int NORM = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlPathFillMode value.
    /// </summary>
    public static String getName(/*DmlPathFillMode*/int dmlPathFillMode)
    {
    	switch (dmlPathFillMode)
    	{
    		case DARKEN: return "DARKEN";
    		case DARKEN_LESS: return "DARKEN_LESS";
    		case LIGHTEN: return "LIGHTEN";
    		case LIGHTEN_LESS: return "LIGHTEN_LESS";
    		case NONE: return "NONE";
    		case NORM: return "NORM";
    		default: return "Unknown DmlPathFillMode value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlPathFillMode value.
    /// </summary>
    public static String toString(/*DmlPathFillMode*/int dmlPathFillMode)
    {
    	switch (dmlPathFillMode)
    	{
    		case DARKEN: return "Darken";
    		case DARKEN_LESS: return "DarkenLess";
    		case LIGHTEN: return "Lighten";
    		case LIGHTEN_LESS: return "LightenLess";
    		case NONE: return "None";
    		case NORM: return "Norm";
    		default: return "Unknown DmlPathFillMode value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlPathFillMode name.
    /// </summary>
    public static /*DmlPathFillMode*/int fromName(String dmlPathFillModeName)
    {
    	if ("DARKEN".equals(dmlPathFillModeName)) return DARKEN;
    	if ("DARKEN_LESS".equals(dmlPathFillModeName)) return DARKEN_LESS;
    	if ("LIGHTEN".equals(dmlPathFillModeName)) return LIGHTEN;
    	if ("LIGHTEN_LESS".equals(dmlPathFillModeName)) return LIGHTEN_LESS;
    	if ("NONE".equals(dmlPathFillModeName)) return NONE;
    	if ("NORM".equals(dmlPathFillModeName)) return NORM;
    	throw new IllegalArgumentException("Unknown DmlPathFillMode name.");
    }
    
    /// <summary>
    /// Returns array of DmlPathFillMode values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		DARKEN,
    		DARKEN_LESS,
    		LIGHTEN,
    		LIGHTEN_LESS,
    		NONE,
    		NORM,
    	};
    }

    public static final int length = 6;
}
