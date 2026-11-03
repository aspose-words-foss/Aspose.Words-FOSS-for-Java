// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/10/2014 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// 21.4.7.22 ST_ContinueDirection (Continue Direction)
/// </summary>
/*enum*/ final class DmlContinueDirection
{
    private DmlContinueDirection(){}
    
    public static final int REVERSE_DIRECTION = 0;
    public static final int SAME_DIRECTION = 1;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer DmlContinueDirection value.
    /// </summary>
    public static String getName(/*DmlContinueDirection*/int dmlContinueDirection)
    {
    	switch (dmlContinueDirection)
    	{
    		case REVERSE_DIRECTION: return "REVERSE_DIRECTION";
    		case SAME_DIRECTION: return "SAME_DIRECTION";
    		default: return "Unknown DmlContinueDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer DmlContinueDirection value.
    /// </summary>
    public static String toString(/*DmlContinueDirection*/int dmlContinueDirection)
    {
    	switch (dmlContinueDirection)
    	{
    		case REVERSE_DIRECTION: return "ReverseDirection";
    		case SAME_DIRECTION: return "SameDirection";
    		default: return "Unknown DmlContinueDirection value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by DmlContinueDirection name.
    /// </summary>
    public static /*DmlContinueDirection*/int fromName(String dmlContinueDirectionName)
    {
    	if ("REVERSE_DIRECTION".equals(dmlContinueDirectionName)) return REVERSE_DIRECTION;
    	if ("SAME_DIRECTION".equals(dmlContinueDirectionName)) return SAME_DIRECTION;
    	throw new IllegalArgumentException("Unknown DmlContinueDirection name.");
    }
    
    /// <summary>
    /// Returns array of DmlContinueDirection values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		REVERSE_DIRECTION,
    		SAME_DIRECTION,
    	};
    }

    public static final int length = 2;
}

