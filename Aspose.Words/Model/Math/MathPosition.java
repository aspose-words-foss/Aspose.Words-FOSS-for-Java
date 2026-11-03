// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the position of the parent Office Math elements.
/// </summary>
/*enum*/ final class MathPosition
{
    private MathPosition(){}
    
    public static final int TOP = 0;
    
    public static final int BOTTOM = 1;
    
    public static final int DEFAULT = BOTTOM;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathPosition value.
    /// </summary>
    public static String getName(/*MathPosition*/int mathPosition)
    {
    	switch (mathPosition)
    	{
    		case TOP: return "TOP";
    		case BOTTOM:
    		/*case DEFAULT:*/
    			return "BOTTOM" + " | " + "DEFAULT";
    		default: return "Unknown MathPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathPosition value.
    /// </summary>
    public static String toString(/*MathPosition*/int mathPosition)
    {
    	switch (mathPosition)
    	{
    		case TOP: return "Top";
    		case BOTTOM:
    		/*case DEFAULT:*/
    			return "Bottom" + " | " + "Default";
    		default: return "Unknown MathPosition value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathPosition name.
    /// </summary>
    public static /*MathPosition*/int fromName(String mathPositionName)
    {
    	if ("TOP".equals(mathPositionName)) return TOP;
    	if ("BOTTOM".equals(mathPositionName)) return BOTTOM;
    	if ("DEFAULT".equals(mathPositionName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathPosition name.");
    }
    
    /// <summary>
    /// Returns array of MathPosition values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		BOTTOM,
    		DEFAULT,
    	};
    }

    public static final int length = 3;
}
