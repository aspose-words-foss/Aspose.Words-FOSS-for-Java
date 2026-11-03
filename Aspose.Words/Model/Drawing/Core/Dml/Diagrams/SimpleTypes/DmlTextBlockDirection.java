// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/05/2015 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.61 ST_TextBlockDirection (Text Block Direction)
/// </summary>
/*enum*/ final class DmlTextBlockDirection
{
    private DmlTextBlockDirection(){}
    
    public static final int HORIZONTAL = 0;
    public static final int VERTICAL = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlTextBlockDirection value.
    /// </summary>
    public static String getName(/*DmlTextBlockDirection*/int dmlTextBlockDirection)
    {
    	switch (dmlTextBlockDirection)
    	{
    		case HORIZONTAL: return "HORIZONTAL";
    		case VERTICAL: return "VERTICAL";
    		default: return "Unknown DmlTextBlockDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlTextBlockDirection value.
    /// </summary>
    public static String toString(/*DmlTextBlockDirection*/int dmlTextBlockDirection)
    {
    	switch (dmlTextBlockDirection)
    	{
    		case HORIZONTAL: return "Horizontal";
    		case VERTICAL: return "Vertical";
    		default: return "Unknown DmlTextBlockDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlTextBlockDirection name.
    /// </summary>
    public static /*DmlTextBlockDirection*/int fromName(String dmlTextBlockDirectionName)
    {
    	if ("HORIZONTAL".equals(dmlTextBlockDirectionName)) return HORIZONTAL;
    	if ("VERTICAL".equals(dmlTextBlockDirectionName)) return VERTICAL;
    	throw new IllegalArgumentException("Unknown DmlTextBlockDirection name.");
    }
    
    /// <summary>
    /// Returns array of DmlTextBlockDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		HORIZONTAL,
    		VERTICAL,
    	};
    }

    public static final int length = 2;
}

