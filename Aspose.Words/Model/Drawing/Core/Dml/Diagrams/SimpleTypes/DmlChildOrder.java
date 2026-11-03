// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.15 ST_ChildOrderType (Child Order)
/// </summary>
/*enum*/ final class DmlChildOrder
{
    private DmlChildOrder(){}
    
    public static final int BOTTOM = 0;
    public static final int TOP = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlChildOrder value.
    /// </summary>
    public static String getName(/*DmlChildOrder*/int dmlChildOrder)
    {
    	switch (dmlChildOrder)
    	{
    		case BOTTOM: return "BOTTOM";
    		case TOP: return "TOP";
    		default: return "Unknown DmlChildOrder value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlChildOrder value.
    /// </summary>
    public static String toString(/*DmlChildOrder*/int dmlChildOrder)
    {
    	switch (dmlChildOrder)
    	{
    		case BOTTOM: return "Bottom";
    		case TOP: return "Top";
    		default: return "Unknown DmlChildOrder value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlChildOrder name.
    /// </summary>
    public static /*DmlChildOrder*/int fromName(String dmlChildOrderName)
    {
    	if ("BOTTOM".equals(dmlChildOrderName)) return BOTTOM;
    	if ("TOP".equals(dmlChildOrderName)) return TOP;
    	throw new IllegalArgumentException("Unknown DmlChildOrder name.");
    }
    
    /// <summary>
    /// Returns array of DmlChildOrder values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BOTTOM,
    		TOP,
    	};
    }

    public static final int length = 2;
}

