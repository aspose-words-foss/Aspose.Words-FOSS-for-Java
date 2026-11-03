// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 11/14/2013 by Alexey Noskov

package com.aspose;

// ********* THIS FILE IS AUTO PORTED *********


public /*enum*/ final class ComparableFileType
{
    private ComparableFileType(){}
    
    public static final int TEXT = 0;
    public static final int IMAGE = 1;
    public static final int ZIP = 2;
    public static final int MOBI = 3;
    public static final int AZW_3 = 4;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ComparableFileType value.
    /// </summary>
    public static String getName(/*ComparableFileType*/int comparableFileType)
    {
    	switch (comparableFileType)
    	{
    		case TEXT: return "TEXT";
    		case IMAGE: return "IMAGE";
    		case ZIP: return "ZIP";
    		case MOBI: return "MOBI";
    		case AZW_3: return "AZW_3";
    		default: return "Unknown ComparableFileType value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ComparableFileType value.
    /// </summary>
    public static String toString(/*ComparableFileType*/int comparableFileType)
    {
    	switch (comparableFileType)
    	{
    		case TEXT: return "Text";
    		case IMAGE: return "Image";
    		case ZIP: return "Zip";
    		case MOBI: return "Mobi";
    		case AZW_3: return "Azw3";
    		default: return "Unknown ComparableFileType value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by ComparableFileType name.
    /// </summary>
    public static /*ComparableFileType*/int fromName(String comparableFileTypeName)
    {
    	if ("TEXT".equals(comparableFileTypeName)) return TEXT;
    	if ("IMAGE".equals(comparableFileTypeName)) return IMAGE;
    	if ("ZIP".equals(comparableFileTypeName)) return ZIP;
    	if ("MOBI".equals(comparableFileTypeName)) return MOBI;
    	if ("AZW_3".equals(comparableFileTypeName)) return AZW_3;
    	throw new IllegalArgumentException("Unknown ComparableFileType name.");
    }
    
    /// <summary>
    /// Returns array of ComparableFileType values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TEXT,
    		IMAGE,
    		ZIP,
    		MOBI,
    		AZW_3,
    	};
    }

    public static final int length = 5;
}

