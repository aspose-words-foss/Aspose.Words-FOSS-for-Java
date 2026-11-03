// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/01/2013 by Alexey Butalov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********



public /*enum*/ final class GoldTestFileType
{
    private GoldTestFileType(){}
    
    public static final int UNKNOWN = 0;
    public static final int TEXT = 1;
    public static final int ZIP = 2;
    public static final int IMAGE = 3;
    public static final int MOBI = 4;
    public static final int AZW_3 = 5;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer GoldTestFileType value.
    /// </summary>
    public static String getName(/*GoldTestFileType*/int goldTestFileType)
    {
    	switch (goldTestFileType)
    	{
    		case UNKNOWN: return "UNKNOWN";
    		case TEXT: return "TEXT";
    		case ZIP: return "ZIP";
    		case IMAGE: return "IMAGE";
    		case MOBI: return "MOBI";
    		case AZW_3: return "AZW_3";
    		default: return "Unknown GoldTestFileType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer GoldTestFileType value.
    /// </summary>
    public static String toString(/*GoldTestFileType*/int goldTestFileType)
    {
    	switch (goldTestFileType)
    	{
    		case UNKNOWN: return "Unknown";
    		case TEXT: return "Text";
    		case ZIP: return "Zip";
    		case IMAGE: return "Image";
    		case MOBI: return "Mobi";
    		case AZW_3: return "Azw3";
    		default: return "Unknown GoldTestFileType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by GoldTestFileType name.
    /// </summary>
    public static /*GoldTestFileType*/int fromName(String goldTestFileTypeName)
    {
    	if ("UNKNOWN".equals(goldTestFileTypeName)) return UNKNOWN;
    	if ("TEXT".equals(goldTestFileTypeName)) return TEXT;
    	if ("ZIP".equals(goldTestFileTypeName)) return ZIP;
    	if ("IMAGE".equals(goldTestFileTypeName)) return IMAGE;
    	if ("MOBI".equals(goldTestFileTypeName)) return MOBI;
    	if ("AZW_3".equals(goldTestFileTypeName)) return AZW_3;
    	throw new IllegalArgumentException("Unknown GoldTestFileType name.");
    }
    
    /// <summary>
    /// Returns array of GoldTestFileType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		UNKNOWN,
    		TEXT,
    		ZIP,
    		IMAGE,
    		MOBI,
    		AZW_3,
    	};
    }

    public static final int length = 6;
}

