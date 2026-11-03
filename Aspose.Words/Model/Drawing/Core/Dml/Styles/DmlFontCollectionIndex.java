// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 13/02/2011 by Alexey Titov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Represents one of the fonts associated with the style.
/// </summary>
/*enum*/ final class DmlFontCollectionIndex
{
    private DmlFontCollectionIndex(){}
    
    /// <summary>
    /// The major font of the style's font scheme.
    /// </summary>
    public static final int MAJOR = 0;
    /// <summary>
    /// The minor font of the style's font scheme.
    /// </summary>
    public static final int MINOR = 1;
    /// <summary>
    /// No font reference.
    /// </summary>
    public static final int NONE = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlFontCollectionIndex value.
    /// </summary>
    public static String getName(/*DmlFontCollectionIndex*/int dmlFontCollectionIndex)
    {
    	switch (dmlFontCollectionIndex)
    	{
    		case MAJOR: return "MAJOR";
    		case MINOR: return "MINOR";
    		case NONE: return "NONE";
    		default: return "Unknown DmlFontCollectionIndex value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlFontCollectionIndex value.
    /// </summary>
    public static String toString(/*DmlFontCollectionIndex*/int dmlFontCollectionIndex)
    {
    	switch (dmlFontCollectionIndex)
    	{
    		case MAJOR: return "Major";
    		case MINOR: return "Minor";
    		case NONE: return "None";
    		default: return "Unknown DmlFontCollectionIndex value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlFontCollectionIndex name.
    /// </summary>
    public static /*DmlFontCollectionIndex*/int fromName(String dmlFontCollectionIndexName)
    {
    	if ("MAJOR".equals(dmlFontCollectionIndexName)) return MAJOR;
    	if ("MINOR".equals(dmlFontCollectionIndexName)) return MINOR;
    	if ("NONE".equals(dmlFontCollectionIndexName)) return NONE;
    	throw new IllegalArgumentException("Unknown DmlFontCollectionIndex name.");
    }
    
    /// <summary>
    /// Returns array of DmlFontCollectionIndex values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		MAJOR,
    		MINOR,
    		NONE,
    	};
    }

    public static final int length = 3;
}

