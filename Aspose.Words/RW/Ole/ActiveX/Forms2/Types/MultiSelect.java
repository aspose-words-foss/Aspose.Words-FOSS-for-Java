// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 25/11/2020 by Ilya Navrotskiy

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether the control permits multiple selections.
/// </summary>
/// <dev>
/// [MS-OFORMS] 2.5.52 MultiSelect
/// </dev>
/*enum*/ final class MultiSelect
{
    private MultiSelect(){}
    
    public static final int SINGLE = 0x00;
    public static final int MULTI = 0x01;
    public static final int EXTENDED = 0x02;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MultiSelect value.
    /// </summary>
    public static String getName(/*MultiSelect*/int multiSelect)
    {
    	switch (multiSelect)
    	{
    		case SINGLE: return "SINGLE";
    		case MULTI: return "MULTI";
    		case EXTENDED: return "EXTENDED";
    		default: return "Unknown MultiSelect value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MultiSelect value.
    /// </summary>
    public static String toString(/*MultiSelect*/int multiSelect)
    {
    	switch (multiSelect)
    	{
    		case SINGLE: return "Single";
    		case MULTI: return "Multi";
    		case EXTENDED: return "Extended";
    		default: return "Unknown MultiSelect value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MultiSelect name.
    /// </summary>
    public static /*MultiSelect*/int fromName(String multiSelectName)
    {
    	if ("SINGLE".equals(multiSelectName)) return SINGLE;
    	if ("MULTI".equals(multiSelectName)) return MULTI;
    	if ("EXTENDED".equals(multiSelectName)) return EXTENDED;
    	throw new IllegalArgumentException("Unknown MultiSelect name.");
    }
    
    /// <summary>
    /// Returns array of MultiSelect values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		SINGLE,
    		MULTI,
    		EXTENDED,
    	};
    }

    public static final int length = 3;
}

