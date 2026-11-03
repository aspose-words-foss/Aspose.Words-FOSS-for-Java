// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 16/11/2010 by Dmitry Vorobyev

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies a field's area - field code or field result.
/// </summary>
/*enum*/ final class FieldArea
{
    private FieldArea(){}
    
    public static final int CODE = 0;
    public static final int RESULT = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer FieldArea value.
    /// </summary>
    public static String getName(/*FieldArea*/int fieldArea)
    {
    	switch (fieldArea)
    	{
    		case CODE: return "CODE";
    		case RESULT: return "RESULT";
    		default: return "Unknown FieldArea value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer FieldArea value.
    /// </summary>
    public static String toString(/*FieldArea*/int fieldArea)
    {
    	switch (fieldArea)
    	{
    		case CODE: return "Code";
    		case RESULT: return "Result";
    		default: return "Unknown FieldArea value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by FieldArea name.
    /// </summary>
    public static /*FieldArea*/int fromName(String fieldAreaName)
    {
    	if ("CODE".equals(fieldAreaName)) return CODE;
    	if ("RESULT".equals(fieldAreaName)) return RESULT;
    	throw new IllegalArgumentException("Unknown FieldArea name.");
    }
    
    /// <summary>
    /// Returns array of FieldArea values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CODE,
    		RESULT,
    	};
    }

    public static final int length = 2;
}

