// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 15/06/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether a field should have a field separator.
/// </summary>
/*enum*/ final class FieldSeparatorPresence
{
    private FieldSeparatorPresence(){}
    
    /// <summary>
    /// The field never has a separator. Attempting to set its result throws an exception.
    /// </summary>
    public static final int NEVER = 0;
    /// <summary>
    /// The field may or may not have a separator. Attempting to set its result inserts a separator if needed,
    /// but the format readers do not insert it if it is missing.
    /// </summary>
    public static final int SOMETIMES = 1;
    /// <summary>
    /// The field always has a spearator. Attempting to set its result inserts a separator if needed,
    /// and the format readers insert it if it is missing.
    /// </summary>
    public static final int ALWAYS = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldSeparatorPresence value.
    /// </summary>
    public static String getName(/*FieldSeparatorPresence*/int fieldSeparatorPresence)
    {
    	switch (fieldSeparatorPresence)
    	{
    		case NEVER: return "NEVER";
    		case SOMETIMES: return "SOMETIMES";
    		case ALWAYS: return "ALWAYS";
    		default: return "Unknown FieldSeparatorPresence value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldSeparatorPresence value.
    /// </summary>
    public static String toString(/*FieldSeparatorPresence*/int fieldSeparatorPresence)
    {
    	switch (fieldSeparatorPresence)
    	{
    		case NEVER: return "Never";
    		case SOMETIMES: return "Sometimes";
    		case ALWAYS: return "Always";
    		default: return "Unknown FieldSeparatorPresence value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldSeparatorPresence name.
    /// </summary>
    public static /*FieldSeparatorPresence*/int fromName(String fieldSeparatorPresenceName)
    {
    	if ("NEVER".equals(fieldSeparatorPresenceName)) return NEVER;
    	if ("SOMETIMES".equals(fieldSeparatorPresenceName)) return SOMETIMES;
    	if ("ALWAYS".equals(fieldSeparatorPresenceName)) return ALWAYS;
    	throw new IllegalArgumentException("Unknown FieldSeparatorPresence name.");
    }
    
    /// <summary>
    /// Returns array of FieldSeparatorPresence values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NEVER,
    		SOMETIMES,
    		ALWAYS,
    	};
    }

    public static final int length = 3;
}

