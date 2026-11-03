// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 07/17/2014 by Andrey Noskov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies dashing scheme.
/// </summary>
/*enum*/ final class DmlDashType
{
    private DmlDashType(){}
    
    /// <summary>
    /// Specifies a dash stop primitive. Dashing schemes are built by specifying an ordered list of dash stop primitive.
    /// </summary>
    public static final int CUSTOM_DASH = 0;

    /// <summary>
    /// Specifies that a preset line dashing scheme should be used.
    /// </summary>
    public static final int PRESET_DASH = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlDashType value.
    /// </summary>
    public static String getName(/*DmlDashType*/int dmlDashType)
    {
    	switch (dmlDashType)
    	{
    		case CUSTOM_DASH: return "CUSTOM_DASH";
    		case PRESET_DASH: return "PRESET_DASH";
    		default: return "Unknown DmlDashType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlDashType value.
    /// </summary>
    public static String toString(/*DmlDashType*/int dmlDashType)
    {
    	switch (dmlDashType)
    	{
    		case CUSTOM_DASH: return "CustomDash";
    		case PRESET_DASH: return "PresetDash";
    		default: return "Unknown DmlDashType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlDashType name.
    /// </summary>
    public static /*DmlDashType*/int fromName(String dmlDashTypeName)
    {
    	if ("CUSTOM_DASH".equals(dmlDashTypeName)) return CUSTOM_DASH;
    	if ("PRESET_DASH".equals(dmlDashTypeName)) return PRESET_DASH;
    	throw new IllegalArgumentException("Unknown DmlDashType name.");
    }
    
    /// <summary>
    /// Returns array of DmlDashType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CUSTOM_DASH,
    		PRESET_DASH,
    	};
    }

    public static final int length = 2;
}

