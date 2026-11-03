// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 22/04/2011 by Roman Korchagin


package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies line cap style.
/// </summary>
/// <seealso cref="Stroke.EndCap"/>
public /*enum*/ final class EndCap
{
    private EndCap(){}
    
    /// <summary>
    /// Rounded ends.
    /// </summary>
    public static final int ROUND = 0;
    /// <summary>
    /// Square protrudes by half line width.
    /// </summary>
    public static final int SQUARE = 1;
    /// <summary>
    /// Line ends at end point.
    /// </summary>
    public static final int FLAT = 2;
    /// <summary>
    /// Default value is <see cref="Flat"/>.
    /// </summary>
    public static final int DEFAULT = FLAT;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer EndCap value.
    /// </summary>
    public static String getName(/*EndCap*/int endCap)
    {
    	switch (endCap)
    	{
    		case ROUND: return "ROUND";
    		case SQUARE: return "SQUARE";
    		case FLAT:
    		/*case DEFAULT:*/
    			return "FLAT" + " | " + "DEFAULT";
    		default: return "Unknown EndCap value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer EndCap value.
    /// </summary>
    public static String toString(/*EndCap*/int endCap)
    {
    	switch (endCap)
    	{
    		case ROUND: return "Round";
    		case SQUARE: return "Square";
    		case FLAT:
    		/*case DEFAULT:*/
    			return "Flat" + " | " + "Default";
    		default: return "Unknown EndCap value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by EndCap name.
    /// </summary>
    public static /*EndCap*/int fromName(String endCapName)
    {
    	if ("ROUND".equals(endCapName)) return ROUND;
    	if ("SQUARE".equals(endCapName)) return SQUARE;
    	if ("FLAT".equals(endCapName)) return FLAT;
    	if ("DEFAULT".equals(endCapName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown EndCap name.");
    }
    
    /// <summary>
    /// Returns array of EndCap values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		ROUND,
    		SQUARE,
    		FLAT,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}

