// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 28/02/2011 by Denis Darkin
package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies the vertical layout of the groupChr object. Applies only to <see cref="MathObjectGroupCharacter"/>.
/// While <see cref="MathPosition"/> specifies the position of the grouping character,
/// this type specifies the alignment of the object with respect to the baseline.
/// </summary>
/*enum*/ final class MathVerticalJustification
{
    private MathVerticalJustification(){}
    
    public static final int TOP = 0;

    public static final int BOTTOM = 1;

    public static final int DEFAULT = TOP;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathVerticalJustification value.
    /// </summary>
    public static String getName(/*MathVerticalJustification*/int mathVerticalJustification)
    {
    	switch (mathVerticalJustification)
    	{
    		case TOP:
    		/*case DEFAULT:*/
    			return "TOP" + " | " + "DEFAULT";
    		case BOTTOM: return "BOTTOM";
    		default: return "Unknown MathVerticalJustification value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathVerticalJustification value.
    /// </summary>
    public static String toString(/*MathVerticalJustification*/int mathVerticalJustification)
    {
    	switch (mathVerticalJustification)
    	{
    		case TOP:
    		/*case DEFAULT:*/
    			return "Top" + " | " + "Default";
    		case BOTTOM: return "Bottom";
    		default: return "Unknown MathVerticalJustification value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathVerticalJustification name.
    /// </summary>
    public static /*MathVerticalJustification*/int fromName(String mathVerticalJustificationName)
    {
    	if ("TOP".equals(mathVerticalJustificationName)) return TOP;
    	if ("BOTTOM".equals(mathVerticalJustificationName)) return BOTTOM;
    	if ("DEFAULT".equals(mathVerticalJustificationName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathVerticalJustification name.");
    }
    
    /// <summary>
    /// Returns array of MathVerticalJustification values.
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
