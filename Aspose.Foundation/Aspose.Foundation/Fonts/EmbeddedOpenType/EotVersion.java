// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class EotVersion
{
    private EotVersion(){}
    
    public static final int UNKNOWN = 0x00000000;
    public static final int VERSION_10 = 0x00010000;
    public static final int VERSION_21 = 0x00020001;
    public static final int VERSION_22 = 0x00020002;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EotVersion value.
    /// </summary>
    public static String getName(/*EotVersion*/int eotVersion)
    {
    	switch (eotVersion)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case VERSION_10: return "VERSION_10";
    		case VERSION_21: return "VERSION_21";
    		case VERSION_22: return "VERSION_22";
    		default: return "Unknown EotVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EotVersion value.
    /// </summary>
    public static String toString(/*EotVersion*/int eotVersion)
    {
    	switch (eotVersion)
    	{
    		case UNKNOWN: return "Unknown";
    		case VERSION_10: return "Version10";
    		case VERSION_21: return "Version21";
    		case VERSION_22: return "Version22";
    		default: return "Unknown EotVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EotVersion name.
    /// </summary>
    public static /*EotVersion*/int fromName(String eotVersionName)
    {
    	if ("UNKNOWN".equals(eotVersionName)) return UNKNOWN;
    	if ("VERSION_10".equals(eotVersionName)) return VERSION_10;
    	if ("VERSION_21".equals(eotVersionName)) return VERSION_21;
    	if ("VERSION_22".equals(eotVersionName)) return VERSION_22;
    	throw new IllegalArgumentException("Unknown EotVersion name.");
    }
    
    /// <summary>
    /// Returns array of EotVersion values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		VERSION_10,
    		VERSION_21,
    		VERSION_22,
    	};
    }

    public static final int length = 4;
}

