// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 23/08/2011 by Alexey Morozov

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Specifies whether the two sub-lines within one line are enclosed within a pair of brackets when displayed, 
/// and the type of brackets that are displayed. 
/// </summary>
/// <remarks>See [MS-DOC] 2.9.332 UFEL.</remarks>
/*enum*/ final class CombineBrackets
{
    private CombineBrackets(){}
    
    public static final int NONE = 0x00;

    /// <summary>
    /// Specifies round - "()" - brackets.
    /// </summary>
    public static final int ROUND = 0x01;

    /// <summary>
    /// Specifies round - "[]" - brackets.
    /// </summary>
    public static final int SQUARE = 0x02;

    /// <summary>
    /// Specifies round - "&lt;&gt;" - brackets.
    /// </summary>
    public static final int ANGLE = 0x03;

    /// <summary>
    /// Specifies round - "{}" - brackets.
    /// </summary>
    public static final int CURLY = 0x04;

    /// <summary>
    /// Same as <see cref="None"/>.
    /// </summary>
    public static final int DEFAULT = NONE;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer CombineBrackets value.
    /// </summary>
    public static String getName(/*CombineBrackets*/int combineBrackets)
    {
    	switch (combineBrackets)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "NONE" + " | " + "DEFAULT";
    		case ROUND: return "ROUND";
    		case SQUARE: return "SQUARE";
    		case ANGLE: return "ANGLE";
    		case CURLY: return "CURLY";
    		default: return "Unknown CombineBrackets value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer CombineBrackets value.
    /// </summary>
    public static String toString(/*CombineBrackets*/int combineBrackets)
    {
    	switch (combineBrackets)
    	{
    		case NONE:
    		/*case DEFAULT:*/
    			return "None" + " | " + "Default";
    		case ROUND: return "Round";
    		case SQUARE: return "Square";
    		case ANGLE: return "Angle";
    		case CURLY: return "Curly";
    		default: return "Unknown CombineBrackets value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by CombineBrackets name.
    /// </summary>
    public static /*CombineBrackets*/int fromName(String combineBracketsName)
    {
    	if ("NONE".equals(combineBracketsName)) return NONE;
    	if ("ROUND".equals(combineBracketsName)) return ROUND;
    	if ("SQUARE".equals(combineBracketsName)) return SQUARE;
    	if ("ANGLE".equals(combineBracketsName)) return ANGLE;
    	if ("CURLY".equals(combineBracketsName)) return CURLY;
    	if ("DEFAULT".equals(combineBracketsName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown CombineBrackets name.");
    }
    
    /// <summary>
    /// Returns array of CombineBrackets values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		NONE,
    		ROUND,
    		SQUARE,
    		ANGLE,
    		CURLY,
    		DEFAULT,
    	};
    }

    public static final int length = 6;
}

