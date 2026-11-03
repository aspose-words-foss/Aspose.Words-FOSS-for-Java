// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 02/12/2023 by Konstantin Kornilov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/*enum*/ final class ProportionalThumb /*: ushort*/
{
    private ProportionalThumb(){}
    
    public static final /*ushort*/short FIXED = (short) 0;
    public static final /*ushort*/short PROPORTIONAL = (short) 0xFFFF;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer ProportionalThumb value.
    /// </summary>
    public static String getName(/*ProportionalThumb*//*ushort*/short proportionalThumb)
    {
    	if (FIXED == proportionalThumb) return "FIXED";
    	if (PROPORTIONAL == proportionalThumb) return "PROPORTIONAL";
    	return "Unknown ProportionalThumb value.";
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer ProportionalThumb value.
    /// </summary>
    public static String toString(/*ProportionalThumb*//*ushort*/short proportionalThumb)
    {
    	if (FIXED == proportionalThumb) return "Fixed";
    	if (PROPORTIONAL == proportionalThumb) return "Proportional";
    	return "Unknown ProportionalThumb value.";
    }
    
    /// <summary>
    /// Returns integer representation by ProportionalThumb name.
    /// </summary>
    public static /*ProportionalThumb*//*ushort*/short fromName(String proportionalThumbName)
    {
    	if ("FIXED".equals(proportionalThumbName)) return FIXED;
    	if ("PROPORTIONAL".equals(proportionalThumbName)) return PROPORTIONAL;
    	throw new IllegalArgumentException("Unknown ProportionalThumb name.");
    }
    
    /// <summary>
    /// Returns array of ProportionalThumb values.
    /// </summary>
    public static /*ushort*/short[] getValues()
    {
    	return new /*ushort*/short[]
    	{
    		FIXED,
    		PROPORTIONAL,
    	};
    }

    public static final int length = 2;
}

