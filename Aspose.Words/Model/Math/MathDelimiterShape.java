// Copyright (c) 2001-2026 Aspose Pty Ltd. All Rights Reserved.
// 01/03/2011 by Denis Darkin

package com.aspose.words;

// ********* THIS FILE IS AUTO PORTED *********


/// <summary>
/// Defines shape of delimiters.
/// </summary>
/*enum*/ final class MathDelimiterShape
{
    private MathDelimiterShape(){}
    
    /// <summary>
    /// Delimiters centered around the math axis of the mathematical text and made to fit the entire height 
    /// of their contents.
    /// </summary>
    public static final int CENTERED = 0;
    
    /// <summary>
    /// Delimiters height can be altered to exactly match their contents.
    /// </summary>
    public static final int MATCH = 1;
  
    /// <summary>
    /// Default value is <see cref="Centered"/>.
    /// </summary>
    public static final int DEFAULT = CENTERED;
    
    /// <summary>
    /// Returns JAVA_STYLE string representation of integer MathDelimiterShape value.
    /// </summary>
    public static String getName(/*MathDelimiterShape*/int mathDelimiterShape)
    {
    	switch (mathDelimiterShape)
    	{
    		case CENTERED:
    		/*case DEFAULT:*/
    			return "CENTERED" + " | " + "DEFAULT";
    		case MATCH: return "MATCH";
    		default: return "Unknown MathDelimiterShape value.";
    	}
    }
    
    /// <summary>
    /// Returns DotNetStyle string representation of integer MathDelimiterShape value.
    /// </summary>
    public static String toString(/*MathDelimiterShape*/int mathDelimiterShape)
    {
    	switch (mathDelimiterShape)
    	{
    		case CENTERED:
    		/*case DEFAULT:*/
    			return "Centered" + " | " + "Default";
    		case MATCH: return "Match";
    		default: return "Unknown MathDelimiterShape value.";
    	}
    }
    
    /// <summary>
    /// Returns integer representation by MathDelimiterShape name.
    /// </summary>
    public static /*MathDelimiterShape*/int fromName(String mathDelimiterShapeName)
    {
    	if ("CENTERED".equals(mathDelimiterShapeName)) return CENTERED;
    	if ("MATCH".equals(mathDelimiterShapeName)) return MATCH;
    	if ("DEFAULT".equals(mathDelimiterShapeName)) return DEFAULT;
    	throw new IllegalArgumentException("Unknown MathDelimiterShape name.");
    }
    
    /// <summary>
    /// Returns array of MathDelimiterShape values.
    /// </summary>
    public static int[] getValues()
    {
    	return new int[]
    	{
    		CENTERED,
    		MATCH,
    		DEFAULT,
    	};
    }

    public static final int length = 3;
}
