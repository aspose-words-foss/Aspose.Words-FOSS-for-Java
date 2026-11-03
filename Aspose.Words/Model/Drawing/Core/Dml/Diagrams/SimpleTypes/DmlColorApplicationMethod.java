// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2013 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.16 ST_ClrAppMethod (Color Application Method Type)
/// </summary>
/*enum*/ final class DmlColorApplicationMethod
{
    private DmlColorApplicationMethod(){}
    
    public static final int CYCLE = 0;
    public static final int REPEAT = 1;
    public static final int SPAN = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlColorApplicationMethod value.
    /// </summary>
    public static String getName(/*DmlColorApplicationMethod*/int dmlColorApplicationMethod)
    {
    	switch (dmlColorApplicationMethod)
    	{
    		case CYCLE: return "CYCLE";
    		case REPEAT: return "REPEAT";
    		case SPAN: return "SPAN";
    		default: return "Unknown DmlColorApplicationMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlColorApplicationMethod value.
    /// </summary>
    public static String toString(/*DmlColorApplicationMethod*/int dmlColorApplicationMethod)
    {
    	switch (dmlColorApplicationMethod)
    	{
    		case CYCLE: return "Cycle";
    		case REPEAT: return "Repeat";
    		case SPAN: return "Span";
    		default: return "Unknown DmlColorApplicationMethod value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlColorApplicationMethod name.
    /// </summary>
    public static /*DmlColorApplicationMethod*/int fromName(String dmlColorApplicationMethodName)
    {
    	if ("CYCLE".equals(dmlColorApplicationMethodName)) return CYCLE;
    	if ("REPEAT".equals(dmlColorApplicationMethodName)) return REPEAT;
    	if ("SPAN".equals(dmlColorApplicationMethodName)) return SPAN;
    	throw new IllegalArgumentException("Unknown DmlColorApplicationMethod name.");
    }
    
    /// <summary>
    /// Returns array of DmlColorApplicationMethod values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CYCLE,
    		REPEAT,
    		SPAN,
    	};
    }

    public static final int length = 3;
}

