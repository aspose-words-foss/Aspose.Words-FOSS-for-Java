// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 10/07/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Text cap types
/// </summary>
/*enum*/ final class DmlCapitalization
{
    private DmlCapitalization(){}
    
    public static final int NONE = 0;
    public static final int SMALL = 1;
    public static final int ALL = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlCapitalization value.
    /// </summary>
    public static String getName(/*DmlCapitalization*/int dmlCapitalization)
    {
    	switch (dmlCapitalization)
    	{
    		case NONE: return "NONE";
    		case SMALL: return "SMALL";
    		case ALL: return "ALL";
    		default: return "Unknown DmlCapitalization value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlCapitalization value.
    /// </summary>
    public static String toString(/*DmlCapitalization*/int dmlCapitalization)
    {
    	switch (dmlCapitalization)
    	{
    		case NONE: return "None";
    		case SMALL: return "Small";
    		case ALL: return "All";
    		default: return "Unknown DmlCapitalization value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlCapitalization name.
    /// </summary>
    public static /*DmlCapitalization*/int fromName(String dmlCapitalizationName)
    {
    	if ("NONE".equals(dmlCapitalizationName)) return NONE;
    	if ("SMALL".equals(dmlCapitalizationName)) return SMALL;
    	if ("ALL".equals(dmlCapitalizationName)) return ALL;
    	throw new IllegalArgumentException("Unknown DmlCapitalization name.");
    }
    
    /// <summary>
    /// Returns array of DmlCapitalization values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		SMALL,
    		ALL,
    	};
    }

    public static final int length = 3;
}
