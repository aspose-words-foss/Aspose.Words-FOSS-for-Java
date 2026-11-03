// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines the vertical justification of parent element with respect to surrounding text.
/// Applicable to <see cref="MathObjectArray"/> and <see cref="MathObjectMatrix"/>.
/// </summary>
/*enum*/ final class MathBaseJustification
{
    private MathBaseJustification(){}
    
    public static final int TOP = 0;
    
    public static final int BOTTOM = 1;
    
    public static final int CENTER = 2;
    
    public static final int DEFAULT = CENTER;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathBaseJustification value.
    /// </summary>
    public static String getName(/*MathBaseJustification*/int mathBaseJustification)
    {
    	switch (mathBaseJustification)
    	{
    		case TOP: return "TOP";
    		case BOTTOM: return "BOTTOM";
    		case CENTER:
    		/*case DEFAULT:*/
    			return "CENTER" + " | " + "DEFAULT";
    		default: return "Unknown MathBaseJustification value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathBaseJustification value.
    /// </summary>
    public static String toString(/*MathBaseJustification*/int mathBaseJustification)
    {
    	switch (mathBaseJustification)
    	{
    		case TOP: return "Top";
    		case BOTTOM: return "Bottom";
    		case CENTER:
    		/*case DEFAULT:*/
    			return "Center" + " | " + "Default";
    		default: return "Unknown MathBaseJustification value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathBaseJustification name.
    /// </summary>
    public static /*MathBaseJustification*/int fromName(String mathBaseJustificationName)
    {
    	if ("TOP".equals(mathBaseJustificationName)) return TOP;
    	if ("BOTTOM".equals(mathBaseJustificationName)) return BOTTOM;
    	if ("CENTER".equals(mathBaseJustificationName)) return CENTER;
    	if ("DEFAULT".equals(mathBaseJustificationName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathBaseJustification name.");
    }
    
    /// <summary>
    /// Returns array of MathBaseJustification values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		TOP,
    		BOTTOM,
    		CENTER,
    		DEFAULT,
    	};
    }

    public static final int length = 4;
}
