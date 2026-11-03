// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 26/07/2006 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Line join style.
/// </summary>
/// <seealso cref="Stroke.JoinStyle"/>
public /*enum*/ final class JoinStyle
{
    private JoinStyle(){}
    
    /// <summary>
    /// Join edges by a straight line.
    /// </summary>
    public static final int BEVEL = 0;     
    /// <summary>
    /// Extend edges until they join.
    /// </summary>
    public static final int MITER = 1;     
    /// <summary>
    /// Draw an arc between the two edges.
    /// </summary>
    public static final int ROUND = 2;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer JoinStyle value.
    /// </summary>
    public static String getName(/*JoinStyle*/int joinStyle)
    {
    	switch (joinStyle)
    	{
    		case BEVEL: return "BEVEL";
    		case MITER: return "MITER";
    		case ROUND: return "ROUND";
    		default: return "Unknown JoinStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer JoinStyle value.
    /// </summary>
    public static String toString(/*JoinStyle*/int joinStyle)
    {
    	switch (joinStyle)
    	{
    		case BEVEL: return "Bevel";
    		case MITER: return "Miter";
    		case ROUND: return "Round";
    		default: return "Unknown JoinStyle value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by JoinStyle name.
    /// </summary>
    public static /*JoinStyle*/int fromName(String joinStyleName)
    {
    	if ("BEVEL".equals(joinStyleName)) return BEVEL;
    	if ("MITER".equals(joinStyleName)) return MITER;
    	if ("ROUND".equals(joinStyleName)) return ROUND;
    	throw new IllegalArgumentException("Unknown JoinStyle name.");
    }
    
    /// <summary>
    /// Returns array of JoinStyle values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		BEVEL,
    		MITER,
    		ROUND,
    	};
    }

    public static final int length = 3;
}

