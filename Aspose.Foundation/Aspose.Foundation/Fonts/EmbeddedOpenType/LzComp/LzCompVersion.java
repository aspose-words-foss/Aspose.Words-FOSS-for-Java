// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2011 by Konstantin Kornilov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Version of the LZCOMP algorithm.
/// </summary>
/*enum*/ final class LzCompVersion
{
    private LzCompVersion(){}
    
    public static final int VERSION_0 = 0;
    public static final int VERSION_1 = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer LzCompVersion value.
    /// </summary>
    public static String getName(/*LzCompVersion*/int lzCompVersion)
    {
    	switch (lzCompVersion)
    	{
    		case VERSION_0: return "VERSION_0";
    		case VERSION_1: return "VERSION_1";
    		default: return "Unknown LzCompVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer LzCompVersion value.
    /// </summary>
    public static String toString(/*LzCompVersion*/int lzCompVersion)
    {
    	switch (lzCompVersion)
    	{
    		case VERSION_0: return "Version0";
    		case VERSION_1: return "Version1";
    		default: return "Unknown LzCompVersion value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by LzCompVersion name.
    /// </summary>
    public static /*LzCompVersion*/int fromName(String lzCompVersionName)
    {
    	if ("VERSION_0".equals(lzCompVersionName)) return VERSION_0;
    	if ("VERSION_1".equals(lzCompVersionName)) return VERSION_1;
    	throw new IllegalArgumentException("Unknown LzCompVersion name.");
    }
    
    /// <summary>
    /// Returns array of LzCompVersion values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		VERSION_0,
    		VERSION_1,
    	};
    }

    public static final int length = 2;
}

